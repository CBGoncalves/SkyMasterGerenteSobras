package com.projetos.skymaster.skymastergerentesobras;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/com/projetos/skymaster/skymastergerentesobras/img/logo_sky_reduzida.jpg"));
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/Login.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setTitle("Bem-Vindo!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
