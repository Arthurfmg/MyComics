package com.arthurfmg.mycomics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;

import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.ui.activity.LoginActivity;
import com.arthurfmg.mycomics.ui.activity.VolumeListActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public final static String SEARCH_MESSAGE = "com.dledford.mycomics.SEARCH_MESSAGE";
    private FirebaseAuth autenticacao;
    private Button btnSair;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MyComics");
        setSupportActionBar(toolbar);

        btnSair = findViewById(R.id.btnIdSair);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deslogarUsuario();
            }
        });

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    /** Called when the user clicks the Send button */
    public void comicVineApiSearch(View view) {
        Intent intent = new Intent(this, VolumeListActivity.class);
        EditText editText = (EditText) findViewById(R.id.character_search);
        String searchText = editText.getText().toString();
        intent.putExtra(SEARCH_MESSAGE, searchText);
        startActivity(intent);
        editText.setText("");
    }

    public void deslogarUsuario(){
        autenticacao.signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
