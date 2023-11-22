package com.projetos.skymaster.skymastergerentesobras.dao;

import com.projetos.skymaster.skymastergerentesobras.models.Registro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistroDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public List<Registro> selectRegistersByDate() throws SQLException {
        List<Registro> list = new ArrayList<>();
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
                    "ORDER BY data DESC LIMIT 5;");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Registro registro = new Registro();
                registro.setTipo(rs.getString("tipo"));
                registro.setNumNotaEntrada(rs.getString("numNotaEntrada"));
                registro.setQuantidade(rs.getInt("quantidade"));
                registro.setDescricaoItem(rs.getString("descricaoItem"));
                registro.setNomeTipoItem(rs.getString("nomeTipoItem"));
                registro.setNomeMarca(rs.getString("nomeMarca"));
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
}
