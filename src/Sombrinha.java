public class Sombrinha {
    int lotacao;
    int sombrinhaID;
    int hora;
    int data;


    public Sombrinha(int sombrinhaID, int lotacao) {
        this.sombrinhaID = sombrinhaID;
        this.lotacao = lotacao;
    }

    public Sombrinha(int sombrinhaID, int hora, int data, int lotacao) {
        this.sombrinhaID = sombrinhaID;
        this.hora = hora;
        this.data = data;
        this.lotacao = lotacao;
    }
}

