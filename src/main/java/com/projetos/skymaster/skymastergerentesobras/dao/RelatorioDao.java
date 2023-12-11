package com.projetos.skymaster.skymastergerentesobras.dao;

import java.io.IOException;
import java.sql.*;

public class RelatorioDao {

    public static Connection getConnection() throws ClassNotFoundException {
        Connection con = null;
        try {
            System.out.println("Chamando conexão");
            try {
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/smgerentesobra", "root", "root");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public static ResultSet getResultSet(String de, String ate) throws ClassNotFoundException, SQLException {
        ResultSet rs = null;
        try {
            Connection con = RelatorioDao.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT *\r\n"
                            + "FROM (\r\n"
                            + "    SELECT 'entrada' AS tipo, registroentrada.numNotaEntrada, registroentrada.qtdEntrada AS quantidade, tipoitem.nometipoitem, item.descricaoItem, marca.nomeMarca, setor.nomeSetor, obra.nomeobra, usuario.nomeUsuario, tipousuario.nomeTipoUsuario, registroentrada.dataEntrada as data\n" +
                            "    FROM registroentrada\n" +
                            "    INNER JOIN item ON registroentrada.codItem = item.codItem\n" +
                            "    INNER JOIN tipoitem ON item.codTipoItem = tipoitem.codTipoItem\n" +
                            "    INNER JOIN marca ON item.codMarca = marca.codMarca\n" +
                            "    INNER JOIN setor ON item.codSetor = setor.codSetor\n" +
                            "    INNER JOIN obra ON registroentrada.codObra = obra.codObra\n" +
                            "    INNER JOIN usuario ON registroentrada.codUsuario = usuario.codUsuario\n" +
                            "    INNER JOIN tipousuario ON usuario.codTipoUsuario = tipousuario.codTipoUsuario\n" +
                            "    UNION\n" +
                            "    SELECT 'saida' AS tipo,registrosaida.numNotaSaida, registrosaida.qtdSaida AS quantidade, tipoitem.nometipoitem, item.descricaoItem, marca.nomeMarca, setor.nomeSetor, obra.nomeobra, usuario.nomeUsuario, tipousuario.nomeTipoUsuario, registrosaida.dataSaida as data\n" +
                            "    FROM registrosaida\n" +
                            "    INNER JOIN item ON registrosaida.codItem = item.codItem\n" +
                            "    INNER JOIN tipoitem ON item.codTipoItem = tipoitem.codTipoItem\n" +
                            "    INNER JOIN marca ON item.codMarca = marca.codMarca\n" +
                            "    INNER JOIN setor ON item.codSetor = setor.codSetor\n" +
                            "    INNER JOIN obra ON registrosaida.codObra = obra.codObra\n" +
                            "    INNER JOIN usuario ON registrosaida.codUsuario = usuario.codUsuario\n" +
                            "    INNER JOIN tipousuario ON usuario.codTipoUsuario = tipousuario.codTipoUsuario"
                            + ") AS combined_result\r\n"
                            + "WHERE combined_result.data BETWEEN ? AND ?\r\n"
                            + "ORDER BY combined_result.data;");
            ps.setString(1, de);
            ps.setString(2, ate);
            rs = ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
