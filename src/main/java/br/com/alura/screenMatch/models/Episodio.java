package br.com.alura.screenMatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
  private int temporada;
  private String titulo;
  private int numeroEpisodio;
  private double avaliacao;
  private LocalDate dataLancamento;

  public Episodio(int numeroTemporada, DadosEpisodio dadosEpisodio){
    this.temporada = numeroTemporada;
    this.titulo = dadosEpisodio.titulo();
    this.numeroEpisodio = dadosEpisodio.numeroEpisodio();

    try {
      this.avaliacao = Double.parseDouble(dadosEpisodio.avaliacao());
    } catch (NumberFormatException e) {
      this.avaliacao = 0.0;
    }

    try {
      this.dataLancamento = LocalDate.parse(dadosEpisodio.dataDeLancamento());
    } catch (DateTimeParseException e) {
      this.dataLancamento = null;
    }
  }

  public int getTemporada() {
    return temporada;
  }

  public void setTemporada(int temporada) {
    this.temporada = temporada;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public int getNumeroEpisodio() {
    return numeroEpisodio;
  }

  public void setNumeroEpisodio(int numeroEpisodio) {
    this.numeroEpisodio = numeroEpisodio;
  }

  public double getAvaliacao() {
    return avaliacao;
  }

  public void setAvaliacao(double avaliacao) {
    this.avaliacao = avaliacao;
  }

  public LocalDate getDataDeLancamento() {
    return dataLancamento;
  }

  public void setDataDeLancamento(LocalDate dataDeLancamento) {
    this.dataLancamento = dataDeLancamento;
  }

  @Override
  public String toString() {
    return "temporada=" + temporada +
        ", titulo='" + titulo + '\'' +
        ", numeroEpisodio=" + numeroEpisodio +
        ", avaliacao=" + avaliacao +
        ", dataDeLancamento=" + dataLancamento;
  }
}
