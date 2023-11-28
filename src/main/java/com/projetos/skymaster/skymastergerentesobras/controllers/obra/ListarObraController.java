package com.projetos.skymaster.skymastergerentesobras.controllers.obra;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.ObraDao;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.Obra;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarObraController {
    @FXML
    private AnchorPane root;
    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Obra, Integer> codObraColumn;
    @FXML
    private TableColumn<Obra, String> nomeObraColumn;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnDeletar;
    private ObraDao obraDao;

    public void initialize() throws SQLException {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/NavigationBar.fxml"));
            Parent menuBar = loader.load();
            root.getChildren().add(menuBar);

            NavigationBarController menuBarController = loader.getController();

            String tipoUsuario = TipoUsuarioNav.getInstance().getTipoUsuario();

            if ("Administrador".equals(tipoUsuario)) {
                menuBarController.exibirOpcoesAdmin();
            } else if ("Funcionário".equals(tipoUsuario)) {
                menuBarController.exibirOpcoesFuncionario();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        obraDao = new ObraDao();

        codObraColumn.setCellValueFactory(new PropertyValueFactory<>("codObra"));
        nomeObraColumn.setCellValueFactory(new PropertyValueFactory<>("nomeObra"));

        try {
            List<Obra> obras = obraDao.selectAllObras();
            tableView.getItems().addAll(obras);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleEditarButtonAction(ActionEvent event) {
    }

    public void handleDeletarButtonAction(ActionEvent event) {
    }
}
