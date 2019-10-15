package br.com.sgoa.Controller;

import br.com.sgoa.Entidade.Usuario;
import br.com.sgoa.Facade.UsuarioFacade;
import br.com.sgoa.Security.LogonMB;
import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
@Specializes
public class LoginController extends AdminSession implements Serializable {

    @Inject
    private UsuarioFacade usuarioFacade;
    private Usuario usuario = new Usuario();
    private LogonMB facesLogin;
    private Boolean autorizarLogin = false;
/*
    public Boolean validaMenu(String menu){
        for (Permitir p : usuario.getPerfil().getPermitir()) { // verifica permisoes do perfil do usuario
            if(menu.equals(p.getNome())){ /// compara com os menus liberanos nas permisoes
                return true; /// retorna o que for altorizado ao usuario
            }
        }
        return false;
    }*/

    @Inject
    private AdminConfig adminConfig;

    public void login(Usuario usu) throws IOException {
        usuario.setLogin(usu.getLogin());
        Faces.getExternalContext().getFlash().setKeepMessages(true);
        Faces.redirect(adminConfig.getIndexPage());
    }

    @Override
    public boolean isLoggedIn() {
        return autorizarLogin;
    }
    public String logar() throws IOException {
        usuario = usuarioFacade.validaUsuario(usuario);
        if(usuario.getLogin().equals("NOP") || usuario != null){
            autorizarLogin = true;
            Faces.getExternalContext().getFlash().setKeepMessages(true);
            Faces.redirect(adminConfig.getIndexPage());
            return "";
        }else{
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Usuário e/ou senha inválidos!",
                            null));
            return "login.xhtml";
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }


}
