import java.util.ArrayList;

public class Genero {
  protected static int auxID = 0;
  protected int id;
  protected String nome;
  protected ArrayList<Titulo> titulos = new ArrayList<>();

  public Genero(String nome) {
    this.nome = nome;
    this.id = auxID++;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void adicionarTitulo(Titulo titulo) {
    this.titulos.add(titulo);
  }

  public void removerTitulo(Titulo titulo) {
    this.titulos.remove(titulo);
  }

  public ArrayList<Titulo> getTitulos() {
    return titulos;
  }

  @Override
  public String toString() {
    return "Genero{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", titulos=" + titulos +
            '}';
  }
}
