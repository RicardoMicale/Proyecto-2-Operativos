
/**
 *
 * @author carlo
 */
public class Pana {
    int id, prioridad, contador;

    public Pana (int id,int prioridad){
        this.id = id;
        this.prioridad = prioridad;
        this.contador = 0;
    }

    public void subirContador(){
        this.contador += 1;
    }

    public void reiniciarContador() {
        this.contador = 0;
    }
}
