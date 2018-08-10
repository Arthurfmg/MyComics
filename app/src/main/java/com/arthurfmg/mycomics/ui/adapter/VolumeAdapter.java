package com.arthurfmg.mycomics.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.Base64Custom;
import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.common.Preferencias;
import com.arthurfmg.mycomics.rest.model.ComicVineModel;
import com.arthurfmg.mycomics.rest.model.UserModel;
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
                Log.v("VolumeAdapter", "Id: " + volumeModel.getId() +
                        "\n Nome: " + volumeModel.getName() + "\n Api: " + volumeModel.getApi_detail_url());
                holder.estrela.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp));
                cadastraVolumeFirebase(volumeModel.getId(), volumeModel.getName(), volumeModel.getApi_detail_url());

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

    public void cadastraVolumeFirebase(final Long id, final String name, final String api_detail_url){

        comicVineModel = new ComicVineModel();
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        String usuario = autenticacao.getCurrentUser().getEmail().toString();

        usuario = Base64Custom.codificarBase64(usuario);

        comicVineModel.setId(id);
        comicVineModel.setName(name);
        comicVineModel.setApi_detail_url(api_detail_url);

        firebase = ConfigFirebase.getFirebase();
        firebase.child("colecao").child(usuario).setValue(comicVineModel);

        Toast.makeText(context, comicVineModel.getName() + " está nos favoritos!", Toast.LENGTH_LONG).show();
    }
}
