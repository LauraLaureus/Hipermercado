package hipermercado;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {

    public static Cola cola = new Cola();

    public static void main(String[] args) throws InterruptedException {

        int numCajas = new CLIUserInterface().preguntaNumCajas();
        int clientesTotales = new CLIUserInterface().preguntaNumClientes();

        Contabilidad cajaFuerte = new Contabilidad(numCajas, cola);
        ManejadorDeCajas jefe = new ManejadorDeCajas(numCajas, cola, cajaFuerte);
        ManejadorDeClientes directorDeClientes
                = new ManejadorDeClientes(clientesTotales, cola, jefe);

        Momento.momentoInicioSimulacion();
        directorDeClientes.start();
        jefe.arrancaCajas();

        jefe.join();
        directorDeClientes.join();

        while (jefe.cuentaCajasVivas() > 0) {
        }
        if(!Momento.expiróLaSimulacion())
            Momento.aborta();
            
        System.out.println("FIN DE LA SIMULACIóN");
        System.out.println("STATUS COLA________");
        cola.showStatus();
        System.out.println("STATUS CONTABILIDAD_________");
        cajaFuerte.showStatus();
        System.out.println("STATUS MANEJADOR DE CLIENTES______");
        directorDeClientes.showStatus();
        System.out.println("STATUS MANEJADOR DE CAJA___________");
        jefe.showStatus();
    }

    static class ManejadorDeClientes extends Thread {

        private final ManejadorDeCajas caja_mnj;
        private int clientesAtendidos;
        private final int clientesTotales;
        private final Cola cola;

        public ManejadorDeClientes(int clientesTotales, Cola cola, ManejadorDeCajas mnj) {
            this.clientesTotales = clientesTotales;
            this.cola = cola;
            this.clientesAtendidos = 0;
            this.caja_mnj = mnj;
        }

        @Override
        public void run() {

            for (int i = 0; i < clientesTotales; i++) {
                if (caja_mnj.cuentaCajasVivas() == 0 || Momento.expiróLaSimulacion()) {
                    break;
                }
                cola.añadirFinal(new Cliente());
                clientesAtendidos = i;
                try {
                    Thread.sleep((long) (5000 * Math.random()));
                } catch (InterruptedException ex) {
                    break;
                }
            }
            System.out.println("MANEJADOR-CLIENTES: Ya no se generarán más clientes"
                    + Main.Momento.esteMomento());
        }

        public void showStatus() {
            System.out.println("Clientes que quedaron fuera de la cola:"
                    + (clientesTotales - clientesAtendidos - 1));
        }
    }

    static class ManejadorDeCajas extends Thread {

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

        }

        @Override
        public void interrupt() {
            for (Caja caja : hipermercado) {
                caja.interrupt();
            }
            super.interrupt();
        }

        public synchronized int cuentaCajasVivas() {
            int resultado = 0;
            for (Caja caja : hipermercado) {
                if (caja.isAlive()) {
                    resultado++;
                }
            }
            return resultado;
        }

        public void showStatus() {
            System.out.println("Numero de cajas creadas:" + numCajas);
        }

        public ArrayList<Caja> dameConjuntoCajas() {
            return this.hipermercado;
        }
    }

    static class CLIUserInterface {

        public int preguntaNumCajas() {

            Scanner teclado = new Scanner(System.in);

            int respuesta;
            System.out.println("¿Cuántas cajas tiene el hipermercado?");
            respuesta = teclado.nextInt();

            return respuesta;
        }

        public int preguntaNumClientes() {

            Scanner teclado = new Scanner(System.in);

            int respuesta;
            System.out.println("¿Cuántos clientes pasaron durante el día?");
            respuesta = teclado.nextInt();

            return respuesta;
        }

    }

    

    static class Momento {

        public static long start = 0;
        

        public static synchronized String esteMomento() {
            GregorianCalendar calendar = new GregorianCalendar();
            Integer h = calendar.get(GregorianCalendar.HOUR_OF_DAY);
            Integer m = calendar.get(GregorianCalendar.MINUTE);
            Integer s = calendar.get(GregorianCalendar.SECOND);

            return "(" + h.toString() + ":" + m.toString() + ":" + s.toString() + ")";
        }

        public static void momentoInicioSimulacion() {
            if (start == 0) {
                start = System.nanoTime();
            }
        }

        public synchronized static boolean expiróLaSimulacion() {
            if(start == 0) return true;
            long resta = System.nanoTime() - start; 
            return resta >= 60000000000L;       
        }

        private static void aborta() {
            start = 0;
        }
    }
}
