package br.com.sgoa.Entidade;

import br.com.sgoa.util.MD5;
import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuario;

    @Column(name = "login_usuario")
    private String login;

    @Column(name = "senha_usuario")
    private String senha;

    @Transient
    private String comfirmarsenha;

    @JoinColumn(name = "pessoa")
    @ManyToOne
    private Pessoa pessoa;


    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) throws Exception {
        this.senha = MD5.main(senha);
    }

    public String getComfirmarsenha() {
        return comfirmarsenha;
    }

    public void setComfirmarsenha(String comfirmarsenha) throws Exception {
        this.comfirmarsenha = MD5.main(comfirmarsenha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;

        Usuario usuario = (Usuario) o;

        if (getIdUsuario() != null ? !getIdUsuario().equals(usuario.getIdUsuario()) : usuario.getIdUsuario() != null)
            return false;
        return getPessoa() != null ? getPessoa().equals(usuario.getPessoa()) : usuario.getPessoa() == null;
    }

    @Override
    public int hashCode() {
        int result = getIdUsuario() != null ? getIdUsuario().hashCode() : 0;
        result = 31 * result + (getPessoa() != null ? getPessoa().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UsuarioFacade{" +
                "idUsuario=" + idUsuario +
                ", login='" + login + '\'' +
                '}';
    }
}
