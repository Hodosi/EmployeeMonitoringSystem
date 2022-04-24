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
import model.Task;
import service.IService;

import java.util.List;


public class SystemController {
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
        this.currentBoss = null;
        bossAnchorPane.setVisible(false);
        loginAnchorPane.setVisible(true);
    }

    @FXML
    public void logoutActionEmployee(){
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
        loginAnchorPane.setVisible(false);
        bossAnchorPane.setVisible(true);
        initViewEmployeesBoss();
        initViewTasksBoss();
    }

    private void initViewEmployeesBoss(){
        employeeTableColumnBoss.setCellValueFactory(new PropertyValueFactory<>("username"));
        loginTimeTableColumnBoss.setCellValueFactory(new PropertyValueFactory<>("loginTime"));
        employeesTableViewBoss.setItems(employeeObservableListBoss);

        try {
            employeeObservableListBoss.setAll(service.findPresentEmployeesForBoss(this.currentBoss));

            employeesTableViewBoss.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if(newValue != null){
                    Employee employee = new Employee("", newValue.getUsername(), "");
                    employee.setId(newValue.getId());
                    List<TaskDTO> taskList = service.findAllTasksForEmployee(employee);
                    taskObservableListBoss.setAll(taskList);
                }
            });
        } catch (SystemException exception){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("System");
            alert.setHeaderText("Error");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    private void initViewTasksBoss(){
        taskTableColumnBoss.setCellValueFactory(new PropertyValueFactory<>("description"));
        tasksTableViewBoss.setItems(taskObservableListBoss);

//        try {
//            //todo
//        } catch (SystemException exception){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("System");
//            alert.setHeaderText("Error");
//            alert.setContentText(exception.getMessage());
//            alert.showAndWait();
//        }
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
    public void presentAction(){

    }

    @FXML
    public void finishTaskAction(){

    }

    private void initEmployeePage(){
        loginAnchorPane.setVisible(false);
        employeeAnchorPane.setVisible(true);
        //todo
    }

}
