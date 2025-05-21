package br.com.alura.screenMatch.principal;

import br.com.alura.screenMatch.models.DadosEpisodio;
import br.com.alura.screenMatch.models.DadosSerie;
import br.com.alura.screenMatch.models.DadosTemporada;
import br.com.alura.screenMatch.models.Episodio;
import br.com.alura.screenMatch.service.ConsumoApi;
import br.com.alura.screenMatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;



public class Principal {
  private Scanner leitura = new Scanner(System.in);
  private ConsumoApi consumoApi = new ConsumoApi();
  private ConverteDados conversor = new ConverteDados();
  private final String ENDERECO = "https://www.omdbapi.com/?t=";
  private final String API_KEY = "&apikey=96998504";

  public void exibeMenu(){

    System.out.println("Digite o nome da serie para buscar: ");
    var nomeSerie = leitura.nextLine();
    var json = consumoApi.obterDados(ENDERECO+nomeSerie.replace(" ", "+") +API_KEY);
    DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
    System.out.println(dados);

    List<DadosTemporada> temporadas = new ArrayList<>();

		for(int i =1; i <= dados.totalTemporadas(); i++){
			json = consumoApi.obterDados(ENDERECO+nomeSerie.replace(" ", "+")+"&season="+i+API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}

		temporadas.forEach(System.out::println);

//    for(int i = 0; i < dados.totalTemporadas(); i++){
//      List<DadosEpisodio> episodiosTemporada = temporadas.get(i).dadosEpisodios();
//      for(int j = 0; j < episodiosTemporada.size(); j++){
//        System.out.println(episodiosTemporada.get(j).titulo());
//      }
//    }
//    temporadas.forEach(t -> t.dadosEpisodios()
//                                            .forEach(e-> System.out.println(e.titulo())));


    List<DadosEpisodio> dadosEpisodios = temporadas.stream()
        .flatMap(t -> t.dadosEpisodios().stream())
        .collect(Collectors.toList());
    System.out.println("\nTop 5 episodios: ");
    dadosEpisodios.stream()
            .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
            .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
            .limit(5)
            .forEach(System.out::println);

    List<Episodio> episodios = temporadas.stream()
        .flatMap(t -> t.dadosEpisodios().stream()
            .map(d -> new Episodio(t.temporada(), d)))
        .collect(Collectors.toList());

    episodios.forEach(System.out::println);

    System.out.println("Digite uma data de inicio do episodio");

    var ano = leitura.nextInt();
    leitura.nextLine();

    LocalDate dataBusca = LocalDate.of(ano ,1 ,1);

    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    episodios.stream()
        .filter(e -> e.getDataDeLancamento() != null && e.getDataDeLancamento().isAfter(dataBusca))
        .forEach(e -> System.out.println(
            "Temporada: " + e.getTemporada() +
                " Episodio: " + e.getTitulo() +
                " Data de Lançamento: "+ e.getDataDeLancamento().format(formatador)
        ));



  }
}
