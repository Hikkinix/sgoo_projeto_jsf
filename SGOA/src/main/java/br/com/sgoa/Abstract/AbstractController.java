package br.com.sgoa.Abstract;


import br.com.sgoa.Converter.ConverterGenerico;
import br.com.sgoa.Converter.MoneyConverter;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractController<T> implements Serializable {

    private T selected;
    private ConverterGenerico converter;
    private List<T> lista;
    private boolean ativarView = false;
    private boolean ativarCreate = false;
    private boolean ativarList = false;

    private MoneyConverter moneyconverter;

    protected abstract AbstractFacade getEjb();

    protected abstract Class getClasse();

    protected abstract String getAutoComplete();

    public T getSelected() {
        return selected;
    }

    public void setSelected(T selected) {
        this.selected = selected;
    }

    public void errorMensagem(String mens) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(
                        FacesMessage.SEVERITY_ERROR,
                        mens,
                        null));
    }
    ///--------------
    public ConverterGenerico getConverter() {
        if (converter == null) {
            converter = new ConverterGenerico(getEjb());
        }
        return converter;
    }

    public void setConverter(ConverterGenerico conver) {
        this.converter = conver;
    }

    public MoneyConverter getMoneyconverter() {
        if (moneyconverter == null) {
            moneyconverter = new MoneyConverter();
        }
        return moneyconverter;
    }

    public void setMoneyconverter(MoneyConverter moneyconverter) {
        this.moneyconverter = moneyconverter;
    }

    public List<T> listaTodos() {
        return getEjb().listaTodos();
    }

    public List<T> listaFiltrando(String filtro) {
        return getEjb().listaFiltrando(filtro, getAutoComplete());
    }
    
     public List<T> listaFiltro(String filtro, String atributo) {
        return getEjb().listaFiltro(filtro, atributo);
    }

    ///
    @PostConstruct
    public void init() {
        layoutList();
    }

    public void layoutCreate() {
        ativarCreate = true;
        ativarView = false;
        ativarList = false;
    }

    public void layoutList() {
        ativarCreate = false;
        ativarView = false;
        ativarList = true;
    }

    public void layoutView() {
        ativarCreate = false;
        ativarView = true;
        ativarList = false;
    }

    ///  Metodos
    public void salvar() throws Exception{
        layoutList();
        getEjb().salvar(selected);
    }

    public void novo() {
        try {
            selected = (T) getClasse().newInstance();
            layoutCreate();
        } catch (InstantiationException ex) {
            Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AbstractController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir(T e) {
        getEjb().excluir(e);
    }

    public void editar(T e) {
        selected = e;
        layoutCreate();
    }


    /// 
    public boolean isAtivarView() {
        return ativarView;
    }

    public void setAtivarView(boolean ativarView) {
        this.ativarView = ativarView;
    }

    public boolean isAtivarCreate() {
        return ativarCreate;
    }

    public void setAtivarCreate(boolean ativarCreate) {
        this.ativarCreate = ativarCreate;
    }

    public boolean isAtivarList() {
        return ativarList;
    }

    public void setAtivarList(boolean ativatList) {
        this.ativarList = ativatList;
    }

}
