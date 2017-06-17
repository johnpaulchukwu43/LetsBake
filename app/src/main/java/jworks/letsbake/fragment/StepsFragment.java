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
import jworks.letsbake.adapter.StepAdapter;
import jworks.letsbake.model.Steps;

import static jworks.letsbake.fragment.FoodItemMainFragment.list_of_food;


public class StepsFragment extends Fragment {

    public RecyclerView mStepsRecyclerview;
    public StepAdapter mStepAdapter;
    public  ArrayList<Steps> mSteps = new ArrayList<>();


    public StepsFragment() {
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
        View view = inflater.inflate(R.layout.fragment_steps, container, false);
        mStepsRecyclerview = (RecyclerView)view.findViewById(R.id.rv_steps);
        int position = getActivity().getIntent().getExtras().getInt("item_id");
        mSteps= list_of_food.get(position).getSteps();
        mStepAdapter = new StepAdapter(mSteps);
        RecyclerView.ItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
        mStepsRecyclerview.addItemDecoration(dividerItemDecoration);
        mStepsRecyclerview.setAdapter(mStepAdapter);
        return view;
    }

}
