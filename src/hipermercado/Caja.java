
package hipermercado;

public class Caja extends Thread implements StatusVisible{
    
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
                
                System.out.println("CAJA" + this.getId() + ": se va a atender al "
                        + " cliente " + cliente.dameNombre());
                Thread.sleep((long)recaudacionParcial/10);
                System.out.println("CAJA" + this.getId() + ": se ha atendido a "
                +   cliente.dameNombre());
                
                cliente = cola.sacar();
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

    @Override
    public void showStatus() {
        System.out.println("Recaudacion actual: " + recaudacion);
    }

        
}
