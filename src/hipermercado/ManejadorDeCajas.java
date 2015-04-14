package hipermercado;

import java.util.ArrayList;

public class ManejadorDeCajas extends Thread {

    private final int numCajas;
    private final ArrayList<Caja> hipermercado;
    private final Cola cola;
    private final Contabilidad contable;
    
    public ManejadorDeCajas(int numCajas,Cola cola, Contabilidad cajaFuerte) {
        this.numCajas = numCajas;
        hipermercado = new ArrayList<>();
        this.cola = cola;
        this.contable = cajaFuerte;
    }

    public void creaCajas() {
        for (int i = 0; i < numCajas; i++) {
           hipermercado.add(new Caja(cola, contable));
        }
    }
    
    public void arrancaCajas(){
        for (Caja caja : hipermercado) {
            caja.start();
        }
    }
    
    public void interrupeCajaEnElIndice(int i){
        if(hipermercado.get(i).isAlive()){
            hipermercado.get(i).interrupt();
            System.out.println("Se interrumpe la caja " + i );
        }else{
            System.out.println("No se ha podido interrumpir la caja " + i);
        }
    }
    
    public int cuentaCajasVivas(){
        int resultado = 0;
        for (Caja caja : hipermercado) {
            if(caja.isAlive()) resultado++;
        }
        return resultado;
    }
}
