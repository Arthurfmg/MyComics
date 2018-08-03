package com.dledford.comichoarder.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.dledford.comichoarder.R;
import com.dledford.comichoarder.common.ExceptionHandler;
import com.dledford.comichoarder.common.RecyclerItemClickListener;
import com.dledford.comichoarder.rest.model.ComicVineIssueModel;
import com.dledford.comichoarder.rest.model.ComicVineResult;
import com.dledford.comichoarder.rest.services.ComicVineService;
import com.dledford.comichoarder.ui.adapter.IssueAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dledford.comichoarder.ui.activity.VolumeListActivity.VOLUME_ID;

public class IssuesListActivity extends AppCompatActivity {

    ArrayList<ComicVineIssueModel> edicao = new ArrayList<>();
    public final static String EDICAO_ID = "com.dledford.comichoarder.EDICAO_ID";
    ComicVineIssueModel bestMatch = null;
    private RecyclerView recyclerEdicao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_issues_list);

        recyclerEdicao = findViewById(R.id.idRecyclerIssue);

        //define layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerEdicao.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        final String volumeID = intent.getStringExtra(VOLUME_ID).toString();
        //final String textoDefinitivo = volumeID.replaceAll(" ", "-");
        new ComicVineService().findIssueByVolume(volumeID).enqueue(new Callback<ComicVineResult<ArrayList<ComicVineIssueModel>>>() {
            @Override
            public void onResponse(Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> call,
                                   Response<ComicVineResult<ArrayList<ComicVineIssueModel>>> response) {
                Log.d("IComicVineService", "Successfully response fetched");
                edicao = response.body().getResults();
                //edicao = new VolumeService().sortBestMatch(textoDefinitivo, volume);
                IssueAdapter adapter = new IssueAdapter(edicao, IssuesListActivity.this);
                recyclerEdicao.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> call, Throwable t) {
                Log.d("IComicVineService", "Error Occured: " + t.getMessage());
            }
        });
    }
}
