package dwau.mycanadianfoodguide;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MilkAlternatives extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_alternatives);
        Bundle extras = getIntent().getExtras(); // gets intent extras from recommendations, and displays json in a listview
        if (extras != null){
            String mkadstmt = extras.getString("MKADStmt");
            ArrayList mkafoods = extras.getStringArrayList("MKAFoods");
            TextView tv = (TextView)findViewById(R.id.drt_stmt);
            ListView lv = (ListView)findViewById(R.id.foods);
            ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mkafoods);
            tv.setText(mkadstmt);
            lv.setAdapter(aa);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_milk_alternatives, menu);
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
