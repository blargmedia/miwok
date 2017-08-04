package ca.blargmedia.miwok;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * WordAdapter class
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int listColor;

    public WordAdapter (Activity context, ArrayList<Word> words, int colour)  {
        // initialize the ArrayAdapter's internal storage for the context and the list.
        // 2nd arg is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        listColor = colour;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the Word object located at this position in the list
        Word currentWord = getItem(position);

        ImageView iconImageView = (ImageView) listItemView.findViewById(R.id.icon_image_view);
        if (currentWord.hasImage()) {
            iconImageView.setImageResource(currentWord.getImageResourceId());
            iconImageView.setVisibility(View.VISIBLE);
        }
        else {
            iconImageView.setVisibility(View.GONE);
        }

        // Find the TextView in the list_item.xml layout with the miwok word
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the default word
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);
        defaultTextView.setText(currentWord.getDefaultTranslation());

        View textViews = listItemView.findViewById(R.id.word_views);
        int colour = ContextCompat.getColor(getContext(),listColor);
        textViews.setBackgroundColor(colour);

        // Return the whole list item layout (with 2 TextViews) so that it can be shown in the ListView
        return listItemView;
    }
}
