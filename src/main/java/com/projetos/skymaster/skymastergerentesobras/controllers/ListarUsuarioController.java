package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarUsuarioController {

    @FXML
    private AnchorPane root;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Usuario, String> nomeColumn;
    @FXML
    private TableColumn<Usuario, String> senhaColumn;
    @FXML
    private TableColumn<Usuario, String> tipoUsuarioColumn;

    private UsuarioDao usuarioDao;

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

        usuarioDao = new UsuarioDao();

        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        senhaColumn.setCellValueFactory(new PropertyValueFactory<>("senha"));
        tipoUsuarioColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        try {
            List<Usuario> usuarios = usuarioDao.selectAllUsers();

            tableView.getItems().addAll(usuarios);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void handleEditarButtonAction(ActionEvent event) {
    }

    public void handleDeletarButtonAction(ActionEvent event) {
    }
}
