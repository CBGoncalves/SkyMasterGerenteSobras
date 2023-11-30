package com.projetos.skymaster.skymastergerentesobras.controllers.registro;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.ItemDao;
import com.projetos.skymaster.skymastergerentesobras.dao.ObraDao;
import com.projetos.skymaster.skymastergerentesobras.dao.RegistroDao;
import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CadastrarEntradaController {
    @FXML
    private AnchorPane root;
    @FXML
    private ChoiceBox<Item> campoItem;
    @FXML
    private ChoiceBox<Obra> campoObra;
    @FXML
    private Button btnRegistrar;
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
    public CadastrarEntradaController(ItemDao itemDao, ObraDao obraDao) {
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

        btnRegistrar.setOnAction(event -> {
            try {
                handleRegistrarButtonAction(event);
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

    public void handleRegistrarButtonAction(ActionEvent event) throws SQLException{
        Window owner = btnRegistrar.getScene().getWindow();

        UsuarioHolder usuarioHolder = UsuarioHolder.getInstance();
        String nomeUsuario = usuarioHolder.getNome();
        String nomeItem;
        String nomeObra;
        String quantidadeEntrada = campoQuantidade.getText();
        String numNotaEntrada = campoNotaFiscal.getText();

        String tipoItem = "";
        String descricaoItem = "";

        try {
            nomeItem = campoItem.getValue().toString();
            nomeObra = campoObra.getValue().toString();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha os campos!");
            return;
        }

        if (nomeItem.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Selecione um Item!");
            return;
        }
        if (nomeObra.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Selecione uma Obra!");
            return;
        }
        if (quantidadeEntrada.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,owner,"Falha no Cadastro!",
                    "Digite a quantidade da entrada!");
            return;
        }
        if (numNotaEntrada.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha o número da NF!");
            return;
        }
        if (nomeUsuario.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Selecione um Usuario!");
            return;
        }

        String padraoRegex = "^([\\S ]+)-([\\S ]+)";

        Pattern padrao = Pattern.compile(padraoRegex);

        Matcher matcher = padrao.matcher(nomeItem);

        if (matcher.find()) {
            tipoItem = matcher.group(1);
            descricaoItem = matcher.group(2);
        } else {
            System.out.println("Nenhuma correspondência encontrada.");
        }

        int qtdEntrada = Integer.parseInt(quantidadeEntrada);

        RegistroDao registroDao = new RegistroDao();
        registroDao.createRegistroEntrada(tipoItem, descricaoItem, nomeObra, qtdEntrada, numNotaEntrada, nomeUsuario);
    }

    public void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageRegistroEntrada = (Stage) btnCancelar.getScene().getWindow();
        stageRegistroEntrada.close();

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
