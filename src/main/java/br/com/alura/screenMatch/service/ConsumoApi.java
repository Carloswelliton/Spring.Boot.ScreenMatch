package br.com.alura.screenMatch.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

  public String obterDados(String endereco){
    HttpClient client = HttpClient.newHttpClient();

    HttpRequest request = HttpRequest.newBuilder().uri(URI.create(endereco)).build();

    HttpResponse<String> response = null;
    try{
      response = client.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    String json = response.body();

    if(response.statusCode() != 200){
      throw new RuntimeException("Erro na busca da API "+response.body());
    }
    return json;
  }
}
