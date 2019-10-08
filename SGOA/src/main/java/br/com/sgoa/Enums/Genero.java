package br.com.sgoa.Enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public enum Genero implements Serializable{

    NA("Não Informar"),
    M("Masculino"),
    F("Feminino");

    @Getter private final String descricao;

    Genero(String descricao) {
        this.descricao = descricao;
    }
    public List<Genero> getListaGenero(){
        List<Genero> descricaoGeneros = new ArrayList<>();
        for (Genero genero : Genero.values()) {
            descricaoGeneros.add(genero);
            System.out.println(genero.getDescricao());

        }
        return descricaoGeneros;
    }
}
