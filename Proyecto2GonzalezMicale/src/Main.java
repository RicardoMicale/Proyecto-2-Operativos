
import javax.swing.JOptionPane;

/**
 *
 * @author Ricardo Micale
 *         Carlos Gonzalez
 */
public class Main {
    public static Interfaz i = new Interfaz();
    public static Robot robot = new Robot();
    public static Admin admin = new Admin();
    public static boolean vivo = true;
    public static int panasVendidos = 0;
    public static void main(String[] args) {
    int ciclo = 1;
        try{
            i.setVisible(vivo);
            Pana panaInicial = new Pana(1,1);
            robot.realizarPruebas(panaInicial);
            while(vivo){
                admin.elegirRevision();
                admin.crearPana(ciclo);
                admin.mantenimientoListo();
                ciclo++;
                System.out.println("Ciclo N°: "+ciclo);
                }
            }catch(Exception e){JOptionPane.showMessageDialog(null, "Ocurrio un error.\nRevisa los datos y corre de nuevo el programa.");}
    }
}    
