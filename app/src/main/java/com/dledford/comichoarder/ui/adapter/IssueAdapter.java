package com.dledford.comichoarder.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dledford.comichoarder.R;
import com.dledford.comichoarder.rest.model.ComicVineIssueModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.MyViewHolder> {

    private List<ComicVineIssueModel> issue;
    private Context context;

    public IssueAdapter(List<ComicVineIssueModel> issue, Context context) {
        this.issue = issue;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_issue, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ComicVineIssueModel issueModel = issue.get(position);

        holder.titulo.setText(issueModel.getName());
        Picasso.with(context)
                .load(issue.get(position).getImage().getThumb_url())
                //.placeholder(R.drawable.default_hero)
                .error(R.drawable.default_hero)
                .into(holder.capa);
        holder.ano.setText(issueModel.getStore_date());
    }

    @Override
    public int getItemCount() {
        return issue.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView ano;
        private TextView titulo;
        private TextView edicao;
        private ImageView capa;
        private ImageView check;

        public MyViewHolder(View itemView) {
            super(itemView);
            ano = itemView.findViewById(R.id.idAnoEdicao);
            titulo = itemView.findViewById(R.id.idTitulo);
            capa = itemView.findViewById(R.id.idImagemEdicao);
        }
    }
}
