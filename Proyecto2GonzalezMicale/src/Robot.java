import java.lang.Math;
import java.util.*;
/**
 *
 * @author carlo
 */
public class Robot extends Thread {
    
	int tiempo = 3;

  public void realizarPruebas(Pana pana) {
    if(!(pana==null)){
        actualizarRobotInterfaz(pana);
        System.out.println("Robot revisando pana N°: " + pana.id);
        try{
            Thread.sleep(tiempo*1000);
        }catch (Exception e) {
            System.out.println("Error en tiempo.");}
            //Se calcula la prioridad
        double probabilidad = generarDecimal(0.0, 1.0);
        //Se revisa el ciclo en el que esta
        //Cada 2 el administrador puede crear un pana nuevo
        if(probabilidad <= 0.3) {
            System.out.print("El pana N° "+pana.id+ " ha salido al mercado.");
        }else if(0.3 < probabilidad && probabilidad <= 0.8) {
            //En este caso se vuelve a encolar el pana para volver
            //a revisarlo
            Queue<Pana> cola_aux = Main.admin.obtenerCola(pana.prioridad);
            cola_aux.poll();
            //Se vuelve a encolar el pana en la cola
            Main.admin.encolarPana(pana, cola_aux);
        }else if(probabilidad > 0.8) {
            //Se encola el pana en la cola de mantenimiento
            Queue<Pana> cola_aux = Main.admin.obtenerCola(4);
            Main.admin.encolarPana(pana, cola_aux);
        }
    }else{
        System.out.println("No se obtuvo pana en robot.");
    }}

    public void actualizarRobotInterfaz(Pana pana){
        String id = String.valueOf(pana.id);
        Interfaz.idRevision.setText(id);
    }
  
  
  public double generarDecimal(double min, double max){
    return ((Math.random() * (max - min)) + min);
    }
}
