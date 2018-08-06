package com.arthurfmg.mycomics.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.rest.model.ComicVineIssueModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.MyViewHolder> {

    private List<ComicVineIssueModel> issue;
    private Context context;
    private boolean isExpand;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final ComicVineIssueModel issueModel = issue.get(position);

        //verifica se existe um título, se não houver ele informa que não há titulo
        if(issueModel.getName() != null){
            holder.titulo.setText(issueModel.getName());
        }else {
            holder.titulo.setText("No Title");
        }

        Picasso.with(context)
                .load(issue.get(position).getImage().getThumb_url())
                //.placeholder(R.drawable.default_hero)
                .error(R.drawable.default_hero)
                .into(holder.capa);

        //verifica se o ano de lançamento é nulo, se for ele mostra o ano que está na capa da revista
        if(issueModel.getStore_date() != null){
            holder.ano.setText(issueModel.getStore_date());
        }else{
            holder.ano.setText(issueModel.getCover_date());
        }

        holder.edicao.setText("Issue #" + issueModel.getIssue_number());

        //Formata o texto das informações para não haver tag HTML e seta as informações como invisivel
        String textoFormatado = issueModel.getDescription().replaceAll("<.*?>", "");
        holder.informacoes.setText(textoFormatado);
        holder.informacoes.setVisibility(View.GONE);

        //Transforma a seta em um botão fazendo aparecer/desaparecer as informções
        holder.seta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isExpand){
                    holder.seta.animate().rotation(0).start();
                    isExpand = false;
                    holder.informacoes.setVisibility(View.GONE);
                }else {
                    holder.seta.animate().rotation(180).start();
                    isExpand = true;
                    holder.informacoes.setVisibility(View.VISIBLE);
                }
            }
        });
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
        private TextView informacoes;
        private ImageView seta;

        public MyViewHolder(View itemView) {
            super(itemView);
            ano = itemView.findViewById(R.id.idAnoEdicao);
            titulo = itemView.findViewById(R.id.idTitulo);
            capa = itemView.findViewById(R.id.idImagemEdicao);
            edicao = itemView.findViewById(R.id.idEdicao);
            informacoes = itemView.findViewById(R.id.idInformacoes);
            seta = itemView.findViewById(R.id.idSetaInformacoes);
        }
    }
}
