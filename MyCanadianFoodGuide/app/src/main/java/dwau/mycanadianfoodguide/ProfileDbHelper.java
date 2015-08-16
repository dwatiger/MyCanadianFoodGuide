package dwau.mycanadianfoodguide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by dwau on 15-08-06.
 */
public class ProfileDbHelper extends SQLiteOpenHelper {

    public static final String TABLE_PROFILES = "profiles";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PROFILE = "profile";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_AGE = "age";
    public static final String DB_NAME = "profiles.db";
    public static final int DB_VERSION = 1;

    private static final String[] allColumns = { COLUMN_ID, COLUMN_PROFILE, COLUMN_SEX, COLUMN_AGE };

    public static final String DB_CREATE = "create table "
            + TABLE_PROFILES + "(" + COLUMN_ID
            + "integer primary key, " + COLUMN_PROFILE
            + " text not null, " +
            COLUMN_SEX + " text not null, " + COLUMN_AGE +
            ");";

    public ProfileDbHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS profiles");
        this.onCreate(db);
    }

    public void createProfile(Profile profile){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROFILE, profile.getProfileName());
        values.put(COLUMN_SEX, profile.getSex());
        values.put(COLUMN_AGE, profile.getAge());
        database.insert(TABLE_PROFILES, null, values);
        database.close();
    }

    public Profile readProfile(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROFILES, allColumns, " id = ?", new String[]{
                String.valueOf(id)}, null, null, null, null);
        if(cursor != null){ cursor.moveToFirst(); }
        Profile profile = new Profile();
        profile.setId(Integer.parseInt(cursor.getString(0)));
        profile.setProfileName(cursor.getString(1));
        profile.setSex(cursor.getString(2));
        profile.setAge(cursor.getString(3));

        return profile;
    }

    public List getAllProfiles(){
        List profiles = new LinkedList();

        String query = "SELECT * FROM " + TABLE_PROFILES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Profile profile = null;
        if(cursor.moveToFirst()){
            do{
                profile = new Profile();
                profile.setId(Integer.parseInt(cursor.getString(0)));
                profile.setProfileName(cursor.getString(1));
                profile.setSex(cursor.getString(2));
                profile.setAge(cursor.getString(3));
                profiles.add(profile);
            } while(cursor.moveToNext());
        }
        return profiles;
    }

    public int updateProfile(Profile profile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PROFILE, profile.getProfileName());
        values.put(COLUMN_SEX, profile.getSex());
        values.put(COLUMN_AGE, profile.getAge());
        int i = db.update(TABLE_PROFILES, values, COLUMN_ID + " = ?", new String[] { String.valueOf(profile.getId()) });
        db.close();
        return i;
    }

    public void deleteProfile(Profile profile){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROFILES, COLUMN_ID + " = ?", new String[] { String.valueOf(profile.getId())});
        db.close();
    }


}
