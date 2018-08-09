package com.arthurfmg.mycomics.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.Base64Custom;
import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.common.Preferencias;
import com.arthurfmg.mycomics.rest.model.ComicVineModel;
import com.arthurfmg.mycomics.rest.model.VolumeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/*
    Adapter que faz listagem do volume pegando os itens do cardVolume e
    transformando nas informações que vem da API
 */

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.MyViewHolder>{

    private List<VolumeModel> volume;
    private Context context;
    private FirebaseAuth autenticacao;
    private DatabaseReference firebase;
    private VolumeModel volumeModel;
    private String identificadorUsuario;
    private ComicVineModel comicVineModel;

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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        volumeModel = volume.get(position);

        holder.nome.setText(volumeModel.getName());
        Picasso.with(context)
                .load(volume.get(position).getImage().getThumb_url())
                //.placeholder(R.drawable.default_hero)
                .error(R.drawable.default_hero)
                .into(holder.imageView);
        holder.ano.setText(volumeModel.getStart_year());
        holder.edicoes.setText(volumeModel.getCount_of_issues());
        holder.editora.setText(volumeModel.getPublisher().getName());

        holder.estrela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.estrela.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp));
                cadastraVolumeFirebase();
            }
        });
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
        private ImageView estrela;

        public MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.idNomeRevista);
            imageView = itemView.findViewById(R.id.idImagemVolume);
            ano = itemView.findViewById(R.id.idAno);
            edicoes = itemView.findViewById(R.id.idEdicoes);
            editora = itemView.findViewById(R.id.idEditora);
            estrela = itemView.findViewById(R.id.idEstrela);
        }
    }

    public void cadastraVolumeFirebase(){

        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //recuperar identificador do usuario (base64)
                Preferencias preferencias = new Preferencias(context);
                String identificadorUsuarioLogado = preferencias.getIdentificador();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
