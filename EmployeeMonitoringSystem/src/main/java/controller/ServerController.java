package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import service.IService;

import java.io.IOException;

public class ServerController {
    private IService service;

    public void setService(IService service){
        this.service = service;
    }

    @FXML
    public void startNewClient() throws IOException {

        Stage stage = new Stage();

        stage.setTitle("Client");

        FXMLLoader loader = new FXMLLoader();
        Pane pane = loader.load(
                getClass().getResourceAsStream("../views/systemPage.fxml")
        );

        SystemController systemController = loader.getController();

        systemController.setService(service);

        loader.setController(systemController);

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }
}
