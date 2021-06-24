import java.lang.Math;
import java.util.*;
/**
 *
 * @author Ricardo Micale
 *         Carlos Gonzalez
 */
public class Robot extends Thread {
    
	int tiempo = 10;

    /**
     *Toma un objeto pana y lo duerme por una cantidad de tiempo determinada.
     * Luego puede ser enviado al mercado o encolado de nuevo de acuerdo a las probabilidades.
     * @param pana
     * El objeto Pana a revisar.
     */
    public void realizarPruebas(Pana pana) {
    if(!(pana==null)){
        actualizarRobotInterfaz(pana);
        System.out.println("Robot revisando pana N°: " + pana.id);
        try{
            Thread.sleep(tiempo*1000);
        }catch (InterruptedException e) {
            System.out.println("Error en tiempo.");}
            //Se calcula la prioridad
        double probabilidad = generarDecimal(0.0, 1.0);
        //Se revisa el ciclo en el que esta
        //Cada 2 el administrador puede crear un pana nuevo
        if(probabilidad <= 0.3) {
            System.out.print("El pana N° "+pana.id+ " ha salido al mercado.");
            actualizarPanasMercado(pana);
        }else if(0.3 < probabilidad && probabilidad <= 0.8) {
            //En este caso se vuelve a encolar el pana para volver
            //a revisarlo
            Queue<Pana> cola_aux = Main.admin.obtenerCola(pana.prioridad);
            //Se vuelve a encolar el pana en la cola
            Main.admin.encolarPana(pana, cola_aux);
        }else if(probabilidad > 0.8) {
            //Se encola el pana en la cola de mantenimiento
            Queue<Pana> cola_aux = Main.admin.obtenerCola(4);
            Main.admin.encolarPana(pana, cola_aux);
        }
        actualizarRobotInterfaz(pana);
    }else{
        
        System.out.println("No se obtuvo pana en robot.");
    }}

    /**
     *Actualiza la interfaz gráfica del Robot.
     * @param pana
     * Toma el atributo id para mostrarlo en revisión.
     */
    public void actualizarRobotInterfaz(Pana pana){
        String id = String.valueOf(pana.id);
        Interfaz.idRevision.setText(id);
    }
    
    /**
     *Muestra una lista de los Panas que salieron al mercado.
     * @param pana
     * Toma los id de los Panas que salieron al mercado.
     */
    public void actualizarPanasMercado(Pana pana){
        String id = String.valueOf(pana.id);
        Interfaz.jTextAreaPanasMercado.append("-Pana N° " + id + " ");
    }
  
    /**
     *Genera un decimal aleatorio.
     * @param min
     * Valor minimo
     * @param max
     * Valor maximo
     * @return
     * Un número decimal aleatorio.
     */
    public double generarDecimal(double min, double max){
    return ((Math.random() * (max - min)) + min);
    }
}
