package com.example.papay.parcial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import controlador.DeudaController;
import controlador.PrestamoController;
import modelo.Deudor;
import modelo.Prestamo;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DeudasFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DeudasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DeudasFragment extends Fragment {
    static final int CAMERA = 3;
    static final int READSD = 4;
    EditText nombreQuienDebo;
    EditText mondoDeudaDebo;
    Button btnbtnQuienDebo;
    Button btnMostrarFotoDeuda;
    View view;
    ImageView imageViewQuienDebo;
    DeudaController controller;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DeudasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeudasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeudasFragment newInstance(String param1, String param2) {
        DeudasFragment fragment = new DeudasFragment();
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
        // ask();
    }

    public void ask() {
        askForPermission(Manifest.permission.CAMERA, CAMERA);
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READSD);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_deudas, container, false);

        nombreQuienDebo = (EditText) view.findViewById(R.id.nombreQuienDebo);
        mondoDeudaDebo = (EditText) view.findViewById(R.id.mondoDeudaDebo);
        btnbtnQuienDebo = (Button) view.findViewById(R.id.btnQuienDebo);
        btnMostrarFotoDeuda = (Button) view.findViewById(R.id.btnMostrarFotoDeuda);
        imageViewQuienDebo = (ImageView) view.findViewById(R.id.imageViewQuienDebo);
        controller = new DeudaController(getActivity());
        btnbtnQuienDebo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(view);
            }
        });
        btnMostrarFotoDeuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFotos();
            }
        });
        ask();
        return view;


    }

    public void cargarFotos() {
        ArrayList<Deudor>deudores = controller.buscarDeudores();
        ArrayList<String>direcciones=new ArrayList<>();
        if(deudores!=null){
            for (Deudor d:deudores) {
                direcciones.add(d.getRutaFoto());
            }
            int r=(int)Math.random()*deudores.size()+0;
            //String dir = getActivity().getExternalFilesDir(null) + "/" + nombreQuienDebo.getText().toString() + ".jpg";
            Bitmap bitmap1 = BitmapFactory.decodeFile(deudores.get(r).getRutaFoto());
            imageViewQuienDebo.setImageBitmap(bitmap1);
        }
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


    public void tomarFoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File foto = new File(getActivity().getExternalFilesDir(null), nombreQuienDebo.getText().toString() + ".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
        String dir = getActivity().getExternalFilesDir(null) + "/" + nombreQuienDebo.getText().toString() + ".jpg";
        Deudor deuda = new Deudor(0, nombreQuienDebo.getText().toString(), mondoDeudaDebo.getText().toString(), dir);
        controller.guardar(deuda);
        startActivity(intent);
        recuperarFotos();

    }

    /*
    public void tomarFoto(View view) {
        if (TextUtils.isEmpty(nombreQuienDebo.getText())) {
            Toast.makeText(getActivity(), "debe escribir un nombre", Toast.LENGTH_SHORT).show();
        } else {
            askForPermission(Manifest.permission.CAMERA, CAMERA);
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.CAMERA) !=
                    PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), " It not permit", Toast.LENGTH_SHORT).show();

            } else {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File foto = new File(getActivity().getExternalFilesDir(null), nombreQuienDebo.getText().toString() + ".jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));

                String dir = getActivity().getExternalFilesDir(null) + "/" + nombreQuienDebo.getText().toString() + ".jpg";
                Deudor deuda = new Deudor(0, nombreQuienDebo.getText().toString(), mondoDeudaDebo.getText().toString(), dir);
                controller.guardar(deuda);
                startActivity(intent);
                recuperarFotos();
            }
        }
    }
*/
    public void recuperarFotos() {

    /*
        File[] fotos =getActivity().getExternalFilesDir(nombreDeudor.getText().toString()).listFiles();

        for (int i = 0; i < fotos.length; i++) {
            ImageView myImage = new ImageView(getActivity());

            int width = 20;
            int height = 20;
            LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width, height);

            myImage.setLayoutParams(parms);
            Bitmap bitmap = BitmapFactory.decodeFile(fotos[i].getAbsolutePath());
            myImage.setImageBitmap(bitmap);
            fotosL.addView(myImage);
            fotosL.setBaselineAlignedChildIndex(fotosL.getBaseline()+1);
        }
        */
        String dir = getActivity().getExternalFilesDir(null) + "/" + nombreQuienDebo.getText().toString() + ".jpg";
        Bitmap bitmap1 = BitmapFactory.decodeFile(dir);
        imageViewQuienDebo.setImageBitmap(bitmap1);
        Toast.makeText(getActivity(), dir + "", Toast.LENGTH_LONG).show();
    }

    public void askForPermission(String permit, int requestCode) {

        if (ContextCompat.checkSelfPermission(getActivity(), permit) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permit)) {

                ActivityCompat.requestPermissions(getActivity(), new String[]{permit}, requestCode);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permit}, requestCode);

            }
        }
    }
}
