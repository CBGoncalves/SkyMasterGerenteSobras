package com.projetos.skymaster.skymastergerentesobras.controllers.item;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.*;
import com.projetos.skymaster.skymastergerentesobras.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CadastrarItemController {
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
    private ChoiceBox<Setor> campoSetor;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnCancelar;
    private TipoItemDao tipoItemDao;
    private MarcaDao marcaDao;
    private SetorDao setorDao;

    public CadastrarItemController(TipoItemDao tipoItemDao, MarcaDao marcaDao, SetorDao setorDao) {
        this.tipoItemDao = tipoItemDao;
        this.marcaDao = marcaDao;
        this.setorDao = setorDao;

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

            List<Setor> setores = setorDao.selectAllSetores();
            ObservableList<Setor> observableListSetor = FXCollections.observableArrayList(setores);
            campoSetor.setItems(observableListSetor);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnCadastrar.setOnAction(event -> {
            try {
                handleCadastrarButtonAction(event);
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

    public void handleCadastrarButtonAction(ActionEvent event) throws SQLException{
        Window owner = btnCadastrar.getScene().getWindow();

        String codigoItem = campoCodItem.getText();
        String nomeTipoItem;
        String descricaoItem = campoDescricao.getText();
        String nomeMarca;
        String nomeSetor;
        try {
            nomeTipoItem = campoTipoItem.getValue().toString();
            nomeMarca = campoMarca.getValue().toString();
            nomeSetor = campoSetor.getValue().toString();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha os campos!");
            return;
        }

        if (codigoItem.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha o campo de Código do Item!");
            return;
        }
        if (nomeTipoItem.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Selecione um Tipo de Item!");
            return;
        }
        if (descricaoItem.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,owner,"Falha no Cadastro!",
                    "Preencha o campo de Descrição do Item!");
            return;
        }
        if (nomeMarca.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Selecione o Nome da Marca do item!");
            return;
        }
        if (nomeSetor.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Selecione o Setor do item!");
            return;
        }

        int codItem = Integer.parseInt(codigoItem);

        ItemDao itemDao = new ItemDao();
        itemDao.createItem(codItem, nomeTipoItem, descricaoItem, nomeMarca, nomeSetor);
    }

    public void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageCadastroItem = (Stage) btnCancelar.getScene().getWindow();
        stageCadastroItem.close();

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

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
