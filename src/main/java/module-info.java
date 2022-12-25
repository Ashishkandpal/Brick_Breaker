module com.example.ashish_kandpal_brick_breaker {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ashish_kandpal_brick_breaker to javafx.fxml;
    exports com.example.ashish_kandpal_brick_breaker;
}