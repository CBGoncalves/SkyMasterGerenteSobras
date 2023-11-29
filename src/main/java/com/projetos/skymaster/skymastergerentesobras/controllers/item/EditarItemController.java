package com.projetos.skymaster.skymastergerentesobras.controllers.item;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.TipoItemDao;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.TipoItem;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditarItemController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoCodItem;
    @FXML
    private ChoiceBox<TipoItem> campoTipoItem;
    @FXML
    private TextField campoDescricao;
    @FXML
    private ChoiceBox<Marca> campoMarca;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCancelar;
    private TipoItemDao tipoItemDao;
    private MarcaDao marcaDao;
    private Item item;

    public EditarItemController(Item item, TipoItemDao tipoItemDao, MarcaDao marcaDao) {
        this.item = item;
        this.tipoItemDao = tipoItemDao;
        this.marcaDao = marcaDao;
    }

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

        try {
            List<TipoItem> tiposItem = tipoItemDao.selectAllTiposItem();
            ObservableList<TipoItem> observableList = FXCollections.observableArrayList(tiposItem);
            campoTipoItem.setItems(observableList);

            List<Marca> marcas = marcaDao.selectAllMarcas();
            ObservableList<Marca> observableListMarca = FXCollections.observableArrayList(marcas);
            campoMarca.setItems(observableListMarca);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnEditar.setOnAction(event -> {
            try {
                handleEditarButtonAction(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnCancelar.setOnAction(event -> {
            try {
                handleCancelarButtonAction(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    private void handleEditarButtonAction(ActionEvent event) throws SQLException{
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageEditar = (Stage) btnCancelar.getScene().getWindow();
        stageEditar.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/item/ListarItem.fxml"));
        Parent root = loader.load();

        Stage listarItem = new Stage();
        listarItem.setTitle("Listar Item");
        listarItem.setScene(new Scene(root));
        listarItem.setResizable(false);
        listarItem.getIcons().add(icon);
        listarItem.show();
    }
}
