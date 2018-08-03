package com.arthurfmg.mycomics.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dledford.comichoarder.R;
import com.arthurfmg.mycomics.common.ExceptionHandler;
import com.arthurfmg.mycomics.common.RecyclerItemClickListener;
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

public class VolumeListActivity extends Activity{
    ArrayList<VolumeModel> volume = new ArrayList<>();
    public final static String VOLUME_ID = "com.dledford.mycomics.VOLUME_ID";
    VolumeModel bestMatch = null;
    private RecyclerView recyclerVolume;
    //private List<VolumeModel> volumes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_volume_list);

        recyclerVolume = findViewById(R.id.idRecycler);

        //define layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerVolume.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        final String volumeSearchText = intent.getStringExtra(SEARCH_MESSAGE);
        final String textoDefinitivo = volumeSearchText.replaceAll(" ", "-");
        new ComicVineService().findVolumeByName(volumeSearchText).enqueue(new Callback<ComicVineResult<ArrayList<VolumeModel>>>() {
            @Override
            public void onResponse(Call<ComicVineResult<ArrayList<VolumeModel>>> call,
                                   Response<ComicVineResult<ArrayList<VolumeModel>>> response) {
                Log.d("IComicVineService", "Successfully response fetched");
                volume = response.body().getResults();
                volume = new VolumeService().sortBestMatch(textoDefinitivo, volume);
                //ComicVineModelAdapter adapter = new ComicVineModelAdapter(VolumeListActivity.this, volume);
                VolumeAdapter adapter = new VolumeAdapter(VolumeListActivity.this, volume);
                //ListView listView = (ListView) findViewById(R.id.volume_list);
                //listView.setAdapter(adapter);
                recyclerVolume.setAdapter(adapter);

                recyclerVolume.addOnItemTouchListener(
                        new RecyclerItemClickListener(getApplicationContext(), recyclerVolume, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(VolumeListActivity.this, IssuesListActivity.class);
                                intent.putExtra(VOLUME_ID, volume.get(position).getId().toString());
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        })
                );
            }

            @Override
            public void onFailure(Call<ComicVineResult<ArrayList<VolumeModel>>> call, Throwable t) {
                Log.d("IComicVineService", "Error Occured: " + t.getMessage());
            }
        });
    }
}

