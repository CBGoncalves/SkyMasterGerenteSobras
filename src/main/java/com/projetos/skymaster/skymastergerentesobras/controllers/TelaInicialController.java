package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.projetos.skymaster.skymastergerentesobras.models.Registro;
import com.projetos.skymaster.skymastergerentesobras.dao.RegistroDao;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TelaInicialController {

    @FXML
    private TableView<Registro> tableView;
    @FXML
    private TableColumn<Registro, String> tipoColumn;
    @FXML
    private TableColumn<Registro, String> numNotaColumn;
    @FXML
    private TableColumn<Registro, Integer> qtdColumn;
    @FXML
    private TableColumn<Registro, String> descricaoColumn;
    @FXML
    private TableColumn<Registro, String> tipoItemColumn;
    @FXML
    private TableColumn<Registro, String> marcaColumn;
    @FXML
    private TableColumn<Registro, String> obraColumn;
    @FXML
    private TableColumn<Registro, String> usuarioColumn;
    @FXML
    private TableColumn<Registro, LocalDate> dataColumn;

    @FXML
    private MenuBar elemento1;

    @FXML
    private MenuBar elemento2;


    private Usuario user;

    // Injeta o UserDao
    public void setUser(Usuario user) {
        this.user = user;
        // Atualiza a interface com base no tipoUsuario
        atualizarInterface();
    }
    private RegistroDao registroDao;

    public void initialize() throws SQLException {

        registroDao = new RegistroDao();

        tipoColumn.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        numNotaColumn.setCellValueFactory(new PropertyValueFactory<>("numNotaEntrada"));
        qtdColumn.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        descricaoColumn.setCellValueFactory(new PropertyValueFactory<>("descricaoItem"));
        tipoItemColumn.setCellValueFactory(new PropertyValueFactory<>("nomeTipoItem"));
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("nomeMarca"));
        obraColumn.setCellValueFactory(new PropertyValueFactory<>("nomeObra"));
        usuarioColumn.setCellValueFactory(new PropertyValueFactory<>("nomeUsuario"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        try {
            List<Registro> registros = registroDao.selectRegistersByDate();

            tableView.getItems().addAll(registros);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void atualizarInterface() {
        // Obtém o tipoUsuario do UserDao
        String tipoUsuario = user.getTipoUsuario();

        // Faz a condicional com base no tipoUsuario
        if ("Funcionário".equals(tipoUsuario)) {
            // Carrega o elemento 1, por exemplo
            elemento2.setVisible(false); // Esconde o elemento 2, se necessário
        } else {
            // Carrega o elemento 2 ou outra lógica
            elemento1.setVisible(false); // Esconde o elemento 1, se necessário
        }
    }
}
