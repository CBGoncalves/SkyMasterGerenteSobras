package com.projetos.skymaster.skymastergerentesobras.models;

public class Setor {
    private int codSetor;
    private String nomeSetor;

    public int getCodSetor() {
        return codSetor;
    }

    public void setCodSetor(int codSetor) {
        this.codSetor = codSetor;
    }

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
    }

    @Override
    public String toString() {
        return nomeSetor;
    }
}
