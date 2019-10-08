package br.com.sgoa.Entidade;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@Entity
@DiscriminatorValue(value = "V")
public class ParcelasVenda extends Parcelas implements Serializable {

    @JoinColumn(name = "venda", referencedColumnName = "idVenda")
    @ManyToOne
    @Getter @Setter
    private Venda venda;

    public String getDescreverPlanoPaga(Venda venda) {
        String descrever = " ";
//        this.setVenda(venda);
        if (this.getFormaPaga() != null) {
            descrever = " " + this.getFormaPaga().getNomeFormPag()
                    + ", " + this.getFormaPaga().getTipoPlano().getDescricao();
            if (this.getNumParcela() > 0) {
                descrever += " - " + this.getNumParcela().toString() + "x de R$" + getValorParcela();
            }
        }
        return descrever;
    }

    public String getDescreverPlanoPagaEntrada(Venda venda) {
        String descrever = " ";
        if (this.getFormaPaga() != null && isPossuiEntrada()) {
            descrever = " Entradad de R$" + getValorPrimeiraParcela().toString();
        } else {
            descrever = "Sem Entrada";
        }
        return descrever;
    }
}
