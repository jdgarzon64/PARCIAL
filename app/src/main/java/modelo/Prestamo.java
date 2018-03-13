package modelo;

/**
 * Created by papay on 13/03/2018.
 */

public class Prestamo {
    public int prestamo_id;
    public String nombre;
    public String monto;
    public String rutaFoto;

    public Prestamo(int prestamo_id, String nombre, String monto, String rutaFoto) {
        this.prestamo_id = prestamo_id;
        this.nombre = nombre;
        this.monto = monto;
        this.rutaFoto = rutaFoto;
    }

    public int getPrestamo_id() {
        return prestamo_id;
    }

    public void setPrestamo_id(int prestamo_id) {
        this.prestamo_id = prestamo_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }
}
