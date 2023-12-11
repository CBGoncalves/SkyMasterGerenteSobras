package com.projetos.skymaster.skymastergerentesobras.controllers.marca;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.controllers.usuario.EditarUsuarioController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.TipoUsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
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

public class ListarMarcaController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoFiltro;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Marca, Integer> codMarcaColumn;
    @FXML
    private TableColumn<Marca, String> nomeMarcaColumn;
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
            ObservableList<Marca> observableList = FXCollections.observableArrayList(marcas);

            FilteredList<Marca> filteredData = new FilteredList<>(observableList, m -> true);

            campoFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(marca -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (Integer.toString(marca.getCodMarca()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (marca.getNomeMarca().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Marca> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedData);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void handleEditarButtonAction(ActionEvent event) {
        Marca marca = null;
        marca = (Marca) tableView.getSelectionModel().getSelectedItem();
        if (marca == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Editar",
                    "Você precisa selecionar uma marca para edição!");
            return;
        }
        try {
            Stage stageListar = (Stage) btnEditar.getScene().getWindow();
            stageListar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/marca/EditarMarca.fxml"));
            EditarMarcaController controller = new EditarMarcaController(marca);
            loader.setController(controller);
            Parent root = loader.load();

            Stage editarMarca = new Stage();
            editarMarca.setTitle("Editar Marca");
            editarMarca.setScene(new Scene(root));
            editarMarca.setResizable(false);
            editarMarca.getIcons().add(icon);
            editarMarca.show();
        } catch(IOException e) {
            e.printStackTrace();
        }
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
