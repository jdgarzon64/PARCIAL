package dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import Infraestructura.Conexion;
import modelo.Deudor;



public class DeudaDAO {
    /**
     * clase conexion
     */
    private Conexion conex;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public DeudaDAO(Activity activity) {
        conex = new Conexion(activity);
    }


    public boolean guardar(Deudor deudor) {
        ContentValues registro = new ContentValues();
        registro.put("nombre", deudor.getNombre());
        registro.put("monto", deudor.getMonto());
        registro.put("rutafoto", deudor.getRutaFoto());
        return conex.ejecutarInsert("deudores", registro);
    }

    public Deudor buscar(String nombre) {
        Deudor deudor = null;
        String consulta = "select deudor_id, nombre, monto, rutafoto from deudores where "
                + " nombre = '" + nombre + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            deudor = new Deudor(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3));
        }
        conex.cerrarConexion();
        return deudor;
    }


    public ArrayList<Deudor> listar() {
        ArrayList<Deudor> listaDeudores = new ArrayList<>();
        String consulta = "select deudor_id, nombre, monto, rutafoto from deudores";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.moveToFirst()) {
            do {
                Deudor deudor = new Deudor(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3));
                listaDeudores.add(deudor);
            } while (temp.moveToNext());
        }
        return listaDeudores;
    }

}
