package br.com.alura.screenMatch.principal;

import br.com.alura.screenMatch.models.DadosEpisodio;
import br.com.alura.screenMatch.models.DadosSerie;
import br.com.alura.screenMatch.models.DadosTemporada;
import br.com.alura.screenMatch.models.Episodio;
import br.com.alura.screenMatch.service.ConsumoApi;
import br.com.alura.screenMatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

//    List<DadosEpisodio> dadosEpisodios = temporadas.stream()
//        .flatMap(t -> t.dadosEpisodios().stream())
//        .collect(Collectors.toList());
//    System.out.println("\nTop 10 episodios: ");
//    dadosEpisodios.stream()
//        .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//        //.peek(e -> System.out.println("Primeiro filtro (N/A)" + e))
//        .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//        //.peek(e -> System.out.println("Ordenação)" + e))
//        .limit(10)
//        //.peek(e -> System.out.println("Limite" + e))
//        .map(e -> e.titulo().toUpperCase())
//        //.peek(e -> System.out.println("Map)" + e))
//        .forEach(System.out::println);

    List<Episodio> episodios = temporadas.stream()
        .flatMap(t -> t.dadosEpisodios().stream()
        .map(d -> new Episodio(t.temporada(), d)))
        .collect(Collectors.toList());
    episodios.forEach(System.out::println);


//    //Buscado episodios por titulo
//    System.out.println("Digite o nome do episodio para localizar");
//    var trechoTitulo = leitura.nextLine();
//
//    Optional<Episodio> episodioBuscado = episodios.stream()
//        .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))
//        .findFirst();
//    if(episodioBuscado.isPresent()){
//      System.out.println("Episodio encontrado!");
//      System.out.println("Temporada: "+ episodioBuscado.get());
//    }else{
//      System.out.println("Episodio não encontrado");
//    }

//    System.out.println("Digite uma data de inicio do episodio");
//
//    var ano = leitura.nextInt();
//    leitura.nextLine();
//
//    LocalDate dataBusca = LocalDate.of(ano ,1 ,1);
//
//    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//    episodios.stream()
//        .filter(e -> e.getDataDeLancamento() != null && e.getDataDeLancamento().isAfter(dataBusca))
//        .forEach(e -> System.out.println(
//            "Temporada: " + e.getTemporada() +
//                " Episodio: " + e.getTitulo() +
//                " Data de Lançamento: "+ e.getDataDeLancamento().format(formatador)
//        ));

    //Filtra a média por temporada
    Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
        .filter(e-> e.getAvaliacao() > 0.0)
        .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

    System.out.println(avaliacoesPorTemporada);

    //Estatiscas das avaliações gerais (mostra todas as estaticas usadas para chegar nos resultados)
    DoubleSummaryStatistics est = episodios.stream()
        .filter(e-> e.getAvaliacao()>0.0)
        .collect(Collectors.summarizingDouble(e-> e.getAvaliacao()));
    System.out.println("Média: " + est.getAverage());
    System.out.println("Melhor episodio: " + est.getMax());
    System.out.println("Pior episodio: " + est.getMin());
    System.out.println("Quantidade: " + est.getCount());
  }
}
