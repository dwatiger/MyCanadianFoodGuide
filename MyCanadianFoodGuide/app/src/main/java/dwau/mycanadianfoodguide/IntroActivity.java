package dwau.mycanadianfoodguide;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class IntroActivity extends ActionBarActivity {

    public static final String COLUMN_PROFILE = "profile";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_SEX = "sex";

    private static final String[] column = { COLUMN_PROFILE, COLUMN_SEX, COLUMN_AGE };
    ProfileDbHelper dbHelper = new ProfileDbHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> listAge = new ArrayList<String>();
        ArrayList<String> listSex = new ArrayList<String>();
        ArrayList<Integer> listId = new ArrayList<Integer>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("profiles", column, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex("profile")));
            listSex.add(cursor.getString(cursor.getColumnIndex("sex")));
            listAge.add(cursor.getString(cursor.getColumnIndex("age")));
        }
        cursor.close();
        CustomProfileAdapter adapter = new CustomProfileAdapter(list, listAge, listSex, this);
        ListView lv = (ListView)findViewById(R.id.profileList);
        lv.setAdapter(adapter);

        Button cButton = (Button)findViewById(R.id.continueBtn);
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toProfile(v);
            }
        });

    }

    public void toProfile(View v){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
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
