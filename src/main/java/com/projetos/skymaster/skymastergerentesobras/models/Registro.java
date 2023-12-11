package com.projetos.skymaster.skymastergerentesobras.models;

import java.time.LocalDate;

public class Registro {

    private int codRegistro;
    private String tipo;
    private String numNotaEntrada;
    private int quantidade;
    private String descricaoItem;
    private String nomeTipoItem;
    private String nomeMarca;
    private String nomeSetor;
    private String nomeObra;
    private String nomeUsuario;
    private LocalDate data;

    public int getCodRegistro() {
        return codRegistro;
    }

    public void setCodRegistro(int codRegistro) {
        this.codRegistro = codRegistro;
    }

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

    public String getNomeSetor() {
        return nomeSetor;
    }

    public void setNomeSetor(String nomeSetor) {
        this.nomeSetor = nomeSetor;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
