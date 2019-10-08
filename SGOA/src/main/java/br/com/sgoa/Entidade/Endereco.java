package br.com.sgoa.Entidade;


import javax.faces.event.ActionListener;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Endereco")
public class Endereco implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long idEndereco;

    @Column(name = "cep_endereco")
    @Getter
    @Setter
    private String cep;

    @Column(name = "numero_casa_endereco", nullable = false)
    @Getter
    @Setter
    private String numero;

    @Column(name = "rua_endereco")
    @Getter
    @Setter
    private String rua = "";

    @Column(name = "bairro_endereco", length = 255)
    @Getter
    @Setter
    private String bairro = "";

    @Column(name = "endereco_padrao")
    @Getter
    @Setter
    private Boolean padrao = false;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "cidade")
    @Getter
    @Setter
    private Cidade cidade;

    @ManyToOne(cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Pessoa pessoa;

    @Override
    public Long getId() {
        return idEndereco;
    }


    public String getLogradouro(){
        return rua +", "+ bairro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;

        Endereco endereco = (Endereco) o;

        if (idEndereco != null ? !idEndereco.equals(endereco.idEndereco) : endereco.idEndereco != null) return false;
        return cep != null ? cep.equals(endereco.cep) : endereco.cep == null;
    }

    @Override
    public int hashCode() {
        int result = idEndereco != null ? idEndereco.hashCode() : 0;
        result = 31 * result + (cep != null ? cep.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "idEndereco=" + idEndereco +
                ", cep='" + cep + '\'' +
                ", numero='" + numero + '\'' +
                ", rua='" + rua + '\'' +
                ", bairro='" + bairro + '\'' +
                ", cidade=" + cidade +
                '}';
    }

}
