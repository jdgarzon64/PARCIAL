package controlador;

import android.app.Activity;

import java.util.ArrayList;

import dao.PrestamoDAO;
import modelo.Prestamo;

/**
 * Created by papay on 13/03/2018.
 */

public class PrestamoController {
    PrestamoDAO dao;
    private ArrayList<Prestamo> listaUsuarios;

    public PrestamoController(Activity activity) {

        dao = new PrestamoDAO(activity);
    }

    public ArrayList<Prestamo> buscarDeudores() {

        listaUsuarios = new ArrayList<>();
        listaUsuarios = dao.listar();
        if (listaUsuarios.size() > 0) {
            return listaUsuarios;
        } else {
            return null;
        }

    }

    public boolean guardar(Prestamo obj) {
        return dao.guardar(obj);
    }
    public Prestamo buscar(String obj) {
        return dao.buscar(obj);
    }
}
