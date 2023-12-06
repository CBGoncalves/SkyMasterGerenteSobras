module com.projetos.skymaster.skymastergerentesobras {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires itextpdf;

    opens com.projetos.skymaster.skymastergerentesobras to javafx.fxml;
    exports com.projetos.skymaster.skymastergerentesobras;
    exports com.projetos.skymaster.skymastergerentesobras.controllers;
    opens com.projetos.skymaster.skymastergerentesobras.controllers to javafx.fxml;
    opens com.projetos.skymaster.skymastergerentesobras.models;
    exports com.projetos.skymaster.skymastergerentesobras.controllers.registro;
    opens com.projetos.skymaster.skymastergerentesobras.controllers.registro to javafx.fxml;
    exports com.projetos.skymaster.skymastergerentesobras.controllers.item;
    opens com.projetos.skymaster.skymastergerentesobras.controllers.item to javafx.fxml;
    exports com.projetos.skymaster.skymastergerentesobras.controllers.marca;
    opens com.projetos.skymaster.skymastergerentesobras.controllers.marca to javafx.fxml;
    exports com.projetos.skymaster.skymastergerentesobras.controllers.obra;
    opens com.projetos.skymaster.skymastergerentesobras.controllers.obra to javafx.fxml;
    exports com.projetos.skymaster.skymastergerentesobras.controllers.usuario;
    opens com.projetos.skymaster.skymastergerentesobras.controllers.usuario to javafx.fxml;
    exports com.projetos.skymaster.skymastergerentesobras.controllers.tipoItem;
    opens com.projetos.skymaster.skymastergerentesobras.controllers.tipoItem to javafx.fxml;

}