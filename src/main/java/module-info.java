module com.example.directory {
    requires javafx.controls;
    requires javafx.fxml;

    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.directory to javafx.fxml;
    exports com.example.directory;
    exports com.example.directory.Controllers;
    exports com.example.directory.Controllers.Admin;
    exports com.example.directory.Controllers.client;
    exports com.example.directory.Models;
    exports com.example.directory.Views;
}