package com.dledford.comichoarder.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dledford.comichoarder.R;
import com.dledford.comichoarder.common.ExceptionHandler;
import com.dledford.comichoarder.rest.model.ComicVineCharacterModel;
import com.dledford.comichoarder.rest.model.ComicVineIssueModel;
import com.dledford.comichoarder.rest.model.ComicVineResult;
import com.dledford.comichoarder.rest.services.ComicVineService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dledford.comichoarder.ui.activity.CharacterListActivity.CHARACTER_ID;

/**
 * Created by phesto on 1/12/2017.
 */

public class CharacterActivity extends Activity {
    ComicVineCharacterModel character;
    ComicVineIssueModel firstAppearance;
    ComicVineService comicVineService = new ComicVineService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_character);
        Intent intent = getIntent();
        final Long id = intent.getLongExtra(CHARACTER_ID,0);

        comicVineService.findCharacterById(id).enqueue(new Callback<ComicVineResult<ComicVineCharacterModel>>() {
            @Override
            public void onResponse(Call<ComicVineResult<ComicVineCharacterModel>> call, Response<ComicVineResult<ComicVineCharacterModel>> response) {
                Log.d("IComicVineService", "Successfully response fetched");
                character = response.body().getResults();
                TextView name = (TextView) findViewById(R.id.name);
                ImageView image = (ImageView) findViewById(R.id.character_image);
                name.setText(character.getName());
                comicVineService.findIssueById(character.getFirst_appeared_in_issue().getId()).enqueue(new Callback<ComicVineResult<ComicVineIssueModel>>() {
                    @Override
                    public void onResponse(Call<ComicVineResult<ComicVineIssueModel>> call, Response<ComicVineResult<ComicVineIssueModel>> response) {
                        firstAppearance = response.body().getResults();
                        TextView appearance = (TextView) findViewById(R.id.first_appearance);
                        appearance.setText(firstAppearance.getVolume().getName() + " #" + firstAppearance.getIssue_number().toString());
                        appearance.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
//                        Intent i = new Intent(CharacterListActivity.this, CharacterActivity.class);
//                        i.putExtra(CHARACTER_ID, characters.get(position).getId());
//                        startActivity(i);

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ComicVineResult<ComicVineIssueModel>> call, Throwable t) {
                        Log.d("IComicVineService", "Error Occured: " + t.getMessage());
                    }
                });
                Picasso.with(CharacterActivity.this)
                        .load(character.getImage().getThumb_url())
                        //.placeholder(R.drawable.default_hero)
                        .error(R.drawable.default_hero)
                        .into(image);
            }

            @Override
            public void onFailure(Call<ComicVineResult<ComicVineCharacterModel>> call, Throwable t) {
                Log.d("IComicVineService", "Error Occured: " + t.getMessage());
            }
        });
    }
}
