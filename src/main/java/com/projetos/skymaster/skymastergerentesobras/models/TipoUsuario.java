package com.projetos.skymaster.skymastergerentesobras.models;

public class TipoUsuario {
    private int codTipoUsuario;
    private String nomeTipoUsuario;

    public int getCodTipoUsuario() {
        return codTipoUsuario;
    }

    public void setCodTipoUsuario(int codTipoUsuario) {
        this.codTipoUsuario = codTipoUsuario;
    }

    public String getNomeTipoUsuario() {
        return nomeTipoUsuario;
    }

    public void setNomeTipoUsuario(String nomeTipoUsuario) {
        this.nomeTipoUsuario = nomeTipoUsuario;
    }

    @Override
    public String toString() {
        return nomeTipoUsuario;
    }
}
