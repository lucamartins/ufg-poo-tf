import java.util.ArrayList;

public class Genero {
  protected static int auxID = 0;
  protected int id;
  protected String nome;
  protected ArrayList<Titulo> titulos;

  public Genero(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void adicionarTitulo(Titulo titulo) {
    // ...
  }

  public void removerTitulo(Titulo titulo) {
    // ...
  }

  public ArrayList<Titulo> getTitulos() {
    return titulos;
  }
}
