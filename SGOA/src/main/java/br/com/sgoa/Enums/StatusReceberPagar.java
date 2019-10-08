package br.com.sgoa.Enums;

import lombok.Getter;

import java.io.Serializable;

public enum StatusReceberPagar implements Serializable {

    PAGA("Pagamento Efetuado"),
    GERADA("Pedido de realizacao de conta"),
    CANCELADA("Pedido Cancelado"),
    PROCESSAMENTO("Pagamento não Efetuado");

    @Getter
    private String descricao;

    StatusReceberPagar(String descricao) {
        this.descricao = descricao;
    }
}
