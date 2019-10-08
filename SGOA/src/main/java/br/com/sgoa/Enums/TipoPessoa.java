package br.com.sgoa.Enums;

public enum TipoPessoa {

    PESSOA_FISICA("Pessoa fisica"),
    PESSOA_JURIDICA("Pessoa juridica");

    private String descricao;

    TipoPessoa(String descricao) {
    }

    public String getDescricao() {
        return descricao;
    }
}
