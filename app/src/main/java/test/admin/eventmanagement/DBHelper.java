package test.admin.eventmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "event.db";

    private ContentValues cValues;
    private SQLiteDatabase dataBase = null;

    private String CREATE_TABLE_EVENT;

    private String CREATE_TABLE_USER;

    public  static final String TABLE_EVENT="tbl_event";

    public  static final String TABLE_USER="tbl_user";

    public  static final String COL_EVE_NAME="event_name";
    public  static final String COL_EVE_IMG="image_path";
    public  static final String COL_EVE_DATE="date";
    public  static final String COL_EVE_CAPT="caption";
    public  static final String COL_EVE_DETAILS="ev_det";


    public  static final String COL_USER_NAME="user_name";
    public  static final String COL_USER_EMAIL="user_email";
    public  static final String COL_USER_MOBILE="user_mobile";
    public  static final String COL_USER_PASS="user_pass";
    public  static final String COL_USER_COLG="user_colg";
    public  static final String COL_USER_ID="user_id";



    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA FOREIGN_KEYS=ON ");
    }

    public DBHelper(Context context) {
        super(context,  DATABASE_NAME, null, 1);

        }

    @Override
    public void onCreate(SQLiteDatabase db) {

        CREATE_TABLE_USER="create table tbl_user(user_id text,user_name text,user_mobile text,user_colg,user_pass)";
        db.execSQL(CREATE_TABLE_USER);

        CREATE_TABLE_EVENT="create table tbl_event(event_id integer primary key autoincrement,event_name text,date text,caption text,image_path text)";
        db.execSQL(CREATE_TABLE_EVENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if EXISTS tbl_user");
        onCreate(db);

        db.execSQL("DROP TABLE if EXISTS tbl_event");
        onCreate(db);

    }


    void insertUser(String name,String mobile,String colg,String uname,String pass)
    {

        try {
            dataBase = getWritableDatabase();
        } catch (SQLException s) {
            new Exception("Error with DB Open");
        }
        cValues=new ContentValues();

        cValues.put(COL_USER_NAME,name);
        cValues.put(COL_USER_MOBILE,mobile);
        cValues.put(COL_USER_COLG,colg);
        cValues.put(COL_USER_ID,uname);
        cValues.put(COL_USER_PASS,pass);

        dataBase.insert(TABLE_USER, null, cValues);

        dataBase.close();


    }

    public long insertEvent(String imagePath, String title, String date, String caption){
        long returnVal = 0;
        try {
            dataBase = getWritableDatabase();
            dataBase.beginTransaction();
            ContentValues contentValues=new ContentValues();

            contentValues.put(COL_EVE_NAME,title);
            contentValues.put(COL_EVE_CAPT,caption);
            contentValues.put(COL_EVE_IMG,imagePath);
            contentValues.put(COL_EVE_DATE,date);

            returnVal = dataBase.insert(TABLE_EVENT, null, contentValues);

            dataBase.setTransactionSuccessful();
            dataBase.endTransaction();
        } catch (Exception s) {
            dataBase.endTransaction();
            s.printStackTrace();
            new Exception("Error with DB Open");
        }

        return returnVal;

    }

    public ArrayList<Event> getAllEvents() {

        ArrayList<Event>  eventArrayList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_EVENT;
        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                Event event = new Event();
                event.setEventName(cursor.getString(1));
                event.setEventDate(cursor.getString(2));
                event.setEventCaption(cursor.getString(3));
                event.setEventImg(cursor.getString(4));
                eventArrayList.add(event);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return eventArrayList;
    }
}
