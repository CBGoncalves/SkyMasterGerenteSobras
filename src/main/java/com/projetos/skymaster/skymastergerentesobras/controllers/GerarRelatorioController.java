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
import javafx.scene.input.MouseEvent;
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
    private Button btnGerarEntradasSaidas;
    @FXML
    private Button btnGerarEstoque;
    @FXML
    private Button btnGerarReposicoes;
    @FXML
    private Button btnEntradaSaida;



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

    public void handleGerarEntradasSaidasButtonAction(ActionEvent event) throws IOException, DocumentException, ClassNotFoundException {
        Window owner = btnGerarEntradasSaidas.getScene().getWindow();
        try {
            LocalDate de = campoDe.getValue();
            LocalDate ate = campoAte.getValue();

            System.out.println(de + " | " + ate);

            if (de.toString().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Erro ao Gerar Relatório!",
                        "Selecione as datas!");
            }
            if (ate.toString().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, owner, "Erro ao Gerar Relatório!",
                        "Selecione as datas!");
            }

            GerarPdfController gerarPdfController = new GerarPdfController();
            gerarPdfController.gerarPdfRelatorioEntradasSaidas(de, ate);
            //gerarPdfController.gerarPdfRelatorioEstoque();
            //gerarPdfController.gerarPdfRelatorioReposicoes();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, owner, "Erro!",
                    "Preencha todos os campos e tente novamente!");
        }
    }

    public void handleGerarEstoqueButtonAction(ActionEvent event) throws IOException, DocumentException, ClassNotFoundException {
        Window owner = btnGerarEstoque.getScene().getWindow();
        try{
            GerarPdfController gerarPdfController = new GerarPdfController();
            gerarPdfController.gerarPdfRelatorioEstoque();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro!",
                    "Feche os relatórios abertos e tente novamente!");
        }

    }

    public void handleGerarReposicoesButtonAction(ActionEvent event) throws IOException, DocumentException, ClassNotFoundException {
        Window owner = btnGerarReposicoes.getScene().getWindow();
        try {
            GerarPdfController gerarPdfController = new GerarPdfController();
            gerarPdfController.gerarPdfRelatorioReposicoes();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro!",
                    "Feche os relatórios abertos e tente novamente!");
        }
    }

    public void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageGerarRelatorio = (Stage) btnCancelar.getScene().getWindow();
        stageGerarRelatorio.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/GerarRelatorios.fxml"));
        Parent root = loader.load();

        Stage relatorios = new Stage();
        relatorios.setTitle("Relatórios");
        relatorios.setScene(new Scene(root));
        relatorios.setResizable(false);
        relatorios.getIcons().add(icon);
        relatorios.show();
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void handleTelaInicial(MouseEvent mouseEvent) throws IOException{
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

    public void handleEntradaSaidaButtonAction(ActionEvent event) throws IOException {
        Stage stageRelatorios = (Stage) btnEntradaSaida.getScene().getWindow();
        stageRelatorios.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/GerarRelatorioEntradasSaidas.fxml"));
        Parent root = loader.load();

        Stage relatorios = new Stage();
        relatorios.setTitle("Relatórios");
        relatorios.setScene(new Scene(root));
        relatorios.setResizable(false);
        relatorios.getIcons().add(icon);
        relatorios.show();
    }
}
