package com.projetos.skymaster.skymastergerentesobras.models;

public class Item {
    private int codItem;
    private String nomeTipoItem;
    private String descricaoItem;
    private String nomeMarca;
    private Double quantidadeItem;

    public Double getQuantidadeItem() {
        return quantidadeItem;
    }

    public void setQuantidadeItem(Double quantidadeItem) {
        this.quantidadeItem = quantidadeItem;
    }

    public int getCodItem() {
        return codItem;
    }

    public void setCodItem(int codItem) {
        this.codItem = codItem;
    }

    public String getNomeTipoItem() {
        return nomeTipoItem;
    }

    public void setNomeTipoItem(String nomeTipoItem) {
        this.nomeTipoItem = nomeTipoItem;
    }

    public String getDescricaoItem() {
        return descricaoItem;
    }

    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    @Override
    public String toString() {
        return nomeTipoItem + "-" + descricaoItem;
    }
}
