package dao;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;


import java.util.ArrayList;
import java.util.List;

import Infraestructura.Conexion;
import modelo.Prestamo;


public class PrestamoDAO {
    /**
     * clase conexion
     */
    private Conexion conex;

    /**
     * constructor de la clase
     *
     * @param activity
     */
    public PrestamoDAO(Activity activity) {
        conex = new Conexion(activity);
    }


    public boolean guardar(Prestamo prestamo) {
        ContentValues registro = new ContentValues();
        registro.put("nombre", prestamo.getNombre());
        registro.put("monto", prestamo.getMonto());
        registro.put("rutafoto", prestamo.getRutaFoto());
        return conex.ejecutarInsert("prestamos", registro);
    }

    public Prestamo buscar(String nombre) {
        Prestamo prestamo = null;
        String consulta = "select prestamo_id, nombre, monto, rutafoto from prestamos where "
                + " nombre = '" + nombre + "'";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.getCount() > 0) {
            temp.moveToFirst();
            prestamo = new Prestamo(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3));
        }
        conex.cerrarConexion();
        return prestamo;
    }


    public ArrayList<Prestamo> listar() {
        ArrayList<Prestamo> listaPrestamos = new ArrayList<>();
        String consulta = "select prestamo_id, nombre, monto, rutafoto from prestamos";
        Cursor temp = conex.ejecutarSearch(consulta);
        if (temp.moveToFirst()) {
            do {
                Prestamo prestamo = new Prestamo(temp.getInt(0), temp.getString(1), temp.getString(2), temp.getString(3));
                listaPrestamos.add(prestamo);
            } while (temp.moveToNext());
        }
        return listaPrestamos;
    }

}
