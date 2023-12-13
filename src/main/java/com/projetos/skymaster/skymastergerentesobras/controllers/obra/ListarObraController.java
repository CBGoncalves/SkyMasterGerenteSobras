package com.projetos.skymaster.skymastergerentesobras.controllers.obra;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.controllers.marca.EditarMarcaController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.ObraDao;
import com.projetos.skymaster.skymastergerentesobras.models.*;
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

public class ListarObraController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoFiltro;
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
            ObservableList<Obra> observableList = FXCollections.observableArrayList(obras);

            FilteredList<Obra> filteredData = new FilteredList<>(observableList, o -> true);

            campoFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(obra -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Integer.toString(obra.getCodObra()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (obra.getNomeObra().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Obra> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedData);
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

    public void handleDeletarButtonAction(ActionEvent event) throws SQLException{
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
        reloadTableView();
    }

    private void reloadTableView () throws SQLException {
        List<Obra> obras = obraDao.selectAllObras();
        ObservableList<Obra> observableList = FXCollections.observableArrayList(obras);
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
