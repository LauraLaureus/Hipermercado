
package hipermercado;

public class Caja extends Thread {
    
    private static int semilla = 0;
    private final Cola fila;
    private final Contabilidad cajaFuerte;
    private double recaudacion;
    private final int id;
    
    public Caja(Cola cola, Contabilidad cajaFuerte) {
        this.fila = cola;
        this.cajaFuerte = cajaFuerte;
        this.id = semilla;
        incrementaSemilla();
    }
    
    public synchronized static void incrementaSemilla(){
        semilla++;
    }
    
    @Override
    public long getId(){
        return this.id;
    }
    
    @Override
    public void run(){
    
        try{
            
            Cliente cliente = fila.sacar();
            while(cliente != null){
                double recaudacionParcial = cliente.damePrecioCarro();
                this.recaudacion += recaudacionParcial;
                
                System.out.println("CAJA" + this.getId() + ": se va a atender al "
                        + " cliente " + cliente.dameNombre() + Main.Momento.esteMomento());
                Thread.sleep((long)recaudacionParcial/10*1000);
                System.out.println("CAJA" + this.getId() + ": se ha atendido a "
                +   cliente.dameNombre() + Main.Momento.esteMomento());
                
                cliente = fila.sacar();
            }
            System.out.println("CAJA" + this.getId() + " no tengo más clientes.");
        }
        catch(InterruptedException e){
            
        }
        finally{
            pasarLaContabilidad();
        }
    }

    private void pasarLaContabilidad() {
        cajaFuerte.añadeSaldo(recaudacion,  this.getId());
    }

    public void showStatus() {
        System.out.println("Recaudacion actual: " + recaudacion);
    }

        
}
