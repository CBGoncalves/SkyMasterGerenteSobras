package com.projetos.skymaster.skymastergerentesobras.dao;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public void selectUserFromLogin(String usuario, String senha, Stage stageLogin) throws SQLException {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            PreparedStatement preparedStatement = con.prepareStatement("SELECT nomeUsuario, senhaUsuario FROM usuario WHERE nomeUsuario=? AND senhaUsuario=?");
            preparedStatement.setString(1, usuario);
            preparedStatement.setString(2, senha);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                try {
                    stageLogin.close();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/TelaInicial.fxml"));
                    Parent root = loader.load();

                    Stage telaInicial = new Stage();
                    telaInicial.setTitle("Tela Inicial");
                    telaInicial.setScene(new Scene(root));
                    telaInicial.setResizable(false);
                    telaInicial.show();

                    showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                            "Bem vindo " + usuario + "!");

                } catch (IOException e) {

                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Falha no login!",
                        "Usuário ou senha incorretos!");
            }

        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException exception) {
        for (Throwable e : exception) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + ((SQLException) e).getMessage());
                Throwable t = exception.getCause();
                while (t != null) {
                    System.out.println("Cause:" + t);
                    t = t.getCause();
                }
            }
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(null);
        alert.show();
    }
}
