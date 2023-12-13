package com.projetos.skymaster.skymastergerentesobras.controllers.registro;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.controllers.item.EditarItemController;
import com.projetos.skymaster.skymastergerentesobras.dao.*;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Registro;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoricoRegistroController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoFiltro;
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
    private TableColumn<Registro, String> setorColumn;
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
        setorColumn.setCellValueFactory(new PropertyValueFactory<>("nomeSetor"));
        obraColumn.setCellValueFactory(new PropertyValueFactory<>("nomeObra"));
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("nomeUsuario"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        dataColumn.setCellFactory(column -> new TableCell<Registro, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (empty || date == null) {
                    setText(null);
                } else {
                    Registro registro = getTableView().getItems().get(getIndex());
                    setText(registro.getDataFormatada());
                }
            }
        });

        try {
            List<Registro> registros = registroDao.selectRegistersByDate();
            ObservableList<Registro> observableList = FXCollections.observableArrayList(registros);

            FilteredList<Registro> filteredList = new FilteredList<>(observableList, r -> true);

            campoFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(registro -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (registro.getTipo().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registro.getNumNotaEntrada().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (Integer.toString(registro.getQuantidade()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registro.getDescricaoItem().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registro.getNomeTipoItem().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registro.getNomeMarca().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registro.getNomeSetor().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registro.getNomeObra().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registro.getNomeUsuario().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (registro.getDataFormatada().toString().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Registro> sortedData = new SortedList<>(filteredList);

            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedData);
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
                EditarEntradaController controller = new EditarEntradaController(registro, new ItemDao(), new ObraDao());
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
                EditarSaidaController controller = new EditarSaidaController(registro, new ItemDao(), new ObraDao());
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

    public void handleDeletarButtonAction(ActionEvent event) throws SQLException {
        Registro registro = null;
        registro = tableView.getSelectionModel().getSelectedItem();
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
            reloadTableView();
        } else if (tipoRegistro.equals("saida")) {
            registroDao.deleteRegistroSaida(registro);
            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Registro deletado com sucesso!");
            reloadTableView();        }
    }

    private void reloadTableView () throws SQLException {
        List<Registro> registros = registroDao.selectRegistersByDate();
        ObservableList<Registro> observableList = FXCollections.observableArrayList(registros);
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
