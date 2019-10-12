package br.com.sgoa.Enums;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum PerfilUsuario implements Serializable{

    MASTER("Não Informar"),
    ADM("Masculino"),
    USER("Feminino");

    @Getter private final String descricao;

    PerfilUsuario(String descricao) {
        this.descricao = descricao;
    }
    public List<PerfilUsuario> getListaGenero(){
        List<PerfilUsuario> descricaoGeneros = new ArrayList<>();
        for (PerfilUsuario genero : PerfilUsuario.values()) {
            descricaoGeneros.add(genero);
            System.out.println(genero.getDescricao());

        }
        return descricaoGeneros;
    }
}
