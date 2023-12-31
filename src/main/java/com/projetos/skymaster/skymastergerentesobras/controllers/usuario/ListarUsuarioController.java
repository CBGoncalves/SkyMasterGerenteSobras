package com.projetos.skymaster.skymastergerentesobras.controllers.usuario;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.TipoUsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Obra;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarUsuarioController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoFiltro;
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

            ObservableList<Usuario> observableList = FXCollections.observableArrayList(usuarios);

            FilteredList<Usuario> filteredData = new FilteredList<>(observableList, u -> true);

            campoFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(usuario -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (usuario.getNome().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (usuario.getTipo().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (usuario.getSenha().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Usuario> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedData);
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

    public void handleDeletarButtonAction(ActionEvent event) throws SQLException {
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
        reloadTableView();
    }

    private void reloadTableView () throws SQLException {
        List<Usuario> usuarios = usuarioDao.selectAllUsers();
        ObservableList<Usuario> observableList = FXCollections.observableArrayList(usuarios);
        tableView.setItems(observableList);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(null);
        alert.show();
    }

    public void handleTelaInicial(MouseEvent mouseEvent) throws IOException{
        Stage stageGerarRelatorio = (Stage) btnEditar.getScene().getWindow();
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
