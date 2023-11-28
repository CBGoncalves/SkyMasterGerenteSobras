package com.projetos.skymaster.skymastergerentesobras.controllers.tipoItem;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.controllers.marca.EditarMarcaController;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarTipoItemController {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<TipoItem, Integer> codTipoItemColumn;
    @FXML
    private TableColumn<TipoItem, String> nomeTipoItemColumn;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;

    private TipoItemDao tipoItemDao;

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

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tipoItemDao = new TipoItemDao();

        codTipoItemColumn.setCellValueFactory(new PropertyValueFactory<>("codTipoItem"));
        nomeTipoItemColumn.setCellValueFactory(new PropertyValueFactory<>("nomeTipoItem"));

        try {
            List<TipoItem> tiposItem = tipoItemDao.selectAllTiposItem();
            tableView.getItems().addAll(tiposItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleEditarButtonAction(ActionEvent event) {
        TipoItem tipoItem = null;
        tipoItem = (TipoItem) tableView.getSelectionModel().getSelectedItem();
        if (tipoItem == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Editar",
                    "Você precisa selecionar um tipo de item para edição!");
            return;
        }
        try {
            Stage stageListar = (Stage) btnEditar.getScene().getWindow();
            stageListar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/tipoItem/EditarTipoItem.fxml"));
            EditarTipoItemController controller = new EditarTipoItemController(tipoItem);
            loader.setController(controller);
            Parent root = loader.load();

            Stage editarTipoItem = new Stage();
            editarTipoItem.setTitle("Editar Tipo de Item");
            editarTipoItem.setScene(new Scene(root));
            editarTipoItem.setResizable(false);
            editarTipoItem.getIcons().add(icon);
            editarTipoItem.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeletarButtonAction(ActionEvent event) {
        TipoItem tipoItem = null;
        tipoItem = (TipoItem) tableView.getSelectionModel().getSelectedItem();
        if (tipoItem == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Deletar",
                    "Você precisa selecionar um tipo de item para remover!");
            return;
        }
        tipoItemDao.deleteTipoItem(tipoItem);
        showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                "Tipo de Item deletado com sucesso!");
        tableView.getItems().remove(tipoItem);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(null);
        alert.show();
    }
}
