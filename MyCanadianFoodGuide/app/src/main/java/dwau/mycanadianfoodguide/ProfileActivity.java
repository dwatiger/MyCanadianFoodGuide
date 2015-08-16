package dwau.mycanadianfoodguide;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by dwau on 15-07-31.
 */
public class ProfileActivity extends ActionBarActivity {

    private Spinner ageSpn;
    private String[] ages;
    private Button submitProfile;
    private EditText edProfileName;
    private String sex;
    private String age;
    ProfileDbHelper db = new ProfileDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
// "Form" type page for profile creation
        // accesses profile db helper and uses the information taken to create a new profile
        edProfileName = (EditText)findViewById(R.id.editProfile);
        ages = getResources().getStringArray(R.array.ageGroups);
        ageSpn = (Spinner)findViewById(R.id.ageSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ages);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ageSpn.setAdapter(adapter);

        submitProfile = (Button)findViewById(R.id.profileSubmit);
        submitProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String profileName = edProfileName.getText().toString();
                age = ageSpn.getSelectedItem().toString();
                db.createProfile(new Profile(profileName, sex, age));
                toFoodOption(v);
            }
        });
    }

    public void toFoodOption(View v){ // on submit, sends information to recommendation activity
        Intent intent = new Intent(this, RecommendationsActivity.class);
        intent.putExtra("profileAge", age);
        intent.putExtra("profileSex", sex);
        startActivity(intent);
    }

    public void onRadioButtonClicked(View v){ // sets the gender variable
        boolean checked = ((RadioButton) v).isChecked();
        switch(v.getId()){
            case R.id.radioMale:
                if (checked) {
                    sex = "male";
                    break;
                }
            case R.id.radioFemale:
                if (checked) {
                    sex = "female";
                    break;
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
