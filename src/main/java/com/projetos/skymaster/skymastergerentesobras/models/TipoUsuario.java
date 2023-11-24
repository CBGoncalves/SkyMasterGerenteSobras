package com.projetos.skymaster.skymastergerentesobras.models;

public class TipoUsuario {
    private static TipoUsuario instance;
    private String tipoUsuario;

    private TipoUsuario() {
        // Construtor privado para evitar instâncias adicionais
    }

    public static TipoUsuario getInstance() {
        if (instance == null) {
            instance = new TipoUsuario();
        }
        return instance;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

}
