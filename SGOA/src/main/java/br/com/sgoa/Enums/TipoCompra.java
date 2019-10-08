package br.com.sgoa.Enums;

public enum TipoCompra {

    PEDIDO("PEDIDO"),
    CANCELADA("Cancelada"),
    FATURAMENTO("Faturamento");

    private String descricao;

    TipoCompra(String descricao) {
    }
}
