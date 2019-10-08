package br.com.sgoa.Entidade;

import br.com.sgoa.Entidades.Embedable.AtribuicaoPessoa;
import br.com.sgoa.Enums.Genero;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pessoa")
public class Pessoa implements Serializable, EntidadePai {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter private Long idPessoa;

    @Column(name = "nome_pessoa", nullable = false)
    @Getter @Setter private String nomePessoa = "";

    @Column(name = "cpfCnpj_pessoa", nullable = false, unique = true)
    @Getter @Setter private String cpfcnpj = "";

    @Column(name = "genero_pessoa")
    @Getter @Setter private Genero genero = Genero.NA;

    @Column(name = "email_pessoa", length = 30)
    @Getter @Setter private String emaill = "";

    @JoinColumn(name = "grupo_pessoa")
    @ManyToOne(cascade = CascadeType.ALL)
    @Getter @Setter private Grupo grupoPessoa;

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "pessoa")
    @Getter @Setter private List<Telefone> telefones = new ArrayList<>();

    @Embedded
    @Getter @Setter private AtribuicaoPessoa atribuicaoPessoa = new AtribuicaoPessoa();

    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY,
            mappedBy = "pessoa")
    @Getter @Setter private List<Endereco> enderecos = new ArrayList<>();


    public Pessoa() {
    }

    public Pessoa(List<Telefone> telefone) {
        this.telefones = telefone;
    }

    public void addTelefone(Telefone telefone){
        telefones.add(telefone);
        telefone.setPessoa(this);
    }

    public void addEndereco(Endereco endereco){
        enderecos.add(endereco);
        endereco.setPessoa(this);

    }

    public Telefone getTelefone(){
        Telefone tel = new Telefone();
        if (telefones != null && !telefones.isEmpty()) {
            for (Telefone telefone : telefones) {
                if (telefone.getPadrao()) {
                    return telefone;
                }
                tel = telefone;
            }
            tel = telefones.get(0);
        }
            return tel;
    }

    public Endereco getEndereco(){
        Endereco ende = new Endereco();
        if (enderecos != null && !enderecos.isEmpty()) {
            for (Endereco endereco : enderecos) {
                if (endereco.getPadrao()) {
                    return endereco;
                }
                ende = endereco;
            }
            ende = enderecos.get(0);
        }
        return ende;
    }

    @Override
    public Long getId() {
        return idPessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;

        Pessoa pessoa = (Pessoa) o;

        if (idPessoa != null ? !idPessoa.equals(pessoa.idPessoa) : pessoa.idPessoa != null) return false;
        return cpfcnpj != null ? cpfcnpj.equals(pessoa.cpfcnpj) : pessoa.cpfcnpj == null;
    }

    @Override
    public int hashCode() {
        int result = idPessoa != null ? idPessoa.hashCode() : 0;
        result = 31 * result + (cpfcnpj != null ? cpfcnpj.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "idPessoa=" + idPessoa +
                '}';
    }
}
