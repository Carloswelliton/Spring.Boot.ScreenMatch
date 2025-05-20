package br.com.alura.screenMatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season")int temporada,
                             @JsonAlias("Episodes")List<DadosEpisodio> dadosEpisodios) {}
