
package jworks.letsbake.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

import jworks.letsbake.Interface.ListItemClickListener;
import jworks.letsbake.R;
import jworks.letsbake.adapter.DividerItemDecoration;
import jworks.letsbake.adapter.VideoAdapter;
import jworks.letsbake.model.Steps;

import static jworks.letsbake.fragment.FoodItemMainFragment.list_of_food;


public class VideoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mPlayer;

    // autoplay = false
    private boolean autoPlay = false;

    // used to remember the playback position
    private int currentWindow;
    private long playbackPosition;

    // constant fields for saving and restoring bundle
    public static final String AUTOPLAY = "autoplay";
    public static final String CURRENT_WINDOW_INDEX = "current_window_index";
    public static final String PLAYBACK_POSITION = "playback_position";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RecyclerView mVideoRecyclerview;
    public VideoAdapter mVideoAdapter;
    public  ArrayList<Steps> mSteps = new ArrayList<>();
    public String VIDEO_URL = "VIDEO_URL";
    public String STARTING_VIDEO_URL = "START_VIDEO";


    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // if we have saved player state, restore it
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong(PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(CURRENT_WINDOW_INDEX, 0);
            autoPlay = savedInstanceState.getBoolean(AUTOPLAY, false);
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.media_view);
        mVideoRecyclerview = (RecyclerView)view.findViewById(R.id.rv_video);
        final int position = getActivity().getIntent().getExtras().getInt("item_id");
        mSteps= list_of_food.get(position).getSteps();
        mVideoAdapter = new  VideoAdapter(new ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {
                int vposition = clickedItemIndex;
                VIDEO_URL = mSteps.get(vposition).getVideoURL();
                initializePlayer(VIDEO_URL);
            }
        },getActivity(), mSteps);
        RecyclerView.ItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL);
       mVideoRecyclerview.addItemDecoration(dividerItemDecoration);
        mVideoRecyclerview.setAdapter(mVideoAdapter);
        return view ;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void initializePlayer(String uri) {
        // create a new instance of SimpleExoPlayer
        mPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getActivity()),
                new DefaultTrackSelector(),
                new DefaultLoadControl());

        // attach the just created player to the view responsible for displaying the media (i.e. media controls, visual feedback)
        mPlayerView.setPlayer(mPlayer);
        mPlayer.setPlayWhenReady(autoPlay);

        // resume playback position
        mPlayer.seekTo(currentWindow, playbackPosition);

        Uri muri = Uri.parse(uri);
        MediaSource mediaSource = buildMediaSource(muri);

        // now we are ready to start playing our media files
        mPlayer.prepare(mediaSource);
    }

    /*
    * This method returns ExtractorMediaSource or one of its compositions
    * ExtractorMediaSource is suitable for playing regular files like (mp4, mp3, webm etc.)
    * This is appropriate for the baking app project, since all recipe videos are not in adaptive formats (i.e. HLS, Dash etc)
    */
    private MediaSource buildMediaSource(Uri uri) {
        DefaultExtractorsFactory extractorSourceFactory = new DefaultExtractorsFactory();
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("ua");

        ExtractorMediaSource audioSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorSourceFactory, null, null);

        // this return a single mediaSource object. i.e. no next, previous buttons to play next/prev media file
        return new ExtractorMediaSource(uri, dataSourceFactory, extractorSourceFactory, null, null);

        /*
         * Uncomment the line below to play multiple meidiaSources in sequence aka playlist (and totally without buffering!)
         * NOTE: you have to comment the return statement just above this comment
         */


//          ExtractorMediaSource videoSource1 = new ExtractorMediaSource(Uri.parse(VIDEO_1), dataSourceFactory, extractorSourceFactory, null, null);
//          ExtractorMediaSource videoSource2 = new ExtractorMediaSource(Uri.parse(VIDEO_2), dataSourceFactory, extractorSourceFactory, null, null);
//          // returns a mediaSource collection
//          return new ConcatenatingMediaSource(videoSource1, audioSource, videoSource2);


    }

    private void releasePlayer() {
        if (mPlayer != null) {
            // save the player state before releasing its resources
            playbackPosition = mPlayer.getCurrentPosition();
            currentWindow = mPlayer.getCurrentWindowIndex();
            autoPlay = mPlayer.getPlayWhenReady();
            mPlayer.release();
            mPlayer = null;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
         /*
        * A simple configuration change such as screen rotation will destroy this activity
        * so we'll save the player state here in a bundle (that we can later access in onCreate) before everything is lost
        * NOTE: we cannot save player state in onDestroy like we did in onPause and onStop
        * the reason being our activity will be recreated from scratch and we would have lost all members (e.g. variables, objects) of this activity
        */
        if (mPlayer == null) {
            outState.putLong(PLAYBACK_POSITION, playbackPosition);
            outState.putInt(CURRENT_WINDOW_INDEX, currentWindow);
            outState.putBoolean(AUTOPLAY, autoPlay);
        }
    }

    // This is just an implementation detail to have a pure full screen experience. Nothing fancy here
    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    /*
     * NOTE: we initialize the player either in onStart or onResume according to API level
     * API level 24 introduced support for multiple windows to run side-by-side. So it's safe to initialize our player in onStart
     * more on Multi-Window Support here https://developer.android.com/guide/topics/ui/multi-window.html
     * Before API level 24, we wait as long as onResume (to grab system resources) before initializing player
    */
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // start in pure full screen
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mPlayer == null)) {

        }
    }


    /**
     * Before API level 24 we release player resources early
     * because there is no guarantee of onStop being called before the system terminates our app
     * remember onPause means the activity is partly obscured by something else (e.g. incoming call, or alert dialog)
     * so we do not want to be playing media while our activity is not in the foreground.
     */
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    // API level 24+ we release the player resources when the activity is no longer visible (onStop)
    // NOTE: On API 24+, onPause is still visible!!! So we do not not want to release the player resources
    // this is made possible by the new Android Multi-Window Support https://developer.android.com/guide/topics/ui/multi-window.html
    // We stop playing media on API 24+ only when our activity is no longer visible aka onStop
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}

