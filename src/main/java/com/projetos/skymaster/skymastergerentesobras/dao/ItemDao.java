package com.projetos.skymaster.skymastergerentesobras.dao;

import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.TipoItem;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public List<Item> selectAllItens() throws SQLException {
        List<Item> itemList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            PreparedStatement preparedStatement = con.prepareStatement("SELECT item.codItem, tipoitem.nomeTipoItem, item.descricaoItem, marca.nomeMarca\n" +
                    "FROM tipoitem\n" +
                    "INNER JOIN item ON tipoitem.codTipoItem = item.codTipoItem\n" +
                    "INNER JOIN marca ON item.codMarca = marca.codMarca;");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setCodItem(rs.getInt("codItem"));
                item.setNomeTipoItem(rs.getString("nomeTipoItem"));
                item.setDescricaoItem(rs.getString("descricaoItem"));
                item.setNomeMarca(rs.getString("nomeMarca"));
                itemList.add(item);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return itemList;
    }

    public void createItem(int codItem, String nomeTipoItem, String descricaoItem, String nomeMarca) throws SQLException {
        int codTipoItem = getCodTipoItemByNome(nomeTipoItem);
        System.out.println(codTipoItem);
        int codMarca = getCodMarcaByNome(nomeMarca);

        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO item(codItem,descricaoItem,codMarca,codTipoItem)VALUES(?,?,?,?);");
            preparedStatement.setInt(1, codItem);
            preparedStatement.setString(2, descricaoItem);
            preparedStatement.setInt(3, codMarca);
            preparedStatement.setInt(4, codTipoItem);
            preparedStatement.executeUpdate();

            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Item cadastrado com sucesso!");

        } catch (SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro no Cadastro!",
                    "Valores inválidos ou item já existente!");
        }
    }

    public void updateItem(int codItemAntigo, int codItem, String nomeTipoItem, String descricaoItem, String nomeMarca) throws SQLException {
        int codTipoItem = getCodTipoItemByNome(nomeTipoItem);
        int codMarca = getCodMarcaByNome(nomeTipoItem);

        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE item SET item.codItem=?, item.descricaoItem=?, item.codMarca=?, item.codTipoItem WHERE item.codItem=?;");
            preparedStatement.setInt(1, codItem);
            preparedStatement.setString(2, descricaoItem);
            preparedStatement.setInt(3, codMarca);
            preparedStatement.setInt(4, codTipoItem);
            preparedStatement.setInt(5, codItemAntigo);
            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro na Edição!",
                    "Valores inválidos ou item já existente!");
        }
    }

    public void deleteUsuario(Item i) {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM item WHERE codItem = ?;");
            preparedStatement.setInt(1, i.getCodItem());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    public static int getCodTipoItemByNome(String nomeTipoItem) {
        TipoItem t = null;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement ps = con.prepareStatement("select * from tipoitem where nomeTipoItem=?");
            ps.setString(1, nomeTipoItem);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                t = new TipoItem();
                t.setCodTipoItem(rs.getInt("codTipoItem"));
                t.setNomeTipoItem(rs.getString("nomeTipoItem"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return t.getCodTipoItem();
    }

    public static int getCodMarcaByNome(String nomeMarca) {
        Marca m = null;
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement ps = con.prepareStatement("select * from marca where nomeMarca=?");
            ps.setString(1, nomeMarca);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                m = new Marca();
                m.setCodMarca(rs.getInt("codMarca"));
                m.setNomeMarca(rs.getString("nomeMarca"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return m.getCodMarca();
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
