package com.arthurfmg.mycomics.services;

import android.text.TextUtils;

import com.arthurfmg.mycomics.rest.model.ComicVineCharacterModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by phesto on 1/12/2017.
 */

public class CharacterService {

    public ArrayList<ComicVineCharacterModel> sortBestMatch(String searchText, List<ComicVineCharacterModel>characters){
        ComicVineCharacterModel result = null;
        ArrayList<ComicVineCharacterModel> exactMatches = new ArrayList<>();
        ArrayList<ComicVineCharacterModel> nonExact = new ArrayList<>();
        int index = 0;
        for(ComicVineCharacterModel character : characters){
            if(TextUtils.equals(searchText.toUpperCase(),character.getName().toUpperCase())){
               exactMatches.add(character);
            }else{
                nonExact.add(character);
            }
        }
        Collections.sort(exactMatches,new Comparator<ComicVineCharacterModel>(){public int compare(ComicVineCharacterModel c1, ComicVineCharacterModel c2){
            return c1.getCount_of_issue_appearances().compareTo(c2.getCount_of_issue_appearances());
        }});
        exactMatches.addAll(nonExact);
        return exactMatches;
    }
}
