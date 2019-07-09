package com.example.login.Login.Cadastro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login.Acoes.Barbeiro.FragmentInicial;
import com.example.login.Acoes.Barbeiro.InicialBarbeiro;
import com.example.login.Conexao;
import com.example.login.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class Cadastro extends AppCompatActivity {

    private ImageView aliasFotoPerfil;
    private byte[] imagem = null;
    private EditText aliasNome, aliasSobrenome, aliasEmail, aliasSenha, aliasDescricao;
    private CardView aliasFinalizar;
    private ImageView aliasFace;
    private FirebaseAuth auth;

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        //criam um stream para ByteArray
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream); //comprime a imagem
        return outputStream.toByteArray(); //retorna a imagem como um Array de Bytes (byte[])
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        aliasFotoPerfil = (ImageView) findViewById(R.id.imageView);
        //aliasFotoPerfil.setOnClickListener(this);
        aliasNome = (EditText) findViewById(R.id.editText);
        aliasSobrenome = (EditText) findViewById(R.id.editText3);
        aliasEmail = (EditText) findViewById(R.id.editText4);
        aliasSenha = (EditText) findViewById(R.id.editText5);
        aliasDescricao = (EditText) findViewById(R.id.editText6);
        aliasFinalizar = (CardView) findViewById(R.id.cardView);
        aliasFace = findViewById(R.id.imageView2);



        aliasFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InicialBarbeiro.class);
                startActivity(intent);
            }
        });

        aliasFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://pt-br.facebook.com/"));
                startActivity(intent);
            }
        });



        aliasFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String fotoperfl = aliasFotoPerfil;
                String email = aliasEmail.getText().toString().trim();
                String senha = aliasSenha.getText().toString().trim();
                String nome = aliasNome.getText().toString().trim();
                String sobrenome = aliasSobrenome.getText().toString().trim();
                String descricao = aliasDescricao.getText().toString().trim();

                criarUsuario( email, senha, nome, sobrenome, descricao);
            }
        });
           }

    private void criarUsuario(String email, String senha, final String nome, final String sobrenome, final String descricao) {
        auth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            DatabaseReference user = database.getReference("users").child(auth.getUid());

                            user.child("nome").setValue(nome);
                            user.child("sobrenome").setValue(sobrenome);
                            user.child("descricao").setValue(descricao);

                            Toast.makeText(Cadastro.this, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Cadastro.this, InicialBarbeiro.class);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(Cadastro.this, "Você não" +
                                    " informou algum dado ou a quantidade de carecteres" +
                                    " é invalida (Letras e números)! "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth= Conexao.getFirebaseAuth();
    }



}
