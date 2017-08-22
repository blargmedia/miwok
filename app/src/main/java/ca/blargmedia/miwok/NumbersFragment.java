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
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

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

    public NumbersFragment() {
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
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        // use a listview for the number words via the enclosing activity
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

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

                    // use the position to find the right audio file via the enclosing activity
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
