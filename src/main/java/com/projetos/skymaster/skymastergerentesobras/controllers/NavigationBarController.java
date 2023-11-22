package com.projetos.skymaster.skymastergerentesobras.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationBarController {

    @FXML
    private MenuBar menuBar;

    Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky2.jpg"));

    public void handleCadastrarUsuario(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/CadastrarUsuario.fxml"));
            Parent root = loader.load();

            Stage cadastrarUsuario = new Stage();
            cadastrarUsuario.setTitle("Cadastrar Usuário");
            cadastrarUsuario.setScene(new Scene(root));
            cadastrarUsuario.setResizable(false);
            cadastrarUsuario.getIcons().add(icon);
            cadastrarUsuario.show();

        } catch (IOException e) {

        }
    }

    public void handleListarUsuario(ActionEvent event) {

    }

    public void handleCadastrarItens(ActionEvent event) {
    }

    public void handleListarItens(ActionEvent event) {
    }

    public void handleCadastrarMarcas(ActionEvent event) {
    }

    public void handleListarMarcas(ActionEvent event) {
    }

    public void handleCadastrarObras(ActionEvent event) {
    }

    public void handleListarObras(ActionEvent event) {
    }

    public void handleCadastrarEntradas(ActionEvent event) {
    }

    public void handleCadastrarSaidas(ActionEvent event) {
    }

    public void handleHistorico(ActionEvent event) {
    }

    public void handleGerarRelatorio(ActionEvent event) {
    }
}
