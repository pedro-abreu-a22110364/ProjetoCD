import java.util.ArrayList;

public class Praia {

    char praiaID;
    ArrayList<Sombrinha> sombrinhas = new ArrayList<>();

    public Praia(char praiaID) {
        this.praiaID = praiaID;
    }

    public void adicionarSombrinha(Sombrinha sombrinha) {
        sombrinhas.add(sombrinha);
    }
}
