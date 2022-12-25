package com.example.ashish_kandpal_brick_breaker;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    static Stage globalStage;
    static boolean left = false, right = false;

    //for controlling the speed of slider and ball
    static double x = 0.5;
    static double y = 0.5;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start_page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        globalStage = stage;
        stage.setTitle("Let's play!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onChooseButtonClick() throws IOException {//this is for choosing the difficulty
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("level.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        globalStage.setScene(scene);
        globalStage.setTitle(("Take your chance!!!"));
        globalStage.show();

    }

    @FXML
    public void onStartButtonClick(ActionEvent actionEvent) throws IOException {
        //for choosing the difficulty
        Node node = (Node) actionEvent.getSource() ;
        String data = (String) node.getUserData();
        if(data.equals("easy")){
            x += 0.5;
            y += 0.5;
        }
        if(data.equals("mid")) {
            x += 1;
            y += 1;
        }
        if(data.equals("hard")) {
            x += 1.5;
            y += 1.5;
        }
        //for choosing the difficulty

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        //for keyboard input
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A) {
                    left = true;
                    System.out.println("hello application p");
                }
                if (keyEvent.getCode() == KeyCode.D) {
                    right = true;
                    System.out.println("hello application p");
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.A) {
                    left = false;
                    System.out.println("hello application r");
                }
                if (keyEvent.getCode() == KeyCode.D) {
                    right = false;
                    System.out.println("hello application r");
                }
            }
        });
//        for keyboard input

        globalStage.setScene(scene);
        globalStage.setTitle(("Game Begins"));
        globalStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
