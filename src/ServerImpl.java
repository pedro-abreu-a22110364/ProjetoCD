import java.io.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ServerImpl implements ServerIntf {

  private static String filePath = "reservas.txt";
  public static ArrayList<Praia> praias = new ArrayList<>();
  public static HashMap<String, ArrayList<Sombrinha>> reservas = new HashMap<>();

  public ServerImpl() throws RemoteException {
  }

  @Override
  public void inicializacao () throws RemoteException {
    // Criação das praias
    Praia praiaA = new Praia('A');
    Praia praiaB = new Praia('B');
    Praia praiaC = new Praia('C');

    // Criação das sombrinhas da praia A
    for (int i = 1; i <= 10; i++) {
      praiaA.adicionarSombrinha(new Sombrinha('A',i, 2));
    }

    for (int i = 11; i <= 15; i++) {
      praiaA.adicionarSombrinha(new Sombrinha('A',i, 3));
    }

    for (int i = 16; i <= 20; i++) {
      praiaA.adicionarSombrinha(new Sombrinha('A',i, 4));
    }

    // Criação das sombrinhas da praia B
    for (int i = 1; i <= 5; i++) {
      praiaB.adicionarSombrinha(new Sombrinha('B',i, 2));
    }

    for (int i = 6; i <= 10; i++) {
      praiaB.adicionarSombrinha(new Sombrinha('B',i, 3));
    }

    praiaB.adicionarSombrinha(new Sombrinha('B',11, 4));

    // Criação das sombrinhas da praia C
    for (int i = 1; i <= 10; i++) {
      praiaC.adicionarSombrinha(new Sombrinha('C',i, 2));
    }

    // Adicionar as praias ao arraylist das praias
    praias.add(praiaA);
    praias.add(praiaB);
    praias.add(praiaC);


    try {
      FileWriter fileWriter = new FileWriter(filePath);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      printWriter.close();
      fileWriter.close();
    } catch (IOException e) {
      System.out.println("Problemas na criaçao do file reservas.txt");
    }

  }

  @Override
  public String reservarSombrinha (char praiaID, int dia, int hora, int lotacao) throws RemoteException {
    try {
      if (hora > 20 || hora < 8) {
        return "Reserva não são possiveis à hora selecionada";
      } else {
        // Ler o arquivo de reservas
        File arquivo = new File(filePath);
        Scanner scanner = new Scanner(arquivo);

        // Verificar se há uma reserva para a praia, dia e hora especificados
        boolean reservaExistente = false;
        while (scanner.hasNextLine()) {
          String linha = scanner.nextLine();
          String[] partes = linha.split(" ");

          char idPraia = partes[0].charAt(0);
          int diaReserva = Integer.parseInt(partes[1]);
          int horaReserva = Integer.parseInt(partes[2]);

          if (idPraia == praiaID && diaReserva == dia && horaReserva == hora) {
            reservaExistente = true;
            break;
          }
        }
        scanner.close();
        // o que eu quero é verificar se a praia ainda tem lugares disponiveis para uma data especifica
        //falta controlar o tipo de smbrinha que estas a dar e ver se nao das mais do que o que tens
        // Se não houver reserva, adicionar a nova reserva ao arquivo
        if (!reservaExistente) {
          //sera que ainda temos vagas para aquele dia naquela hora??? vejamos atraves da seguinte funão
          FileWriter writer = new FileWriter(arquivo, true);
          BufferedWriter bufferWriter = new BufferedWriter(writer);
          //bufferWriter.write(praiaID + " " + praia.sombrinhas.get(i).sombrinhaID + " " + dia + " " + hora + " " + lotacao);
          bufferWriter.newLine();
          bufferWriter.close();
          return "Reserva efetuada com sucesso.";

        } else {
          return "Reserva não é possível";
        }
      }

    } catch (IOException e) {
      // Lidar com exceções de leitura/escrita de arquivo
      throw new RemoteException("Erro ao processar a reserva.", e);
    }
  }
  //nas reservas falta qcontrolar a quantidade de vezes que para um dia para uma dada hora posso requisitar a praia A1, pporque na inicialização nao lhes dei nomes

  @Override
  public String cancelarSombrinha (int sombrinhaID) throws RemoteException {
    ArrayList<String> linhas = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      String linhaAtual;
      while ((linhaAtual = reader.readLine()) != null) {
        String[] partes = linhaAtual.split(" ");
        int sombrinhaIdLinha = Integer.parseInt(partes[1]);

        if (sombrinhaIdLinha != sombrinhaID) {
          linhas.add(linhaAtual);
        }
      }
    } catch (IOException e) {
      System.out.println("Erro no cancelamento");
    }

    // Escrita das linhas atualizadas de volta ao arquivo
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      for (String linha : linhas) {
        writer.write(linha);
        writer.newLine();
      }
    } catch (IOException e) {
      System.out.println("Erro na escrita da nova listagem sem sombrinha cancelada");
    }

    return "Sombrinha cancelada com sucesso.";
  }

  @Override
  public String listarSombrinhas (char praiaID, int dia, int hora) throws RemoteException {
    //listar sombrinhas não reservadas numa praia, na data e hora proposta e estando o utilizador autenticado
    //
    return null;
  }

}
