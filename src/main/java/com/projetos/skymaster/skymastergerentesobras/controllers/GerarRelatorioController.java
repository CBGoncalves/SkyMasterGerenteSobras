package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.itextpdf.text.DocumentException;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

public class GerarRelatorioController {
    @FXML
    private AnchorPane root;
    @FXML
    private DatePicker campoDe;
    @FXML
    private DatePicker campoAte;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGerar;

    public void initialize() throws SQLException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/NavigationBar.fxml"));
            Parent menuBar = loader.load();
            root.getChildren().add(menuBar);

            NavigationBarController menuBarController = loader.getController();

            String tipoUsuario = TipoUsuarioNav.getInstance().getTipoUsuario();

            if ("Administrador".equals(tipoUsuario)) {
                menuBarController.exibirOpcoesAdmin();
            } else if ("Funcionário".equals(tipoUsuario)) {
                menuBarController.exibirOpcoesFuncionario();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleGerarButtonAction(ActionEvent event) throws IOException, DocumentException, ClassNotFoundException {
        Window owner = btnGerar.getScene().getWindow();

        String de = campoDe.getValue().toString();
        String ate = campoAte.getValue().toString();

        System.out.println(de + " | " + ate);

        if (de.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro ao Gerar Relatório!",
                    "Selecione as datas!");
        }
        if (ate.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro ao Gerar Relatório!",
                    "Selecione as datas!");
        }

        GerarPdfController gerarPdfController = new GerarPdfController();
        gerarPdfController.gerarPdfRelatorio(de, ate);

    }

    public void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageGerarRelatorio = (Stage) btnCancelar.getScene().getWindow();
        stageGerarRelatorio.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/TelaInicial.fxml"));
        Parent root = loader.load();

        Stage telaInicial = new Stage();
        telaInicial.setTitle("Tela Inicial");
        telaInicial.setScene(new Scene(root));
        telaInicial.setResizable(false);
        telaInicial.getIcons().add(icon);
        telaInicial.show();
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
