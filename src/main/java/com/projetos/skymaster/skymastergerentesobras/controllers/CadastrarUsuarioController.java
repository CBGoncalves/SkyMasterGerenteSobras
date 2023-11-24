package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.projetos.skymaster.skymastergerentesobras.dao.TipoUsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuario;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import javafx.beans.Observable;
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
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CadastrarUsuarioController {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private PasswordField campoConfirmarSenha;
    @FXML
    private ChoiceBox<TipoUsuario> campoTipoUsuario;
    @FXML
    private Button btnCadastrar;
    @FXML
    private Button btnCancelar;
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

    public void handleCadastrarButtonAction(ActionEvent event) throws SQLException {
        Window owner = btnCadastrar.getScene().getWindow();

        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();
        String confirmarSenha = campoConfirmarSenha.getText();
        String tipoUsuario;
        try {
            tipoUsuario = campoTipoUsuario.getValue().toString();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha os campos!");
            return;
        }


        if (usuario.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha o campo usuário!");
            return;
        }
        if (senha.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha o campo de senha!");
            return;
        }
        if (confirmarSenha.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,owner,"Falha no Cadastro!",
                    "Preencha o campo de confirmação de senha!");
            return;
        }
        if (tipoUsuario.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Selecione um tipo de usuário!");
            return;
        }
        if (!confirmarSenha.equals(senha)) {
            showAlert(Alert.AlertType.ERROR,owner,"Falha no Cadastro!",
                    "As senhas não coincidem! Por favor, preencha os campos de forma igual!");
            return;
        }

        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.createUsuario(usuario, senha, tipoUsuario);
    }

    public void handleCancelarButtonAction(ActionEvent event) throws IOException {
        Stage stageCadastro = (Stage) btnCancelar.getScene().getWindow();
        stageCadastro.close();

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
