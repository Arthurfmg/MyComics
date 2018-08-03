package com.arthurfmg.mycomics.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.rest.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth usuarioFirebase = FirebaseAuth.getInstance();

    private UserModel ususario = new UserModel();

    private TextView nome;
    private TextView email;
    private TextView senha;
    private Button botaoCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nome = findViewById(R.id.idTxtNome);
        email = findViewById(R.id.idTxtEmail);
        senha = findViewById(R.id.idTxtSenha);
        botaoCadastrar = findViewById(R.id.idBotaoCadastrar);

        //é quase isso, mas não é isso... veja exemplos do Whatsapp...
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuarioFirebase.createUserWithEmailAndPassword(email.getText().toString(), senha.getText().toString());
            }
        });
    }
}
