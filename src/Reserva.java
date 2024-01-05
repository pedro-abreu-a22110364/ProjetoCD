public class Reserva extends Sombrinha {
    private int hora;
    private int data;

    public Reserva(String praiaID, int sombrinhaID, int lotacao, int hora, int data){
        super(praiaID, sombrinhaID, lotacao);
        this.hora = hora;
        this.data = data;
    }

    public int getHora() {
        return hora;
    }

    public int getData() {
        return data;
    }
}
