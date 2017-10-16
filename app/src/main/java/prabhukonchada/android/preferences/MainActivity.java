package prabhukonchada.android.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    TextView preferenceValue;
    TextView listPreferenceValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceValue = (TextView) findViewById(R.id.preference_value);
        listPreferenceValue = (TextView) findViewById(R.id.preference_list_item_value);
        setUpSharedPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.settings)
        {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }

        return true;
    }

    private void setUpSharedPreferences()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean check_box_status = sharedPreferences.getBoolean("check_box_selection",true);
        Float textSize = Float.valueOf(sharedPreferences.getString("size","30"));
        preferenceValue.setTextSize(textSize);
        listPreferenceValue.setTextSize(textSize);
        preferenceValue.setText(String.valueOf(check_box_status));
        listPreferenceValue.setText(sharedPreferences.getString("color",getString(R.string.pref_entry_two)));
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("check_box_selection"))
        {
            preferenceValue.setText(String.valueOf(sharedPreferences.getBoolean(key,true)));
        }
        if(key.equals("color"))
        {
            listPreferenceValue.setText(sharedPreferences.getString(key,getString(R.string.pref_entry_two)));
        }
        if(key.equals("size"))
        {
            listPreferenceValue.setTextSize(Float.valueOf(sharedPreferences.getString(key,"30")));
            preferenceValue.setTextSize(Float.valueOf(sharedPreferences.getString(key,"30")));
        }

    }
}
