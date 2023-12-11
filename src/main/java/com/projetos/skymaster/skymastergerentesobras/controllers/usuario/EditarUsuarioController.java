package com.projetos.skymaster.skymastergerentesobras.controllers.usuario;

import com.projetos.skymaster.skymastergerentesobras.controllers.NavigationBarController;
import com.projetos.skymaster.skymastergerentesobras.dao.TipoUsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.dao.UsuarioDao;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuario;
import com.projetos.skymaster.skymastergerentesobras.models.TipoUsuarioNav;
import com.projetos.skymaster.skymastergerentesobras.models.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditarUsuarioController {
    @FXML
    private AnchorPane root;
    @FXML
    private ImageView logoSky;
    @FXML
    private TextField campoNome;
    @FXML
    private PasswordField campoSenha;
    @FXML
    private PasswordField campoConfirmarSenha;
    @FXML
    private ChoiceBox<TipoUsuario> campoTipoUsuario;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnCancelar;
    private TipoUsuarioDao tipoUsuarioDao;
    private Usuario usuario;

    public EditarUsuarioController(TipoUsuarioDao tipoUsuarioDao, Usuario usuario) {
        this.tipoUsuarioDao = tipoUsuarioDao;
        this.usuario = usuario;
    }

    public void initialize() throws SQLException {
        try {
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

            try {
                List<TipoUsuario> tiposUsuario = tipoUsuarioDao.getAllTipoUsuario();
                ObservableList<TipoUsuario> observableList = FXCollections.observableArrayList(tiposUsuario);
                campoTipoUsuario.setItems(observableList);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        campoNome.setText(usuario.getNome());
        campoSenha.setText(usuario.getSenha());
        campoConfirmarSenha.setText(usuario.getSenha());

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

    public void handleEditarButtonAction(ActionEvent event) throws SQLException {
        Window owner = btnEditar.getScene().getWindow();

        int codUsuario = usuario.getCodUsuario();
        String nome = campoNome.getText();
        String senha = campoSenha.getText();
        String confirmarSenha = campoConfirmarSenha.getText();
        String tipoUsuario;
        try {
            tipoUsuario = campoTipoUsuario.getValue().toString();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha os campos!");
            return;
        }


        if (nome.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha o campo usuário!");
            return;
        }
        if (senha.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Preencha o campo de senha!");
            return;
        }
        if (confirmarSenha.isEmpty()) {
            showAlert(Alert.AlertType.ERROR,owner,"Falha no Cadastro!",
                    "Preencha o campo de confirmação de senha!");
            return;
        }
        if (tipoUsuario.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Falha no Cadastro!",
                    "Selecione um tipo de usuário!");
            return;
        }
        if (!confirmarSenha.equals(senha)) {
            showAlert(Alert.AlertType.ERROR,owner,"Falha no Cadastro!",
                    "As senhas não coincidem! Por favor, preencha os campos de forma igual!");
            return;
        }

        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.updateUsuario(codUsuario, nome, senha, tipoUsuario);

        try {
            Stage stageEditar = (Stage) btnEditar.getScene().getWindow();
            stageEditar.close();

            Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/usuario/ListarUsuario.fxml"));
            Parent root = loader.load();

            Stage listarUsuario = new Stage();
            listarUsuario.setTitle("Listar Usuário");
            listarUsuario.setScene(new Scene(root));
            listarUsuario.setResizable(false);
            listarUsuario.getIcons().add(icon);
            listarUsuario.show();

            showAlert(Alert.AlertType.CONFIRMATION, owner,"Sucesso!",
                    "Usuário Editado com Sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleCancelarButtonAction(ActionEvent event) throws IOException {
        Stage stageEditar = (Stage) btnCancelar.getScene().getWindow();
        stageEditar.close();

        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/projetos/skymaster/skymastergerentesobras/views/usuario/ListarUsuario.fxml"));
        Parent root = loader.load();

        Stage listarUsuario = new Stage();
        listarUsuario.setTitle("Listar Usuário");
        listarUsuario.setScene(new Scene(root));
        listarUsuario.setResizable(false);
        listarUsuario.getIcons().add(icon);
        listarUsuario.show();
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
