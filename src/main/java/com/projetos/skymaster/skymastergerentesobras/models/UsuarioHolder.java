package com.projetos.skymaster.skymastergerentesobras.models;

public class UsuarioHolder {

    private static UsuarioHolder instance;
    private int codUsuario;
    private String nome;
    private String senha;
    private String tipo;

    private UsuarioHolder() {

    }

    public static UsuarioHolder getInstance() {
        if (instance == null) {
            instance = new UsuarioHolder();
        }
        return instance;
    }

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

    @Override
    public String toString(){ return nome; }
}
