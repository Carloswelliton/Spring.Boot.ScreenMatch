package br.com.alura.screenMatch.service;

import br.com.alura.screenMatch.models.DadosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.ConnectException;

public class ConverteDados implements IConverteDados {
  private ObjectMapper mapper = new ObjectMapper();


  @Override
  public <T> T obterDados(String json, Class<T> classe) {
    try {
      return mapper.readValue(json, classe);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
