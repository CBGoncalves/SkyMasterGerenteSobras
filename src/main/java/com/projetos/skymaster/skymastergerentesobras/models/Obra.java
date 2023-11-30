package com.projetos.skymaster.skymastergerentesobras.models;

public class Obra {
    private int codObra;
    private String nomeObra;

    public int getCodObra() {
        return codObra;
    }

    public void setCodObra(int codObra) {
        this.codObra = codObra;
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    @Override
    public String toString() {
        return nomeObra;
    }
}
