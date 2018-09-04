package com.arthurfmg.mycomics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.ui.activity.LoginActivity;
import com.arthurfmg.mycomics.ui.activity.VolumeListActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public final static String SEARCH_MESSAGE = "com.dledford.mycomics.SEARCH_MESSAGE";
    private FirebaseAuth autenticacao;
    private Toolbar toolbar;
    private RecyclerView recyclerMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MyComics");
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_main, menu);

        SearchView mSearchView = (SearchView) menu.findItem(R.id.item_pesquisar).getActionView();

        //Define um texto de ajuda:
        mSearchView.setQueryHint("Pesquisar");
        mSearchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.item_sair:
                deslogarUsuario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deslogarUsuario(){
        autenticacao.signOut();
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this, VolumeListActivity.class);
        intent.putExtra(SEARCH_MESSAGE, query);
        startActivity(intent);
        finish();

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
