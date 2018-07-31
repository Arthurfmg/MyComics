package com.dledford.comichoarder.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dledford.comichoarder.R;
import com.dledford.comichoarder.rest.model.ComicVineCharacterModel;
import com.dledford.comichoarder.rest.model.ComicVineModel;
import com.dledford.comichoarder.rest.model.VolumeModel;
import com.dledford.comichoarder.services.RemoteImageTask;
import com.dledford.comichoarder.ui.activity.CharacterListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by phesto on 1/8/2017.
 */

public class ComicVineModelAdapter extends ArrayAdapter<VolumeModel> {

    private Context context;

    /*public ComicVineModelAdapter(Context context, ArrayList<ComicVineCharacterModel> characters) {
        super(context,0,characters);
        this.context = context;
    }*/

    public ComicVineModelAdapter(Context context, ArrayList<VolumeModel> volume) {
        super(context, 0, volume);
        this.context = context;
    }

    public static class ViewHolder {
        public TextView name;
        public TextView id;
        public ImageView image;
        VolumeModel volume;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        VolumeModel volume = getItem(position);
        final ViewHolder holder;
        try {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_volume, parent, false);
                holder = new ViewHolder();
                holder.volume = volume;
                holder.name = (TextView) convertView.findViewById(R.id.idNomeRevista);
                holder.id = (TextView) convertView.findViewById(R.id.character_id);
                holder.image = (ImageView) convertView.findViewById(R.id.idImagemVolume);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(volume.getName());
            holder.id.setText(volume.getId().toString());
            if (holder.image != null) {
                Picasso.with(convertView.getContext())
                        .load(volume.getImage().getThumb_url())
                        //.placeholder(R.drawable.default_hero)
                        .error(R.drawable.default_hero)
                        .into(holder.image);
            }
        } catch (Exception e) {

        }
        return convertView;
    }

}
