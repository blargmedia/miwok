package ca.blargmedia.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * ColoursFragment
 */
public class ColoursFragment extends Fragment {

    private MediaPlayer mp;

    private AudioManager am;

    private AudioManager.OnAudioFocusChangeListener afcl = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT: // pause
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    mp.pause();
                    mp.seekTo(0); // restart playing from beginning of translation
                    break;
                case AudioManager.AUDIOFOCUS_LOSS: // stop
                    releaseMediaPlayer();
                    break;
                case AudioManager.AUDIOFOCUS_GAIN: // resume
                    mp.start();
                    break;
            }
        }
    };

    public ColoursFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // need to inflate the view from the layout and return it at the end
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // init audio manager to system audio service
        // for fragment, will need to get this via the activity first
        am = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        // use a listview for the number words
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_colors);

        // for fragment, need to findViewById from the view object instead of the activity method
        ListView listView = (ListView) rootView.findViewById(R.id.word_list);

        // bind the adapter to the view
        listView.setAdapter(itemsAdapter);

        // setup listener to play audio file
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // release player in case it's active
                releaseMediaPlayer();

                // request audio focus from system
                int amFocus = am.requestAudioFocus(afcl, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (amFocus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    // use the position to find the right audio file
                    mp = MediaPlayer.create(getActivity(), words.get(position).getAudioResourceId());
                    mp.start();

                    // release player when done
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });

        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    // helper to clean up media player by releasing its resources
    private void releaseMediaPlayer() {

        // check whether the media player might be currently playing a sound
        if (mp != null) {
            mp.release(); // release if in use
            mp = null; // using null to indicate media player is not currently playing
            am.abandonAudioFocus(afcl); // also release audio focus
        }
    }
}
