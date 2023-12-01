package com.projetos.skymaster.skymastergerentesobras.controllers.registro;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.ItemDao;
import com.projetos.skymaster.skymastergerentesobras.dao.ObraDao;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Obra;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
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

public class EditarSaidaController {

    @FXML
    private AnchorPane root;
    @FXML
    private ChoiceBox<Item> campoItem;
    @FXML
    private ChoiceBox<Obra> campoObra;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField campoQuantidade;
    @FXML
    private TextField campoNotaFiscal;
    @FXML
    private ChoiceBox<Usuario> campoUsuario;

    private ItemDao itemDao;
    private ObraDao obraDao;
    public EditarSaidaController(ItemDao itemDao, ObraDao obraDao) {
        this.itemDao = itemDao;
        this.obraDao = obraDao;
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
            List<Item> itens = itemDao.selectAllItens();
            ObservableList<Item> observableList = FXCollections.observableArrayList(itens);
            campoItem.setItems(observableList);

            List<Obra> obras = obraDao.selectAllObras();
            ObservableList<Obra> observableListMarca = FXCollections.observableArrayList(obras);
            campoObra.setItems(observableListMarca);

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
    private void handleEditarButtonAction(ActionEvent event) throws SQLException {
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) throws IOException {
        Stage stageEditarEntrada = (Stage) btnCancelar.getScene().getWindow();
        stageEditarEntrada.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/registro/HistoricoRegistro.fxml"));
        Parent root = loader.load();

        Stage historico = new Stage();
        historico.setTitle("Histórico de Registros");
        historico.setScene(new Scene(root));
        historico.setResizable(false);
        historico.getIcons().add(icon);
        historico.show();
    }
}
