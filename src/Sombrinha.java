import java.util.ArrayList;

public class Sombrinha extends Praia {
    int sombrinhaID;
    int lotacao;
    ArrayList<Reserva> reservas;


    public Sombrinha(int sombrinhaID, int lotacao) {
        this.sombrinhaID = sombrinhaID;
        this.lotacao = lotacao;
        this.reservas = new ArrayList<>();
    }
}

