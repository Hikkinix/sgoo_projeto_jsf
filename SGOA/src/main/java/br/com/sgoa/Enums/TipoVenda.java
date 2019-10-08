package br.com.sgoa.Enums;

public enum TipoVenda {

    PEDIDO("PEDIDO"),
    CANCELADA("Cancelada"),
    FATURAMENTO("Faturamento");

    private String descricao;

    TipoVenda(String descricao) {
    }
}
