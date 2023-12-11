package com.projetos.skymaster.skymastergerentesobras.dao;

import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.Setor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SetorDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";

    public List<Setor> selectAllSetores() throws SQLException {
        List<Setor> setorList = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
            PreparedStatement preparedStatement = con.prepareStatement("SELECT codSetor, nomeSetor FROM setor");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Setor setor = new Setor();
                setor.setCodSetor(rs.getInt("codSetor"));
                setor.setNomeSetor(rs.getString("nomeSetor"));
                setorList.add(setor);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return setorList;
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
