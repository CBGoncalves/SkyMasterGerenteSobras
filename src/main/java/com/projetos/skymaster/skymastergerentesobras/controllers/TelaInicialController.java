package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.projetos.skymaster.skymastergerentesobras.dao.ItemDao;
import com.projetos.skymaster.skymastergerentesobras.dao.RegistroDao;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Registro;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TelaInicialController {

    @FXML
    private AnchorPane root;
    @FXML
    private Label tipoItem;
    @FXML
    private Label descricaoItem;
    @FXML
    private Label qtd;
    @FXML
    private Label tipo;
    @FXML
    private Label descricao;
    @FXML
    private Label quantidade;
    @FXML
    private Label responsavel;
    @FXML
    private Button btnHistorico;
    @FXML
    private Button btnListaItens;

    Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

    private RegistroDao registroDao;
    private ItemDao itemDao;

    public void initialize() throws SQLException {
        registroDao = new RegistroDao();
        itemDao = new ItemDao();

        try {
            Registro registro = registroDao.selectLastRegisterByDate();

            tipoItem.setText(registro.getNomeTipoItem());
            descricaoItem.setText(registro.getDescricaoItem());
            qtd.setText(Integer.toString(registro.getQuantidade()));
            responsavel.setText(registro.getNomeUsuario());

            Item item = itemDao.selectItensHome();

            tipo.setText(item.getNomeTipoItem());
            descricao.setText(item.getDescricaoItem());
            quantidade.setText(Double.toString(item.getQuantidadeItem()));




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
    }

    public void handleHistoricoButtonAction(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) btnHistorico.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/registro/HistoricoRegistro.fxml"));
            Parent root = loader.load();

            Stage historicoRegistro = new Stage();
            historicoRegistro.setTitle("Histórico Registro");
            historicoRegistro.setScene(new Scene(root));
            historicoRegistro.setResizable(false);
            historicoRegistro.getIcons().add(icon);
            historicoRegistro.show();

        } catch (IOException e) {

        }
    }

    public void handleListarItensButtonAction(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) btnListaItens.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/item/ListarItem.fxml"));
            Parent root = loader.load();

            Stage listarItem = new Stage();
            listarItem.setTitle("Listar Item");
            listarItem.setScene(new Scene(root));
            listarItem.setResizable(false);
            listarItem.getIcons().add(icon);
            listarItem.show();

        } catch (IOException e) {

        }
    }
}
