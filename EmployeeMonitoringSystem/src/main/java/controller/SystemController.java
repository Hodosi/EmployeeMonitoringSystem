package controller;

import exception.SystemException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import model.Boss;
import model.DTO.EmployeeDTO;
import model.DTO.TaskDTO;
import model.Employee;
import service.IService;
import utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class SystemController implements Observer {
    private IService service;
    private Boss currentBoss;
    private Employee currentEmployee;

    public void setService(IService service){
        this.service = service;
    }

    ObservableList<EmployeeDTO> employeeObservableListBoss = FXCollections.observableArrayList();
    ObservableList<TaskDTO> taskObservableListBoss = FXCollections.observableArrayList();
    ObservableList<TaskDTO> taskObservableListEmployee = FXCollections.observableArrayList();

    @FXML
    private AnchorPane loginAnchorPane;

    @FXML
    private AnchorPane bossAnchorPane;

    @FXML
    private AnchorPane employeeAnchorPane;

    // login section

    @FXML
    private TextField usernameLoginTextField;

    @FXML
    private TextField passwordLoginTextField;

    @FXML
    public void loginAction(){
        String username = usernameLoginTextField.getText().trim();
        String password = passwordLoginTextField.getText().trim();

        try{
            service.login(username, password);

            Boss crtBoss = service.findBossByUsername(username);
            if(crtBoss != null){
                this.currentBoss = crtBoss;
                initBossPage();
            }
            else {
                Employee crtEmployee = service.findEmployeeByUsername(username);
                if(crtEmployee != null){
                    this.currentEmployee = crtEmployee;
                    initEmployeePage();
                }
                else {
                    throw new SystemException("login error no boss and no employee");
                }
            }

        } catch (SystemException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System");
            alert.setHeaderText("Error");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    // logout section

    @FXML
    public void logoutActionBoss(){
        service.removeObserver(this);
        this.currentBoss = null;
        bossAnchorPane.setVisible(false);
        loginAnchorPane.setVisible(true);
    }

    @FXML
    public void logoutActionEmployee(){
        service.removeObserver(this);
        service.logoutEmployee(this.currentEmployee);
        this.currentEmployee = null;
        employeeAnchorPane.setVisible(false);
        loginAnchorPane.setVisible(true);
    }

    // boss page

    @FXML
    private TableView<EmployeeDTO> employeesTableViewBoss;

    @FXML
    private TableColumn<EmployeeDTO, String> employeeTableColumnBoss;

    @FXML
    private TableColumn<EmployeeDTO, String> loginTimeTableColumnBoss;

    @FXML
    private TableView<TaskDTO> tasksTableViewBoss;

    @FXML
    private TableColumn<TaskDTO, String> taskTableColumnBoss;

    @FXML
    private TextField taskNameTextFieldBoss;

    @FXML
    private TextField taskDescriptionTextFieldBoss;

    @FXML
    public void sendTaskAction(){
        try {

            String taskName = taskNameTextFieldBoss.getText().trim();
            String taskDescription = taskDescriptionTextFieldBoss.getText().trim();
            EmployeeDTO employeeDTO = employeesTableViewBoss.getSelectionModel().getSelectedItem();

            if(taskName.equals("")){
                throw new SystemException("empty task name");
            }
            if(taskDescription.equals("")){
                throw new SystemException("empty description");
            }
            if(service.findTaskByName(taskName) != null){
                throw new SystemException("task already exists");
            }
            if(employeeDTO == null){
                throw new SystemException("no employee selected");
            }

            service.sendTask(taskName, taskDescription, employeeDTO.getUsername());

        } catch (SystemException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System");
            alert.setHeaderText("Error");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    private void initBossPage(){
        service.addObserver(this);
        loginAnchorPane.setVisible(false);
        bossAnchorPane.setVisible(true);
        try {
            initViewEmployeesBoss();
            initViewTasksBoss();
        } catch (SystemException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System");
            alert.setHeaderText("Error");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }

    }

    private void initViewEmployeesBoss(){
        employeeTableColumnBoss.setCellValueFactory(new PropertyValueFactory<>("username"));
        loginTimeTableColumnBoss.setCellValueFactory(new PropertyValueFactory<>("loginTime"));
        employeesTableViewBoss.setItems(employeeObservableListBoss);

        employeeObservableListBoss.setAll(service.findPresentEmployeesForBoss(this.currentBoss));
        taskObservableListBoss.setAll(new ArrayList<>());

        employeesTableViewBoss.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                Employee employee = new Employee("", newValue.getUsername(), "");
                employee.setId(newValue.getId());
                List<TaskDTO> taskList = service.findAllTasksForEmployee(employee);
                taskObservableListBoss.setAll(taskList);
            }
        });
    }

    private void initViewTasksBoss(){
        taskTableColumnBoss.setCellValueFactory(new PropertyValueFactory<>("description"));
        tasksTableViewBoss.setItems(taskObservableListBoss);
    }


    // employee page
    @FXML
    private TableView<TaskDTO> tasksTableViewEmployee;

    @FXML
    private TableColumn<TaskDTO, String> taskTableColumnEmployee;

    @FXML
    private ComboBox<Integer> hhComboBoxEmployee;

    @FXML
    private ComboBox<Integer> mmComboBoxEmployee;

    @FXML
    private Button presentButton;

    @FXML
    public void presentAction(){
        int hh = hhComboBoxEmployee.getValue();
        int mm = mmComboBoxEmployee.getValue();
        try {
            service.presentAction(hh, mm, this.currentEmployee);
            presentButton.setDisable(true);
            hhComboBoxEmployee.setDisable(true);
            mmComboBoxEmployee.setDisable(true);
        } catch (SystemException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System");
            alert.setHeaderText("Error");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    public void finishTaskAction(){
        try {
            TaskDTO taskDTO = tasksTableViewEmployee.getSelectionModel().getSelectedItem();
            if(taskDTO == null){
                throw new SystemException("no task selected");
            }
            service.finishTask(taskDTO.getId());
        } catch (SystemException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System");
            alert.setHeaderText("Error");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    private void initEmployeePage(){
        service.addObserver(this);
        loginAnchorPane.setVisible(false);
        employeeAnchorPane.setVisible(true);
        presentButton.setDisable(false);
        hhComboBoxEmployee.setDisable(false);
        mmComboBoxEmployee.setDisable(false);
        try {
            initViewTasksEmployee();
            initComboBoxes();
        } catch (SystemException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System");
            alert.setHeaderText("Error");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    private void initViewTasksEmployee() {
        taskTableColumnEmployee.setCellValueFactory(new PropertyValueFactory<>("description"));
        tasksTableViewEmployee.setItems(taskObservableListEmployee);

        taskObservableListEmployee.setAll(service.findAllTasksForEmployee(this.currentEmployee));
    }

    private void initComboBoxes(){
        List<Integer> hh = IntStream.range(0, 24).boxed().collect(Collectors.toList());
        List<Integer> mm = IntStream.range(0, 60).boxed().collect(Collectors.toList());

        hhComboBoxEmployee.getItems().setAll(hh);
        mmComboBoxEmployee.getItems().setAll(mm);

        hhComboBoxEmployee.getSelectionModel().selectFirst();
        mmComboBoxEmployee.getSelectionModel().selectFirst();
    }

    @Override
    public void update() {
        if(currentBoss != null){
            employeeObservableListBoss.setAll(service.findPresentEmployeesForBoss(this.currentBoss));
            taskObservableListBoss.setAll(new ArrayList<>());
        }
        else if(currentEmployee != null){
            taskObservableListEmployee.setAll(service.findAllTasksForEmployee(this.currentEmployee));
        }
    }
}
