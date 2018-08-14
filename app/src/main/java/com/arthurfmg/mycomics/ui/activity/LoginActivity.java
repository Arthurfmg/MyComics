package com.arthurfmg.mycomics.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurfmg.mycomics.MainActivity;
import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.Base64Custom;
import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.common.Preferencias;
import com.arthurfmg.mycomics.rest.model.UserModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button login;
    private TextView txtCadastro;

    private DatabaseReference referenciaFirebase;
    private FirebaseAuth autenticacao;
    CallbackManager mCallbackManager;

    private UserModel usuario;

    private ValueEventListener valueEventListener;
    private String identificadorUsuarioLogado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = findViewById(R.id.idEmailLogin);
        senha = findViewById(R.id.idSenhaLogin);
        login = findViewById(R.id.idBotaoLogin);
        txtCadastro = findViewById(R.id.idTxtCadastro);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario = new UserModel();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });

        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaCadastro();
            }
        });

        FacebookLogin();
    }

    private void verificarUsuarioLogado(){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    private void validarLogin(){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());

                    referenciaFirebase = ConfigFirebase.getFirebase().child("Usuarios").child(identificadorUsuarioLogado);

                    valueEventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            UserModel usuarioRecuperado = dataSnapshot.getValue(UserModel.class);
                            Preferencias preferencias = new Preferencias(LoginActivity.this);
                            preferencias.salvarDados(identificadorUsuarioLogado, usuarioRecuperado.getNome());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    referenciaFirebase.addListenerForSingleValueEvent(valueEventListener);

                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer login", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Erro ao fazer login " + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void FacebookLogin(){
        mCallbackManager = CallbackManager.Factory.create();
        final LoginButton loginButton = (LoginButton) findViewById(R.id.idFacebook);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

                //cadastrar o login do Facebook no  banco de dados do Firebase
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("LoginActivity", response.toString());
                        try{
                            UserModel user = new UserModel();
                            user.setNome(object.getString("name"));
                            user.setEmail(object.getString("email"));

                            String identificadorUsuario = Base64Custom.codificarBase64(user.getEmail());

                            user.setIdUsuario(identificadorUsuario);
                            user.Salvar();

                            Preferencias preferencias = new Preferencias(LoginActivity.this);
                            preferencias.salvarDados(identificadorUsuario, user.getNome());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "name, email, id");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(final AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        autenticacao.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //entrou com sucesso
                    FirebaseUser user = autenticacao.getCurrentUser();

                    abrirTelaPrincipal();
                } else {
                    Toast.makeText(LoginActivity.this, "Falha ao entrar com Facebook." + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirTelaCadastro(){
        Intent intent = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(intent);
    }
}
