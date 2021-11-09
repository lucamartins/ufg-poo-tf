import javax.swing.*;
import java.io.*;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class Netflix {
  public static void main(String[] args) throws IOException, ClassNotFoundException {
    // Variaveis principais do programa
    ArrayList<Titulo> titulos = new ArrayList<>();
    ArrayList<Genero> generos = new ArrayList<>();

    // Banco de dados 1 - Titulos
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
      System.out.println("Obs: Banco de dados 1 se encontra vazio.");
    } catch (ClassNotFoundException e) {
      System.out.println("Erro fatal: classes incompativeis.");
      throw e;
    }

    // Banco de dados 2 - Generos
    Genero genero;
    FileInputStream fileIn2 = new FileInputStream("db2.txt");

    try {
      ObjectInputStream objIn2 = new ObjectInputStream(fileIn2);

      while (true) {
        try {
          // Ler o objeto e adicionar ao arraylist
          genero = (Genero) objIn2.readObject();
          generos.add(genero);
        } catch (EOFException e) {
          // Tratar o caso em que tentamos ler mais um objeto na stream mas nao havia, ou seja, antigimos o final do arquivo - end of file - EOF
          break;
        }
      }

      objIn2.close();
    } catch (IOException e) {
      System.out.println("Obs: Banco de dados 2 se encontra vazio.");
    } catch (ClassNotFoundException e) {
      System.out.println("Erro fatal: classes incompativeis.");
      throw e;
    }

    // Boas vindas
    String username = JOptionPane.showInputDialog("Qual o seu nome?");
    if (username == null || username.equals("")) username = "Usuário Anônimo";
    JOptionPane.showMessageDialog(null, "Bem vindo, " + username + ". " + "\nEssa aplicação é uma simulação da Netflix.");

    // Menu inicial
    Object[] opcoes = { "1 - Incluir título", "2 - Alterar título", "3 - Consultar títulos", "4 - Listar títulos", "5 - Avaliar tìtulo", "6 - Gerar Sinopse", "7 - Gerenciar gêneros", "8 - Gerenciar associacoes" };
    String opcao = (String)JOptionPane.showInputDialog(null, "O que você deseja fazer?", "Menu inicial", JOptionPane.QUESTION_MESSAGE, null, opcoes, "Selecionar..");

    // Loop que dirige o programa
    while (!Objects.equals(opcao, "Sair") && opcao != null) {
      // Funcao 1 - Incluir titulo
      if (opcao == opcoes[0]) {
        Object[] tipos = { "Filme", "Série", "Documentário" };
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
        if (!titulos.isEmpty()) {
          String nomeBusca = JOptionPane.showInputDialog(null, "Qual o nome do título que você deseja alterar?");
          int index = -1, aux = 0;
          for (Titulo tit : titulos) {
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
                  } else
                    JOptionPane.showMessageDialog(null, "Falha ao adicionar: uma série para ser válida deve possuir ao menos 1 temporada e 1 episódio.");
                } catch (NumberFormatException e) {
                  JOptionPane.showMessageDialog(null, "Falha ao adicionar: a quantidade de temporadas e de episódios devem ser números inteiros válidos.");
                }
              }
            } else
              JOptionPane.showMessageDialog(null, "Falha ao alterar: dados inseridos incorretamente.");
          } else
            JOptionPane.showMessageDialog(null, "Falha: título não encontrado no banco de dados.");
        } else
          JOptionPane.showMessageDialog(null, "Falha: ainda não há títulos armazenados.");
      }

      // Funcao 3 - Consultar titulos
      else if (opcao == opcoes[2]) {
        if (!titulos.isEmpty()) {
          String nomeBusca = JOptionPane.showInputDialog(null, "Qual o nome do título que você deseja buscar?");

          if (nomeBusca != null) {
            int res = -1;
            for (Titulo tit : titulos) {
              if (tit.nome.equalsIgnoreCase(nomeBusca)) {
                JOptionPane.showMessageDialog(null, "Título encontrado:\n" + tit);
                res = 1;
                break;
              }
            }
            if (res == -1) JOptionPane.showMessageDialog(null, "Falha: Título não encontrado.");
          } else
            JOptionPane.showMessageDialog(null, "Falha: nome inválido.");
        } else
          JOptionPane.showMessageDialog(null, "Falha: ainda não há títulos.");
      }

      // Funcao 4 - Listar titulos
      else if (opcao == opcoes[3]) {
        if (titulos.isEmpty()) JOptionPane.showMessageDialog(null, "Ainda não há títulos armazenados.");
        else {
          String[] opcoesListagem = {"Listar todos", "Listar por genero"};
          String tipoListagem = (String)JOptionPane.showInputDialog(null, "Qual listagem voce deseja?", "Listar títulos", JOptionPane.PLAIN_MESSAGE, null, opcoesListagem, "Selecionar..");
          StringBuilder lista = new StringBuilder();

          if (tipoListagem != null) {
            if (tipoListagem.equals(opcoesListagem[0])) {
              // Listar todos
              for (Titulo tit : titulos) {
                lista.append(tit).append("\n");
              }
            } else if (tipoListagem.equals(opcoesListagem[1])) {
              // Listar por genero
              String[] opcoesGeneros = {"Filme", "Serie", "Documentario"};
              String gen = (String)JOptionPane.showInputDialog(null, "Qual genero voce deseja listar?", "Listar por genero", JOptionPane.PLAIN_MESSAGE, null, opcoesGeneros, "Selecionar..");
              if (gen != null) {
                if (gen.equals(opcoesGeneros[0])) {
                  for (Titulo tit : titulos) {
                    if (tit instanceof Filme) lista.append(tit).append("\n");
                  }
                } else if (gen.equals(opcoesGeneros[1])) {
                  for (Titulo tit : titulos) {
                    if (tit instanceof Serie) lista.append(tit).append("\n");
                  }
                } else if (gen.equals(opcoesGeneros[2])) {
                  for (Titulo tit : titulos) {
                    if (tit instanceof Documentario) lista.append(tit).append("\n");
                  }
                }
              }
            }
            if (lista.length() != 0) JOptionPane.showMessageDialog(null, lista);
            else JOptionPane.showMessageDialog(null, "Não há títulos no gênero especificado.");
          }
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
      else if (opcao == opcoes[5]) {
        if (titulos.isEmpty()) JOptionPane.showMessageDialog(null, "Ainda não há títulos.");
        else {
          String nomeBusca = JOptionPane.showInputDialog(null, "Para qual título você deseja gerar sinopse?");
          int index = -1, aux = 0;

          for(Titulo tit : titulos) {
            if (tit.nome.equalsIgnoreCase(nomeBusca)) {
              index = aux;
              break;
            }
            aux++;
          }

          if (index != -1) {
            Titulo buscado = titulos.get(index);
            JOptionPane.showMessageDialog(null, buscado.gerarSinopse());
          } else {
            JOptionPane.showMessageDialog(null, "Título não encontrado.");
          }
        }
      }

      // Funcao 7 - Gerencias generos
      else if (opcao == opcoes[6]) {
        String[] opcoesGen = {"1 - Inserir novo", "2 - Remover", "3 - Adicionar titulo a um genero", "4 - Remover titulo de um genero", "5 - Listar generos" };
        String opGen = (String)JOptionPane.showInputDialog(null, "O que voce deseja realizar?", "Gerenciar generos", JOptionPane.PLAIN_MESSAGE, null, opcoesGen, "Selecionar..");

        if (opGen != null && opGen.equals(opcoesGen[0])) {
          // Novo genero
          String nomeGen = JOptionPane.showInputDialog(null, "Digite o nome do novo gênero: ");
          if (nomeGen != null) {
            generos.add(new Genero(nomeGen));
            JOptionPane.showMessageDialog(null, "Gênero criado com sucesso.");
          }
        } else if (opGen != null && opGen.equals(opcoesGen[1])) {
          // Remover genero
          if (!generos.isEmpty()) {
            String nomeGen = JOptionPane.showInputDialog(null, "Digite o nome do gênero que você deseja remover: ");
            int index = -1, cnt = 0;
            for (Genero gen : generos) {
              if (gen.getNome().equals(nomeGen)) {
                index = cnt;
                break;
              }
              cnt++;
            }
            if (index != -1) {
              generos.remove(index);
              JOptionPane.showMessageDialog(null, "Genero removido com sucesso.");
            } else JOptionPane.showMessageDialog(null, "Falha: gênero não foi encontrado.");
          } else JOptionPane.showMessageDialog(null, "Falha: não há gêneros criados.");
        } else if (opGen != null && opGen.equals(opcoesGen[2])) {
          // Adicionar filme ao genero
          if (!generos.isEmpty()) {
            String nomeGen = JOptionPane.showInputDialog(null, "Digite o nome do gênero que você deseja adicionar um titulo: ");
            String nomeTit = JOptionPane.showInputDialog(null, "Digite o nome do titulo: ");

            if (nomeGen != null && nomeTit != null) {
              int indexGen = -1, indexTit = -1, cnt = 0;
              for (Genero gen : generos) {
                if (gen.getNome().equals(nomeGen)) {
                  indexGen = cnt;
                  break;
                }
                cnt++;
              }
              cnt = 0;
              for (Titulo tit : titulos) {
                if (tit.getNome().equalsIgnoreCase(nomeTit)) {
                  indexTit = cnt;
                  break;
                }
                cnt++;
              }
              if (indexGen != -1 && indexTit != -1) {
                generos.get(indexGen).adicionarTitulo(titulos.get(indexTit));
                JOptionPane.showMessageDialog(null, "Filme adicionado ao gênero com sucesso.");
              } else JOptionPane.showMessageDialog(null, "Falha: gênero ou filme não encontrado(s).");
            }
          } else JOptionPane.showMessageDialog(null, "Falha: não há gêneros criados.");
        } else if (opGen != null && opGen.equals(opcoesGen[3])) {
          // Remover titulo do genero
          if (!generos.isEmpty()) {
            String nomeGen = JOptionPane.showInputDialog(null, "Digite o nome do gênero que você deseja remover um titulo: ");
            String nomeTit = JOptionPane.showInputDialog(null, "Digite o nome do titulo: ");

            if (nomeGen != null && nomeTit != null) {
              int indexGen = -1, indexFilme = -1, cnt = 0;
              for (Genero gen : generos) {
                if (gen.getNome().equals(nomeGen)) {
                  indexGen = cnt;
                  break;
                }
                cnt++;
              }
              cnt = 0;
              for (Titulo tit : titulos) {
                if (tit.getNome().equalsIgnoreCase(nomeTit)) {
                  indexFilme = cnt;
                  break;
                }
                cnt++;
              }
              if (indexGen != -1 && indexFilme != -1) {
                generos.get(indexGen).removerTitulo(titulos.get(indexFilme));
                JOptionPane.showMessageDialog(null, "Filme removido do gênero com sucesso.");
              } else JOptionPane.showMessageDialog(null, "Falha: gênero não foi encontrado.");
            }
          } else JOptionPane.showMessageDialog(null, "Falha: não há gêneros criados.");
        } else if(opGen != null && opGen.equals(opcoesGen[4])) {
          if (!generos.isEmpty()) {
            StringBuilder listarGen = new StringBuilder();
            for (Genero gen : generos) listarGen.append(gen).append("\n");
            JOptionPane.showMessageDialog(null, listarGen);
          } else JOptionPane.showMessageDialog(null, "Falha: não há gêneros criados.");
        }
      }

      // Funcao 8 - Gerenciar associacoes
      else if (opcao == opcoes[7]) {
        if (titulos.isEmpty()) JOptionPane.showMessageDialog(null, "Falha: não há títulos armazenados.");
        else {
          String[] opcoes8 = {"1 - Gerenciar associacao de filmes", "2 - Gerenciar associacao de series", "3 - Gerenciar associacao de documentarios" };
          String opGen = (String)JOptionPane.showInputDialog(null, "O que voce deseja realizar?", "Gerenciar associacao", JOptionPane.PLAIN_MESSAGE, null, opcoes8, "Selecionar..");

          if (opGen != null && opGen.equals(opcoes8[0])) {
            // Gerenciar filmes semelhantes
            String filme1 = JOptionPane.showInputDialog(null, "Digite o nome do primeiro filme: ");
            String filme2 = JOptionPane.showInputDialog(null, "Digite o nome do segundo filme: ");

            String[] opcoesAssFilme = { "1 - Associar como semelhantes", "2 - Remover associacao" };
            String opAssFilme = (String)JOptionPane.showInputDialog(null, "O que voce deseja realizar?", "Associacao de filmes", JOptionPane.PLAIN_MESSAGE, null, opcoesAssFilme, "Selecionar..");

            if (filme1 != null && filme2 != null && opAssFilme != null) {
              int ind1 = -1, ind2 = -1, cnt = 0;

              for (Titulo tit : titulos) {
                if (tit.getNome().equalsIgnoreCase(filme1)) {
                  ind1 = cnt;
                }
                else if (tit.getNome().equalsIgnoreCase(filme2)) {
                  ind2 = cnt;
                }
                cnt++;
                if (ind1 != -1 && ind2 != -1) break;
              }

              if (ind1 != -1 && ind2 != -1) {
                if (opAssFilme.equals(opcoesAssFilme[0])) {
                  ((Filme)titulos.get(ind1)).adicionarFilmeSemelhante((Filme)titulos.get(ind2));
                } else {
                  ((Filme)titulos.get(ind1)).removerFilmeSemelhante((Filme)titulos.get(ind2));
                }
              } else {
                JOptionPane.showMessageDialog(null, "Falha: filmes não encontrados.");
              }
            } else {
              JOptionPane.showMessageDialog(null, "Falha: entrada de dados incorreta.");
            }
          } else if (opGen != null && opGen.equals(opcoes8[1])) {
            // Gerenciar series semelhantes
            String serie1 = JOptionPane.showInputDialog(null, "Digite o nome da primeira serie: ");
            String serie2 = JOptionPane.showInputDialog(null, "Digite o nome da segunda serie: ");

            String[] opcoesAssSerie = { "1 - Associar filmes semelhantes como semelhantes", "2 - Remover associacao" };
            String opAssSerie = (String)JOptionPane.showInputDialog(null, "O que voce deseja realizar?", "Associacao de filmes", JOptionPane.PLAIN_MESSAGE, null, opcoesAssSerie, "Selecionar..");

            if (serie1 != null && serie2 != null && opAssSerie != null) {
              int ind1 = -1, ind2 = -1, cnt = 0;

              for (Titulo tit : titulos) {
                if (tit.getNome().equalsIgnoreCase(serie1)) {
                  ind1 = cnt;
                }
                else if (tit.getNome().equalsIgnoreCase(serie2)) {
                  ind2 = cnt;
                }
                cnt++;
                if (ind1 != -1 && ind2 != -1) break;
              }

              if (ind1 != -1 && ind2 != -1) {
                if (opAssSerie.equals(opcoesAssSerie[0])) {
                  ((Serie)titulos.get(ind1)).adicionarSerieSemelhante((Serie)titulos.get(ind2));
                } else {
                  ((Serie)titulos.get(ind1)).removerSerieSemelhante((Serie)titulos.get(ind2));
                }
              } else {
                JOptionPane.showMessageDialog(null, "Falha: filmes não encontrados.");
              }
            } else {
              JOptionPane.showMessageDialog(null, "Falha: entrada de dados incorreta.");
            }
          } else if (opGen != null && opGen.equals(opcoes8[2])) {
            // Gerenciar documentarios semelhantes
            String doc1 = JOptionPane.showInputDialog(null, "Digite o nome do primeiro documentario: ");
            String doc2 = JOptionPane.showInputDialog(null, "Digite o nome do segundo documentario: ");

            String[] opcoesAssDoc = { "1 - Associar como semelhantes", "2 - Remover associacao" };
            String opAssDoc = (String)JOptionPane.showInputDialog(null, "O que voce deseja realizar?", "Associacao de documentarios", JOptionPane.PLAIN_MESSAGE, null, opcoesAssDoc, "Selecionar..");

            if (doc1 != null && doc2 != null && opAssDoc != null) {
              int ind1 = -1, ind2 = -1, cnt = 0;

              for (Titulo tit : titulos) {
                if (tit.getNome().equalsIgnoreCase(doc1)) {
                  ind1 = cnt;
                }
                else if (tit.getNome().equalsIgnoreCase(doc2)) {
                  ind2 = cnt;
                }
                cnt++;
                if (ind1 != -1 && ind2 != -1) break;
              }

              if (ind1 != -1 && ind2 != -1) {
                if (opAssDoc.equals(opcoesAssDoc[0])) {
                  ((Documentario)titulos.get(ind1)).adicionarDocSemelhante((Documentario)titulos.get(ind2));
                } else {
                  ((Documentario)titulos.get(ind1)).removerDocSemelhante((Documentario)titulos.get(ind2));
                }
              } else {
                JOptionPane.showMessageDialog(null, "Falha: documentarios não encontrados.");
              }
            } else {
              JOptionPane.showMessageDialog(null, "Falha: entrada de dados incorreta.");
            }
          }
        }
      }

      opcao = (String)JOptionPane.showInputDialog(null, "O que você deseja fazer?", "Menu inicial", JOptionPane.QUESTION_MESSAGE, null, opcoes, "Selecionar..");
    }

    // Salvar todos dados no banco de dados 1
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

    // Salvar todos dados no banco de dados 2
    FileOutputStream fileOut2 = new FileOutputStream("db2.txt");
    ObjectOutputStream objOut2 = new ObjectOutputStream(fileOut2);
    generos.forEach((gen) -> {
      try {
        objOut2.writeObject(gen);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    objOut2.close();

    // Goodbye
    JOptionPane.showMessageDialog(null, "Até mais, " + username + ". " + "Esperamos te ver novamente!");
  }
}
