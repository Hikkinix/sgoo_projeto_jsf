package br.com.sgoa.Enums;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public enum StatusCompra implements Serializable {

    PAGA("Pagamento Efetuado"),
    PEDIDO("Pedido de realizacao de compra"),
    ENCERADA("Finalizada sem Faturamento"),
    PROCESSAMENTO("Pagamento não Efetuado");

    @Getter
    private String descricao;

    StatusCompra(String descricao) {
        this.descricao = descricao;
    }
}
