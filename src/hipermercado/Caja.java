
package hipermercado;

public class Caja extends Thread {
    
    private final Cola cola;
    private final Contabilidad cajaFuerte;
    private double recaudacion;
    
    public Caja(Cola cola, Contabilidad cajaFuerte) {
        this.cola = cola;
        this.cajaFuerte = cajaFuerte;
    }
    
    public void run(){
    
        try{
            Cliente cliente = cola.sacar();
            while(cliente != null){
                double recaudacionParcial = cliente.damePrecioCarro();
                this.recaudacion += recaudacionParcial;
                
                System.out.println("CAJA: se va a atender a un cliente");
                Thread.sleep((long)recaudacionParcial/10);
                System.out.println("CAJA: se ha atendido a un cliente");
                
                cliente = cola.sacar();
            }
            
        }
        catch(Exception e){
         //Me han interrumpido y.y
        }
        finally{
            pasarLaContabilidad();
        }
    }

    private void pasarLaContabilidad() {
        cajaFuerte.añadeSaldo(recaudacion);
    }

        
}
