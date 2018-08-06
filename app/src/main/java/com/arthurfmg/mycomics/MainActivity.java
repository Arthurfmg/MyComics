package com.arthurfmg.mycomics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.ui.activity.LoginActivity;
import com.arthurfmg.mycomics.ui.activity.VolumeListActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public final static String SEARCH_MESSAGE = "com.dledford.mycomics.SEARCH_MESSAGE";
    private FirebaseAuth autenticacao;
    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        btnSair = findViewById(R.id.btnIdSair);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deslogarUsuario();
            }
        });

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
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
