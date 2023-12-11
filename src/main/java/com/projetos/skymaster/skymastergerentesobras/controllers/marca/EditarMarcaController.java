package com.projetos.skymaster.skymastergerentesobras.controllers.marca;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.MarcaDao;
import com.projetos.skymaster.skymastergerentesobras.models.Marca;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class EditarMarcaController {

    @FXML
    private AnchorPane root;
    @FXML
    private ImageView logoSky;
    @FXML
    private TextField campoCodMarca;
    @FXML
    private TextField campoNomeMarca;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCancelar;
    private Marca marca;

    public EditarMarcaController(Marca marca) {
        this.marca = marca;
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

        campoCodMarca.setText(Integer.toString(marca.getCodMarca()));
        campoNomeMarca.setText(marca.getNomeMarca());

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
        logoSky.setOnMouseClicked(mouseEvent -> {
            try {
                handleTelaInicial(mouseEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @FXML
    private void handleEditarButtonAction(ActionEvent event) throws SQLException{
        Window owner = btnEditar.getScene().getWindow();

        String codigoMarca = campoCodMarca.getText();
        String nomeMarca = campoNomeMarca.getText();
        int codigoParametro = marca.getCodMarca();

        if (codigoMarca.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro na Edição",
                    "Preencha o campo de Código da Marca!");
            return;
        }

        if (nomeMarca.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Erro na Edição",
                    "Preencha o campo de Nome da Marca");
            return;
        }

        int codMarca = Integer.parseInt(codigoMarca);
        MarcaDao marcaDao = new MarcaDao();
        marcaDao.updateMarca(codMarca, nomeMarca, codigoParametro);

        try {
            Stage stageEditar = (Stage) btnEditar.getScene().getWindow();
            stageEditar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/marca/ListarMarca.fxml"));
            Parent root = loader.load();

            Stage listarMarca = new Stage();
            listarMarca.setTitle("Listar Marca");
            listarMarca.setScene(new Scene(root));
            listarMarca.setResizable(false);
            listarMarca.getIcons().add(icon);
            listarMarca.show();

            showAlert(Alert.AlertType.CONFIRMATION, owner,"Sucesso!",
                    "Marca Editada com Sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancelarButtonAction(ActionEvent event) throws IOException{
        Stage stageEditar = (Stage) btnCancelar.getScene().getWindow();
        stageEditar.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/marca/ListarMarca.fxml"));
        Parent root = loader.load();

        Stage listarMarca = new Stage();
        listarMarca.setTitle("Listar Marca");
        listarMarca.setScene(new Scene(root));
        listarMarca.setResizable(false);
        listarMarca.getIcons().add(icon);
        listarMarca.show();
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void handleTelaInicial(MouseEvent mouseEvent) throws IOException{
        Stage stageGerarRelatorio = (Stage) btnCancelar.getScene().getWindow();
        stageGerarRelatorio.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/TelaInicial.fxml"));
        Parent root = loader.load();

        Stage telaInicial = new Stage();
        telaInicial.setTitle("Tela Inicial");
        telaInicial.setScene(new Scene(root));
        telaInicial.setResizable(false);
        telaInicial.getIcons().add(icon);
        telaInicial.show();
    }
}
