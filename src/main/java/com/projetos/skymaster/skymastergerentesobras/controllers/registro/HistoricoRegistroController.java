package com.projetos.skymaster.skymastergerentesobras.controllers.registro;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.controllers.item.EditarItemController;
import com.projetos.skymaster.skymastergerentesobras.dao.*;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Registro;
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
import java.time.LocalDate;
import java.util.List;

public class HistoricoRegistroController {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView<Registro> tableView;
    @FXML
    private TableColumn<Registro, String> tipoColumn;
    @FXML
    private TableColumn<Registro, String> numNotaColumn;
    @FXML
    private TableColumn<Registro, Integer> qtdColumn;
    @FXML
    private TableColumn<Registro, String> descricaoColumn;
    @FXML
    private TableColumn<Registro, String> tipoItemColumn;
    @FXML
    private TableColumn<Registro, String> marcaColumn;
    @FXML
    private TableColumn<Registro, String> obraColumn;
    @FXML
    private TableColumn<Registro, String> usuarioColumn;
    @FXML
    private TableColumn<Registro, LocalDate> dataColumn;
    @FXML
    private Button btnEditar;

    private RegistroDao registroDao;

    public void initialize() throws SQLException {

        registroDao = new RegistroDao();

        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        numNotaColumn.setCellValueFactory(new PropertyValueFactory<>("numNotaEntrada"));
        qtdColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricaoItem"));
        tipoItemColumn.setCellValueFactory(new PropertyValueFactory<>("nomeTipoItem"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeMarca"));
        obraColumn.setCellValueFactory(new PropertyValueFactory<>("nomeObra"));
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("nomeUsuario"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        try {
            List<Registro> registros = registroDao.selectRegistersByDate();

            tableView.getItems().addAll(registros);
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
    }

    public void handleEditarButtonAction(ActionEvent event) {
        Registro registro = null;
        registro = (Registro) tableView.getSelectionModel().getSelectedItem();
        if (registro == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Editar",
                    "Você precisa selecionar um registro para edição!");
            return;
        }
        try {
            if (registro.getTipo().equals("entrada")){
                Stage stageHistorico = (Stage) btnEditar.getScene().getWindow();
                stageHistorico.close();

                Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/registro/EditarEntrada.fxml"));
                EditarEntradaController controller = new EditarEntradaController(new ItemDao(), new ObraDao());
                loader.setController(controller);
                Parent root = loader.load();

                Stage editarEntrada = new Stage();
                editarEntrada.setTitle("Editar Entrada");
                editarEntrada.setScene(new Scene(root));
                editarEntrada.setResizable(false);
                editarEntrada.getIcons().add(icon);
                editarEntrada.show();
            } else if (registro.getTipo().equals("saida")) {
                Stage stageHistorico = (Stage) btnEditar.getScene().getWindow();
                stageHistorico.close();

                Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/registro/EditarSaida.fxml"));
                EditarSaidaController controller = new EditarSaidaController(new ItemDao(), new ObraDao());
                loader.setController(controller);
                Parent root = loader.load();

                Stage editarSaida = new Stage();
                editarSaida.setTitle("Editar Saída");
                editarSaida.setScene(new Scene(root));
                editarSaida.setResizable(false);
                editarSaida.getIcons().add(icon);
                editarSaida.show();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeletarButtonAction(ActionEvent event) {
        Registro registro = null;
        registro = (Registro) tableView.getSelectionModel().getSelectedItem();
        if (registro == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Deletar",
                    "Você precisa selecionar um registro para remover!");
            return;
        }
        String tipoRegistro = registro.getTipo();

        if (tipoRegistro.equals("entrada")) {
            registroDao.deleteRegistroEntrada(registro);
            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Registro deletado com sucesso!");
            tableView.getItems().remove(registro);
        } else if (tipoRegistro.equals("saida")) {
            registroDao.deleteRegistroSaida(registro);
            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Registro deletado com sucesso!");
            tableView.getItems().remove(registro);
        }
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
