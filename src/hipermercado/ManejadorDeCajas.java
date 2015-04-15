package hipermercado;

import java.util.ArrayList;

public class ManejadorDeCajas extends Thread implements StatusVisible {

    private final int numCajas;
    private final ArrayList<Caja> hipermercado;
    private final Cola cola;
    private final Contabilidad contable;

    public ManejadorDeCajas(int numCajas, Cola cola, Contabilidad cajaFuerte) {
        this.numCajas = numCajas;
        hipermercado = new ArrayList<>();
        this.cola = cola;
        this.contable = cajaFuerte;
        creaCajas();
    }

    private void creaCajas() {
        for (int i = 0; i < numCajas; i++) {
            hipermercado.add(new Caja(cola, contable));
        }
    }

    public void arrancaCajas() {
        for (Caja caja : hipermercado) {
            caja.start();
        }
        for (Caja caja : hipermercado) {
            try {
                this.join();
            } catch (InterruptedException ex) {
                System.out.println("Se ha interrumpido al manejador de cajas");
                System.out.println("Interrumpiendo todas las cajas");
                for (Caja hipermercado2 : hipermercado) {
                    hipermercado2.interrupt();
                }
                break;
            }
        }
    }
    
    
    @Override
    public void interrupt(){
        for (Caja caja : hipermercado) {
            caja.interrupt();
        }
        super.interrupt();
    }

    public void interrupeCajaEnElIndice(int i) {
        if (hipermercado.get(i).isAlive()) {
            hipermercado.get(i).interrupt();
            System.out.println("Se interrumpe la caja " + i);
            if (cuentaCajasVivas() == 0) {
                cola.cerrar();
            }
        } else {
            System.out.println("No se ha podido interrumpir la caja " + i);
        }
    }

    public int cuentaCajasVivas() {
        int resultado = 0;
        for (Caja caja : hipermercado) {
            if (caja.isAlive()) {
                resultado++;
            }
        }
        return resultado;
    }

    @Override
    public void showStatus() {
        System.out.println("Numero de cajas creadas:" + numCajas);
    }
    
}
