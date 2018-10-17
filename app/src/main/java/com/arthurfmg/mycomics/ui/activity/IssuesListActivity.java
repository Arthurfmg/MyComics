package com.arthurfmg.mycomics.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.EndlessRecyclerViewScrollListener;
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
    public final static String EDICAO_ID = "com.arthurfmg.mycomics.EDICAO_ID";
    private RecyclerView recyclerEdicao;
    LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String offset = "0";
    private int atual = 0;
    private IssueAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private ComicVineService comicVineService = new ComicVineService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        setContentView(R.layout.activity_issues_list);

        recyclerEdicao = findViewById(R.id.idRecyclerIssue);
        toolbar = findViewById(R.id.toolbar_issues);

        //define layout
        layoutManager = new LinearLayoutManager(this);
        recyclerEdicao.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        final String volumeID = intent.getStringExtra(VolumeListActivity.VOLUME_ID);
        String volumeName = intent.getStringExtra(VolumeListActivity.VOLUME_NAME);

        toolbar.setTitle(volumeName.toUpperCase());
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        setSupportActionBar(toolbar);

        findIssue(volumeID, offset);

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if(atual >= 100) {
                    findIssue(volumeID, offset);
                }
            }
        };

        recyclerEdicao.addOnScrollListener(scrollListener);
    }

    public void findIssue(final String volumeID, final String offset){
        comicVineService.findIssueByVolume(volumeID, offset).enqueue(new Callback<ComicVineResult<ArrayList<ComicVineIssueModel>>>() {
            @Override
            public void onResponse(final Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> call,
                                   final Response<ComicVineResult<ArrayList<ComicVineIssueModel>>> response) {
                Log.d("IComicVineService", "Successfully response fetched");
                edicao.addAll(response.body().getResults());

                if(adapter == null){
                    adapter = new IssueAdapter(edicao, IssuesListActivity.this, volumeID);
                    recyclerEdicao.setAdapter(adapter);
                }else {
                    adapter.notifyDataSetChanged();
                }

                Long nResultados = response.body().getNumber_of_page_results();

                if(nResultados == 100){
                    atual += nResultados;
                    IssuesListActivity.this.offset = String.valueOf(atual);
                }

                findViewById(R.id.idLoadingIssue).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ComicVineResult<ArrayList<ComicVineIssueModel>>> call, Throwable t) {
                Log.d("IComicVineService", "Error Occured: " + t.getMessage());
            }
        });
    }
}
