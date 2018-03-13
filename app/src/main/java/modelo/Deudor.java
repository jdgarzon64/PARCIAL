package modelo;

/**
 * Created by papay on 13/03/2018.
 */

public class Deudor {

    public int deudor_id;
    public String nombre;
    public String monto;
    public String rutaFoto;

    public Deudor(int deudor_id, String nombre, String monto, String rutaFoto) {
        this.deudor_id=deudor_id;
        this.nombre = nombre;
        this.monto = monto;
        this.rutaFoto = rutaFoto;
    }

    public int getDeudor_id() {
        return deudor_id;
    }

    public void setDeudor_id(int deudor_id) {
        this.deudor_id = deudor_id;
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
