import java.util.ArrayList;

public class Serie extends Titulo {
  protected int qntEpisodios;
  protected int qntTemporadas;
  protected ArrayList<Serie> seriesSemelhantes = new ArrayList<>();

  public Serie(String nome, String descricao, String diretor, String dataLancamento, int qntEpisodios, int qntTemporadas) {
    super(nome, descricao, diretor, dataLancamento);
    this.qntEpisodios = qntEpisodios;
    this.qntTemporadas = qntTemporadas;
  }

  public int getQntEpisodios() {
    return qntEpisodios;
  }

  public void setQntEpisodios(int qntEpisodios) {
    this.qntEpisodios = qntEpisodios;
  }

  public int getQntTemporadas() {
    return qntTemporadas;
  }

  public void setQntTemporadas(int qntTemporadas) {
    this.qntTemporadas = qntTemporadas;
  }

  public void reproduzirSerie(int temp, int ep) {
    //
    // Esta função é meramente representativa, se fosse uma aplicação real, mandaria o conteúdo em vídeo para ser consumido para o usuário.
    //
  }

  public ArrayList<Serie> getSeriesSemelhantes() {
    return seriesSemelhantes;
  }

  public void adicionarSerieSemelhante(Serie serie) {
    this.seriesSemelhantes.add(serie);
  }

  public void removerSerieSemelhante(Serie serie) {
    this.seriesSemelhantes.remove(serie);
  }

  @Override
  public String toString() {
    return "Serie{" +
            "qntEpisodios=" + qntEpisodios +
            ", qntTemporadas=" + qntTemporadas +
            ", seriesSemelhantes=" + seriesSemelhantes +
            ", id=" + id +
            ", nome='" + nome + '\'' +
            ", descricao='" + descricao + '\'' +
            ", avaliacao=" + avaliacao +
            ", generos=" + generos +
            ", diretor='" + diretor + '\'' +
            ", dataLancamento='" + dataLancamento + '\'' +
            '}';
  }
}
