package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.projetos.skymaster.skymastergerentesobras.controllers.item.CadastrarItemController;
import com.projetos.skymaster.skymastergerentesobras.controllers.registro.CadastrarEntradaController;
import com.projetos.skymaster.skymastergerentesobras.controllers.registro.CadastrarSaidaController;
import com.projetos.skymaster.skymastergerentesobras.controllers.usuario.CadastrarUsuarioController;
import com.projetos.skymaster.skymastergerentesobras.dao.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationBarController {

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu UsuarioItem;
    @FXML
    private Menu TipoItem;

    Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

    public void handleCadastrarUsuario(ActionEvent event) {

        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/usuario/CadastrarUsuario.fxml"));
            CadastrarUsuarioController controller = new CadastrarUsuarioController(new TipoUsuarioDao());
            loader.setController(controller);
            Parent root = loader.load();

            Stage cadastrarUsuario = new Stage();
            cadastrarUsuario.setTitle("Cadastrar Usuário");
            cadastrarUsuario.setScene(new Scene(root));
            cadastrarUsuario.setResizable(false);
            cadastrarUsuario.getIcons().add(icon);
            cadastrarUsuario.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleListarUsuario(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/usuario/ListarUsuario.fxml"));
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

    public void handleCadastrarTipoItem(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/tipoItem/CadastrarTipoItem.fxml"));
            Parent root = loader.load();

            Stage cadastrarTipoItem = new Stage();
            cadastrarTipoItem.setTitle("Cadastrar Tipo de Item");
            cadastrarTipoItem.setScene(new Scene(root));
            cadastrarTipoItem.setResizable(false);
            cadastrarTipoItem.getIcons().add(icon);
            cadastrarTipoItem.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleListarTipoItem(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/tipoItem/ListarTipoItem.fxml"));
            Parent root = loader.load();

            Stage listarTipoItem = new Stage();
            listarTipoItem.setTitle("Listar Tipo de Item");
            listarTipoItem.setScene(new Scene(root));
            listarTipoItem.setResizable(false);
            listarTipoItem.getIcons().add(icon);
            listarTipoItem.show();

        } catch (IOException e) {
        }
    }

    public void handleCadastrarItens(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/item/CadastrarItem.fxml"));
            CadastrarItemController controller = new CadastrarItemController(new TipoItemDao(), new MarcaDao());
            loader.setController(controller);
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

    public void handleCadastrarMarcas(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/marca/CadastrarMarca.fxml"));
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
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/marca/ListarMarca.fxml"));
            Parent root = loader.load();

            Stage listarMarca = new Stage();
            listarMarca.setTitle("Listar Marca");
            listarMarca.setScene(new Scene(root));
            listarMarca.setResizable(false);
            listarMarca.getIcons().add(icon);
            listarMarca.show();

        } catch (IOException e) {

        }
    }

    public void handleCadastrarObras(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/obra/CadastrarObra.fxml"));
            Parent root = loader.load();

            Stage cadastrarObra = new Stage();
            cadastrarObra.setTitle("Cadastrar Obra");
            cadastrarObra.setScene(new Scene(root));
            cadastrarObra.setResizable(false);
            cadastrarObra.getIcons().add(icon);
            cadastrarObra.show();

        } catch (IOException e) {

        }
    }

    public void handleListarObras(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/obra/ListarObra.fxml"));
            Parent root = loader.load();

            Stage listarObra = new Stage();
            listarObra.setTitle("Listar Obra");
            listarObra.setScene(new Scene(root));
            listarObra.setResizable(false);
            listarObra.getIcons().add(icon);
            listarObra.show();

        } catch (IOException e) {

        }
    }

    public void handleCadastrarEntradas(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/registro/CadastrarEntrada.fxml"));
            CadastrarEntradaController controller = new CadastrarEntradaController(new ItemDao(), new ObraDao());
            loader.setController(controller);
            Parent root = loader.load();

            Stage cadastrarEntrada = new Stage();
            cadastrarEntrada.setTitle("Cadastrar Entrada");
            cadastrarEntrada.setScene(new Scene(root));
            cadastrarEntrada.setResizable(false);
            cadastrarEntrada.getIcons().add(icon);
            cadastrarEntrada.show();

        } catch (IOException e) {

        }
    }

    public void handleCadastrarSaidas(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/registro/CadastrarSaida.fxml"));
            CadastrarSaidaController controller = new CadastrarSaidaController(new ItemDao(), new ObraDao());
            loader.setController(controller);
            Parent root = loader.load();

            Stage cadastrarSaida = new Stage();
            cadastrarSaida.setTitle("Cadastrar Saída");
            cadastrarSaida.setScene(new Scene(root));
            cadastrarSaida.setResizable(false);
            cadastrarSaida.getIcons().add(icon);
            cadastrarSaida.show();

        } catch (IOException e) {

        }
    }

    public void handleHistorico(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
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

    public void handleGerarRelatorio(ActionEvent event) {
        try {
            Stage stageAtual = (Stage) menuBar.getScene().getWindow();
            stageAtual.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/GerarRelatorio.fxml"));
            Parent root = loader.load();

            Stage gerarRelatorio = new Stage();
            gerarRelatorio.setTitle("Gerar Relatório");
            gerarRelatorio.setScene(new Scene(root));
            gerarRelatorio.setResizable(false);
            gerarRelatorio.getIcons().add(icon);
            gerarRelatorio.show();

        } catch (IOException e) {

        }
    }

    public void exibirOpcoesAdmin() {
        UsuarioItem.setVisible(true);
        TipoItem.setVisible(true);
    }

    public void exibirOpcoesFuncionario() {
        UsuarioItem.setVisible(false);
        TipoItem.setVisible(false);
    }
}
