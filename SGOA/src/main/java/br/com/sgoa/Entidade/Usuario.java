package br.com.sgoa.Entidade;

import br.com.sgoa.Enums.PerfilUsuario;
import br.com.sgoa.util.MD5;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter @Setter
    private Long idUsuario;

    @Column(name = "login_usuario")
    @Getter @Setter
    private String login;

    @Column(name = "senha_usuario")
    @Getter @Setter
    private String senha;

    @Transient
    @Getter @Setter
    private String comfirmarsenha;

    @JoinColumn(name = "pessoa")
    @OneToOne
    @Getter @Setter
    private Pessoa pessoa;

    @Column(name = "perfil")
    @Getter @Setter
    private PerfilUsuario perfilUsuario;


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
