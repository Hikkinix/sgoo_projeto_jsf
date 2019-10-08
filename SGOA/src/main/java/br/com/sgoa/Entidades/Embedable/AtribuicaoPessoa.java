/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Entidades.Embedable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

/**
 *
 * @author orin
 */
@Embeddable
public class AtribuicaoPessoa implements Serializable {

    @Column(name = "cliente_Pessoa")
    private Boolean cliente = false;

    @Column(name = "fornecedor_Pessoa")
    private Boolean fornecedor = false;

    @Column(name = "colaborador_Pessoa")
    private Boolean colaborador = false;

    @Column(name = "transportadora_Pessoa")
    private Boolean transportadora = false;

    public Boolean getCliente() {
        return cliente;
    }

    public void setCliente(Boolean cliente) {
        this.cliente = cliente;
    }

    public Boolean getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Boolean fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Boolean getColaborador() {
        return colaborador;
    }

    public void setColaborador(Boolean colaborador) {
        this.colaborador = colaborador;
    }

    public Boolean getTransportadora() {
        return transportadora;
    }

    public void setTransportadora(Boolean transportadora) {
        this.transportadora = transportadora;
    }

    

}
