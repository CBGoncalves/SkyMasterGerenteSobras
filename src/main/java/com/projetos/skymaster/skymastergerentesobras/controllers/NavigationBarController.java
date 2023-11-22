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
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/ListarUsuario.fxml"));
            Parent root = loader.load();

            Stage listarUsuario = new Stage();
            listarUsuario.setTitle("Listar Usuário");
            listarUsuario.setScene(new Scene(root));
            listarUsuario.setResizable(false);
            listarUsuario.getIcons().add(icon);
            listarUsuario.show();

        } catch (IOException e) {

        }
    }

    public void handleCadastrarItens(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/CadastrarItem.fxml"));
            Parent root = loader.load();

            Stage cadastrarItem = new Stage();
            cadastrarItem.setTitle("Cadastrar Item");
            cadastrarItem.setScene(new Scene(root));
            cadastrarItem.setResizable(false);
            cadastrarItem.getIcons().add(icon);
            cadastrarItem.show();

        } catch (IOException e) {

        }
    }

    public void handleListarItens(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/ListarItem.fxml"));
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

    public void handleCadastrarMarcas(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/CadastrarMarca.fxml"));
            Parent root = loader.load();

            Stage cadastrarMarca = new Stage();
            cadastrarMarca.setTitle("Cadastrar Marca");
            cadastrarMarca.setScene(new Scene(root));
            cadastrarMarca.setResizable(false);
            cadastrarMarca.getIcons().add(icon);
            cadastrarMarca.show();

        } catch (IOException e) {

        }
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
