import java.util.ArrayList;

public class Filme extends Titulo {
  protected ArrayList<Filme> filmesSemelhantes = new ArrayList<>();

  public Filme(String nome, String descricao, String diretor, String dataLancamento) {
    super(nome, descricao, diretor, dataLancamento);
  }

  public void reproduzirFilme() {
    //
    // Esta função é meramente representativa, se fosse uma aplicação real, mandaria o conteúdo em vídeo para ser consumido para o usuário.
    //
  }

  public ArrayList<Filme> getFilmesSemelhantes() {
    return filmesSemelhantes;
  }

  public void adicionarFilmeSemelhante(Filme filme) {
    this.filmesSemelhantes.add(filme);
  }

  public void removerFilmeSemelhante(Filme filme) {
    this.filmesSemelhantes.remove(filme);
  }

  @Override
  public String toString() {
    return "Filme{" +
            "filmesSemelhantes=" + filmesSemelhantes +
            ", id=" + id +
            ", nome='" + nome + '\'' +
            ", descricao='" + descricao + '\'' +
            ", avaliacao=" + avaliacao +
            ", generos=" + generos +
            ", diretor='" + diretor + '\'' +
            ", dataLancamento='" + dataLancamento + '\'' +
            "} ";
  }
}
