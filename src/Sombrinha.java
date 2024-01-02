public class Sombrinha extends Praia {
    private int sombrinhaID;
    private int lotacao;

    public Sombrinha(String praiaID, int sombrinhaID, int lotacao) {
        super(praiaID);
        this.sombrinhaID = sombrinhaID;
        this.lotacao = lotacao;
    }

    public int getSombrinhaID() {
        return sombrinhaID;
    }

    public int getLotacao() {
        return lotacao;
    }
}

