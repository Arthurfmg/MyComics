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
import com.dledford.comichoarder.rest.model.VolumeModel;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
    Adapter que faz listagem do volume pegando os itens do cardVolume e
    transformando nas informações que vem da API
 */

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.MyViewHolder>{

    private List<VolumeModel> volume;
    private Context context;

    public VolumeAdapter(Context c, List<VolumeModel> volume) {
        this.volume = volume;
        this.context = c;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_volume, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VolumeModel volumeModel = volume.get(position);

        holder.nome.setText(volumeModel.getName());
        Picasso.with(context)
                .load(volume.get(position).getImage().getThumb_url())
                //.placeholder(R.drawable.default_hero)
                .error(R.drawable.default_hero)
                .into(holder.imageView);
        holder.ano.setText(volumeModel.getStart_year());
        holder.edicoes.setText(volumeModel.getCount_of_issues());
        holder.editora.setText(volumeModel.getPublisher().getName());
    }

    @Override
    public int getItemCount() {
        return volume.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nome;
        private ImageView imageView;
        private TextView ano;
        private TextView edicoes;
        private TextView editora;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.idNomeRevista);
            imageView = itemView.findViewById(R.id.idImagemVolume);
            ano = itemView.findViewById(R.id.idAno);
            edicoes = itemView.findViewById(R.id.idEdicoes);
            editora = itemView.findViewById(R.id.idEditora);
        }
    }
}
