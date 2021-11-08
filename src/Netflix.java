import javax.swing.*;
import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Netflix {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // Abrir stream e ler banco de dados
    ArrayList<Titulo> titulos = new ArrayList<>();
    Titulo titulo;
    FileInputStream fileIn = new FileInputStream("db.txt");

    try {
      ObjectInputStream objIn = new ObjectInputStream(fileIn);

      while (true) {
        try {
          // Ler o objeto e adicionar ao arraylist
          titulo = (Titulo) objIn.readObject();
          titulos.add(titulo);
        } catch (EOFException e) {
          // Tratar o caso em que tentamos ler mais um objeto na stream mas nao havia, ou seja, antigimos o final do arquivo - end of file - EOF
          break;
        }
      }

      objIn.close();
    } catch (IOException e) {
      System.out.println("Obs: Banco de dados se encontra vazio.");
    } catch (ClassNotFoundException e) {
      System.out.println("Erro fatal: classes incompativeis.");
      throw e;
    }

    // Boas vindas
    String username = JOptionPane.showInputDialog("Qual o seu nome?");
    if (username == null || username.equals("")) username = "Usuário Anônimo";
    JOptionPane.showMessageDialog(null, "Bem vindo, " + username + ". " + "\nEssa aplicação é uma simulação da Netflix.");

    // Menu inicial
    Object[] opcoes = { "1 - Incluir título", "2 - Alterar título", "3 - Consultar títulos", "4 - Listar títulos", "5 - Avaliar tìtulo", "6 - Gerar Sinopse", "7 - Modificar gênero", "Sair" };
    String opcao = (String)JOptionPane.showInputDialog(null, "O que você deseja fazer?", "Menu inicial", JOptionPane.QUESTION_MESSAGE, null, opcoes, "Selecionar..");

    // Loop que dirige o programa
    while (!Objects.equals(opcao, "Sair") && opcao != null) {
      // Funcao 1 - Incluir titulo
      if (opcao == opcoes[0]) {
        Object[] tipos = { "Filme", "Série", "Documentário", "Cancelar" };
        String tipo = (String)JOptionPane.showInputDialog(null, "Qual o tipo do título?", "Incluir título", JOptionPane.PLAIN_MESSAGE, null, tipos, "Selecionar..");

        if (tipo != null) {
          String nome = JOptionPane.showInputDialog(null, "Nome:");
          String desc = JOptionPane.showInputDialog(null, "Descrição:");
          String diretor = JOptionPane.showInputDialog(null, "Diretor:");
          String data = JOptionPane.showInputDialog(null, "Data de lançamento:");

          if (nome != null && desc != null && diretor != null && data != null) {
            if (tipo.equals(tipos[0])) {
              // Filme
              titulos.add(new Filme(nome, desc, diretor, data));
            } else if (tipo.equals(tipos[1])) {
              // Serie
              try {
                int qntTemp = Integer.parseInt(JOptionPane.showInputDialog(null, "Número de temporadas:"));
                int qntEp = Integer.parseInt(JOptionPane.showInputDialog(null, "Número de episódios:"));
                if (qntTemp != 0 && qntEp != 0) {
                  titulos.add(new Serie(nome, desc, diretor, data, qntEp, qntTemp));
                } else JOptionPane.showMessageDialog(null, "Falha ao adicionar: uma série para ser válida deve possuir ao menos 1 temporada e 1 episódio.");
              } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Falha ao adicionar: a quantidade de temporadas e de episódios devem ser números inteiros válidos.");
              }
            } else if (tipo.equals(tipos[2])) {
              // Documentario
              titulos.add(new Documentario(nome, desc, diretor, data));
            }
          } else {
            JOptionPane.showMessageDialog(null, "Falha ao adicionar: dados incorretos.");
          }
        }
      }

      // Funcao 2 - Alterar titulo
      else if (opcao == opcoes[1]) {
        String nomeBusca = JOptionPane.showInputDialog(null, "Qual o nome do título que você deseja alterar?");
        int index = -1, aux = 0;
        for(Titulo tit : titulos) {
          if (tit.nome.equalsIgnoreCase(nomeBusca)) {
            index = aux;
            break;
          }
          aux++;
        }
        if (index != -1) {
          Titulo alterar = titulos.get(index);

          JOptionPane.showMessageDialog(null, "Título encontrado no banco de dados. Selecione OK para editar os dados.");

          String nome = JOptionPane.showInputDialog(null, "Nome:", alterar.getNome());
          String desc = JOptionPane.showInputDialog(null, "Descrição:", alterar.getDescricao());
          String diretor = JOptionPane.showInputDialog(null, "Diretor:", alterar.getDiretor());
          String data = JOptionPane.showInputDialog(null, "Data de lançamento:", alterar.getDataLancamento());

          if (nome != null && desc != null && diretor != null && data != null) {
            alterar.setNome(nome);
            alterar.setDescricao(desc);
            alterar.setDiretor(diretor);
            alterar.setDataLancamento(data);

            if (alterar instanceof Serie) {
              try {
                int qntTemp = Integer.parseInt(JOptionPane.showInputDialog(null, "Número de temporadas:", ((Serie) alterar).getQntTemporadas()));
                int qntEp = Integer.parseInt(JOptionPane.showInputDialog(null, "Número de episódios:", ((Serie) alterar).getQntEpisodios()));
                if (qntTemp != 0 && qntEp != 0) {
                  ((Serie) alterar).setQntTemporadas(qntTemp);
                  ((Serie) alterar).setQntEpisodios(qntEp);
                  JOptionPane.showMessageDialog(null, "Dados atualizados com sucesso.");
                } else JOptionPane.showMessageDialog(null, "Falha ao adicionar: uma série para ser válida deve possuir ao menos 1 temporada e 1 episódio.");
              } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Falha ao adicionar: a quantidade de temporadas e de episódios devem ser números inteiros válidos.");
              }
            }
          } else JOptionPane.showMessageDialog(null, "Falha ao alterar: dados inseridos incorretamente.");
        } else JOptionPane.showMessageDialog(null, "Falha: título não encontrado no banco de dados.");
      }

      // Funcao 3 - Consultar titulos
      else if (opcao == opcoes[2]) {
        String nomeBusca = JOptionPane.showInputDialog(null, "Qual o nome do título que você deseja buscar?");

        for (Titulo tit : titulos) {
          if (tit.nome.equalsIgnoreCase(nomeBusca)) JOptionPane.showMessageDialog(null, "Título encontrado:\n" + tit);
          else JOptionPane.showMessageDialog(null, "Falha: Título não existente no banco de dados.");
        }
      }

      // Funcao 4 - Listar titulos
      else if (opcao == opcoes[3]) {
        if (titulos.isEmpty()) JOptionPane.showMessageDialog(null, "Ainda não há títulos armazenados.");
        else {
          StringBuilder lista = new StringBuilder();
          for (Titulo tit : titulos) {
            lista.append(tit).append("\n");
          }
          JOptionPane.showMessageDialog(null, lista);
        }
      }

      // Funcao 5 - Avaliar titulo
      else if (opcao == opcoes[4]) {
        if (titulos.isEmpty()) JOptionPane.showMessageDialog(null, "Ainda não há títulos para serem avaliados.");
        else {
          String buscar = JOptionPane.showInputDialog(null, "Qual o nome do título que você deseja avaliar?");
          if (buscar != null) {
            Titulo encontrado = null;
            for (Titulo tit : titulos) {
              if (tit.getNome().equalsIgnoreCase(buscar)) encontrado = tit;
            }

            if (encontrado != null) {
              String in = JOptionPane.showInputDialog(null, "Qual sua avaliação (de 1.0 a 5.0) para o título " + encontrado.getNome() + "?");
              if (in != null) {
                double nota = Double.parseDouble(in);
                if (nota >= 1 && nota <= 5) {
                  encontrado.avaliar(nota);
                  DecimalFormat df = new DecimalFormat("0.00");
                  df.setRoundingMode(RoundingMode.DOWN);
                  JOptionPane.showMessageDialog(null, "Avaliação registrada com sucesso. A nota atual de " + "\"" + encontrado.getNome() + "\"" + " é " +  df.format(encontrado.getAvaliacao()) + ".");
                }
                else JOptionPane.showMessageDialog(null, "Falha: número inválido fornecido.");
              }
            } else JOptionPane.showMessageDialog(null, "Falha: título não encontrado.");
          }
        }
      }

      // Funcao 6 - Gerar Sinopse

      // Funcao 7 - Modificar genero


      opcao = (String)JOptionPane.showInputDialog(null, "O que você deseja fazer?", "Menu inicial", JOptionPane.QUESTION_MESSAGE, null, opcoes, "Selecionar..");
    }

    // Salvar todos dados no banco de dados
    FileOutputStream fileOut = new FileOutputStream("db.txt");
    ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
    titulos.forEach((tit) -> {
      try {
        objOut.writeObject(tit);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    objOut.close();

    JOptionPane.showMessageDialog(null, "Até mais, " + username + ". " + "Esperamos te ver novamente!");
  }
}
