package ru.xxi_empire.rkhunter.distancebetweentwocities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import ru.xxi_empire.rkhunter.distancebetweentwocities.helpers.*;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    InputMethodManager inputManager;

    AutoCompleteTextView autocompleteInputCity1;
    AutoCompleteTextView autocompleteInputCity2;
    Button calculateButton;

    private void checkInputGroup() {
        if (
                !autocompleteInputCity1.getText().toString().matches("")
                &&
                !autocompleteInputCity2.getText().toString().matches("")
        ) {
            this.calculateButton.setEnabled(true);
        } else {
            this.calculateButton.setEnabled(false);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(getBaseContext(), MapsActivity.class);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        calculateButton = (Button) findViewById(R.id.button_calculate);
        // calculateButton.setEnabled(false);


        autocompleteInputCity1 = (AutoCompleteTextView) findViewById(R.id.input_city_1);
        autocompleteInputCity1.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));

        autocompleteInputCity1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                intent.putExtra("HOME_CITY", description);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                checkInputGroup();
            }
        });

        autocompleteInputCity1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkInputGroup();
            }
        });

        autocompleteInputCity2 = (AutoCompleteTextView) findViewById(R.id.input_city_2);
        autocompleteInputCity2.setAdapter(new PlacesAutoCompleteAdapter(this, R.layout.autocomplete_list_item));

        autocompleteInputCity2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get data associated with the specified position
                // in the list (AdapterView)
                String description = (String) parent.getItemAtPosition(position);
                intent.putExtra("DESTINATION_CITY", description);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        autocompleteInputCity2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkInputGroup();
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }
}
