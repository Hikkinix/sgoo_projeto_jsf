package br.com.sgoa.util;

import javax.inject.Named;

@Named(value = "icons")
public class iconsUtil {

    private String editar;
    private String salvar;
    private String excluir;
    private String voltar;
    private String atualizar;
    private String adicionar;
    private String estornar;
    private String novo;
    private String pagar;
    private String deletar;

    public String getEditar() {
        return "ui-icon-pencil";
    }

    public String getDeletar() {
        return "ui-icon-close";
    }

    public String getSalvar() {
        return "ui-icon-disk";
    }

    public String getExcluir() {
        return excluir;
    }

    public String getVoltar() {
        return "ui-icon-arrowthick-1-w";
    }

    public String getAdicionar() {
        return "ui-icon-plusthick";
    }

    public String getAtualizar() {
        return atualizar;
    }

    public String getEstornar() {
        return estornar;
    }

    public String getNovo() {
        return "ui-icon-document";
    }

    public String getPagar() {
        return "ui-icon-calculator";
    }
}
