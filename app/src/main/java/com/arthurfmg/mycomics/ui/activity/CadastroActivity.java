package com.arthurfmg.mycomics.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.Base64Custom;
import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.common.Preferencias;
import com.arthurfmg.mycomics.rest.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    private UserModel usuario;

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

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new UserModel();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario(){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()){
                            Toast.makeText(CadastroActivity.this, "Sucesso ao cadastrar usuario", Toast.LENGTH_LONG).show();
                            FirebaseUser usuarioFirebase = task.getResult().getUser();

                            String identificadorUsuario = Base64Custom.codificarBase64(usuario.getEmail());

                            usuario.setIdUsuario(identificadorUsuario);
                            usuario.Salvar();

                            Preferencias preferencias = new Preferencias(CadastroActivity.this);
                            preferencias.salvarDados(identificadorUsuario, usuario.getNome());

                            AbrirLogin();
                        } else {
                            String erroExcecao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e numeros";
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                erroExcecao = "O email digitado é invalido";
                            } catch (FirebaseAuthUserCollisionException e){
                                erroExcecao = "Esse email já existe";
                            } catch (Exception e){
                                erroExcecao = "Erro ao efetuar cadastro!";
                                e.printStackTrace();
                            }

                            Toast.makeText(CadastroActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void AbrirLogin(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
