package com.projetos.skymaster.skymastergerentesobras.models;

import java.time.LocalDate;

public class Registro {
    private String tipo;
    private String numNotaEntrada;
    private int quantidade;
    private String descricaoItem;
    private String nomeTipoItem;
    private String nomeMarca;
    private String nomeObra;
    private String nomeUsuario;
    private String nomeTipoUsuario;
    private LocalDate data;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumNotaEntrada() {
        return numNotaEntrada;
    }

    public void setNumNotaEntrada(String numNotaEntrada) {
        this.numNotaEntrada = numNotaEntrada;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getDescricaoItem() {
        return descricaoItem;
    }

    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }

    public String getNomeTipoItem() {
        return nomeTipoItem;
    }

    public void setNomeTipoItem(String nomeTipoItem) {
        this.nomeTipoItem = nomeTipoItem;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    public String getNomeObra() {
        return nomeObra;
    }

    public void setNomeObra(String nomeObra) {
        this.nomeObra = nomeObra;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeTipoUsuario() {
        return nomeTipoUsuario;
    }

    public void setNomeTipoUsuario(String nomeTipoUsuario) {
        this.nomeTipoUsuario = nomeTipoUsuario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
