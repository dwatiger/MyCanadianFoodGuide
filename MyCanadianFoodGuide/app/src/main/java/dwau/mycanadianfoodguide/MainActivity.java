package dwau.mycanadianfoodguide;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private static String url = "http://open.canada.ca/data/api/action/package_show?id=e5f4a98e-0ccf-4e5e-9912-d308b46c5a7f";
    private static final String TAG_METADATA = "metadata_created";
    JsonParser jsonparser = new JsonParser();
    String metadata;
    JSONObject jobj = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check metadata for update and make a short toast about if there is a data update or not
        new retrievedata().execute();

        Button sButton = (Button)findViewById(R.id.startButton);
        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toIntro(v);
            }
        });
    }

    class retrievedata extends AsyncTask<String,String,String>{ // thread task for json metadata checker
        // http://techlovejump.com/android-json-parser-from-url/
        @Override
        protected String doInBackground(String... arg0) {
            jobj = jsonparser.makeHttpRequest(url);
            try {
                metadata = jobj.getString(TAG_METADATA);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return metadata;
        }
        protected void onPostExecute(String ab){ // checks if data is equal to current version
            Log.d("postexecute", ""+metadata);
            if (metadata != "2015-06-11T13:19:02.389655"){ // there isn't a version for the json metadata
                Toast.makeText(getApplicationContext(), "There is a new update for the data", Toast.LENGTH_LONG).show();
            }
        } // i used metadata_created as the check

    }

    public void toIntro(View v){ // sends intent to intro activity
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
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
