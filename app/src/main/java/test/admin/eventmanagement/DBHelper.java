package test.admin.eventmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import test.admin.eventmanagement.util.SessionManager;

public class DBHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "event.db";

    private ContentValues cValues;
    private SQLiteDatabase dataBase = null;

    private String CREATE_TABLE_EVENT;
    private String CREATE_TABLE_USER;
    private String CREATE_TABLE_PARTICIPATES;

    public  static final String TABLE_EVENT="tbl_event";
    public  static final String TABLE_USER="tbl_user";
    public  static final String TABLE_PARTICIPATES="tbl_participates";

    public  static final String COL_EVE_NAME="event_name";
    public  static final String COL_EVE_IMG="image_path";
    public  static final String COL_EVE_DATE="date";
    public  static final String COL_EVE_CAPT="caption";
    public  static final String COL_EVE_COLG="colg_name";
    public  static final String COL_EVE_DETAILS="ev_det";


    public  static final String COL_USER_NAME="user_name";
    public  static final String COL_USER_MOBILE="user_mobile";
    public  static final String COL_USER_PASS="user_pass";
    public  static final String COL_USER_COLG="user_colg";
    public  static final String COL_USER_ID="user_id";
    public  static final String COL_USER_TYPE="user_type";

    public static final String COL_PART_ID = "part_id";
    public static final String COL_PART_USER_NAME = "part_user_name";
    public static final String COL_PART_EVENT_ID = "part_event_id";
    public static final String COL_PART_EVENT_NAME = "part_event_name";
    public static final String COL_PART_COLG_NAME = "part_colg_name";

    public  static final String STUDENT="Student";
    public  static final String HEAD="Head";




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

        CREATE_TABLE_USER="create table tbl_user(user_id text,user_name text,user_mobile text,user_colg text,user_pass text, user_type text)";
        db.execSQL(CREATE_TABLE_USER);

        CREATE_TABLE_EVENT="create table tbl_event(event_id integer primary key autoincrement,event_name text,date text,caption text,image_path text, colg_name text)";
        db.execSQL(CREATE_TABLE_EVENT);

        CREATE_TABLE_PARTICIPATES="create table tbl_participates(part_id integer primary key autoincrement,part_user_name text,part_event_id text ,part_event_name text ,part_colg_name text)";
        db.execSQL(CREATE_TABLE_PARTICIPATES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE if EXISTS tbl_user");
        onCreate(db);

        db.execSQL("DROP TABLE if EXISTS tbl_event");
        onCreate(db);

        db.execSQL("DROP TABLE if EXISTS tbl_participates");
        onCreate(db);

    }

    public boolean isHeadAvailable(String cName){
        String where = COL_USER_COLG+" = '"+cName+"' AND "+COL_USER_TYPE+"= '"+HEAD+"'";
        String query = "SELECT * FROM "+TABLE_USER+" WHERE "+where;
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() >= 1){  //colg is with head available
                return true;
        }
        else {
            return false;
        }
    }

    public boolean isUserAvailable(String uName){
        String where = COL_USER_ID+" = '"+uName+"'";
        String query = "SELECT * FROM "+TABLE_USER+" WHERE "+where;

        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor      = db.rawQuery(query, null);
        boolean b = false;
        if (cursor.getCount() > 0){
            b= true;
        }
        else {
            b= false;
        }

        cursor.close();
        db.close();
        return  b;


    }


    public long insertUser(String name,String mobile,String colg,String uname,String pass, String userType)
    {
        long returnVal = 0;

        try {
            dataBase = getWritableDatabase();
            dataBase.beginTransaction();

           ContentValues cValues = new ContentValues();

            cValues.put(COL_USER_NAME, name);
            cValues.put(COL_USER_MOBILE, mobile);
            cValues.put(COL_USER_COLG, colg);
            cValues.put(COL_USER_ID, uname);
            cValues.put(COL_USER_PASS, pass);
            cValues.put(COL_USER_TYPE, userType);

          returnVal =   dataBase.insert(TABLE_USER, null, cValues);
            dataBase.setTransactionSuccessful();
            dataBase.endTransaction();
        } catch (SQLException s) {
            dataBase.endTransaction();
            new Exception("Error with DB Open");
        }

        return returnVal;

    }

    public long insertEvent(String imagePath, String title, String date, String caption, String colgName){
        long returnVal = 0;
        try {
            dataBase = getWritableDatabase();
            dataBase.beginTransaction();
            ContentValues contentValues=new ContentValues();

            contentValues.put(COL_EVE_NAME,title);
            contentValues.put(COL_EVE_CAPT,caption);
            contentValues.put(COL_EVE_IMG,imagePath);
            contentValues.put(COL_EVE_DATE,date);
            contentValues.put(COL_EVE_COLG,colgName);

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

    public boolean checkStudParticAvailable(String userName, String eventId){
        String where = COL_PART_USER_NAME+" = '"+userName+"' AND "+COL_PART_EVENT_ID+" = '"+eventId+"'";
        String query = "SELECT * FROM "+TABLE_PARTICIPATES+" WHERE "+where;

        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor      = db.rawQuery(query, null);
        boolean b = false;
        if (cursor.getCount() > 0)
            b = true;
        else
            b =  false;
        cursor.close();
        db.close();

        return b;

    }

    public long insertParticipte(String userName, String eventId, String eventName, String colgName){
        long returnVal = 0;
        try {
            dataBase = getWritableDatabase();
            dataBase.beginTransaction();
            ContentValues contentValues=new ContentValues();

            contentValues.put(COL_PART_USER_NAME,userName);
            contentValues.put(COL_PART_EVENT_ID,eventId);
            contentValues.put(COL_PART_EVENT_NAME,eventName);
            contentValues.put(COL_PART_COLG_NAME,colgName);

            returnVal = dataBase.insert(TABLE_PARTICIPATES, null, contentValues);

            dataBase.setTransactionSuccessful();
            dataBase.endTransaction();
        } catch (Exception s) {
            dataBase.endTransaction();
            s.printStackTrace();
            new Exception("Error with DB Open");
        }

        return returnVal;

    }

    public List<Participation> getAllParticipations(String cName){
        List<Participation> list = new ArrayList<>();
        String where = COL_PART_COLG_NAME +" = '"+cName+"'";

        String selectQuery = "SELECT  * FROM " + TABLE_PARTICIPATES+" WHERE "+where;
        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Participation participation = new Participation();
                participation.setCollegeName(cursor.getString(4));
                participation.setEventName(cursor.getString(3));
                participation.setUserName(cursor.getString(1));
                list.add(participation);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<Event> getAllEvents(String cName) {

        ArrayList<Event>  eventArrayList = new ArrayList<>();

        String where = COL_EVE_COLG +" = '"+cName+"'";
//        String selectQuery = "SELECT  * FROM " + TABLE_EVENT+" WHERE "+where;
        String selectQuery = "SELECT  * FROM " + TABLE_EVENT;
        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                Event event = new Event();
                event.setId(cursor.getInt(0));
                event.setEventName(cursor.getString(1));
                event.setEventDate(cursor.getString(2));
                event.setEventCaption(cursor.getString(3));
                event.setEventImg(cursor.getString(4));
                event.setColgName(cursor.getString(5));
                eventArrayList.add(event);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return eventArrayList;
    }

    public String[] getAllColleges(){
        String[] cNames;
        String selectQuery = "SELECT DISTINCT * FROM " + TABLE_USER;
        SQLiteDatabase db  = getReadableDatabase();
//        Cursor cursor      = db.rawQuery(selectQuery, null);
        Cursor cursor = db.query(true,TABLE_USER,new String[]{COL_USER_COLG},null,null,null,null,null,null);
        cNames = new String[cursor.getCount()];
        int i = 0;
        if (cursor.moveToFirst()){
            do{
                cNames[i] = cursor.getString(0);
                i++;
            }while (cursor.moveToNext());
        }

        return cNames;

    }



    public boolean login(Context context, String clgName, String userId, String password){
        String where = COL_USER_COLG+" = '"+clgName+"' AND "+COL_USER_ID+" = '"+userId+"' AND "+COL_USER_PASS+" = '"+password+"'";
        Log.d("DBHELPER", "WHERE - "+where);

        String selectQuery = "SELECT  * FROM " + TABLE_USER+" WHERE "+where;
        Log.d("DBHELPER", "QUERY - "+where);
        SQLiteDatabase db  = getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
            SessionManager sessionManager = new SessionManager(context);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            if (cursor.getString(5).equalsIgnoreCase(HEAD))
                sessionManager.setUserType(HEAD);
            else
                sessionManager.setUserType(STUDENT);

            sessionManager.setUserName(cursor.getString(0));
            sessionManager.setUserColgName(cursor.getString(3));

            return true;
        }
        else
            return false;

    }

    public int deleteEvent(String eName) {
        int r = 0;
        dataBase = getWritableDatabase();
        r = dataBase.delete(TABLE_EVENT, "event_name=?", new String[]{String.valueOf(eName)});
        dataBase.close();
        return r;

    }

    public void updateEvent(int id, String eName, String caption, String date, String img){
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        ContentValues cv = new ContentValues();
        cv.put(COL_EVE_NAME,eName); //These Fields should be your String values of actual column names
        cv.put(COL_EVE_DATE,date); //These Fields should be your String values of actual column names
        cv.put(COL_EVE_CAPT,caption); //These Fields should be your String values of actual column names
        cv.put(COL_EVE_IMG,img); //These Fields should be your String values of actual column names

        db.update(TABLE_EVENT, cv, "event_id = "+id, null);
        db.setTransactionSuccessful();
        db.endTransaction();

    }
}
