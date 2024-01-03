import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

public class ServerImpl implements ServerIntf {

  private static final String filePath = "reservas.txt";
  public static final int maxLotacaoA = 55;
  public static final int maxLotacaoB = 29;
  public static final int maxLotacaoC = 20;
  public static HashMap<String, ArrayList<Sombrinha>> sombrinhas = new HashMap<>();
  public static HashMap<String, ArrayList<Reserva>> reservas = new HashMap<>();

  public ServerImpl() throws RemoteException {
  }

  @Override
  public String inicializarSombrinhas() {
    // Arrays para adicionar as sombrinhas
    ArrayList<Sombrinha> sombrinhasTempA = new ArrayList<>();
    ArrayList<Sombrinha> sombrinhasTempB = new ArrayList<>();
    ArrayList<Sombrinha> sombrinhasTempC = new ArrayList<>();

    // Criação das sombrinhas da praia A
    for (int i = 1; i <= 10; i++) {
      sombrinhasTempA.add(new Sombrinha("A",i,2));
    }
    for (int i = 11; i <= 15; i++) {
      sombrinhasTempA.add(new Sombrinha("A",i, 3));
    }
    for (int i = 16; i <= 20; i++) {
      sombrinhasTempA.add(new Sombrinha("A",i, 4));
    }

    // Criação das sombrinhas da praia B
    for (int i = 1; i <= 5; i++) {
      sombrinhasTempB.add(new Sombrinha("B",i, 2));
    }
    for (int i = 6; i <= 10; i++) {
      sombrinhasTempB.add(new Sombrinha("B",i, 3));
    }
    sombrinhasTempB.add(new Sombrinha("B",11, 4));

    // Criação das sombrinhas da praia C
    for (int i = 1; i <= 10; i++) {
      sombrinhasTempC.add(new Sombrinha("C",i, 2));
    }

    // Adicionar arrays ao hashmap
    sombrinhas.put("A",sombrinhasTempA);
    sombrinhas.put("B",sombrinhasTempB);
    sombrinhas.put("C",sombrinhasTempC);

    return "Sombrinhas inicializadas com sucesso";
  }

  @Override
  public String carregarReservas() {

    try {
      File arquivo = new File(filePath);
      Scanner scanner = new Scanner(new FileInputStream(arquivo));

      while (scanner.hasNextLine()) {
        String linha = scanner.nextLine();

        // Split da linha para individualizar a informação
        String[] elementos = linha.split(";");

        // O primeiro elemento corresponde à praia (A,B ou C)
        String chave = elementos[0];

        // Criar a reserva com os elementos extraidos do ficheiro
        Reserva reserva = new Reserva(elementos[0],Integer.parseInt(elementos[1]),Integer.parseInt(elementos[2]),Integer.parseInt(elementos[3]),Integer.parseInt(elementos[4]));

        // Verificar se já existe essa chave
        if (reservas.containsKey(elementos[0])) {
          reservas.get(elementos[0]).add(reserva);
        } else {
          ArrayList<Reserva> reservasTemp = new ArrayList<>();
          reservasTemp.add(reserva);
          reservas.put(chave,reservasTemp);
        }
      }
      return "Carregamento das reservas com sucesso";
    } catch (FileNotFoundException e) {
      System.out.println("Ficheiro não encontrado");
      return "Carregamento das reservas sem sucesso";
    }
  }

  @Override
  public String atualizarReservas() {
    // Escrever no ficheiro
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {

      for (Map.Entry<String,ArrayList<Reserva>> entry : reservas.entrySet()) {
        ArrayList<Reserva> reservasTemp = entry.getValue();

        for (Reserva reserva : reservasTemp) {
          writer.write(reserva.getPraiaID() + ";" + reserva.getSombrinhaID() + ";" + reserva.getLotacao() + ";" + reserva.getHora() + ";" + reserva.getData() + "\n");
        }
      }

      return "Import das reservas com sucesso";
    } catch (IOException e) {
      System.err.println("Erro na escrita: " + e.getMessage());
      return "Erro na escrita: " + e.getMessage();
    }
  }

  @Override
  public String reservarSombrinha (String praiaID, int dia, int hora, int lotacao) throws RemoteException {
    int lotacaoDisponivel = 0;
    ArrayList<Reserva> reservasDiaEHora = new ArrayList<>();
    ArrayList<Sombrinha> sombrinhasNaoReservadas = new ArrayList<>();

    // Validar os inputs inseridos

    // Verificar se a praia é válida
    if (!Objects.equals(praiaID, "A") && !Objects.equals(praiaID, "B") && !Objects.equals(praiaID, "C")) {
      return "Praia invalida";
    }

    // Verificar se o dia é válido (assumir que estamos em janeiro)
    if (dia < 1 || dia > 31) {
      return "Dia invalido";
    }

    // Verificar se a hora é válida
    if (hora < 8 || hora > 20) {
      return "Hora invalida";
    }

    // Verificar se a lotacao é válida
    if (Objects.equals(praiaID, "A")) {
      // Lotacao maxima da praia A é 55
      if (lotacao < 1 || lotacao > maxLotacaoA) {
        return "Lotacao invalida";
      }
    } else if (Objects.equals(praiaID,"B")) {
      // Lotacao maxima da praia B é 29
      if (lotacao < 1 || lotacao > maxLotacaoB) {
        return "Lotacao invalida";
      }
    } else if (Objects.equals(praiaID,"C")) {
      // Lotacao maxima da praia C é 20
      if (lotacao < 1 || lotacao > maxLotacaoC) {
        return "Lotacao invalida";
      }
    }

    // Verificar possibilidade de fazer a reserva

    // Obter o array das reservas e sombrinhas da praia que queremos, para fazer comparações sobre ele
    ArrayList<Reserva> reservasTemp = reservas.get(praiaID);
    ArrayList<Sombrinha> sombrinhasTemp = sombrinhas.get(praiaID);

    if (reservasTemp != null){
      // Filtrar as reservas da praia em questão pelo dia e hora que queremos
      for (Reserva reserva : reservasTemp) {
        if (reserva.getData() == dia && reserva.getHora() == hora) {
          reservasDiaEHora.add(reserva);
        }
      }
    }

    // Filtrar as sombrinhas da praia sem reserva pelo id da sombrinha
    for (Sombrinha sombrinha : sombrinhasTemp) {
      boolean sombrinhaReservada = false;

      for (Reserva reserva : reservasDiaEHora) {
        if (sombrinha.getSombrinhaID() == reserva.getSombrinhaID()) {
          sombrinhaReservada = true;
          break;
        }
      }

      if (!sombrinhaReservada) {
        sombrinhasNaoReservadas.add(sombrinha);
      }
    }

    // Verificar se há lotacao disponivel
    for (Sombrinha sombrinha : sombrinhasNaoReservadas) {
      lotacaoDisponivel += sombrinha.getLotacao();
    }

    if ((lotacaoDisponivel - lotacao) < 0) {
      return "Não há espaço suficiente na praia";
    }

    // Variável auxiliar para controlar posições no array de sombrinhas
    int aux;

    // Efetuar a reserva e colocar no hashmap e no ficheiro
    switch (praiaID){
      case "A":
        // Nas praias que têm diferentes tamanhos, percorrer o array de trás para a frente
        aux = sombrinhasNaoReservadas.size() - 1;

        // Caso o numero de pessoas pertendidas for menor que a lotacao da sombrinha atual, andas para trás até encontrar uma sombrinha que se encaixe
        while (sombrinhasNaoReservadas.get(aux).getLotacao() > lotacao) {
          aux--;
        }

        while (lotacao > 0) {
          Sombrinha sombrinhaTemp = sombrinhasNaoReservadas.get(aux);

          Reserva reservaTemp = new Reserva("A",sombrinhaTemp.getSombrinhaID(),sombrinhaTemp.getLotacao(),hora,dia);

          if (reservas.containsKey("A")) {
            reservas.get("A").add(reservaTemp);
          } else {
            ArrayList<Reserva> reservasTemp2 = new ArrayList<>();
            reservasTemp2.add(reservaTemp);
            reservas.put("A",reservasTemp2);
          }

          atualizarReservas();

          aux--;
          lotacao -= sombrinhaTemp.getLotacao();
        }
        break;
      case "B":
        aux = sombrinhasNaoReservadas.size() - 1;

        while (sombrinhasNaoReservadas.get(aux).getLotacao() > lotacao) {
          aux--;
        }

        while (lotacao > 0) {
          Sombrinha sombrinhaTemp = sombrinhasNaoReservadas.get(aux);

          Reserva reservaTemp = new Reserva("A",sombrinhaTemp.getSombrinhaID(),sombrinhaTemp.getLotacao(),hora,dia);

          if (reservas.containsKey("A")) {
            reservas.get("A").add(reservaTemp);
          } else {
            ArrayList<Reserva> reservasTemp2 = new ArrayList<>();
            reservasTemp2.add(reservaTemp);
            reservas.put("A",reservasTemp2);
          }

          atualizarReservas();

          aux--;
          lotacao -= sombrinhaTemp.getLotacao();
        }
        break;
      case "C":
        // Na praia que têm tamanhos iguais, percorrer o array de frente para a trás
        aux = 0;

        while (lotacao > 0) {

          Sombrinha sombrinhaTemp = sombrinhasNaoReservadas.get(aux);

          Reserva reservaTemp = new Reserva("C",sombrinhaTemp.getSombrinhaID(),sombrinhaTemp.getLotacao(),hora,dia);

          if (reservas.containsKey("C")) {
            reservas.get("C").add(reservaTemp);
          } else {
            ArrayList<Reserva> reservasTemp2 = new ArrayList<>();
            reservasTemp2.add(reservaTemp);
            reservas.put("C",reservasTemp2);
          }

          atualizarReservas();

          aux++;
          lotacao -= sombrinhaTemp.getLotacao();
        }
        break;
    }

    return "Reserva(s) realizada(s) com sucesso";
  }

  @Override
  public String cancelarSombrinha (String praiaID, int sombrinhaID, int dia, int hora) throws RemoteException {
    // Validar os inputs inseridos

    // Verificar se a praia é válida
    if (!Objects.equals(praiaID, "A") && !Objects.equals(praiaID, "B") && !Objects.equals(praiaID, "C")) {
      return "Praia invalida";
    }

    // Verificar se a sombrinha é válida
    if (Objects.equals(praiaID, "A")) {
      // Praia A tem 20 sombrinhas no total
      if (sombrinhaID < 1 || sombrinhaID > 20) {
        return "Sombrinha invalida";
      }
    } else if (Objects.equals(praiaID,"B")) {
      // Praia B tem 11 sombrinhas no total
      if (sombrinhaID < 1 || sombrinhaID > 11) {
        return "Lotacao invalida";
      }
    } else if (Objects.equals(praiaID,"C")) {
      // Praia C tem 10 sombrinhas no total
      if (sombrinhaID < 1 || sombrinhaID > 10) {
        return "Lotacao invalida";
      }
    }

    // Verificar se o dia é válido (assumir que estamos em janeiro)
    if (dia < 1 || dia > 31) {
      return "Dia invalido";
    }

    // Verificar se a hora é válida
    if (hora < 8 || hora > 20) {
      return "Hora invalida";
    }

    // Validar se existe uma reserva com esses parametros

    // Obter o array das reservas da praia que queremos, para fazer comparações sobre ele
    ArrayList<Reserva> reservasTemp = reservas.get(praiaID);

    Reserva reservaRemover = null;
    boolean existe = false;
    for (Reserva reserva : reservasTemp) {
      if (praiaID.equals(reserva.getPraiaID()) && sombrinhaID == reserva.getSombrinhaID() && dia == reserva.getData() && hora == reserva.getHora()) {
        // Remover a reserva do array quando esta coincide com a reserva passada por parametros
        reservaRemover = reserva;
        existe = true;
      }
    }

    // Verificar se a reserva não é nula e se o array list contém alguma reserva
    if (reservaRemover != null && !(reservasTemp.isEmpty())) {
      reservasTemp.remove(reservaRemover);
    }

    if (!existe) {
      // Se não houver nenhuma reserva com esses parametros caso não haja nenhuma reserva que coincida
      return "Reserva não encontrada";
    }

    // Atualizar o hashmap
    reservas.put(praiaID,reservasTemp);

    // Se sim, remover essa reserva do ficheiro txt
    atualizarReservas();

    return "Reserva cancelada com sucesso";
  }

  @Override
  public String listarSombrinhas (String praiaID, int dia, int hora) throws RemoteException {
    ArrayList<Reserva> reservasDiaEHora = new ArrayList<>();
    ArrayList<Sombrinha> sombrinhasNaoReservadas = new ArrayList<>();
    // Verificar possibilidade de fazer a reserva

    // Obter o array das reservas e sombrinhas da praia que queremos, para fazer comparações sobre ele
    ArrayList<Reserva> reservasTemp = reservas.get(praiaID);
    ArrayList<Sombrinha> sombrinhasTemp = sombrinhas.get(praiaID);

    // Verificar se o array e null
    if (reservasTemp == null) {
      reservasTemp = new ArrayList<>();
    }

    // Filtrar as reservas da praia em questão pelo dia e hora que queremos
    for (Reserva reserva : reservasTemp) {
      if (reserva.getData() == dia && reserva.getHora() == hora) {
        reservasDiaEHora.add(reserva);
      }
    }

    // Filtrar as sombrinhas da praia sem reserva pelo id da sombrinha
    for (Sombrinha sombrinha : sombrinhasTemp) {
      boolean sombrinhaReservada = false;

      for (Reserva reserva : reservasDiaEHora) {
        if (sombrinha.getSombrinhaID() == reserva.getSombrinhaID()) {
          sombrinhaReservada = true;
          break;
        }
      }

      if (!sombrinhaReservada) {
        sombrinhasNaoReservadas.add(sombrinha);
      }
    }

    // Construir a string com as praias disponiveis
    StringBuilder str = new StringBuilder();
    for (Sombrinha sombrinha1 : sombrinhasNaoReservadas) {
      str.append("Praia ").append(sombrinha1.getPraiaID()).append("; Sombrinha ").append(sombrinha1.getSombrinhaID()).append("; Lotacao ").append(sombrinha1.getLotacao()).append("\n");
      System.out.println("Praia " + sombrinha1.getPraiaID() + "; Sombrinha " + sombrinha1.getSombrinhaID() + "; Lotacao " + sombrinha1.getLotacao());
    }
    return str.toString();
  }

}
