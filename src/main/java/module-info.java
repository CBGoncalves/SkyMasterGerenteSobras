module com.projetos.skymaster.skymastergerentesobras {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens com.projetos.skymaster.skymastergerentesobras to javafx.fxml;
    exports com.projetos.skymaster.skymastergerentesobras;
    exports com.projetos.skymaster.skymastergerentesobras.controllers;
    opens com.projetos.skymaster.skymastergerentesobras.controllers to javafx.fxml;
    opens com.projetos.skymaster.skymastergerentesobras.models;

}