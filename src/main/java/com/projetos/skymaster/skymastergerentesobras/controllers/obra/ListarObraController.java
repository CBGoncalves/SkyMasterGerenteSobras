package com.projetos.skymaster.skymastergerentesobras.controllers.obra;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.controllers.marca.EditarMarcaController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.ObraDao;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.Obra;
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

public class ListarObraController {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Obra, Integer> codObraColumn;
    @FXML
    private TableColumn<Obra, String> nomeObraColumn;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    private ObraDao obraDao;

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

        obraDao = new ObraDao();

        codObraColumn.setCellValueFactory(new PropertyValueFactory<>("codObra"));
        nomeObraColumn.setCellValueFactory(new PropertyValueFactory<>("nomeObra"));

        try {
            List<Obra> obras = obraDao.selectAllObras();
            tableView.getItems().addAll(obras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleEditarButtonAction(ActionEvent event) {
        Obra obra = null;
        obra = (Obra) tableView.getSelectionModel().getSelectedItem();
        if (obra == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Editar",
                    "Você precisa selecionar uma obra para edição!");
            return;
        }
        try {
            Stage stageListar = (Stage) btnEditar.getScene().getWindow();
            stageListar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/obra/EditarObra.fxml"));
            EditarObraController controller = new EditarObraController(obra);
            loader.setController(controller);
            Parent root = loader.load();

            Stage editarObra = new Stage();
            editarObra.setTitle("Editar Obra");
            editarObra.setScene(new Scene(root));
            editarObra.setResizable(false);
            editarObra.getIcons().add(icon);
            editarObra.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeletarButtonAction(ActionEvent event) {
        Obra obra = null;
        obra = (Obra) tableView.getSelectionModel().getSelectedItem();
        if (obra == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Deletar",
                    "Você precisa selecionar uma obra para remover!");
            return;
        }
        obraDao.deleteObra(obra);
        showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                "Obra deletada com sucesso!");
        tableView.getItems().remove(obra);
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
