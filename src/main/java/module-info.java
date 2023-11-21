module com.projetos.skymaster.skymastergerentesobras {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens com.projetos.skymaster.skymastergerentesobras to javafx.fxml;
    exports com.projetos.skymaster.skymastergerentesobras;
}