package br.com.sgoa.Entidade;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Entity
@DiscriminatorValue(value = "C")
public class ParcelasCompra extends Parcelas implements Serializable {

    @JoinColumn(name = "compra", referencedColumnName = "idCompra")
    @ManyToOne
    @Getter @Setter
    private Compra compra;

    public String getDescreverPlanoPaga(Compra compra) {
        String descrever = " ";
//        this.setCompra(compra);
        if (this.getFormaPaga() != null) {
            descrever = " " + this.getFormaPaga().getNomeFormPag()
                    + ", " + this.getFormaPaga().getTipoPlano().getDescricao();
            if (this.getNumParcela() > 0) {
                descrever += " - " + this.getNumParcela().toString() + "x de R$" + getValorParcela();
            }
        }
        return descrever;
    }

    public String getDescreverPlanoPagaEntrada(Compra compra) {
        String descrever = " ";
        if (this.getFormaPaga() != null && isPossuiEntrada()) {
            descrever = " Entradad de R$" + getValorPrimeiraParcela().toString();
        } else {
            descrever = "Sem Entrada";
        }
        return descrever;
    }
}
