/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.blargmedia.miwok;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        // Handling click events for numbers category
        TextView numbers = (TextView) findViewById(R.id.numbers);
        numbers.setOnClickListener(new View.OnClickListener() {
            // create a listener inline
            @Override
            public void onClick(View v) {
                Intent numbersIntent = new Intent(MainActivity.this, NumbersActivity.class);
                startActivity(numbersIntent);
            }
        });

        // Handling click events for colours category
        TextView colours = (TextView) findViewById(R.id.colors);
        colours.setOnClickListener(new View.OnClickListener() {
            // create a listener inline
            @Override
            public void onClick(View v) {
                Intent coloursIntent = new Intent(MainActivity.this, ColoursActivity.class);
                startActivity(coloursIntent);
            }
        });

        // Handling click events for family category
        TextView family = (TextView) findViewById(R.id.family);
        family.setOnClickListener(new View.OnClickListener() {
            // create a listener inline
            @Override
            public void onClick(View v) {
                Intent familyIntent = new Intent(MainActivity.this, FamilyActivity.class);
                startActivity(familyIntent);
            }
        });

        // Handling click events for phrases category
        TextView phrases = (TextView) findViewById(R.id.phrases);
        phrases.setOnClickListener(new View.OnClickListener() {
            // create a listener inline
            @Override
            public void onClick(View v) {
                Intent phrasesIntent = new Intent(MainActivity.this, PhrasesActivity.class);
                startActivity(phrasesIntent);
            }
        });



    }
}
