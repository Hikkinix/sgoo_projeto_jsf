/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgoa.Converter;

import br.com.sgoa.Abstract.AbstractFacade;
import br.com.sgoa.Controller.EstadoController;
import br.com.sgoa.Entidade.EntidadePai;
import br.com.sgoa.Entidade.Estado;
import br.com.sgoa.Facade.EstadoFacade;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(value = "teste")
public class basicConverter implements Converter{

    private EstadoFacade abstractFacade;

    public basicConverter(EstadoFacade abstractFacade) {
        this.abstractFacade = abstractFacade;
    }

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        return abstractFacade.buscar(Long.parseLong(string));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((EntidadePai)o).getId().toString();
    }

}
