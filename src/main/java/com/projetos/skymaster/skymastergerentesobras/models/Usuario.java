package com.projetos.skymaster.skymastergerentesobras.models;

public class Usuario {

    private int codUsuario;
    private String nome;
    private String senha;
    private String tipo;

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipoUsuario) {
        this.tipo = tipoUsuario;
    }
}
