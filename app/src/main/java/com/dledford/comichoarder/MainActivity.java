package com.dledford.comichoarder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dledford.comichoarder.ui.activity.CharacterListActivity;
import com.dledford.comichoarder.ui.activity.VolumeListActivity;

public class MainActivity extends AppCompatActivity {

    public final static String SEARCH_MESSAGE = "com.dledford.comichoarder.SEARCH_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
