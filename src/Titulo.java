import java.io.Serializable;
import java.util.ArrayList;

public abstract class Titulo implements Serializable {
  protected int id;
  protected String nome;
  protected String descricao;
  protected ArrayList<Double> avaliacoes = new ArrayList<Double>();
  protected double avaliacao;
  protected ArrayList<Genero> generos;
  protected String diretor;
  protected String dataLancamento;

  protected static int auxID = 0;

  public Titulo(String nome, String descricao, String diretor, String dataLancamento) {
    this.id = auxID++;
    this.nome = nome;
    this.descricao = descricao;
    this.diretor = diretor;
    this.dataLancamento = dataLancamento;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public double getAvaliacao() {
    return avaliacao;
  }

  public ArrayList<Genero> getGeneros() {
    return generos;
  }

  public String getDiretor() {
    return diretor;
  }

  public void setDiretor(String diretor) {
    this.diretor = diretor;
  }

  public String getDataLancamento() {
    return dataLancamento;
  }

  public void setDataLancamento(String dataLancamento) {
    this.dataLancamento = dataLancamento;
  }

  public void avaliar(double nota) {
    this.avaliacoes.add(nota);
    double soma = 0;
    for (Double av : this.avaliacoes) soma += av;
    this.avaliacao = soma / avaliacoes.size();
  }

  public String gerarSinopse() {
    String tipo;
    if (this instanceof Filme) tipo = "Filme";
    else if (this instanceof Serie) tipo = "Série";
    else tipo = "Documentário";

    return tipo + " dirigido por " + this.getDiretor() + " lançado em " + this.getDataLancamento() + "\nResumo: " + this.getDescricao();
  }
}
