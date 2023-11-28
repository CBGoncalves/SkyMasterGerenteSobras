package com.projetos.skymaster.skymastergerentesobras.controllers.marca;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarMarcaController {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Usuario, Integer> codMarcaColumn;
    @FXML
    private TableColumn<Usuario, String> nomeMarcaColumn;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;

    private MarcaDao marcaDao;

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


        marcaDao = new MarcaDao();

        codMarcaColumn.setCellValueFactory(new PropertyValueFactory<>("codMarca"));
        nomeMarcaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeMarca"));

        try {
            List<Marca> marcas = marcaDao.selectAllMarcas();
            tableView.getItems().addAll(marcas);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void handleEditarButtonAction(ActionEvent event) {
    }

    public void handleDeletarButtonAction(ActionEvent event) {
        Marca marca = null;
        marca = (Marca) tableView.getSelectionModel().getSelectedItem();
        if (marca == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Deletar",
                    "Você precisa selecionar uma marca para remover!");
            return;
        }
        marcaDao.deleteMarca(marca);
        showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                "Marca deletada com sucesso!");
        tableView.getItems().remove(marca);
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
