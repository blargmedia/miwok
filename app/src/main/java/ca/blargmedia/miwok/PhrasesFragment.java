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
 * PhrasesFragment
 */
public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
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
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        // use a listview for the number words
        WordAdapter itemsAdapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

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
