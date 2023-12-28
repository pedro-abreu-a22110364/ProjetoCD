import java.util.ArrayList;

public class Sombrinha extends Praia {
    int sombrinhaID;
    int lotacao;
    ArrayList<Reserva> reservas;


    public Sombrinha(char praiaID, int sombrinhaID, int lotacao) {
        super(praiaID);
        this.sombrinhaID = sombrinhaID;
        this.lotacao = lotacao;
        this.reservas = new ArrayList<>();
    }
}

