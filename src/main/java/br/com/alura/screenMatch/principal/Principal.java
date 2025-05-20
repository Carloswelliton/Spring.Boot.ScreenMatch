package br.com.alura.screenMatch.principal;

import br.com.alura.screenMatch.service.ConsumoApi;

import java.util.Scanner;

public class Principal {
  private Scanner leitura = new Scanner(System.in);
  private ConsumoApi consumoApi = new ConsumoApi();

  private final String ENDERECO = "https://www.omdbapi.com/?t=";
  private final String API_KEY = "&apikey=96998504";

  public void exibeMenu(){

    System.out.println("Digite o nome da serie para buscar: ");
    var nomeSerie = leitura.nextLine();
    var json = consumoApi.obterDados(ENDERECO+nomeSerie
                            .replace(" ", "+")
                            +API_KEY);

  }
}
