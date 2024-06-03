// module-info.java
module DocumentLibrary {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens application.view to javafx.graphics;
    exports application.view;
    exports application.controller;
    exports application.dao;
    exports application.model;
    exports application.service;
    exports application.state;
    exports application.util;
}

