package u.Schedule.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import u.Schedule.R;
import u.Schedule.entity.globals;

import u.Schedule.entity.favouriteBunches;

import java.util.ArrayList;

public class FavoriteFragment extends ListFragment {

    private static final int Layout = R.layout.fragment_favorite;

    private int titleFavorite = R.string.titleFavorite;

    public int getTitleFavorite() {
        return titleFavorite;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(Layout,container,false);
        globals.fragmentId = 12;

        ArrayList<String> bunches = new ArrayList<String>();

        favouriteBunches.loadFavouriteBunchesList(getActivity());
        for (String item:
                globals.favouriteBunchesList) {
            bunches.add(item);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, bunches);
        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        globals.g_Bunch = getListView().getItemAtPosition(position).toString();
        Fragment fragment = new LessonFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_conteyner, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
