module com.example.myjavafxapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.opencsv;

    opens com.example.myjavafxapp to javafx.fxml;
    exports com.example.myjavafxapp;
}