package com.arthurfmg.mycomics.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.ExceptionHandler;
import com.arthurfmg.mycomics.rest.model.ComicVineIssueModel;
import com.arthurfmg.mycomics.rest.model.ComicVineResult;
import com.arthurfmg.mycomics.rest.services.ComicVineService;
import com.arthurfmg.mycomics.ui.adapter.IssueAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssuesListActivity extends AppCompatActivity {

    ArrayList<ComicVineIssueModel> edicao = new ArrayList<>();
    public final static String EDICAO_ID = "com.dledford.mycomics.EDICAO_ID";
    ComicVineIssueModel bestMatch = null;
    private RecyclerView recyclerEdicao;
    LinearLayoutManager layoutManager;
    private ComicVineResult vineResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_issues_list);

        recyclerEdicao = findViewById(R.id.idRecyclerIssue);

        //define layout
        layoutManager = new LinearLayoutManager(this);
        recyclerEdicao.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        final String volumeID = intent.getStringExtra(VolumeListActivity.VOLUME_ID);

        callIssues(volumeID);

    }

    public void callIssues(final String volumeID){
        new ComicVineService().findIssueByVolume(volumeID).enqueue(new Callback<ComicVineResult<ArrayList<ComicVineIssueModel>>>() {
            @Override
            public void onResponse(final Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> call,
                                   final Response<ComicVineResult<ArrayList<ComicVineIssueModel>>> response) {
                Log.d("IComicVineService", "Successfully response fetched");
                edicao = response.body().getResults();
                //edicao = new VolumeService().sortBestMatch(textoDefinitivo, volume);
                final IssueAdapter adapter = new IssueAdapter(edicao, IssuesListActivity.this, volumeID);
                recyclerEdicao.setAdapter(adapter);

                recyclerEdicao.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if(edicao.size() == layoutManager.findLastCompletelyVisibleItemPosition() + 1){
                            Log.d("Tamanho:", "tamanho: " + edicao.size());
                            //vineResult.setOffset((long) 100);
                            response.body().setOffset((long) 101);
                            response.body().getResults();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> call, Throwable t) {
                Log.d("IComicVineService", "Error Occured: " + t.getMessage());
            }
        });
    }
}
