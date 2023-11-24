package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.projetos.skymaster.skymastergerentesobras.dao.TipoUsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuario;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CadastrarUsuarioController {
    @FXML
    private AnchorPane root;
    @FXML
    private ChoiceBox<TipoUsuario> campoTipoUsuario;
    private TipoUsuarioDao tipoUsuarioDao;

    public CadastrarUsuarioController(TipoUsuarioDao tipoUsuarioDao) {
        this.tipoUsuarioDao = tipoUsuarioDao;
    }

    public void initialize() throws SQLException {
        try {
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
                List<TipoUsuario> tiposUsuario = tipoUsuarioDao.getAllTipoUsuario();
                ObservableList<TipoUsuario> observableList = FXCollections.observableArrayList(tiposUsuario);
                campoTipoUsuario.setItems(observableList);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
