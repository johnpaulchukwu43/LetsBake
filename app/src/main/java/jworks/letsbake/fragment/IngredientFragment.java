package jworks.letsbake.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import jworks.letsbake.R;
import jworks.letsbake.adapter.DividerItemDecoration;
import jworks.letsbake.adapter.IngredientAdapter;
import jworks.letsbake.model.Ingredient;

import static jworks.letsbake.fragment.FoodItemMainFragment.list_of_food;

public class IngredientFragment extends Fragment {

    public RecyclerView mIngredientRecyclerview;
    public IngredientAdapter mIngredientAdapter;
    public  ArrayList<Ingredient> mlist_of_ingredients = new ArrayList<>();


    public IngredientFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredient, container, false);
        mIngredientRecyclerview = (RecyclerView)view.findViewById(R.id.rv_ingredients);
        int position = getActivity().getIntent().getExtras().getInt("item_id");
        mlist_of_ingredients = list_of_food.get(position).getIngredients();
        mIngredientAdapter = new IngredientAdapter(mlist_of_ingredients);
        RecyclerView.ItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mIngredientRecyclerview.addItemDecoration(dividerItemDecoration);
        mIngredientRecyclerview.setAdapter(mIngredientAdapter);
        return view;
    }



}
