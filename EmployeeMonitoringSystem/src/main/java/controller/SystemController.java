package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import model.Boss;
import model.Employee;
import model.Task;
import service.IService;

import java.awt.*;

public class SystemController {
    private IService service;
    private Boss currentBoss;
    private Employee currentEmployee;

    public void setService(IService service){
        this.service = service;
    }

    ObservableList<Employee> employeeObservableListBoss = FXCollections.observableArrayList();
    ObservableList<Task> taskObservableListBoss = FXCollections.observableArrayList();
    ObservableList<Task> taskObservableListEmployee = FXCollections.observableArrayList();

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

    }

    // logout section

    @FXML
    public void logoutActionBoss(){

    }

    @FXML
    public void logoutActionEmployee(){

    }

    // boss page

    @FXML
    private TableView<Employee> employeesTableViewBoss;

    @FXML
    private TableColumn<Employee, String> employeeTableColumnBoss;

    @FXML
    private TableColumn<Employee, String> loginTimeTableColumnBoss;

    @FXML
    private TableView<Task> tasksTableViewBoss;

    @FXML
    private TableColumn<Task, String> taskTableColumnBoss;

    @FXML
    private TextField taskNameTextFieldBoss;

    @FXML
    private TextField taskDescriptionTextFieldBoss;

    @FXML
    public void sendTaskAction(){

    }

    // employee page
    @FXML
    private TableView<Task> tasksTableViewEmployee;

    @FXML
    private TableColumn<Task, String> taskTableColumnEmployee;

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

}
