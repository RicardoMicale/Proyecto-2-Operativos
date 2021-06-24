
/**
 *
 * @author Ricardo Micale
 *         Carlos Gonzalez
 */
public class Pana {
    int id, prioridad, contador;

    public Pana (int id,int prioridad){
        this.id = id;
        this.prioridad = prioridad;
        this.contador = 0;
    }

    /**
     *Es llamado para subir el contador del pana.
     */
    public void subirContador(){
        this.contador += 1;
    }

    /**
     *Reinicia el valor del contador del pana a cero.
     */
    public void reiniciarContador() {
        this.contador = 0;
    }
}
