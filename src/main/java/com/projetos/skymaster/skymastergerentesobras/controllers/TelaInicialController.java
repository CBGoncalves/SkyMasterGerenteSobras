package com.projetos.skymaster.skymastergerentesobras.controllers;

import com.projetos.skymaster.skymastergerentesobras.dao.RegistroDao;
import com.projetos.skymaster.skymastergerentesobras.models.Registro;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    private MenuBar navFuncionario;
    @FXML
    private MenuBar navAdmin;
    private Usuario u;
    private RegistroDao registroDao;

    public void setUsuario(Usuario u) {
        this.u = u;
        atualizarInterface();
    }

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
        String tipoUsuario = u.getTipoUsuario();

        if ("Funcionário".equals(tipoUsuario)) {
            navAdmin.setVisible(false);
        } else {
            navFuncionario.setVisible(false);
        }
    }
}
