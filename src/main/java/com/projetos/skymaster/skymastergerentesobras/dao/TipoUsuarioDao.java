package com.projetos.skymaster.skymastergerentesobras.dao;

import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoUsuarioDao {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/smgerentesobra";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";


    public List<TipoUsuario> getAllTipoUsuario() throws SQLException {
        List<TipoUsuario> tiposUsuario = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);

            PreparedStatement preparedStatement = con.prepareStatement("SELECT codTipoUsuario, nomeTipoUsuario FROM tipousuario;");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                TipoUsuario tp = new TipoUsuario();
                tp.setCodTipoUsuario(rs.getInt("codTipoUsuario"));
                tp.setNomeTipoUsuario(rs.getString("nomeTipoUsuario"));
                tiposUsuario.add(tp);
            }
            System.out.println("Tipos de usuário obtidos: " + tiposUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiposUsuario;
    }

}
