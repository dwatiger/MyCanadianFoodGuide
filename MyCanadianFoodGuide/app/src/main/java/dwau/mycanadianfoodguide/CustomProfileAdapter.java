package dwau.mycanadianfoodguide;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by dwau on 15-08-11.
 */
public class CustomProfileAdapter extends BaseAdapter implements ListAdapter {

    // http://stackoverflow.com/questions/17525886/listview-with-add-and-delete-buttons-in-each-row-in-android
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> listAge = new ArrayList<String>();
    private ArrayList<String> listSex = new ArrayList<String>();
    private Context context;

    ProfileDbHelper db = new ProfileDbHelper(context);

    public CustomProfileAdapter(ArrayList<String> list, ArrayList<String> listAge, ArrayList<String> listSex, Context context){
        this.list = list;
        this.context = context;
        this.listAge = listAge;
        this.listSex = listSex;
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int pos){
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos){
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View view = convertView;
        if(view == null){ // creates layout
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.profile_list_layout, null);
        }
        final TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));
        Button deleteButton = (Button)view.findViewById(R.id.delete_btn);
        Button goButton = (Button)view.findViewById(R.id.go_btn);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { // On delete button - removes profile from list
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Sends profile information to recommendation activity
                String age = listAge.get(position);
                String sex = listSex.get(position);
                Intent intent = new Intent(v.getContext(), RecommendationsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("profileAge", age);
                intent.putExtra("profileSex", sex);
                context.startActivity(intent);
            }
        });
        return view;
    }

}
