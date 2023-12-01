package com.projetos.skymaster.skymastergerentesobras.controllers.registro;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class HistoricoRegistroController {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView tableView;

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
    }

    public void handleEditarButtonAction(ActionEvent event) {
    }

    public void handleDeletarButtonAction(ActionEvent event) {
    }
}
