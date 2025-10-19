public class CineApp {
    public class Cliente {
        private String nombreCliente;
        private String apellidoCliente;

        public Cliente(String nombreCliente, String apellidoCliente) {
            this.nombreCliente = nombreCliente;
            this.apellidoCliente = apellidoCliente;
        }

        public String getNombreCliente() {
            return nombreCliente;
        }

        public String getApellidoCliente() {
            return apellidoCliente;
        }

    }

    public class Compra {
        private Pelicula peliculaComprada;
        private Cliente comprador;
        private int cantidadBoletos;
        private double totalPago;

        public Compra(Pelicula peliculaComprada, Cliente comprador, int cantidadBoletos) {
            this.peliculaComprada = peliculaComprada;
            this.comprador = comprador;
            this.cantidadBoletos = cantidadBoletos;
            this.totalPago = cantidadBoletos * peliculaComprada.getPrecioBoleto();
        }

        public Pelicula getPeliculaComprada() {
            return peliculaComprada;
        }

        public Cliente getComprador() {
            return comprador;
        }

        public int getCantidadBoletos() {
            return cantidadBoletos;
        }

        public double getTotalPago() {
            return totalPago;
        }

    }

    public class Pelicula {
        private String tituloPelicula;
        private double precioBoleto;
        private int boletosVendidos;
        private final int CAPACIDAD_SALA = 23;

        public Pelicula(String tituloPelicula, double precioBoleto) {
            this.tituloPelicula = tituloPelicula;
            this.precioBoleto = precioBoleto;
            this.boletosVendidos = 0;
        }

        public void registrarVenta(int cantidad) {
            this.boletosVendidos += cantidad;
        }

        public String getTituloPelicula() {
            return tituloPelicula;
        }

        public void setTituloPelicula(String tituloPelicula) {
            this.tituloPelicula = tituloPelicula;
        }

        public double getPrecioBoleto() {
            return precioBoleto;
        }

        public void setPrecioBoleto(double precioBoleto) {
            this.precioBoleto = precioBoleto;
        }

        public int getBoletosVendidos() {
            return boletosVendidos;
        }

        public void setBoletosVendidos(int boletosVendidos) {
            this.boletosVendidos = boletosVendidos;
        }

        public void sumarVenta(int cantidad){
            this.boletosVendidos += cantidad;
        }

        public int getCapacidadDisponible() {
            return CAPACIDAD_SALA - boletosVendidos;
        }
    }

}
