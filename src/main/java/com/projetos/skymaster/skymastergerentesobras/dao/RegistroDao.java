package com.projetos.skymaster.skymastergerentesobras.dao;

import com.projetos.skymaster.skymastergerentesobras.models.Item;
import com.projetos.skymaster.skymastergerentesobras.models.Obra;
import com.projetos.skymaster.skymastergerentesobras.models.Registro;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.scene.control.Alert;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class RegistroDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public Registro selectLastRegisterByDate() throws SQLException {
        Registro registro = new Registro();
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("SELECT 'entrada' AS tipo, registroentrada.numNotaEntrada, registroentrada.qtdEntrada AS quantidade, item.descricaoItem, tipoitem.nometipoitem, marca.nomeMarca, obra.nomeobra, usuario.nomeUsuario, registroentrada.dataEntrada as data\n" +
                    "FROM registroentrada\n" +
                    "INNER JOIN item ON registroentrada.codItem = item.codItem\n" +
                    "INNER JOIN tipoitem ON item.codTipoItem = tipoitem.codTipoItem\n" +
                    "INNER JOIN marca ON item.codMarca = marca.codMarca\n" +
                    "INNER JOIN obra ON registroentrada.codObra = obra.codObra\n" +
                    "INNER JOIN usuario ON registroentrada.codUsuario = usuario.codUsuario\n" +
                    "INNER JOIN tipousuario ON usuario.codTipoUsuario = tipousuario.codTipoUsuario\n" +
                    "UNION\n" +
                    "SELECT 'saida' AS tipo,registrosaida.numNotaSaida, registrosaida.qtdSaida AS quantidade, item.descricaoItem, tipoitem.nometipoitem, marca.nomeMarca, obra.nomeobra, usuario.nomeUsuario, registrosaida.dataSaida as data\n" +
                    "FROM registrosaida\n" +
                    "INNER JOIN item ON registrosaida.codItem = item.codItem\n" +
                    "INNER JOIN tipoitem ON item.codTipoItem = tipoitem.codTipoItem\n" +
                    "INNER JOIN marca ON item.codMarca = marca.codMarca\n" +
                    "INNER JOIN obra ON registrosaida.codObra = obra.codObra\n" +
                    "INNER JOIN usuario ON registrosaida.codUsuario = usuario.codUsuario\n" +
                    "ORDER BY data DESC LIMIT 1;");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                registro.setTipo(rs.getString("tipo"));
                registro.setNumNotaEntrada(rs.getString("numNotaEntrada"));
                registro.setQuantidade(rs.getInt("quantidade"));
                registro.setDescricaoItem(rs.getString("descricaoItem"));
                registro.setNomeTipoItem(rs.getString("nomeTipoItem"));
                registro.setNomeMarca(rs.getString("nomeMarca"));
                registro.setNomeObra(rs.getString("nomeObra"));
                registro.setNomeUsuario(rs.getString("nomeUsuario"));
                registro.setData(rs.getDate("data").toLocalDate());
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return registro;
    }

    public List<Registro> selectRegistersByDate() throws SQLException {
        List<Registro> list = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("SELECT registroentrada.codEntrada AS codRegistro, 'entrada' AS tipo, registroentrada.numNotaEntrada, registroentrada.qtdEntrada AS quantidade, item.descricaoItem, tipoitem.nometipoitem, marca.nomeMarca, setor.nomeSetor, obra.nomeobra, usuario.nomeUsuario, tipousuario.nomeTipoUsuario, registroentrada.dataEntrada as data\n" +
                    "FROM registroentrada\n" +
                    "INNER JOIN item ON registroentrada.codItem = item.codItem\n" +
                    "INNER JOIN tipoitem ON item.codTipoItem = tipoitem.codTipoItem\n" +
                    "INNER JOIN marca ON item.codMarca = marca.codMarca\n" +
                    "INNER JOIN setor ON item.codSetor = setor.codSetor\n" +
                    "INNER JOIN obra ON registroentrada.codObra = obra.codObra\n" +
                    "INNER JOIN usuario ON registroentrada.codUsuario = usuario.codUsuario\n" +
                    "INNER JOIN tipousuario ON usuario.codTipoUsuario = tipousuario.codTipoUsuario\n" +
                    "UNION\n" +
                    "SELECT registrosaida.codSaida AS codRegistro, 'saida' AS tipo,registrosaida.numNotaSaida, registrosaida.qtdSaida AS quantidade, item.descricaoItem, tipoitem.nometipoitem, marca.nomeMarca, setor.nomeSetor, obra.nomeobra, usuario.nomeUsuario, tipousuario.nomeTipoUsuario, registrosaida.dataSaida as data\n" +
                    "FROM registrosaida\n" +
                    "INNER JOIN item ON registrosaida.codItem = item.codItem\n" +
                    "INNER JOIN tipoitem ON item.codTipoItem = tipoitem.codTipoItem\n" +
                    "INNER JOIN marca ON item.codMarca = marca.codMarca\n" +
                    "INNER JOIN setor ON item.codSetor = setor.codSetor\n" +
                    "INNER JOIN obra ON registrosaida.codObra = obra.codObra\n" +
                    "INNER JOIN usuario ON registrosaida.codUsuario = usuario.codUsuario\n" +
                    "INNER JOIN tipousuario ON usuario.codTipoUsuario = tipousuario.codTipoUsuario\n" +
                    "ORDER BY data");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Registro registro = new Registro();
                registro.setCodRegistro(rs.getInt("codRegistro"));
                registro.setTipo(rs.getString("tipo"));
                registro.setNumNotaEntrada(rs.getString("numNotaEntrada"));
                registro.setQuantidade(rs.getInt("quantidade"));
                registro.setDescricaoItem(rs.getString("descricaoItem"));
                registro.setNomeTipoItem(rs.getString("nomeTipoItem"));
                registro.setNomeMarca(rs.getString("nomeMarca"));
                registro.setNomeSetor(rs.getString("nomeSetorCa"));
                registro.setNomeObra(rs.getString("nomeObra"));
                registro.setNomeUsuario(rs.getString("nomeUsuario"));
                registro.setData(rs.getDate("data").toLocalDate());
                list.add(registro);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return list;
    }

    public void createRegistroEntrada(String tipoItem, String descricaoItem, String nomeObra, double qtdEntrada, String numNotaEntrada, String nomeUsuario) throws SQLException {
        int codUsuario = getCodUsuarioByNome(nomeUsuario);
        int codItem = getCodItem(tipoItem, descricaoItem);
        int codObra = getCodObraByNome(nomeObra);

        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO RegistroEntrada (numNotaEntrada, qtdEntrada, dataEntrada, codItem, codObra, codUsuario)\n" +
                    "VALUES (?,?,NOW(),?,?,?);");
            preparedStatement.setString(1, numNotaEntrada);
            preparedStatement.setDouble(2, qtdEntrada);
            preparedStatement.setInt(3, codItem);
            preparedStatement.setInt(4, codObra);
            preparedStatement.setInt(5, codUsuario);
            preparedStatement.executeUpdate();

            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Entrada de item registrada com sucesso!");

        } catch (SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro no Registro!",
                    "Valores inválidos ou registro já existente!");
        }
    }

    public void createRegistroSaida(String tipoItem, String descricaoItem, String nomeObra, double qtdSaida, String numNotaSaida, String nomeUsuario) throws SQLException {
        int codUsuario = getCodUsuarioByNome(nomeUsuario);
        int codItem = getCodItem(tipoItem, descricaoItem);
        int codObra = getCodObraByNome(nomeObra);

        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO RegistroSaida (numNotaSaida, qtdSaida, dataSaida, codItem, codObra, codUsuario)\n" +
                    "VALUES (?,?,NOW(),?,?,?);");
            preparedStatement.setString(1, numNotaSaida);
            preparedStatement.setDouble(2, qtdSaida);
            preparedStatement.setInt(3, codItem);
            preparedStatement.setInt(4, codObra);
            preparedStatement.setInt(5, codUsuario);
            preparedStatement.executeUpdate();

            showAlert(Alert.AlertType.CONFIRMATION, "Sucesso!",
                    "Saída de item registrada com sucesso!");

        } catch (SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro no Registro!",
                    "Valores inválidos ou registro já existente!");
        }
    }

    public void updateRegistroEntrada(String numNotaEntrada, double qtdEntrada, String tipoItem, String descricaoItem, String nomeObra, int codEntrada) throws SQLException {
        int codItem = getCodItem(tipoItem, descricaoItem);
        int codObra = getCodObraByNome(nomeObra);

        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE registroentrada SET registroentrada.numNotaEntrada=?, registroentrada.qtdEntrada=?, registroentrada.codItem=?, registroentrada.codObra=? WHERE registroentrada.codEntrada=?;");
            preparedStatement.setString(1, numNotaEntrada);
            preparedStatement.setDouble(2, qtdEntrada);
            preparedStatement.setInt(3, codItem);
            preparedStatement.setInt(4, codObra);
            preparedStatement.setInt(5, codEntrada);
            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro na Edição!",
                    "Valores inválidos ou registro já existente!");
        }
    }

    public void updateRegistroSaida(String numNotaSaida, double qtdSaida, String tipoItem, String descricaoItem, String nomeObra, int codSaida) throws SQLException {
        int codItem = getCodItem(tipoItem, descricaoItem);
        int codObra = getCodObraByNome(nomeObra);

        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("UPDATE registrosaida SET registrosaida.numNotaSaida=?, registrosaida.qtdSaida=?, registrosaida.codItem=?, registrosaida.codObra=? WHERE registrosaida.codSaida=?;");
            preparedStatement.setString(1, numNotaSaida);
            preparedStatement.setDouble(2, qtdSaida);
            preparedStatement.setInt(3, codItem);
            preparedStatement.setInt(4, codObra);
            preparedStatement.setInt(5, codSaida);
            preparedStatement.executeUpdate();

        } catch(SQLException e) {
            printSQLException(e);
            showAlert(Alert.AlertType.ERROR, "Erro na Edição!",
                    "Valores inválidos ou registro já existente!");
        }
    }

    public void deleteRegistroEntrada(Registro r) {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM registroentrada WHERE codEntrada = ?;");
            preparedStatement.setInt(1, r.getCodRegistro());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    public void deleteRegistroSaida(Registro r) {
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM registrosaida WHERE codSaida = ?;");
            preparedStatement.setInt(1, r.getCodRegistro());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }

    }

    public static int getCodItem(String tipoItem, String descricaoItem) {
        Item i = null;
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = con.prepareStatement("SELECT item.codItem\n" +
                    "FROM item\n" +
                    "JOIN tipoitem ON item.codTipoItem = tipoitem.codTipoItem\n" +
                    "WHERE item.descricaoItem = ? AND tipoitem.nomeTipoItem = ?;");
            ps.setString(1, descricaoItem);
            ps.setString(2, tipoItem);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i = new Item();
                i.setCodItem(rs.getInt("codItem"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return i.getCodItem();
    }

    public static int getCodObraByNome(String nomeObra) {
        Obra ob = null;
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = con.prepareStatement("select * from obra where nomeObra=?");
            ps.setString(1, nomeObra);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ob = new Obra();
                ob.setCodObra(rs.getInt("codObra"));
                ob.setNomeObra(rs.getString("nomeObra"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return ob.getCodObra();
    }

    public static int getCodUsuarioByNome(String nomeUsuario) {
        Usuario u = null;
        try {
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement ps = con.prepareStatement("select * from usuario where nomeUsuario=?");
            ps.setString(1, nomeUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                u = new Usuario();
                u.setCodUsuario(rs.getInt("codUsuario"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return u.getCodUsuario();
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
