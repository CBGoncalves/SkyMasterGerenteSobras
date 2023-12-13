package com.projetos.skymaster.skymastergerentesobras.controllers.item;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.ItemDao;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.SetorDao;
import com.projetos.skymaster.skymastergerentesobras.dao.TipoItemDao;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
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
import java.util.List;

public class ListarItemController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoFiltro;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Item, String> codItemColumn;
    @FXML
    private TableColumn<Item, String> nomeTipoItemColumn;
    @FXML
    private TableColumn<Item, String> descricaoItemColumn;
    @FXML
    private TableColumn<Item, String> nomeMarcaColumn;
    @FXML
    private TableColumn<Item, String> nomeSetorColumn;
    @FXML
    private TableColumn<Item, Double> quantidadeColumn;
    @FXML
    private Button btnEditar;

    private ItemDao itemDao;

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

        itemDao = new ItemDao();

        codItemColumn.setCellValueFactory(new PropertyValueFactory<>("codItem"));
        nomeTipoItemColumn.setCellValueFactory(new PropertyValueFactory<>("nomeTipoItem"));
        descricaoItemColumn.setCellValueFactory(new PropertyValueFactory<>("descricaoItem"));
        nomeMarcaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeMarca"));
        nomeSetorColumn.setCellValueFactory(new PropertyValueFactory<>("nomeSetor"));
        quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidadeItem"));

        try {
            List<Item> itens = itemDao.selectAllItens();
            ObservableList<Item> observableList = FXCollections.observableArrayList(itens);

            FilteredList<Item> filteredData = new FilteredList<>(observableList, i -> true);

            campoFiltro.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(item -> {

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (item.getNomeTipoItem().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Integer.toString(item.getCodItem()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }else if (item.getDescricaoItem().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (item.getNomeMarca().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (item.getNomeSetor().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (Double.toString(item.getQuantidadeItem()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Item> sortedData = new SortedList<>(filteredData);

            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedData);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleEditarButtonAction(ActionEvent event) {
        Item item = null;
        item = (Item) tableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Editar",
                    "Você precisa selecionar um item para edição!");
            return;
        }
        try {
            Stage stageListar = (Stage) btnEditar.getScene().getWindow();
            stageListar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/item/EditarItem.fxml"));
            EditarItemController controller = new EditarItemController(item, new TipoItemDao(), new MarcaDao(), new SetorDao());
            loader.setController(controller);
            Parent root = loader.load();

            Stage editarItem = new Stage();
            editarItem.setTitle("Editar Item");
            editarItem.setScene(new Scene(root));
            editarItem.setResizable(false);
            editarItem.getIcons().add(icon);
            editarItem.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeletarButtonAction(ActionEvent event) throws SQLException{
        Item item = null;
        item = (Item) tableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            showAlert(Alert.AlertType.WARNING, "Erro ao Deletar",
                    "Você precisa selecionar um item para remover!");
            return;
        }
        itemDao.deleteItem(item);
        showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                "Item deletado com sucesso!");
        reloadTableView();
    }
    private void reloadTableView () throws SQLException {
        List<Item> itens = itemDao.selectAllItens();
        ObservableList<Item> observableList = FXCollections.observableArrayList(itens);
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
