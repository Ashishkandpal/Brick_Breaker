package com.example.ashish_kandpal_brick_breaker;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Circle circle; // for making the ball

    @FXML
    private AnchorPane scene;

    double deltax = 0.5 + HelloApplication.x;
    double deltay = 0.5 + HelloApplication.y;

    int score = 0;

    private Label text1;

    ArrayList<Rectangle> allBricks = new ArrayList<>();

    private Rectangle slider;

//    private Button left, right


    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            //move paddle
            movePaddle();

//            //adding the score
//            addScore();

            //we need to move ball
            circle.setLayoutX(circle.getLayoutX() + deltax); //for moving in x axis
            circle.setLayoutY(circle.getLayoutY() + deltay); // for moving in y axis

            //we need to check for the collisions for each time
            //we have to check collisions with walls
            checkCollisionWithWalls();
            //we have to check collisions with bar
            checkCollisionWithSlider();
            //we have to check collisions with bricks
            checkCollisionWithBricks();

            //see if all bricks are empty then exit the game
        }
    }));

    public void checkCollisionWithBricks() {
        allBricks.removeIf(currentBrick -> checkCollisionWithSingleBrick(currentBrick));
    }

    public boolean checkCollisionWithSingleBrick(Rectangle brick) {
        if(circle.getBoundsInParent().intersects(brick.getBoundsInParent())) {
            Bounds bounds = brick.getBoundsInLocal();

            boolean bottomSide = circle.getLayoutY() + circle.getRadius() <= bounds.getMaxY();
            boolean topSide = circle.getLayoutY() - circle.getRadius() >= bounds.getMinY();
            boolean rightSide = circle.getLayoutX() - circle.getRadius() <= bounds.getMinX();
            boolean leftSide = circle.getLayoutX() + circle.getRadius() >= bounds.getMaxX();

            if(bottomSide || topSide) {
                deltay *= -1;
            }

            if(leftSide || rightSide) {
                deltax *= -1;
            }


            //score
            scene.getChildren().remove(text1);
            score += 5;
            addScore();
//            Label text1 = new Label();
//            text1.setAlignment(Pos.CENTER_LEFT);
//            text1.setText(Integer.toString(score=score+5));
////        text1.setPadding(new Insets(25, 30, 50, 25));
//            scene.getChildren().add(text1);
//
//            scene.getChildren().remove(text1);


            scene.getChildren().remove(brick);
            return true;
        }
        return false;
    }

    public void checkCollisionWithSlider() {
        if(circle.getBoundsInParent().intersects(slider.getBoundsInParent())) {
            deltay *= -1;
        }
    }

    public void checkCollisionWithWalls() {
        Bounds bounds = scene.getBoundsInLocal();
        //now we will check for each side
        boolean rightSide = circle.getLayoutX() + circle.getRadius() >= bounds.getMaxX();
        boolean leftSide = circle.getLayoutX() - circle.getRadius() <= bounds.getMinX();
        boolean topSide = circle.getLayoutY() - circle.getRadius() <= bounds.getMinY();
        boolean bottomSide = circle.getLayoutY() + circle.getRadius() >= bounds.getMaxY();

        //now check the collision
        if(rightSide || leftSide) {
            deltax *= -1;
        }

        if(topSide) {
            deltay *= -1;
        }

        if(bottomSide) {
            System.exit(99);
        }
    }

//    public Controller() {
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        //adding the score
//        addScore();
        scene.getChildren().remove(text1);
        //adding the slider
        addSlider();
        //now for creating the bricks
        createBricks();

//        addingButtons();//it is for left right button

    }

    public void movePaddle() {
        if(HelloApplication.left) {
            if(slider.getLayoutX() > -260) {
                slider.setLayoutX(slider.getLayoutX() - (1 + HelloApplication.x));
            }
        }
        if(HelloApplication.right) {
            if(slider.getLayoutX() < 260) {
                slider.setLayoutX(slider.getLayoutX() + (1 + HelloApplication.x));
            }
        }
    }

//    private void addingButtons() {
//        left = new Button("left");
//        right = new Button("right");
//
//        left.setLayoutY(350);
//        right.setLayoutY(350);
//
//        left.setLayoutX(20);
//        right.setLayoutX(540);
//
//        left.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                if(slider.getLayoutX() > -260)
//                    slider.setLayoutX(slider.getLayoutX() - 20);
//            }
//        });
//
//        right.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                if(slider.getLayoutX() < 260)
//                    slider.setLayoutX(slider.getLayoutX() + 20);
//            }
//        });
//
//        scene.getChildren().add(left);
//        scene.getChildren().add(right);
//
//    }

    private void addScore() {
        text1 = new Label();
        text1.setAlignment(Pos.CENTER_LEFT);
        text1.setText(Integer.toString(score));
//        text1.setPadding(new Insets(25, 30, 50, 25));
        scene.getChildren().add(text1);

    }

    private void addSlider() {
        slider = new Rectangle(250, 375, 60,15);
        slider.setFill(Color.BLACK);
        scene.getChildren().add(slider);
    }

    private void createBricks() {
        //we will create rectangles now by using nested loop
        int counter = 1;
        for(int i = 200; i > 0; i -= 50) {
            for(int j = 508; j >= 0; j -= 30) {
                if (counter % 2 == 1) {
                    Rectangle rect = new Rectangle(j, i, 40, 40);
                    //now for coloring the boxes
                    if (counter % 3 == 0) {
                        rect.setFill(Color.SKYBLUE);
                    } else if (counter % 3 == 1) {
                        rect.setFill(Color.NAVY);
                    } else {
                        rect.setFill(Color.LIGHTGRAY);
                    }
                    scene.getChildren().add(rect);
                    allBricks.add(rect);
                }
                counter++;
            }
        }
    }
}
