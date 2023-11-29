package com.projetos.skymaster.skymastergerentesobras.controllers.item;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.ItemDao;
import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarItemController {
    @FXML
    private AnchorPane root;
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
        quantidadeColumn.setCellValueFactory(new PropertyValueFactory<>("quantidadeItem"));

        try {
            List<Item> itens = itemDao.selectAllItens();

            tableView.getItems().addAll(itens);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleEditarButtonAction(ActionEvent event) {
    }

    public void handleDeletarButtonAction(ActionEvent event) {
    }
}
