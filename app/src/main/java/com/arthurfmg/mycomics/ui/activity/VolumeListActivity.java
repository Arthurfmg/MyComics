package com.arthurfmg.mycomics.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.arthurfmg.mycomics.MainActivity;
import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.EndlessRecyclerViewScrollListener;
import com.arthurfmg.mycomics.common.ExceptionHandler;
import com.arthurfmg.mycomics.rest.model.ComicVineResult;
import com.arthurfmg.mycomics.rest.model.VolumeModel;
import com.arthurfmg.mycomics.rest.services.ComicVineService;
import com.arthurfmg.mycomics.services.VolumeService;
import com.arthurfmg.mycomics.ui.adapter.VolumeAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.arthurfmg.mycomics.MainActivity.SEARCH_MESSAGE;

/**
 * Created by Arthur on 11/07/2018.
 * Configura a listagem de Volume
 */

public class VolumeListActivity extends AppCompatActivity{
    ArrayList<VolumeModel> volume = new ArrayList<>();
    public final static String VOLUME_ID = "com.arthurfmg.mycomics.VOLUME_ID";
    public final static String VOLUME_NAME = "com.arthurfmg.mycomics.VOLUME_NAME";
    VolumeModel bestMatch = null;
    private RecyclerView recyclerVolume;
    LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String offset = "0";
    private int atual = 0;
    VolumeAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_volume_list);

        recyclerVolume = findViewById(R.id.idRecycler);
        toolbar = findViewById(R.id.toolbar_volume);

        //define layout
        layoutManager = new LinearLayoutManager(this);
        recyclerVolume.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        final String volumeSearchText = intent.getStringExtra(SEARCH_MESSAGE);

        toolbar.setTitle(volumeSearchText.toUpperCase());
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(toolbar);

        final String textoDefinitivo = volumeSearchText.replaceAll(" ", "-");

        findVolume(textoDefinitivo, offset);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                findVolume(textoDefinitivo, offset);
            }
        };

        recyclerVolume.addOnScrollListener(scrollListener);

    }

    public void findVolume(final String volumeName, String offset){
        new ComicVineService().findVolumeByName(volumeName, offset).enqueue(new Callback<ComicVineResult<ArrayList<VolumeModel>>>() {
            @Override
            public void onResponse(Call<ComicVineResult<ArrayList<VolumeModel>>> call,
                                   final Response<ComicVineResult<ArrayList<VolumeModel>>> response) {
                Log.d("IComicVineService", "Successfully response fetched");
                volume = response.body().getResults();
                volume = new VolumeService().sortBestMatch(volumeName, volume);

                if(adapter == null) {
                    adapter = new VolumeAdapter(VolumeListActivity.this, volume);
                    recyclerVolume.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }

                Long nResultados = response.body().getNumber_of_page_results();

                if(nResultados == 100){
                    atual += nResultados;
                    VolumeListActivity.this.offset = String.valueOf(atual);
                }

                findViewById(R.id.idLoadingVolume).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ComicVineResult<ArrayList<VolumeModel>>> call, Throwable t) {
                Log.d("IComicVineService", "Error Occured: " + t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}

