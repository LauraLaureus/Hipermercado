package hipermercado;


public class Cola implements StatusVisible {

    private boolean abierto;
    private int tamaño;
    private int tamañoMax;
    private ClienteEnCola porElPrincipio;
    private ClienteEnCola porElFinal;

    @Override
    public void showStatus() {
        System.out.println("Estado:" + abierto);
        System.out.println("Tamaño actual: " + tamaño);
        System.out.println("Tamaño máximo" + tamañoMax);
    }

    private class ClienteEnCola {

        public Cliente cl;
        public ClienteEnCola siguiente;
    }

    public Cola() {
        tamaño = 0;
        tamañoMax = tamaño;
        this.porElPrincipio = null;
        this.porElFinal = null;
        this.abierto = true;
    }

    public synchronized Cliente sacar() {
        /*Saca y extrae si no hay nada devolver null*/

        if (tamaño == 0) {
            if (abierto) {
                try {
                      this.wait((long) 10000);
                    if (tamaño == 0) {
                        return null;
                    }
                } catch (InterruptedException ex) {
                }
            }else{
                return null;
            }
        }

        Cliente cl = porElPrincipio.cl;
        porElPrincipio = porElPrincipio.siguiente;
        tamaño--;
        
        System.out.println("COLA: se saca un cliente.");
        return cl;
    }

    public synchronized void añadirFinal(Cliente cl) {
        notify(); //o notify solamente camiar de sitio????
        if (!abierto) {
            return;
        }
        ClienteEnCola cec = new ClienteEnCola();
        cec.cl = cl;
        cec.siguiente = null;

        if (tamaño == 0) {
            porElPrincipio = cec;
            porElFinal = cec;
        } else {
            porElFinal.siguiente = cec;
            porElFinal = cec;
        }

        tamaño++;
        modificaElTamañoMaximo();
        
        System.out.println("COLA: se ha añadido un cliente.");
    }

    public synchronized void añadirPrincipio(Cliente cl) {
        ClienteEnCola cec = new ClienteEnCola();
        cec.cl = cl;
        cec.siguiente = null;

        if (tamaño == 0) {
            porElPrincipio = cec;
            porElFinal = cec;
        } else {
            cec.siguiente = porElPrincipio;
            porElPrincipio = cec;
        }
        tamaño++;
        modificaElTamañoMaximo();
    }

    public int tamañoMaximo() {
        return this.tamañoMax;
    }

    private void modificaElTamañoMaximo() {
        if (tamaño > tamañoMax) {
            tamañoMax = tamaño;
        }
    }

    public void cerrar() {
        this.abierto = false;
    }
}
