package com.arthurfmg.mycomics.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arthurfmg.mycomics.R;
import com.arthurfmg.mycomics.common.Base64Custom;
import com.arthurfmg.mycomics.common.ConfigFirebase;
import com.arthurfmg.mycomics.rest.model.ComicVineIssueModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IssueAdapter extends RecyclerView.Adapter<IssueAdapter.MyViewHolder> {

    private List<ComicVineIssueModel> issue;
    private Context context;
    private boolean isExpand = false;
    private DatabaseReference firebase;
    private ChildEventListener childListenerEdicao;
    private ComicVineIssueModel issueModel = new ComicVineIssueModel();
    private ArrayList<ComicVineIssueModel> listIssue = new ArrayList();
    private String volume;
    String textoFormatado;
    Map<String, String> map;

    public IssueAdapter(List<ComicVineIssueModel> issue, Context context, String volume) {
        this.issue = issue;
        this.context = context;
        this.volume = volume;

        setHasStableIds(true);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_issue, parent, false);

        firebase = ConfigFirebase.getFirebase();

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        issueModel = issue.get(position);

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

        if(issueModel.getDescription() != null){
            //Formata o texto das informações para não haver tag HTML
            textoFormatado = issueModel.getDescription().replaceAll("<.*?>", "");

            //Verifica se tem a tabela com lista de capas na descrição e separa ela do texto principal
            if (textoFormatado.contains("List of covers")) {
                String[] retirarLista = textoFormatado.split("List of covers");
                textoFormatado = retirarLista[0];
            }
        } else {
            textoFormatado = "No Description.";
        }

        holder.informacoes.setText(textoFormatado);

        //Transforma a seta em um botão fazendo aparecer/desaparecer as informações
        holder.seta.setOnClickListener(new OnClickListener() {
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

        String idEdicao = issueModel.getId().toString();

        holder.check.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.check.getTag().equals("isChecked")){
                    holder.check.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.uncheck));
                    firebase.child(usuario()).child(volume).child(issue.get(position).getId().toString()).removeValue();
                    holder.check.setTag("uncheck");
                    Toast.makeText(context, issue.get(position).getName() + " deletado com sucesso!", Toast.LENGTH_LONG).show();
                }else {
                    holder.check.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check));
                    holder.check.setTag("isChecked");

                    issueModel.setIssue_number(issue.get(position).getIssue_number());
                    issueModel.setName(issue.get(position).getName());
                    issueModel.setId(issue.get(position).getId());

                    //salva a revista no Firebase no formato de: usuario - id da revista - id da edição - dados da revista
                    DatabaseReference firebaseSalvaRevista = FirebaseDatabase.getInstance().getReference(usuario());
                    firebaseSalvaRevista.child(volume).child(issue.get(position).getId().toString()).setValue(issueModel);

                    Toast.makeText(context, issue.get(position).getName() + " está nos favoritos!", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebase.child(usuario()).child(volume).child(idEdicao);
        recuperarDados(idEdicao, holder.check);
        firebase.addChildEventListener(childListenerEdicao);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
            check = itemView.findViewById(R.id.idUncheck);
        }
    }

    public String usuario(){
        FirebaseAuth autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        String usuario = autenticacao.getCurrentUser().getEmail().toString();

        usuario = Base64Custom.codificarBase64(usuario);

        return usuario;
    }

    public void recuperarDados(final String id, final ImageView check){

        childListenerEdicao = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //listIssue.clear();
                if(map != null) {
                    map.clear();
                }

                for(DataSnapshot dados : dataSnapshot.getChildren()) {
                    map = (Map) dados.getValue();
                    if(map != null) {
                        if (map.containsKey(id)) {
                            check.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.check));
                            check.setTag("isChecked");
                        }
                    }
                }
                //IssueAdapter.this.notifyDataSetChanged();
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
    }
}
