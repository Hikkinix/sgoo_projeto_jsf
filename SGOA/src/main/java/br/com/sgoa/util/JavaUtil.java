package br.com.sgoa.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JavaUtil
{
    public void errorMensagem(String mens) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        mens,
                        null));
    }
}
