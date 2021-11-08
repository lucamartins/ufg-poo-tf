import java.time.LocalDateTime;
import java.util.ArrayList;

public class Documentario extends Titulo {
  protected ArrayList<Documentario> documentariosSemelhantes;

  public Documentario(String nome, String descricao, String diretor, String dataLancamento) {
    super(nome, descricao, diretor, dataLancamento);
  }

  public void reproduzirDoc() {
    //
    // Esta função é meramente representativa, se fosse uma aplicação real, mandaria o conteúdo em vídeo para ser consumido para o usuário.
    //
  }

  public ArrayList<Documentario> getDocumentariosSemelhantes() {
    return documentariosSemelhantes;
  }

  public void adicionarDocSemelhante(Documentario doc) {
    // ...
  }

  public void removerDocSemelhante(Documentario doc) {
    // ...
  }
}
