package com.projetos.skymaster.skymastergerentesobras.controllers.tipoItem;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.TipoItemDao;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class CadastrarTipoItemController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoCodTipoItem;
    @FXML
    private TextField campoNomeTipoItem;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnCancelar;

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

    public void handleCadastrarButtonAction(ActionEvent event) throws SQLException {
        Window owner = btnCadastrar.getScene().getWindow();

        String codigoTipoItem = campoCodTipoItem.getText();
        String nomeTipoItem = campoNomeTipoItem.getText();

        if (codigoTipoItem.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro no Cadastro",
                    "Preencha o campo de Código do Tipo de Item!");
            return;
        }

        if (nomeTipoItem.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro no Cadastro",
                    "Preencha o campo do Nome do Tipo de Item!");
            return;
        }

        int codTipoItem = Integer.parseInt(codigoTipoItem);

        TipoItemDao tipoItemDao = new TipoItemDao();
        tipoItemDao.createTipoItem(codTipoItem, nomeTipoItem);
    }

    public void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageCadastroTipoItem = (Stage) btnCancelar.getScene().getWindow();
        stageCadastroTipoItem.close();

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
}
