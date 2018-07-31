package com.dledford.comichoarder.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dledford.comichoarder.R;
import com.dledford.comichoarder.rest.model.ComicVineResult;
import com.dledford.comichoarder.rest.model.VolumeModel;
import com.dledford.comichoarder.rest.services.ComicVineService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dledford.comichoarder.ui.activity.VolumeListActivity.VOLUME_ID;

/**
 * Created by Arthur on 29/05/2017.
 * Activity que manda para a tela de Edições
 */

public class VolumeActivity extends Activity {
    VolumeModel volume;
    ComicVineService comicVineService = new ComicVineService();
    private Float countIssue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);
        Intent intent = getIntent();
        final Long id = intent.getLongExtra(VOLUME_ID,0);

        comicVineService.findVolumeById(id).enqueue(new Callback<ComicVineResult<VolumeModel>>() {
            @Override
            public void onResponse(Call<ComicVineResult<VolumeModel>> call,
                                   Response<ComicVineResult<VolumeModel>> response) {
                Log.d("IComicVineService", "Successfully response fetched");
                volume = response.body().getResults();
                TextView name = (TextView) findViewById(R.id.name);
                ImageView image = (ImageView) findViewById(R.id.character_image);

                name.setText(volume.getName());

                Picasso.with(VolumeActivity.this)
                    .load(volume.getImage().getThumb_url())
                    //.placeholder(R.drawable.default_hero)
                    .error(R.drawable.default_hero)
                    .into(image);
            }

            @Override
            public void onFailure(Call<ComicVineResult<VolumeModel>> call, Throwable t) {
                Log.d("IComicVineService", "Error Occured: " + t.getMessage());
            }
        });

    }
}
