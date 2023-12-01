package com.projetos.skymaster.skymastergerentesobras.controllers.registro;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.RegistroDao;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Registro;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

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
