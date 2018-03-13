package controlador;

import android.app.Activity;

import java.util.ArrayList;

import dao.DeudaDAO;
import modelo.Deudor;

/**
 * Created by papay on 13/03/2018.
 */

public class DeudaController {
    DeudaDAO dao;
    private ArrayList<Deudor> listaUsuarios;

    public DeudaController(Activity activity) {

        dao = new DeudaDAO(activity);
    }

    public ArrayList<Deudor> buscarDeudores() {

        listaUsuarios = new ArrayList<>();
        listaUsuarios = dao.listar();
        if (listaUsuarios.size() > 0) {
            return listaUsuarios;
        } else {
            return null;
        }

    }

    public boolean guardar(Deudor obj) {
        return dao.guardar(obj);
    }
    public Deudor buscar(String obj) {
        return dao.buscar(obj);
    }

}
