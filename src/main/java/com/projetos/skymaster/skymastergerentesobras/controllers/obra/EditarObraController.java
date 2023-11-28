package com.projetos.skymaster.skymastergerentesobras.controllers.obra;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.dao.ObraDao;
import com.projetos.skymaster.skymastergerentesobras.models.Obra;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class EditarObraController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField campoCodObra;
    @FXML
    private TextField campoNomeObra;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCancelar;
    private Obra obra;

    public EditarObraController(Obra obra) {
        this.obra = obra;
    }

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

        campoCodObra.setText(Integer.toString(obra.getCodObra()));
        campoNomeObra.setText(obra.getNomeObra());

        btnEditar.setOnAction(event -> {
            try {
                handleEditarButtonAction(event);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        btnCancelar.setOnAction(event -> {
            try {
                handleCancelarButtonAction(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
    @FXML
    private void handleEditarButtonAction(ActionEvent event) throws SQLException{
        Window owner = btnEditar.getScene().getWindow();

        String codigoObra = campoCodObra.getText();
        String nomeObra = campoNomeObra.getText();
        int codigoParametro = obra.getCodObra();

        if (codigoObra.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro na Edição",
                    "Preencha o campo de Código da Obra!");
            return;
        }

        if (nomeObra.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro na Edição",
                    "Preencha o campo de Nome da Obra");
            return;
        }

        int codObra = Integer.parseInt(codigoObra);
        ObraDao obraDao = new ObraDao();
        obraDao.updateObra(codObra, nomeObra, codigoParametro);

        try {
            Stage stageEditar = (Stage) btnEditar.getScene().getWindow();
            stageEditar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/obra/ListarObra.fxml"));
            Parent root = loader.load();

            Stage listarObra = new Stage();
            listarObra.setTitle("Listar Obra");
            listarObra.setScene(new Scene(root));
            listarObra.setResizable(false);
            listarObra.getIcons().add(icon);
            listarObra.show();

            showAlert(Alert.AlertType.CONFIRMATION, owner,"Sucesso!",
                    "Obra Editada com Sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageEditar = (Stage) btnCancelar.getScene().getWindow();
        stageEditar.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/obra/ListarObra.fxml"));
        Parent root = loader.load();

        Stage listarObra = new Stage();
        listarObra.setTitle("Listar Obra");
        listarObra.setScene(new Scene(root));
        listarObra.setResizable(false);
        listarObra.getIcons().add(icon);
        listarObra.show();
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
