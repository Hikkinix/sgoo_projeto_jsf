package br.com.sgoa.Enums;

import lombok.Getter;

import java.io.Serializable;

public enum StatusVenda implements Serializable {

    PAGA("Pagamento Efetuado"),
    PEDIDO("Pedido de realizacao de compra"),
    ENCERADA("Finalizada sem Faturamento"),
    PROCESSAMENTO("Pagamento não Efetuado");

    @Getter
    private String descricao;

    StatusVenda(String descricao) {
        this.descricao = descricao;
    }
}
