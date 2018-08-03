package com.arthurfmg.mycomics.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.arthurfmg.mycomics.rest.model.ComicVineModel;

import java.util.List;

/**
 * Created by phesto on 11/18/2016.
 */

public class CharactersFragment extends Fragment {

    private List<ComicVineModel> characters;
    private ListView listView;



    public CharactersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CharactersFragment newInstance() {
        CharactersFragment fragment = new CharactersFragment();
        Bundle args = new Bundle();
        //If your fragment needs params add them in here
        //args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //If your fragment needs params add them in here
            //mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_characters, container, false);
//
//        listView = (ListView) view.findViewById(R.id.listView);
//
//        getCharacterList("spider-man");
//
//        return view;
//    }
//
//
//    private void getCharacterList(String name) {
//        characters = new IComicVineService().findCharacterByName(name);
//    }
//
//    //Our method to show list
//    private void showList() {
//        Log.d("APIPlug", "Show List");
//
//        ActorAdapter adapter = new ActorAdapter(getActivity(), actors);
//        listView.setAdapter(adapter);
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Actor clickedObj = (Actor) parent.getItemAtPosition(position);
//
//                //If you have detail activity about your object define in here
//                /*
//                Intent detail = new Intent(getContext(), ActorDetail.class);
//                detail.putExtra("actorObject", clickedObj);
//                startActivity(detail);
//                */
//            }});
//
//    }
}
