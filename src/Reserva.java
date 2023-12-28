public class Reserva extends Sombrinha {
    int hora;
    //int minutos;
    int data;

    public Reserva(char praiaID, int sombrinhaID, int lotacao, int hora, int data){
        super(praiaID,sombrinhaID,lotacao);
        this.hora = hora;
        this.data = data;
    }
}
