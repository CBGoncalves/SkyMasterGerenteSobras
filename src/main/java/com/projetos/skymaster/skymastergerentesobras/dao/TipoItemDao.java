package com.projetos.skymaster.skymastergerentesobras.dao;

import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.TipoItem;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoItemDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public List<TipoItem> selectAllTiposItem() throws SQLException {
        List<TipoItem> tipoItemList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("SELECT codTipoItem, nomeTipoItem FROM tipoitem");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                TipoItem tipoItem = new TipoItem();
                tipoItem.setCodTipoItem(rs.getInt("codTipoItem"));
                tipoItem.setNomeTipoItem(rs.getString("nomeTipoItem"));
                tipoItemList.add(tipoItem);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return tipoItemList;
    }

    public void createTipoItem(int codTipoItem, String nomeTipoItem) throws SQLException {
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO tipoitem(codTipoItem,nomeTipoItem)VALUES(?,?);");
            preparedStatement.setInt(1, codTipoItem);
            preparedStatement.setString(2, nomeTipoItem);
            preparedStatement.executeUpdate();

            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Tipo de Item cadastrada com sucesso!");
        } catch (SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro no Cadastro!",
                    "Valores inválidos ou tipo de item já existente!");
            return;
        }
    }

    public void updateTipoItem(int codTipoItem, String nomeTipoItem, int codigoParametro) throws SQLException {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE tipoitem SET codTipoItem=?, nomeTipoItem=? WHERE codTipoItem=?;");
            preparedStatement.setInt(1, codTipoItem);
            preparedStatement.setString(2, nomeTipoItem);
            preparedStatement.setInt(3, codigoParametro);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro na Edição!",
                    "Valores inválidos ou tipo de item já existente!");
            return;
        }
    }

    public void deleteTipoItem(TipoItem ti) {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM tipoitem WHERE codTipoItem = ?;");
            preparedStatement.setInt(1, ti.getCodTipoItem());
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
