package com.example.login.Acoes.Barbeiro;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login.Login.Cadastro.Cadastro;
import com.example.login.Modelo.ModeloCadastro;
import com.example.login.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPerfilBarbeiro extends Fragment  {
    private CardView aliasEdit;
    private ImageView aliasFoto1;
    private FirebaseAuth auth;
    private ModeloCadastro modeloCadastro = new ModeloCadastro();
    private ValueEventListener valueEventListener;
    private TextView aliasNome, aliasDescricao;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    private  static  final int PICK_IMAGE_REQUEST=123;
    private byte[] imagem = null;



    public FragmentPerfilBarbeiro() {
        //verificaparametro();
    }

    /*private void verificaparametro() {
        Intent intent = getIntent();
        if (intent.getSerializableExtra("Objeto")==null){

            modeloCadastro=new ModeloCadastro();
        }
        else {
            //  Toast.makeText(this, "Cheio", Toast.LENGTH_SHORT).show();
            modeloCadastro = (ModeloCadastro) intent.getSerializableExtra("Objeto"); // nome do parametro recebido é Objeto...

            if (modeloCadastro.getFoto1() != null) {
                //converte byte[] para Bitmap
                Bitmap bitmap = BitmapFactory.decodeByteArray(modeloCadastro.getFoto1(), 0, modeloCadastro.getFoto1().length);
                //carrega a imagem na ImageView do item da ListView
                aliasFoto1.setImageBitmap(bitmap);
                // para não salvar sem imagem caso o usuário não clique em alterar a imagem (BUG Lógico)
                byte[] img = getBitmapAsByteArray(bitmap); //converte para um fluxo de bytes
                imagem = img; //coloca a imagem no objeto imagem (um array de bytes (byte[]))
            }
        }
    }*/
//@Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RESULT_OK)
//        {
//            Uri arquivoUri = data.getData();
//            Bitmap bitmap = null;
//            try {
//                bitmap = BitmapFactory.decodeStream(getActivity().getApplicationContext().getContentResolver().openInputStream(arquivoUri));
//           aliasFoto1.setImageURI(arquivoUri);
//                aliasFoto1.setImageBitmap(bitmap);
//            }
//            catch (FileNotFoundException e)
//            {
//                e.printStackTrace();
//            }
//            byte[] img = getBitmapAsByteArray(bitmap); //converte para um fluxo de bytes
//            imagem = img;
//        }
//    }

    public static byte [] getBitmapAsbyteArray(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,0,outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_perfil_barbeiro, container, false);

        aliasEdit = (CardView) view.findViewById(R.id.cardView);
        aliasNome = (TextView) view.findViewById(R.id.alianome);
        aliasDescricao = (TextView) view.findViewById(R.id.aliasdescricao);
        aliasFoto1 = (ImageView) view.findViewById(R.id.aliasfoto1);

        aliasFoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        auth = FirebaseAuth.getInstance();

        modeloCadastro = new ModeloCadastro();

        View profileView = inflater.inflate(R.layout.fragment_fragment_perfil_barbeiro, null);

        modeloCadastro.getFoto1();


        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("users").child(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ModeloCadastro modeloCadastro = dataSnapshot.getValue(ModeloCadastro.class);
                String nome = modeloCadastro.getNome();
                aliasNome.setText(nome);
                String descricao = modeloCadastro.getDescricao();
                aliasDescricao.setText(descricao);
                Log.d("BARBEIRO", nome + " / " + descricao);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        uidRef.addListenerForSingleValueEvent(valueEventListener);



        aliasEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                FragmentBarbeiroEdit fragmentEdit = new FragmentBarbeiroEdit();
                ft.replace(R.id.conteiner, fragmentEdit);
                ft.commit();

            }
        });



        return view;
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        //criam um stream para ByteArray
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream); //comprime a imagem
        return outputStream.toByteArray(); //retorna a imagem como um Array de Bytes (byte[])
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }*/

}
