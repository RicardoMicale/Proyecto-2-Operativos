import java.lang.Math;
import java.util.*;
/**
 *
 * @author Ricardo Micale
 *         Carlos Gonzalez
 */
public class Admin {
    //Colas de prioridad
    Queue<Pana> nivel1 = new LinkedList<>();
    Queue<Pana> nivel2 = new LinkedList<>();
    Queue<Pana> nivel3 = new LinkedList<>();
    Queue<Pana> mantenimiento = new LinkedList<>();


    //Numero de id único de cada juguete. 
    public static int idPanas = 2;

    /**
     *Crea un pana. La probabilidad al inicio elige si se crea y se encola el proceso.
     * @param ciclos
     * Parametro en Main que toma los ciclos de producción.
     */
    public void crearPana(int ciclos){
        //Accede al codigo con una probabilidad de 70% y cada dos ciclos de revision.
        if(generarDecimal(0.0, 1.0) <= 0.7 && ciclos % 2 == 0){
            int id = idPanas;
            int prioridad = ((int) (Math.random() * 3) + 1);
            Pana nuevoPana = new Pana(id, prioridad);
            idPanas++;
            Queue<Pana> colaPrioridad = obtenerCola(nuevoPana.prioridad);
            System.out.println("Creado Pana "+nuevoPana.id);
            encolarPana(nuevoPana, colaPrioridad);
        }
        else{
            System.out.println("No se creo Pana.");
        }
    }

    /**
     *Encola un objeto pana una vez creado o terminada su revisión
     * @param pana
     * El objeto Pana a que se le debe asignar una cola.
     * @param nivel
     * La cola donde el pana espera para su revisión. Es determinada por la obtenerCola()
     */
    public void encolarPana(Pana pana, Queue<Pana> nivel) {
        actualizarListasInterfaz();
        nivel.add(pana);
        System.out.println("Encolando Pana " + pana.id + " en nivel " + pana.prioridad);
        actualizarListasInterfaz();
    }
    
    /**
     *Elige que pana debe ser revisado por orden de prioridad.
     */
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
        actualizarListasInterfaz();
        Main.robot.realizarPruebas(primerPana);
        actualizarListasInterfaz();
    }
    
    /**
     *Obtiene la cola de prioridad en la que se encolara el Pana.
     * @param prioridad
     * El atributo entero de prioridad asignado a los Panas.
     * @return
     * Devuelve la cola a asignar.
     */
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
                return mantenimiento;
        }
    }
    
    public void actualizarColas() {
    //Revisa que la segunda cola no este vacia
    //De lo contrario no hay nada que actualizar
//    actualizarListasInterfaz();
    if(!this.nivel2.isEmpty()) {
    //Recorre la cola actualizando los valores
    for(int i = 0; i < this.nivel2.size(); ++i) {
        Pana pana_n2 = this.nivel2.poll();
        //Aumenta su contador
        pana_n2.subirContador();
        //Se revisa el numero del contador para ver si se 
        //reinicia y se le sube la prioridad
        if(pana_n2.contador == 15) {
            //Se reinicia el contador
            pana_n2.reiniciarContador();
            //Se sube su prioridad
            pana_n2.prioridad = pana_n2.prioridad - 1;
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

//        actualizarListasInterfaz();

        //Aumenta su contador
        pana_n3.subirContador();
        //Se revisa el numero del contador para ver si se 
        //reinicia y se le sube la prioridad
        if(pana_n3.contador == 15) {
            //Se reinicia el contador
            pana_n3.reiniciarContador();
            //Se sube su prioridad
            pana_n3.prioridad = pana_n3.prioridad - 1;
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
    actualizarListasInterfaz();
    if(!this.mantenimiento.isEmpty()) {
      
      double probabilidad = generarDecimal(0.0, 1.0);

      if(probabilidad <= 0.45) {
        //Si entra en la probabilidad del 45% se devuelve el pana
        //que esta al principio de la cola de mantenimiento a la cola de
        //revision
        Pana reparado = this.mantenimiento.poll();
        Queue<Pana> cola_aux = obtenerCola(reparado.prioridad);

        this.encolarPana(reparado,cola_aux);
      }
    }
  }
  
    /**
     *Actualiza las colas de la interfaz gráfica.
     */
    public void actualizarListasInterfaz(){
        Interfaz.labelCola1.setText("Cola 1: " + valoresListas(Main.admin.nivel1));
        Interfaz.labelCola2.setText("Cola 2: " + valoresListas(Main.admin.nivel2));
        Interfaz.labelCola3.setText("Cola 3: " + valoresListas(Main.admin.nivel3)); 
        Interfaz.labelMantenimiento.setText("Mantenimiento: " + valoresListas(Main.admin.mantenimiento));
    }
    
    /**
     *Obtiene los valores escritos de cada cola
     * @param lista
     * La cola que se quieren obtener los elementos
     * @return
     * La cadena con el atributo id de los Panas en la cola.
     */
    public String valoresListas(Queue<Pana> lista){
        String valores="";
        Pana[] panas = new Pana[lista.size()];
        lista.toArray(panas);
        for (Pana pana : panas) {
            valores += "-Pana N° " + pana.id + " ";
        }
        return valores;
    }

    /**
     *Genera un decimal aleatorio utilizando Math.random.
     * @param min
     * Valor minimo del número
     * @param max
     * Valor máximo del número
     * @return
     * Un número decimal aleatorio
     */
    public double generarDecimal(double min, double max){
            return ((Math.random() * (max - min)) + min);
    }
}
