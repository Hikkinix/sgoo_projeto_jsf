package br.com.sgoa.Security;

import br.com.sgoa.Entidade.Usuario;
import com.github.adminfaces.template.session.AdminSession;
import org.omnifaces.util.Faces;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

import com.github.adminfaces.template.config.AdminConfig;

import javax.inject.Inject;

@Named
@SessionScoped
public class LogonMB implements Serializable {

    private Usuario usuario;
    private boolean remember;
    @Inject
    private AdminConfig adminConfig;

    public void login(Usuario usu) throws IOException {
        usuario.setLogin(usu.getLogin());
        Faces.getExternalContext().getFlash().setKeepMessages(true);
        Faces.redirect(adminConfig.getIndexPage());
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public AdminConfig getAdminConfig() {
        return adminConfig;
    }

    public void setAdminConfig(AdminConfig adminConfig) {
        this.adminConfig = adminConfig;
    }
}