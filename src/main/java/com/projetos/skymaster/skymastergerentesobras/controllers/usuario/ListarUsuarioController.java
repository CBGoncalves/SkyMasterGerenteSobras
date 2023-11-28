package com.projetos.skymaster.skymastergerentesobras.controllers.usuario;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.TipoUsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
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

public class ListarUsuarioController {

    @FXML
    private AnchorPane root;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Usuario, String> codUsuarioColumn;
    @FXML
    private TableColumn<Usuario, String> nomeColumn;
    @FXML
    private TableColumn<Usuario, String> senhaColumn;
    @FXML
    private TableColumn<Usuario, String> tipoUsuarioColumn;
    @FXML
    private Button btnEditar;

    private UsuarioDao usuarioDao;

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

        usuarioDao = new UsuarioDao();

        codUsuarioColumn.setVisible(false);
        codUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("codUsuario"));
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        senhaColumn.setCellValueFactory(new PropertyValueFactory<>("senha"));
        tipoUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        try {
            List<Usuario> usuarios = usuarioDao.selectAllUsers();

            tableView.getItems().addAll(usuarios);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void handleEditarButtonAction(ActionEvent event) throws SQLException {
        Usuario usuario = null;
        usuario = (Usuario) tableView.getSelectionModel().getSelectedItem();
        if (usuario == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Editar",
                    "Você precisa selecionar um usuário para edição!");
            return;
        }
        try {
            Stage stageListar = (Stage) btnEditar.getScene().getWindow();
            stageListar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/usuario/EditarUsuario.fxml"));
            EditarUsuarioController controller = new EditarUsuarioController(new TipoUsuarioDao(), usuario);
            loader.setController(controller);
            Parent root = loader.load();

            Stage editarUsuario = new Stage();
            editarUsuario.setTitle("Editar Usuário");
            editarUsuario.setScene(new Scene(root));
            editarUsuario.setResizable(false);
            editarUsuario.getIcons().add(icon);
            editarUsuario.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeletarButtonAction(ActionEvent event) {
        Usuario usuario = null;
        usuario = (Usuario) tableView.getSelectionModel().getSelectedItem();
        if (usuario == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Deletar",
                    "Você precisa selecionar um usuário para remover!");
            return;
        }
        usuarioDao.deleteUsuario(usuario);
        showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                "Usuario deletado com sucesso!");
        tableView.getItems().remove(usuario);
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
