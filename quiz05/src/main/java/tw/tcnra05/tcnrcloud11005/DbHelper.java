package tw.tcnra05.tcnrcloud11005;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

////----------------------------------------------------------
//建構式參數說明：
//context 可以操作資料庫的內容本文，一般可直接傳入Activity物件。
//name 要操作資料庫名稱，如果資料庫不存在，會自動被建立出來並呼叫onCreate()方法。
//factory 用來做深入查詢用，入門時用不到。
//version 版本號碼。
////-----------------------
public class DbHelper extends SQLiteOpenHelper {
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 1;    // 資料表名稱
    // ===================資料庫名稱=======================================
    private static final String DB_FILE = "QuizeGame.db";
    //=====================table===============================================
    private static final String DB_TABLE_Q0300 = "q0300";    // 資料庫物件，固定的欄位變數
    private static final String DB_TABLE_Q0312_1 = "q0312_1";//初 rank
    private static final String DB_TABLE_Q0312_2 = "q0312_2";//中 rank
    private static final String DB_TABLE_Q0312_3 = "q0312_3";//高 rank
    private static final String Creat_Table_Q0300 =
            "CREATE TABLE " + DB_TABLE_Q0300 + " ("
                    + "id INTEGER PRIMARY KEY,question VARCHAR(200) NOT NULL,answer VARCHAR(50),wrong_answer1 VARCHAR(50),wrong_answer2 VARCHAR(50),wrong_answer3 VARCHAR(50));";
    private static final String Creat_Table_Q0312_1 =
            "CREATE TABLE " + DB_TABLE_Q0312_1 + " ("
                    + "id INTEGER PRIMARY KEY,name VARCHAR(50) NOT NULL,mail VARCHAR(50) NOT NULL,score INT(20) NOT NULL );";
    private static final String Creat_Table_Q0312_2 =
            "CREATE TABLE " + DB_TABLE_Q0312_2 + " ("
                    + "id INTEGER PRIMARY KEY,name VARCHAR(50) NOT NULL,mail VARCHAR(50) NOT NULL,score INT(20) NOT NULL );";
    private static final String Creat_Table_Q0312_3 =
            "CREATE TABLE " + DB_TABLE_Q0312_3 + " ("
                    + "id INTEGER PRIMARY KEY,name VARCHAR(50) NOT NULL,mail VARCHAR(50) NOT NULL,score INT(20) NOT NULL );";
    private static SQLiteDatabase database;
    public String sCreateTableCommand;
    String TAG = "tcnr05=>";

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        //super(context,name,factory,version);
        super(context, DB_FILE, null, version);
        sCreateTableCommand = "";

    }

    //----------------------------------------------
    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new DbHelper(context, DB_FILE, null, VERSION)
                    .getWritableDatabase();
        }
        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String Creat_Table_Q0300="SELECT id,name,grp from member where id>100";
        db.execSQL(Creat_Table_Q0300);
        db.execSQL(Creat_Table_Q0312_1);
        db.execSQL(Creat_Table_Q0312_2);
        db.execSQL(Creat_Table_Q0312_3);
        /*添加所有table */
        //db.execSQL(q04XX_crTBsql);
        //db.execSQL(q05XX_crTBsql);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Log.d(TAG,"onUpgrade");
        /*刪除所有table */
        db.execSQL("DROP     TABLE     IF    EXISTS    " + DB_TABLE_Q0300);
        db.execSQL("DROP     TABLE     IF    EXISTS    " + DB_TABLE_Q0312_1);
        db.execSQL("DROP     TABLE     IF    EXISTS    " + DB_TABLE_Q0312_2);
        db.execSQL("DROP     TABLE     IF    EXISTS    " + DB_TABLE_Q0312_3);
        //db.execSQL("DROP     TABLE     IF    EXISTS    " + DB_TABLE_QXXXX);

        onCreate(db);
    }

    public int RecCount_Q0300() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0300;
        //用Cursor做一個封包
        Cursor recSet = db.rawQuery(sql, null);//select
        int rcount = 0;
        rcount = recSet.getCount();
        recSet.close();
        return rcount;//回傳筆數
    }

    public String FindRec_Q0300(String tname) {
        SQLiteDatabase db = getReadableDatabase();
        String fldSet = "ans=";
        String sql = "SELECT * FROM " + DB_TABLE_Q0300 + " WHERE question LIKE ? ORDER BY id ASC";//ASC遞增排序 DESC遞減
        String[] args = {"%" + tname + "%"};
        Cursor recSet = db.rawQuery(sql, args); //
        int columnCount = recSet.getColumnCount();
        //===================
        if (recSet.getCount() != 0) {
            recSet.moveToFirst();
            fldSet = recSet.getString(0) + " "
                    + recSet.getString(1) + " "
                    + recSet.getString(2) + " "
                    + recSet.getString(3) + "\n";

            while (recSet.moveToNext()) {
                for (int i = 0; i < columnCount; i++) {
                    fldSet += recSet.getString(i) + " ";
                }
                fldSet += "\n";
            }
        }
        recSet.close();
        db.close();
        return fldSet;
    }
    // 修改用的 抓資料
//    public ArrayList<String> getRecSet() {
//        SQLiteDatabase db = getReadableDatabase();
//        String sql = "SELECT * FROM " + DB_TABLE;
//        Cursor recSet = db.rawQuery(sql, null);
//        ArrayList<String> recAry = new ArrayList<String>();
//        //----------------------------
////        Log.d(TAG, "recSet=" + recSet);
//        int columnCount = recSet.getColumnCount();
//        recSet.moveToFirst();
//        String fldSet = "";
//
//        if (recSet.getCount() != 0) { // 判斷資料如果 不是0比 才執行抓資料
//            for (int i = 0; i < columnCount; i++)
//                fldSet += recSet.getString(i) + "#"; // 欄位跟欄位 用 # 做區隔
//            recAry.add(fldSet);
//        }
//
//
//        while (recSet.moveToNext()) {
//            fldSet = "";
//            for (int i = 0; i < columnCount; i++) {
//                fldSet += recSet.getString(i) + "#"; // 欄位跟欄位 用 # 做區隔
//            }
//            recAry.add(fldSet);
//        }
//        //------------------------
//        recSet.close();
//        db.close();
////        Log.d(TAG, "recAry=" + recAry);
//        return recAry;
//    }

    public ArrayList<String> getRecSet_Q0300() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0300;
        Cursor recSet = db.rawQuery(sql, null);
        ArrayList<String> recAry = new ArrayList<String>();
        //----------------------------
//        Log.d(TAG, "recSet=" + recSet);
        int columnCount = recSet.getColumnCount();
        recSet.moveToFirst();
        String fldSet = "";
        if (recSet.getCount() > 0) { // 判斷資料如果 不是0比 才執行抓資料
            for (int i = 0; i < columnCount; i++)
                fldSet += recSet.getString(i) + "#"; //用#做區隔
            recAry.add(fldSet);
        }

        while (recSet.moveToNext()) {
            fldSet = "";
            for (int i = 0; i < columnCount; i++) {
                fldSet += recSet.getString(i) + "#"; //
            }
            recAry.add(fldSet);
        }
        //------------------------
        recSet.close();
        db.close();
//        Log.d(TAG, "recAry=" + recAry);
        return recAry;
    }
//    public void createTB() {
    // 批次新增
//        int maxrec = 20;
//        SQLiteDatabase db = getWritableDatabase();
//        for (int i = 0; i < maxrec; i++) {
//            ContentValues newRow = new ContentValues();
//            newRow.put("name", "路人" +  u_chinayear(i));
//            newRow.put("grp", "第" + u_chinano((int) (Math.random() * 4 + 1)) + "組");
//            newRow.put("address", "台中市西區工業一路" + (100 + i) + "號");
//            db.insert(DB_TABLE_Q0300, null, newRow);  //insert db
//        }
//        db.close();

//    }
//    private String u_chinano(int input_i) {
//        String c_number = "";
//        String china_no[]={"零","一","二","三","四","五","六","七","八","九"};
//        c_number = china_no[input_i % 10];
//
//        return c_number;
//    }
//    private String u_chinayear(int input_i) {
//        String c_number = "";
//        String china_no[]={"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
//        c_number = china_no[input_i % 10];
//
//        return c_number;
//    }

    public int clearRec_Q0300() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0300;
        Cursor c1 = db.rawQuery(sql, null);
        //Cursor c1=db.exeSQL("");
        //Cursor c2=db.rawQuery();
        //Cursor c3=db.insert();
        //Cursor c4=db.update();
        //Cursor c5=db.delete();
        if (c1.getCount() > 0) {
            //String whereClause:"id<0"
            int rowsAffected = db.delete(DB_TABLE_Q0300, "1", null);//
            //From the documentation of SQLiteDatabase delete method
            //To remove all rows and get a count pass "1" as the whereClause
            db.close();
            c1.close();
            return rowsAffected;
        } else {
            db.close();
            c1.close();
            return -1;
        }


    }

    public int deleteRec_Q0300(String b_id) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0300;
        Cursor recSet = db.rawQuery(sql, null);
        if (recSet.getCount() != 0) {
            String whereClause = "id = '" + b_id + "'";//d='b_id'
            int rowsAffected = db.delete(DB_TABLE_Q0300, whereClause, null);//delete one row
            recSet.close();
            db.close();
            return rowsAffected;
        } else {
            recSet.close();
            db.close();
            return -1;
        }


    }

    public int updateRec_Q0300(String b_id, String b_name, String b_grp, String b_address) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0300;
        Cursor recSet = db.rawQuery(sql, null);
        if (recSet.getCount() != 0) {
            ContentValues rec = new ContentValues();
//   rec.put("id", b_id);
            rec.put("question", b_name);
            rec.put("grp", b_grp);
            rec.put("address", b_address);
            String whereClause = "id ='" + b_id + "'";

            int rowsAffected = db.update(DB_TABLE_Q0300, rec, whereClause, null);
            recSet.close();
            db.close();
            return rowsAffected;
        } else {
            recSet.close();
            db.close();
            return -1;
        }
    }

    //    ContentValues values
    public long insertRec_m_Q0300(ContentValues rec) {
        SQLiteDatabase db = getWritableDatabase();
        long rowID = db.insert(DB_TABLE_Q0300, null, rec);
        Log.d(TAG, "q0300_quiz_insertRec_m");
        db.close();
        return rowID;
    }

    public long insertRec_m_Q0312_1(ContentValues rec) {
        SQLiteDatabase db = getWritableDatabase();
        long rowID = db.insert(DB_TABLE_Q0312_1, null, rec);
        Log.d(TAG, "q0312_1_quiz_insertRec_m");
        db.close();
        return rowID;
    }

    public long insertRec_m_Q0312_2(ContentValues rec) {
        SQLiteDatabase db = getWritableDatabase();
        long rowID = db.insert(DB_TABLE_Q0312_2, null, rec);
        Log.d(TAG, "q0312_2_quiz_insertRec_m");
        db.close();
        return rowID;
    }

    public long insertRec_m_Q0312_3(ContentValues rec) {
        SQLiteDatabase db = getWritableDatabase();
        long rowID = db.insert(DB_TABLE_Q0312_3, null, rec);
        Log.d(TAG, "q0312_3_quiz_insertRec_m");
        db.close();
        return rowID;
    }

    public int RecCount_Q0312_1() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_1;
        //用Cursor做一個封包
        Cursor recSet = db.rawQuery(sql, null);//select
        int rcount = 0;
        rcount = recSet.getCount();
        recSet.close();
        return rcount;//回傳筆數
    }

    public int RecCount_Q0312_2() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_2;
        //用Cursor做一個封包
        Cursor recSet = db.rawQuery(sql, null);//select
        int rcount = 0;
        rcount = recSet.getCount();
        recSet.close();
        return rcount;//回傳筆數
    }

    public int RecCount_Q0312_3() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_3;
        //用Cursor做一個封包
        Cursor recSet = db.rawQuery(sql, null);//select
        int rcount = 0;
        rcount = recSet.getCount();
        recSet.close();
        return rcount;//回傳筆數
    }

    public int updateRec_Q0312_1(String b_name, String b_mail, int b_score) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_1;
        Cursor recSet = db.rawQuery(sql, null);
        if (recSet.getCount() != 0) {
            ContentValues rec = new ContentValues();
//   rec.put("id", b_id);
            rec.put("name", b_name);
//            rec.put("mail", b_mail);
            rec.put("score", b_score);
            String whereClause = "mail = '" + b_mail + "'";

            int rowsAffected = db.update(DB_TABLE_Q0312_1, rec, whereClause, null);
            recSet.close();
            db.close();
            return rowsAffected;
        } else {
            recSet.close();
            db.close();
            return -1;
        }
    }

    public int updateRec_Q0312_2(String b_name, String b_mail, int b_score) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_2;
        Cursor recSet = db.rawQuery(sql, null);
        if (recSet.getCount() != 0) {
            ContentValues rec = new ContentValues();
//   rec.put("id", b_id);
            rec.put("name", b_name);
//            rec.put("mail", b_mail);
            rec.put("score", b_score);
            String whereClause = "mail ='" + b_mail + "'";

            int rowsAffected = db.update(DB_TABLE_Q0312_2, rec, whereClause, null);
            recSet.close();
            db.close();
            return rowsAffected;
        } else {
            recSet.close();
            db.close();
            return -1;
        }
    }

    public int updateRec_Q0312_3(String b_name, String b_mail, int b_score) {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_3;
        Cursor recSet = db.rawQuery(sql, null);
        if (recSet.getCount() != 0) {
            ContentValues rec = new ContentValues();
//   rec.put("id", b_id);
            rec.put("name", b_name);
//            rec.put("mail", b_mail);
            rec.put("score", b_score);
            String whereClause = "mail ='" + b_mail + "'";

            int rowsAffected = db.update(DB_TABLE_Q0312_3, rec, whereClause, null);
            recSet.close();
            db.close();
            return rowsAffected;
        } else {
            recSet.close();
            db.close();
            return -1;
        }
    }

    public long insertRec_Q0312_1(int b_id, String b_name, String b_mail, int b_score) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();

        rec.put("id", b_id);
        rec.put("name", b_name);
        rec.put("mail", b_mail);
        rec.put("score", b_score);
        long rowID = db.insert(DB_TABLE_Q0312_1, null, rec); // SQLite 新增寫法


        db.close();
        return rowID;
    }

    public long insertRec_Q0312_2(int b_id, String b_name, String b_mail, int b_score) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();

        rec.put("id", b_id);
        rec.put("name", b_name);
        rec.put("mail", b_mail);
        rec.put("score", b_score);
        long rowID = db.insert(DB_TABLE_Q0312_2, null, rec); // SQLite 新增寫法
        db.close();
        return rowID;
    }

    public long insertRec_Q0312_3(int b_id, String b_name, String b_mail, int b_score) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues rec = new ContentValues();

        rec.put("id", b_id);
        rec.put("name", b_name);
        rec.put("mail", b_mail);
        rec.put("score", b_score);
        long rowID = db.insert(DB_TABLE_Q0312_3, null, rec); // SQLite 新增寫法
        db.close();
        return rowID;
    }

    public int clearRec_Q0312_1() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_1;
        Cursor c1 = db.rawQuery(sql, null);
        //Cursor c1=db.exeSQL("");
        //Cursor c2=db.rawQuery();
        //Cursor c3=db.insert();
        //Cursor c4=db.update();
        //Cursor c5=db.delete();
        if (c1.getCount() > 0) {
            //String whereClause:"id<0"
            int rowsAffected = db.delete(DB_TABLE_Q0312_1, "1", null);//
            //From the documentation of SQLiteDatabase delete method
            //To remove all rows and get a count pass "1" as the whereClause
            db.close();
            c1.close();
            return rowsAffected;
        } else {
            db.close();
            c1.close();
            return -1;
        }


    }

    public int clearRec_Q0312_2() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_2;
        Cursor c1 = db.rawQuery(sql, null);
        //Cursor c1=db.exeSQL("");
        //Cursor c2=db.rawQuery();
        //Cursor c3=db.insert();
        //Cursor c4=db.update();
        //Cursor c5=db.delete();
        if (c1.getCount() > 0) {
            //String whereClause:"id<0"
            int rowsAffected = db.delete(DB_TABLE_Q0312_2, "1", null);//
            //From the documentation of SQLiteDatabase delete method
            //To remove all rows and get a count pass "1" as the whereClause
            db.close();
            c1.close();
            return rowsAffected;
        } else {
            db.close();
            c1.close();
            return -1;
        }


    }

    public int clearRec_Q0312_3() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_3;
        Cursor c1 = db.rawQuery(sql, null);
        //Cursor c1=db.exeSQL("");
        //Cursor c2=db.rawQuery();
        //Cursor c3=db.insert();
        //Cursor c4=db.update();
        //Cursor c5=db.delete();
        if (c1.getCount() > 0) {
            //String whereClause:"id<0"
            int rowsAffected = db.delete(DB_TABLE_Q0312_3, "1", null);//
            //From the documentation of SQLiteDatabase delete method
            //To remove all rows and get a count pass "1" as the whereClause
            db.close();
            c1.close();
            return rowsAffected;
        } else {
            db.close();
            c1.close();
            return -1;
        }


    }

    public ArrayList<String> getRecSet_Q0312_1() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_1+" ORDER BY score DESC";
        Cursor recSet = db.rawQuery(sql, null);
        ArrayList<String> recAry = new ArrayList<String>();
        //----------------------------
//        Log.d(TAG, "recSet=" + recSet);
        int columnCount = recSet.getColumnCount();
        recSet.moveToFirst();
        String fldSet = "";
        if (recSet.getCount() > 0) { // 判斷資料如果 不是0比 才執行抓資料
            for (int i = 0; i < columnCount; i++)
                fldSet += recSet.getString(i) + "#"; //用#做區隔
            recAry.add(fldSet);
        }

        while (recSet.moveToNext()) {
            fldSet = "";
            for (int i = 0; i < columnCount; i++) {
                fldSet += recSet.getString(i) + "#"; //
            }
            recAry.add(fldSet);
        }
        //------------------------
        recSet.close();
        db.close();
//        Log.d(TAG, "recAry=" + recAry);
        return recAry;
    }

    public ArrayList<String> getRecSet_Q0312_2() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_2+" ORDER BY score DESC";
        Cursor recSet = db.rawQuery(sql, null);
        ArrayList<String> recAry = new ArrayList<String>();
        //----------------------------
//        Log.d(TAG, "recSet=" + recSet);
        int columnCount = recSet.getColumnCount();
        recSet.moveToFirst();
        String fldSet = "";
        if (recSet.getCount() > 0) { // 判斷資料如果 不是0比 才執行抓資料
            for (int i = 0; i < columnCount; i++)
                fldSet += recSet.getString(i) + "#"; //用#做區隔
            recAry.add(fldSet);
        }

        while (recSet.moveToNext()) {
            fldSet = "";
            for (int i = 0; i < columnCount; i++) {
                fldSet += recSet.getString(i) + "#"; //
            }
            recAry.add(fldSet);
        }
        //------------------------
        recSet.close();
        db.close();
//        Log.d(TAG, "recAry=" + recAry);
        return recAry;
    }

    public ArrayList<String> getRecSet_Q0312_3() {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM " + DB_TABLE_Q0312_3+" ORDER BY score DESC";
        Cursor recSet = db.rawQuery(sql, null);
        ArrayList<String> recAry = new ArrayList<String>();
        //----------------------------
//        Log.d(TAG, "recSet=" + recSet);
        int columnCount = recSet.getColumnCount();
        recSet.moveToFirst();
        String fldSet = "";
        if (recSet.getCount() > 0) { // 判斷資料如果 不是0比 才執行抓資料
            for (int i = 0; i < columnCount; i++)
                fldSet += recSet.getString(i) + "#"; //用#做區隔
            recAry.add(fldSet);
        }

        while (recSet.moveToNext()) {
            fldSet = "";
            for (int i = 0; i < columnCount; i++) {
                fldSet += recSet.getString(i) + "#"; //
            }
            recAry.add(fldSet);
        }
        //------------------------
        recSet.close();
        db.close();
//        Log.d(TAG, "recAry=" + recAry);
        return recAry;
    }
}
