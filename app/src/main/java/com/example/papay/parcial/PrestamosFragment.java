package com.example.papay.parcial;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import controlador.PrestamoController;
import modelo.Deudor;
import modelo.Prestamo;


public class PrestamosFragment extends Fragment {
    static final int CAMERA = 3;
    static final int READSD = 4;
    EditText nombreDeudor;
    EditText montoDeuda;
    Button btnTomarFotoDeudor;
    View view;
    Button btnCargarFotos;
    ImageView imageViewDeudor;
    PrestamoController controller;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_prestamos, container, false);
        nombreDeudor = (EditText) view.findViewById(R.id.nombreDeudorTxt);
        montoDeuda = (EditText) view.findViewById(R.id.montoDeudaTxt);
        btnTomarFotoDeudor = (Button) view.findViewById(R.id.btnTomarFoto);
        imageViewDeudor = (ImageView) view.findViewById(R.id.imageviewDeudor);
        controller = new PrestamoController(getActivity());
        btnCargarFotos=(Button)view.findViewById(R.id.btnCargarFotos);
        btnCargarFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarFotos();
            }
        });
        btnTomarFotoDeudor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tomarFoto(view);
            }
        });
        ask();
        return view;
    }
    public void ask() {
        askForPermission(Manifest.permission.CAMERA, CAMERA);
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READSD);

    }
    public void tomarFoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String filePath = Environment.getExternalStorageDirectory() +  nombreDeudor.getText().toString()+".jpg";
        File foto = new File(getActivity().getExternalFilesDir(null), nombreDeudor.getText().toString()+".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
        startActivity(intent);

        String dir = getActivity().getExternalFilesDir(null) + "/" + nombreDeudor.getText().toString() + ".jpg";
        Prestamo prestamo = new Prestamo(0,nombreDeudor.getText().toString(),montoDeuda.getText().toString(),dir);
        controller.guardar(prestamo);
        startActivity(intent);
        recuperarFotos();

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
        String dir = getActivity().getExternalFilesDir(null) + "/" + nombreDeudor.getText().toString() + ".jpg";
        Bitmap bitmap1 = BitmapFactory.decodeFile(dir);
        imageViewDeudor.setImageBitmap(bitmap1);
        Toast.makeText(getActivity(), dir + "", Toast.LENGTH_LONG).show();
    }
    public void cargarFotos() {
        ArrayList<Prestamo> prestamos = controller.buscarDeudores();
        ArrayList<String>direcciones=new ArrayList<>();
        if(prestamos!=null){
            for (Prestamo d:prestamos) {
                direcciones.add(d.getRutaFoto());
            }
            int r=(int)Math.random()*prestamos.size()+0;
            //String dir = getActivity().getExternalFilesDir(null) + "/" + nombreQuienDebo.getText().toString() + ".jpg";
            Bitmap bitmap1 = BitmapFactory.decodeFile(prestamos.get(r).getRutaFoto());
            imageViewDeudor.setImageBitmap(bitmap1);
        }
    }
}
