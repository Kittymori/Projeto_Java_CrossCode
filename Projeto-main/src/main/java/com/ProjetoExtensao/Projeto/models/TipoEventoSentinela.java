package com.ProjetoExtensao.Projeto.models;

public enum TipoEventoSentinela {

    TENTATIVA_SUICIDIO("Tentativa de suicídio"),
    QUEDA("Quedas"),
    DIARREIA("Diarreia"),
    ESCABIOSE("Escabiose"),
    DESIDRATACAO("Desidratação"),
    ULCERA_PRESSAO("Úlcera por pressão"),
    DESNUTRICAO("Desnutrição"),
    OBITO("Óbito"),
    PRESSAO_ARTERIAL("Pressão arterial"),
    GLICEMIA("Glicemia"),
    TEMPERATURA("Temperatura");

    private final String descricao;

    TipoEventoSentinela(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoEventoSentinela getType(String descricao) {
        for (TipoEventoSentinela tipo : values()) {
            if (tipo.descricao.equalsIgnoreCase(descricao.trim())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de evento sentinela inválido: " + descricao);
    }
}