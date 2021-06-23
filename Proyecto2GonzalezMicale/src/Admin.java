import java.lang.Math;
import java.util.*;
/**
 *
 * @author carlo
 */
public class Admin {
    //Colas de prioridad
    Queue<Pana> nivel1 = new LinkedList<>();
    Queue<Pana> nivel2 = new LinkedList<>();
    Queue<Pana> nivel3 = new LinkedList<>();
    Queue<Pana> mantenimiento = new LinkedList<>();

    // Constructor
    // public Admin (){
    // 	this.nivel1 = new LinkedList<>();
    // 	this.nivel2 = new LinkedList<>();
    // 	this.nivel3 = new LinkedList<>();
//   this.mantenimiento = new LinkedList<>();
    // }

    //Numero de id único de cada juguete. 
    public static int idPanas = 1;
    public void crearPana(int ciclos){
        //Accede al codigo con una probabilidad de 70% y cada dos ciclos de revision.
        if(generarDecimal(0.0, 1.0) <= 0.7 && ciclos % 2 == 0){
            int id = idPanas;
            int prioridad = ((int) (Math.random() * 3) + 1);
            Pana nuevoPana = new Pana(id, prioridad);
            idPanas++;
            Queue<Pana> colaPrioridad = obtenerCola(nuevoPana.prioridad);
            encolarPana(nuevoPana, colaPrioridad);
        }
        else{
            System.out.println("No se creo Pana.");
        }
    }
    public void encolarPana(Pana pana, Queue<Pana> nivel) {
        nivel.add(pana);
        System.out.println("Encolando Pana " + pana.id + "en nivel " + pana.prioridad);
    }
    
    public void elegirRevision(){
        Pana primerPana =null;
        if(!(nivel1.isEmpty())){
            primerPana = nivel1.poll();
        }else if(!(nivel2.isEmpty())){
            primerPana = nivel2.poll();}
        else if(!(nivel3.isEmpty())){
            primerPana = nivel3.poll();
        }else{
            System.out.println("Las colas estan vacias");
        }
        actualizarColas();
        Main.robot.realizarPruebas(primerPana);
    }
    
    public Queue<Pana> obtenerCola(int prioridad) {
        switch(prioridad){
            case 1:
                return nivel1;
            case 2:
                return nivel2;
            case 3:
                return nivel3;
            case 4:
                return mantenimiento;
            default:
                return null;
        }
    }
    
    public void actualizarColas() {
    //Revisa que la segunda cola no este vacia
    //De lo contrario no hay nada que actualizar
    if(!this.nivel2.isEmpty()) {
    //Recorre la cola actualizando los valores
    for(int i = 0; i < this.nivel2.size(); ++i) {
        Pana pana_n2 = this.nivel2.poll();
        //Falta actualizar la lista
        //Aumenta su contador
        pana_n2.contador = pana_n2.contador + 1;
        //Se revisa el numero del contador para ver si se 
        //reinicia y se le sube la prioridad
        if(pana_n2.contador == 15) {
            //Se reinicia el contador
            pana_n2.contador = 0;
            //Se sube su prioridad
            pana_n2.prioridad = pana_n2.prioridad + 1;
            //Se busca la cola de ese nivel de prioridad
            Queue<Pana> cola_aux = obtenerCola(pana_n2.prioridad);
            //Se encola en la cola de la prioridad nueva
            encolarPana(pana_n2, cola_aux);
        }else {
            //Como el contador no ha llegado al maximo, 
            //el pana vuelve a su cola original
            this.nivel2.add(pana_n2);
            }
        }
    }
    //Se revisa tambien la cola de menor prioridad
    //Por si se puede actualizar
    if(!this.nivel3.isEmpty()) {
        for(int i = 0; i < this.nivel3.size(); ++i) {
        Pana pana_n3 = this.nivel3.poll();

        //Falta actualizar la lista

        //Aumenta su contador
        pana_n3.subirContador();
        //Se revisa el numero del contador para ver si se 
        //reinicia y se le sube la prioridad
        if(pana_n3.contador == 15) {
            //Se reinicia el contador
            pana_n3.reiniciarContador();
            //Se sube su prioridad
            pana_n3.prioridad = pana_n3.prioridad + 1;
            //Se busca la cola de ese nivel de prioridad
            Queue<Pana> cola_aux = obtenerCola(pana_n3.prioridad);
            //Se encola en la cola de la prioridad nueva
            encolarPana(pana_n3, cola_aux);
            }else {
                //Como el contador no ha llegado al maximo, 
                //el pana vuelve a su cola original
                this.nivel3.add(pana_n3);
            }
        }
    }
    }

  public void mantenimientoListo() {
    //Si la cola de mantenimiento esta vacia no hay nada que hacer
    if(!this.mantenimiento.isEmpty()) {
      
      double probabilidad = generarDecimal(0.0, 1.0);

      if(probabilidad <= 0.45) {
        //Si entra en la probabilidad del 45% se devuelve el pana
        //que esta al principio de la cola de mantenimiento a la cola de
        //revision
        Pana reparado = this.mantenimiento.poll();
        Queue<Pana> cola_aux = obtenerCola(reparado.prioridad);

        //falta Actualizar lista de mantenimiento de prioridad 4
        
        this.encolarPana(reparado,cola_aux);
        //falta Actualizar la cola de la prioridad del pana
      }
    }
  }

	public double generarDecimal(double min, double max){
		return ((Math.random() * (max - min)) + min);
	}
}
