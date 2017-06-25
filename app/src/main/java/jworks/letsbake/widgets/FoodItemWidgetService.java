
package jworks.letsbake.widgets;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import jworks.letsbake.R;
import jworks.letsbake.Utilities.ImageResources;
import jworks.letsbake.model.FoodItem;
import jworks.letsbake.model.Ingredient;

/**
 * Created by CHUKWU JOHNPAUL on 15/06/17.
 */

public class FoodItemWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FoodItemWidgetFactory(this.getApplicationContext(), intent);
    }
}
class FoodItemWidgetFactory implements RemoteViewsService.RemoteViewsFactory{

    private ArrayList<FoodItem> mFoodItem;
    private Context mContext;
    public String[] images = ImageResources.foodPic;
    public FoodItemWidgetFactory(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if(mFoodItem == null) {

            final String FOOD_MOVIE = "https://go.udacity.com/android-baking-app-json";
            Uri builtUri = Uri.parse(FOOD_MOVIE);

            HttpURLConnection urlConnection = null;
            try {
                // get URL
                URL url = new URL(builtUri.toString());
                // open connection
                urlConnection = (HttpURLConnection) url.openConnection();

                // create an input stream reader
                InputStreamReader reader = new InputStreamReader(urlConnection.getInputStream());

                FoodItem[] recipeArray = new Gson().fromJson(reader, FoodItem[].class);
                mFoodItem = new ArrayList<>(Arrays.asList(recipeArray));

            } catch (MalformedURLException e) {
                Log.e("MalformedURLException", e.getMessage());
            } catch (IOException e) {
                Log.e("IOException", e.getMessage());
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mFoodItem.clear();
    }

    @Override
    public int getCount() {
        if (mFoodItem == null)
            return 0;

        return mFoodItem.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

        FoodItem foodItem = mFoodItem.get(position);
        rv.setTextViewText(R.id.widget_tv_iname, foodItem.getName());
        String ingredients = "";
        for (Ingredient ingredient : foodItem.getIngredients()) {
            ingredients += " - " + ingredient.getIngredient() + "\n";
        }
        rv.setTextViewText(R.id.widget_tv_ingredients,ingredients);
        Bundle extras = new Bundle();
        extras.putParcelable(mContext.getString(R.string.recipe),foodItem);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_layout_item,fillIntent);
        return rv;


    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
