import static org.junit.Assert.*;

import org.junit.Test;

public class FilmeTeste {
  @Test
  public void teste1() {
    Filme f1 = new Filme("007", "Agente James Bond", "Oscar", "Janeiro de 2021");
    f1.avaliar(5);
    assertEquals("007", f1.getNome());
    assertEquals(5, f1.getAvaliacao(), 0.0);
  }

  @Test
  public void teste2() {
    Filme f2 = new Filme("Dune", "Viagem interespacial", "Hanz", "Novembro de 2021");
    assertEquals("Viagem interespacial", f2.getDescricao());
    assertEquals("Hanz", f2.getDiretor());
  }
}
