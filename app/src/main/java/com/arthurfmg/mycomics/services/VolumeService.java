package com.arthurfmg.mycomics.services;

import android.text.TextUtils;

import com.arthurfmg.mycomics.rest.model.VolumeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arthur on 5/29/2017.
 */

public class VolumeService {

    public ArrayList<VolumeModel> sortBestMatch(String searchText, List<VolumeModel>volumes){
        VolumeModel result = null;
        ArrayList<VolumeModel> exactMatches = new ArrayList<>();
        ArrayList<VolumeModel> nonExact = new ArrayList<>();
        int index = 0;
        for(VolumeModel volume : volumes){
            if(TextUtils.equals(searchText.toUpperCase(),volume.getName().toUpperCase())){
               exactMatches.add(volume);
            }else{
                nonExact.add(volume);
            }
        }
        /*Collections.sort(exactMatches, new Comparator<VolumeModel>(){public int compare(VolumeModel c1, VolumeModel c2){
            return c1.getNumber().compareTo(c2.getNumber());
        }});*/
        exactMatches.addAll(nonExact);
        return exactMatches;
    }
}
