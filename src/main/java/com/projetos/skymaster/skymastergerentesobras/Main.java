package com.projetos.skymaster.skymastergerentesobras;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/TelaInicial.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 600);

        stage.setTitle("Bem-Vindo!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
