package com.projetos.skymaster.skymastergerentesobras.controllers.tipoItem;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.TipoItemDao;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.TipoItem;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class EditarTipoItemController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoCodTipoItem;
    @FXML
    private TextField campoNomeTipoItem;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCancelar;
    private TipoItem tipoItem;

    public EditarTipoItemController(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

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

        campoCodTipoItem.setText(Integer.toString(tipoItem.getCodTipoItem()));
        campoNomeTipoItem.setText(tipoItem.getNomeTipoItem());

        btnEditar.setOnAction(event -> {
            try {
                handleEditarButtonAction(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnCancelar.setOnAction(event -> {
            try {
                handleCancelarButtonAction(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void handleEditarButtonAction(ActionEvent event) throws SQLException{
        Window owner = btnEditar.getScene().getWindow();

        String codigoTipoItem = campoCodTipoItem.getText();
        String nomeTipoItem = campoNomeTipoItem.getText();
        int codigoParametro = tipoItem.getCodTipoItem();

        if (codigoTipoItem.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro na Edição",
                    "Preencha o campo de Código do Tipo de Item!");
            return;
        }

        if (nomeTipoItem.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro na Edição",
                    "Preencha o campo de Nome do Tipo de Item");
            return;
        }

        int codTipoItem = Integer.parseInt(codigoTipoItem);
        TipoItemDao tipoItemDao = new TipoItemDao();
        tipoItemDao.updateTipoItem(codTipoItem, nomeTipoItem, codigoParametro);

        try {
            Stage stageEditar = (Stage) btnEditar.getScene().getWindow();
            stageEditar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/tipoItem/ListarTipoItem.fxml"));
            Parent root = loader.load();

            Stage listarTipoItem = new Stage();
            listarTipoItem.setTitle("Listar Tipo de Item");
            listarTipoItem.setScene(new Scene(root));
            listarTipoItem.setResizable(false);
            listarTipoItem.getIcons().add(icon);
            listarTipoItem.show();

            showAlert(Alert.AlertType.CONFIRMATION, owner,"Sucesso!",
                    "Tipo de Item Editado com Sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageEditar = (Stage) btnCancelar.getScene().getWindow();
        stageEditar.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/tipoItem/ListarTipoItem.fxml"));
        Parent root = loader.load();

        Stage listarTipoItem = new Stage();
        listarTipoItem.setTitle("Listar Tipo de Item");
        listarTipoItem.setScene(new Scene(root));
        listarTipoItem.setResizable(false);
        listarTipoItem.getIcons().add(icon);
        listarTipoItem.show();
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
