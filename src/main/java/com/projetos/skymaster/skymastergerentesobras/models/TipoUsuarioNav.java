package com.projetos.skymaster.skymastergerentesobras.models;

public class TipoUsuarioNav {
    private static TipoUsuarioNav instance;
    private String tipoUsuario;

    private TipoUsuarioNav() {
        
    }

    public static TipoUsuarioNav getInstance() {
        if (instance == null) {
            instance = new TipoUsuarioNav();
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
