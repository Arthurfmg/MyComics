package com.dledford.comichoarder.ui.activity;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;

        import com.dledford.comichoarder.R;
        import com.dledford.comichoarder.common.ExceptionHandler;
        import com.dledford.comichoarder.rest.model.ComicVineCharacterModel;
        import com.dledford.comichoarder.rest.model.ComicVineResult;
        import com.dledford.comichoarder.rest.services.ComicVineService;
        import com.dledford.comichoarder.services.CharacterService;
        import com.dledford.comichoarder.ui.adapter.ComicVineModelAdapter;

        import java.util.ArrayList;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

        import static com.dledford.comichoarder.MainActivity.SEARCH_MESSAGE;

public class CharacterListActivity extends Activity {
    ArrayList<ComicVineCharacterModel> characters = new ArrayList<>();
    public final static String CHARACTER_ID = "com.dledford.comichoarder.CHARACTER_ID";
    ComicVineCharacterModel bestMatch = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_character_list);
        /*
        Intent intent = getIntent();
        final String characterSearchText = intent.getStringExtra(SEARCH_MESSAGE);
        new ComicVineService().findCharacterByName(characterSearchText).enqueue(new Callback<ComicVineResult<ArrayList<ComicVineCharacterModel>>>() {
            @Override
            public void onResponse(Call<ComicVineResult<ArrayList<ComicVineCharacterModel>>> call, Response<ComicVineResult<ArrayList<ComicVineCharacterModel>>> response) {
                Log.d("IComicVineService", "Successfully response fetched");
                characters = response.body().getResults();
                characters = new CharacterService().sortBestMatch(characterSearchText, characters);
                ComicVineModelAdapter adapter = new ComicVineModelAdapter(CharacterListActivity.this, characters);
                ListView listView = (ListView) findViewById(R.id.character_list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(CharacterListActivity.this, CharacterActivity.class);
                        i.putExtra(CHARACTER_ID, characters.get(position).getId());
                        startActivity(i);

                    }
                });

            }

            @Override
            public void onFailure(Call<ComicVineResult<ArrayList<ComicVineCharacterModel>>> call, Throwable t) {
                Log.d("IComicVineService", "Error Occured: " + t.getMessage());
            }
        });
*/
    }
}
