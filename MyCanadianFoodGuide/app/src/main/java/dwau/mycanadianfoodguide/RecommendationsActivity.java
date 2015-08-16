package dwau.mycanadianfoodguide;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class RecommendationsActivity extends ActionBarActivity {

    private Intent intent;
    private String json;
    private String jsonFood;
    private String data;
    ArrayList<String> listFood;
    ProfileDbHelper dbhelper = new ProfileDbHelper(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            String age = extras.getString("profileAge");
            String sex = extras.getString("profileSex");
            String info = "As a " + sex + " aged " + age + ", this is the amount of Food Guide servings you need each day.";
            TextView tv = (TextView)findViewById(R.id.textView2);
            tv.setText(info);
        }

        Button buttonDefFG = (Button)findViewById(R.id.definitionFG);
        buttonDefFG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecommendationsActivity.this, DefinitionFoodGroup.class);
                startActivity(intent);
            }
        });

        Button buttonVF = (Button)findViewById(R.id.buttonVF);
        Button buttonG = (Button)findViewById(R.id.buttonG);
        Button buttonMKA = (Button)findViewById(R.id.buttonMKA);
        Button buttonMTA = (Button)findViewById(R.id.buttonMTA);
        json = loadJSON("fg_directional_satements-en.json");
        jsonFood = loadJSON("foods-en.json");

        buttonVF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = getJSONDirectionalStatement(json, 3, 0, 10);
                listFood = getJSONFoods(jsonFood, "vf");
                toVeggieFruit(v);
            }
        });
        buttonG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = getJSONDirectionalStatement(json, 5, 3, 10);
                listFood = getJSONFoods(jsonFood, "gr");
                toGrainProduct(v);
            }
        });
        buttonMKA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = getJSONDirectionalStatement(json, 7, 5, 10);
                listFood = getJSONFoods(jsonFood, "da");
                toMilkAlt(v);
            }
        });
        buttonMTA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = getJSONDirectionalStatement(json, 10, 7, 8);
                listFood = getJSONFoods(jsonFood, "me");
                toMeatAlt(v);
            }
        });

    }

    public String loadJSON(String fileName){ // stackoverflow.com/questions/13814503/reading-a-json-file-in-android
        String json = null;
        try{
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e){ e.printStackTrace(); return null; }
        return json;
    }

    public String getJSONDirectionalStatement(String json, int n, int j, int k){ // www.tutorialspoint.com/android/android_json_parser.htm
        data = "";
        try {
            JSONObject jsonRootObject = new JSONObject(json);
            JSONArray jsonArray = jsonRootObject.optJSONArray("directional_statements");
            for (int i = j; i < n; i++){
                if (i == k) { continue; }
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String directStatement = jsonObject.optString("dir_stmt").toString();
                data += directStatement + "\n";
            }
            return data;
        }catch (JSONException e){ e.printStackTrace(); return null; }
    }

    public ArrayList getJSONFoods(String json, String type){
        try{
            JSONObject jsonRootObject = new JSONObject(json);
            JSONArray jsonArray = jsonRootObject.optJSONArray("foods");
            listFood = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String category = jsonObject.optString("fgid");
                if(category.equalsIgnoreCase(type)){
                    String serving = jsonObject.optString("srvg_sz").toString();
                    String food = jsonObject.optString("food").toString();
                    listFood.add(food + "\n" + serving);
                }
            }
            return listFood;
        } catch (JSONException e) { e.printStackTrace(); return null; }
    }

    public void toVeggieFruit(View v){
        intent = new Intent(this, VeggiesFruits.class);
        intent.putExtra("VFDStmt", data);
        intent.putStringArrayListExtra("VFFoods", listFood);
        startActivity(intent);
    }

    public void toGrainProduct(View v){
        intent = new Intent(this, GrainProducts.class);
        intent.putExtra("GPDStmt", data);
        intent.putStringArrayListExtra("GPFoods", listFood);
        startActivity(intent);
    }

    public void toMilkAlt(View v){
        intent = new Intent(this, MilkAlternatives.class);
        intent.putExtra("MKADStmt", data);
        intent.putStringArrayListExtra("MKAFoods", listFood);
        startActivity(intent);
    }

    public void toMeatAlt(View v){
        intent = new Intent(this, MeatAlternatives.class);
        intent.putExtra("MTADStmt", data);
        intent.putStringArrayListExtra("MTAFoods", listFood);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recommendations, menu);
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
