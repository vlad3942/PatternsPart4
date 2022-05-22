package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MVCApplication extends Application {

    //Main Window Stage
    private Stage window;

    @Override
    public void start(Stage stage) throws IOException {
        /*CUSTOM*/
        window = stage;
        window.setOnCloseRequest(e -> {
            e.consume();
            exit();
        });
        /*CUSTOM*/
        FXMLLoader fxmlLoader = new FXMLLoader(MVCApplication.class.getResource("main-window-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 360);
        stage.setTitle("MVC Pattern example");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // -------------------------------- //

    private void exit() {
        final boolean answer = ConfirmBox.display("Выход", "Вы уверены что хотите закрыть программу?");
        if (answer) {
            window.close();
        }
    }
}