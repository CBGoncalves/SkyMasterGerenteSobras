package com.projetos.skymaster.skymastergerentesobras.dao;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ObraDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public void createObra(int codObra, String nomeObra) {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO obra(codObra,nomeObra)VALUES(?,?);");
            preparedStatement.setInt(1, codObra);
            preparedStatement.setString(2, nomeObra);
            preparedStatement.executeUpdate();

            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Obra cadastrada com sucesso!");
        } catch (SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro no Cadastro!",
                    "Valores inválidos ou obra já existente!");
            return;
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
