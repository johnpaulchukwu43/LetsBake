
package jworks.letsbake;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import jworks.letsbake.fragment.FoodItemMainFragment;


public class FoodItemMainActivity extends AppCompatActivity {

    private static final String FRAG_TAG ="FRAG_TAG" ;
    FoodItemMainFragment mFragment;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooditem_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        toolbar.setTitle(getTitle());
        if (savedInstanceState == null) {
            // The Activity is NOT being re-created so we can instantiate a new Fragment
            // and add it to the Activity
            mFragment = new FoodItemMainFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    // It's almost always a good idea to use .replace instead of .add so that
                    // you never accidentally layer multiple Fragments on top of each other
                    // unless of course thats intended
                    .replace(R.id.frameLayout, mFragment, FRAG_TAG)
                    .commit();

        } else {
            // The Activity IS being re-created so we don't need to instantiate the Fragment or add it,
            // but if we need a reference to it, we can use the tag we passed to .replace
            mFragment = (FoodItemMainFragment) getSupportFragmentManager().findFragmentByTag(FRAG_TAG);
        }
    }
    }


