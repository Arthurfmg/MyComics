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
import com.arthurfmg.mycomics.rest.model.ComicVineModel;
import com.arthurfmg.mycomics.rest.model.UserModel;
import com.arthurfmg.mycomics.rest.model.VolumeModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.arthurfmg.mycomics.R.drawable.ic_star_border_black_24dp;

/*
    Adapter que faz listagem do volume pegando os itens do cardVolume e
    transformando nas informações que vem da API
 */

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.MyViewHolder>{

    private ArrayList<VolumeModel> volume = new ArrayList<>();
    private Context context;
    private FirebaseAuth autenticacao;
    private DatabaseReference firebase;
    private VolumeModel volumeModel = new VolumeModel();
    private ComicVineModel comicVineModel = new ComicVineModel();
    private ArrayList<ComicVineModel> listaVineModel = new ArrayList<ComicVineModel>();;
    private ValueEventListener eventListenerVolume;
    private ChildEventListener childListenerVolume;


    public VolumeAdapter(Context c, ArrayList<VolumeModel> volume) {
        this.volume = volume;
        this.context = c;

        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_volume, parent, false);

        firebase = ConfigFirebase.getFirebase();

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        volumeModel = volume.get(position);
        //Log.i("VolumeModel", "ID do volume: " + volumeModel.getId());

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
                if(holder.estrela.getTag().equals("isChecked")){
                    holder.estrela.setImageDrawable(ContextCompat.getDrawable(context, ic_star_border_black_24dp));
                    //Log.i("teste", "Entrei no if!!");
                    firebase.child(usuario()).child(volume.get(position).getId().toString()).removeValue();
                    holder.estrela.setTag("notChecked");
                    Toast.makeText(context, volume.get(position).getName() + " deletado com sucesso!", Toast.LENGTH_LONG).show();
                }else {
                    holder.estrela.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp));
                    //cadastraVolumeFirebase(volumeModel.getId(), volumeModel.getName(), volumeModel.getApi_detail_url());
                    holder.estrela.setTag("isChecked");

                    //pega a posição marcada e seta essas informações para irem pro banco
                    comicVineModel.setId(volume.get(position).getId());
                    comicVineModel.setName(volume.get(position).getName());
                    comicVineModel.setApi_detail_url(volume.get(position).getApi_detail_url());

                    //salva a revista no Firebase no formato de: colecao - id da revista - dados do volume
                    DatabaseReference firebaseSalvaRevista = FirebaseDatabase.getInstance().getReference(usuario());
                    firebaseSalvaRevista.child(comicVineModel.getId().toString()).setValue(comicVineModel);

                    Toast.makeText(context, comicVineModel.getName() + " está nos favoritos!", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebase.child(usuario()).child(volumeModel.getId().toString());
        recuperarDados(volumeModel.getId().toString(), holder.estrela);
        firebase.addChildEventListener(childListenerVolume);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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


    public String usuario(){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        String usuario = autenticacao.getCurrentUser().getEmail().toString();

        usuario = Base64Custom.codificarBase64(usuario);

        return usuario;
    }

    public void recuperarDados(final String id, final ImageView estrela){

        childListenerVolume = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                listaVineModel.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()) {
                    comicVineModel = dados.getValue(ComicVineModel.class);
                    listaVineModel.add(comicVineModel);
                    if(comicVineModel.getId().toString().equals(id)){
                        estrela.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_black_24dp));
                        estrela.setTag("isChecked");
                    } else {
                        //estrela.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_border_black_24dp));
                    }
                }
                VolumeAdapter.this.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(listaVineModel.size() < this.getItemCount()){
            listaVineModel.add(comicVineModel);
        }
    }
}
