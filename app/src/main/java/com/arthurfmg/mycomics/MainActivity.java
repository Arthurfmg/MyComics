package com.arthurfmg.mycomics;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.arthurfmg.mycomics.common.Base64Custom;
import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.common.ExceptionHandler;
import com.arthurfmg.mycomics.rest.model.ComicVineResult;
import com.arthurfmg.mycomics.rest.model.VolumeModel;
import com.arthurfmg.mycomics.rest.services.ComicVineService;
import com.arthurfmg.mycomics.services.VolumeService;
import com.arthurfmg.mycomics.ui.activity.LoginActivity;
import com.arthurfmg.mycomics.ui.activity.VolumeListActivity;
import com.arthurfmg.mycomics.ui.adapter.VolumeAdapter;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public final static String SEARCH_MESSAGE = "com.dledford.mycomics.SEARCH_MESSAGE";
    private FirebaseAuth autenticacao;
    private Toolbar toolbar;
    private RecyclerView recyclerMain;
    private DatabaseReference firebase;
    private ArrayList<Long> listaVolume = new ArrayList<>();
    ArrayList<VolumeModel> volume = new ArrayList<>();
    private ValueEventListener eventListenerVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_main);

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        recyclerMain = findViewById(R.id.idRecyclerMain);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MyComics");
        setSupportActionBar(toolbar);

        //define layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMain.setLayoutManager(layoutManager);

        firebase = ConfigFirebase.getFirebase();
        firebase = firebase.child(usuario());
        RecuperaIdVolume();
        firebase.addValueEventListener(eventListenerVolume);

        /*for(int i = 0; i <= listaVolume.size(); i++){
            new ComicVineService().findVolumeById(listaVolume.get(i)).enqueue(new Callback<ComicVineResult<ArrayList<VolumeModel>>>() {
                @Override
                public void onResponse(Call<ComicVineResult<ArrayList<VolumeModel>>> call,
                                       final Response<ComicVineResult<ArrayList<VolumeModel>>> response) {
                    Log.d("IComicVineService", "Successfully response fetched");
                    volume = response.body().getResults();
                    VolumeAdapter adapter = new VolumeAdapter(MainActivity.this, volume);
                    recyclerMain.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<ComicVineResult<ArrayList<VolumeModel>>> call, Throwable t) {
                    Log.d("IComicVineService", "Error Occured: " + t.getMessage());
                }
            });
        }*/

    }

    public void RecuperaIdVolume(){
        eventListenerVolume = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaVolume.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()) {
                    Long idVolume = dados.child("id").getValue(Long.class);
                    listaVolume.add(idVolume);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
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

    public String usuario(){
        String usuario = autenticacao.getCurrentUser().getEmail().toString();

        usuario = Base64Custom.codificarBase64(usuario);

        return usuario;
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
