package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField campoUsuario;
    @FXML
    private TextField campoSenha;
    @FXML
    private Button btnEntrar;

    public void handleSubmitButtonAction(ActionEvent event) throws SQLException {
        Window owner = btnEntrar.getScene().getWindow();

        String usuario = campoUsuario.getText();
        String senha = campoSenha.getText();

        if (usuario.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro no Formulário!",
                    "Preencha o nome do usuário!");
            return;
        }

        if (senha.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro no Formulário!",
                    "Preencha o campo senha!");
            return;
        }
        Stage stageLogin = (Stage) btnEntrar.getScene().getWindow();

        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.selectUserFromLogin(usuario, senha, stageLogin);

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
