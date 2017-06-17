
package jworks.letsbake.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import jworks.letsbake.FoodItemDetailActivity;
import jworks.letsbake.Interface.ListItemClickListener;
import jworks.letsbake.R;
import jworks.letsbake.Utilities.NetworkUtilities;
import jworks.letsbake.adapter.FoodItemsAdapter;
import jworks.letsbake.model.FoodItem;

public class FoodItemMainFragment extends Fragment{
    private static final String LISTS ="LISTS" ;
    RecyclerView mfoodRecyclerview;
    public AlertDialog.Builder mAlertDialog;
    public AlertDialog mAlert;
    public static ArrayList<FoodItem> list_of_food = new ArrayList<>();
    FoodItemsAdapter mfoodItemsAdapter;

    private static final String FOOD_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_food_item_main, container, false);
        mfoodRecyclerview = (RecyclerView)root.findViewById(R.id.fooditem_list);
        mAlertDialog = new AlertDialog.Builder(getActivity());
        mAlertDialog.setMessage("No Network Connection,put on wifi or mobile data Refresh");
        mAlertDialog.setIcon(android.R.drawable.ic_dialog_alert);
        mAlertDialog.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              successLoad();
            }
        });

        mAlert = mAlertDialog.create();
        if(savedInstanceState != null && savedInstanceState.containsKey(LISTS)){
            showViews(list_of_food);
        }
        else{
            list_of_food = new ArrayList<>();
            successLoad();

        }
        return root;

    }

    private void successLoad() {
        if(NetworkUtilities.CheckForMobileNetwork(getContext()) || NetworkUtilities.CheckForWifiNetwork(getContext())){//check if there is network connection
            LoadData();
        }
        else{
            mAlert.show();

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int pos = item.getItemId();
        switch (pos){
            case R.id.refresh:
                successLoad();
        }
        return super.onOptionsItemSelected(item);
    }

    public void LoadData() {
        final ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading Feeds..Please Wait");
        mProgressDialog.show();
        final ArrayList<FoodItem> temp = new ArrayList<>();
        StringRequest mStringRequest = new StringRequest(Request.Method.GET,FOOD_URL,

                //Listener to make callbacks when data is gotten successfully
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            mProgressDialog.dismiss();
                            JSONArray foodArray = new JSONArray(response);
                            for (int i = 0; i < foodArray.length(); i++) {
                                temp.add(new FoodItem(foodArray.getJSONObject(i)));
                            }
                            list_of_food = temp;
                            showViews(list_of_food);
                        }
                        catch(Exception ex){
                            ex.printStackTrace();
                        }

                    }
                },

                //Listener to make callbacks when there is an error
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Couldnt Load Content, Try refreshing",Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                });

        RequestQueue mRequestQueue =  Volley.newRequestQueue(getActivity());
        mRequestQueue.add(mStringRequest);
    }

    void showViews(ArrayList<FoodItem> foods) {

        RecyclerView.LayoutManager layoutManager;
        //if it is a tablet then show it in rows of 3
        if (getActivity().findViewById(R.id.tab_layout)!=null) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 1);
        }


        mfoodRecyclerview.setLayoutManager(layoutManager);
        mfoodItemsAdapter = new FoodItemsAdapter(new ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {
                Intent intent = new Intent(getActivity(), FoodItemDetailActivity.class);
                intent.putExtra("item_id",clickedItemIndex);
                startActivity(intent);
            }
        }, foods, getActivity());
        mfoodRecyclerview.setAdapter(mfoodItemsAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LISTS,list_of_food);
    }


}
