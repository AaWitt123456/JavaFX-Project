module com.example.awjavafxapplication {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.awjavafxapplication to javafx.fxml;
    exports com.example.awjavafxapplication;
    opens com.example.awjavafxapplication.Controller to javafx.fxml;
    exports com.example.awjavafxapplication.Controller;
    opens com.example.awjavafxapplication.Model to javafx.fxml;
    exports com.example.awjavafxapplication.Model;
}