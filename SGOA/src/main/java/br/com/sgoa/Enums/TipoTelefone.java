package br.com.sgoa.Enums;

public enum TipoTelefone {

    FIXO("fixo"),
    CELULAR("celular");

    private String descricao;

    TipoTelefone(String celular) {
    }

    public String getDescricao() {
        return descricao;
    }
}
