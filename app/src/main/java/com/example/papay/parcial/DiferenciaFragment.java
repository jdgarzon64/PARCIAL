package com.example.papay.parcial;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import controlador.DeudaController;
import controlador.PrestamoController;
import modelo.Deudor;
import modelo.Prestamo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiferenciaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiferenciaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiferenciaFragment extends Fragment {
    PrestamoController prestamoController;
    DeudaController deudaController;
    View view;
    TextView meDebenTv;
    TextView debo;
    TextView diferencia;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DiferenciaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiferenciaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiferenciaFragment newInstance(String param1, String param2) {
        DiferenciaFragment fragment = new DiferenciaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_diferencia, container, false);
        prestamoController = new PrestamoController(getActivity());
        deudaController = new DeudaController(getActivity());
        meDebenTv = (TextView) view.findViewById(R.id.meDebenTv);
        debo = (TextView) view.findViewById(R.id.deboTv);
        diferencia = (TextView) view.findViewById(R.id.saldoTv);
        cargarPrestamos();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void cargarPrestamos() {
        ArrayList<Prestamo> listadoPrestamos = prestamoController.buscarDeudores();
        ArrayList<Deudor> listadoDeudores = deudaController.buscarDeudores();
        int prestamos = 0;
        int deudas = 0;
        int resta = 0;
        for (Prestamo p : listadoPrestamos) {
            prestamos += Integer.parseInt(p.getMonto());
        }
        for (Deudor d : listadoDeudores) {
            deudas += Integer.parseInt(d.getMonto());
        }
        resta = prestamos - deudas;
        meDebenTv.setText("me deben: "+String.valueOf(prestamos));
        debo.setText("debo: "+String.valueOf(deudas));
        diferencia.setText("Diferencia: "+String.valueOf(resta));

    }

}
