package com.projetos.skymaster.skymastergerentesobras.dao;

import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarcaDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public List<Marca> selectAllMarcas() throws SQLException {
        List<Marca> marcaList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("SELECT codMarca, nomeMarca FROM marca");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Marca marca = new Marca();
                marca.setCodMarca(rs.getInt("codMarca"));
                marca.setNomeMarca(rs.getString("nomeMarca"));
                marcaList.add(marca);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return marcaList;
    }

    public void createMarca(int codMarca, String nomeMarca) throws SQLException {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO marca(codMarca,nomeMarca)VALUES(?,?);");
            preparedStatement.setInt(1, codMarca);
            preparedStatement.setString(2, nomeMarca);
            preparedStatement.executeUpdate();

            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Marca cadastrada com sucesso!");
        } catch (SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro no Cadastro!",
                    "Valores inválidos ou marca já existente!");
            return;
        }
    }

    public void updateMarca(int codMarca, String nomeMarca, int codigoParametro) throws SQLException {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE marca SET codMarca=?, nomeMarca=? WHERE codMarca=?;");
            preparedStatement.setInt(1, codMarca);
            preparedStatement.setString(2, nomeMarca);
            preparedStatement.setInt(3, codigoParametro);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro na Edição!",
                    "Valores inválidos ou marca já existente!");
            return;
        }
    }

    public void deleteMarca(Marca m) {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM marca WHERE codMarca = ?;");
            preparedStatement.setInt(1, m.getCodMarca());
            preparedStatement.executeUpdate();
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
