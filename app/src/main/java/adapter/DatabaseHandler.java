package adapter;

/**
 * Created by phong on 2/4/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.codly.Uetface.controller.ScheduleExam;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "android_api";

    // Login table name
    private static final String TABLE_LOGIN = "login";
    private static final String TABLE_TIMETABLE = "timetable";
    private static final String TABLE_EVALUATED = "evaluated";
    private static  final String TABlE_TEACHER = "teacher";
    private  static final String TABLE_ALLSUBJECT = "allsubject";
    private static final String TABLE_SCHEDULES = "schedules";


    //  Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    //private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_MALOP = "malop";
    private static final String KEY_TIETBATDAU  =  "tietbatdau";
    private static final String KEY_TIETKETTHUC =  "tietketthuc";
    private static final String KEY_GIANGDUONG  =   "giangduong";
    private static final String KEY_THU =  "thu";
    private static final String KEY_MAMON = "mamon";
    private static final String KEY_TENMON =  "tenmon";
    private static final String KEY_MADANHGIA  = "madanhgia";

    private static final String KEY_KETQUA = "ketqua";
    private static final String KEY_MAGIANGVIEN = "magiangvien";
    private static final String KEY_TENGIANGVIEN = "tengiangvien";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_MSSV = "mssv";
    private static final String KEY_GHI_CHU = "ghi_chu";

    private static final String KEY_SBD = "sbd";
    private static final String KEY_NGAY_THI = "ngay_thi";
    private static final String KEY_GIOTHI = "gio_thi";
    private static final String KEY_CATHI = "ca_thi";
    private static final String KEY_PHONGTHI= "phong_thi";
    private static final String KEY_HINHTHUCTHI = "hinh_thuc_thi";
    private static final String KEY_MDG_MON = "ma_danh_gia_mon";
    private static final String KEY_MDG_LOP = "ma_danh_gia_lop";





    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TIMETABLE_TABLE = "CREATE TABLE " + TABLE_TIMETABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                +  KEY_MALOP + " TEXT,"
                +  KEY_TIETBATDAU + " TEXT,"
                +  KEY_TIETKETTHUC + " TEXT,"
                +  KEY_GIANGDUONG + " TEXT,"
                +  KEY_THU + " TEXT,"
                +  KEY_MAMON + " TEXT,"
                +  KEY_TENMON + " TEXT,"
                +  KEY_MADANHGIA + " TEXT,"
                +  KEY_MSSV + " TEXT,"
                +  KEY_GHI_CHU + " TEXT"+ ")";


        String CREATE_LOGIN_TABLE  = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_TOKEN + " TEXT" + ")";

        String CREATE_EVALUATED_TABLE  = "CREATE TABLE " + TABLE_EVALUATED + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_KETQUA+ " TEXT,"
                + KEY_COMMENT+ " TEXT,"
                + KEY_MAGIANGVIEN+ " TEXT,"
                + KEY_TENGIANGVIEN+ " TEXT,"
                + KEY_TENMON+ " TEXT,"
                + KEY_MALOP+ " TEXT,"
                + KEY_MSSV+ " TEXT,"
                + KEY_MDG_MON+ " TEXT,"
                + KEY_MDG_LOP + " TEXT" + ")";

        String CREATE_TEACHER_TABLE = "CREATE VIRTUAL TABLE " + TABlE_TEACHER + " USING fts3("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_MAGIANGVIEN + " TEXT,"
                + KEY_TENGIANGVIEN + " TEXT" + ")";

        String CREATE_ALLSUBJECT_TABLE = "CREATE TABLE " + TABLE_ALLSUBJECT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                +  KEY_MALOP + " TEXT,"
                +  KEY_TIETBATDAU + " TEXT,"
                +  KEY_TIETKETTHUC + " TEXT,"
                +  KEY_GIANGDUONG + " TEXT,"
                +  KEY_THU + " TEXT,"
                +  KEY_MAMON + " TEXT,"
                +  KEY_TENMON + " TEXT,"
                +  KEY_MADANHGIA + " TEXT,"
                +  KEY_MSSV + " TEXT,"
                +  KEY_GHI_CHU + " TEXT"+ ")";


        String CREATE_SCHEDULES_TABLE = "CREATE TABLE " + TABLE_SCHEDULES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                +  KEY_SBD + " TEXT,"
                +  KEY_TENMON + " TEXT,"
                +  KEY_NGAY_THI + " TEXT,"
                +  KEY_GIOTHI + " TEXT,"
                +  KEY_CATHI + " TEXT,"
                +  KEY_PHONGTHI + " TEXT,"
                +  KEY_HINHTHUCTHI + " TEXT" + ")";



        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_TIMETABLE_TABLE);
        db.execSQL(CREATE_EVALUATED_TABLE);
        db.execSQL(CREATE_TEACHER_TABLE);
        db.execSQL(CREATE_ALLSUBJECT_TABLE);
        db.execSQL(CREATE_SCHEDULES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATED);
        db.execSQL("DROP TABLE IF EXISTS " + TABlE_TEACHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALLSUBJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULES);




        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String token) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put("token", token); // token
        //values.put(KEY_UID, uid); // Email
        //values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }
    // add teacher to database
    public void addTeacher(TeacherInfo s){
        SQLiteDatabase db = this.getWritableDatabase();
        String magv = s.getMagv();
        String tengv = s.getTengv();


        ContentValues values = new ContentValues();
        values.put(KEY_MAGIANGVIEN, magv);
        values.put(KEY_TENGIANGVIEN,tengv );

        // Inserting Row
        db.insert(TABlE_TEACHER, null, values);
        db.close(); // Closing database connection


    }

    public void addSchedule(FinalExamInfo s){
        SQLiteDatabase db = this.getWritableDatabase();
        String sbd = s.getSbd();
        String ten_mon = s.getMon_hoc();
        String ngay_thi = s.getNgay_thi();
        String gio_thi = s.getGio_thi();
        String ca_thi = s.getCa_thi();
        String  phong_thi = s.getPhong_thi();
        String hinh_thuc_thi = s.getHinh_thuc_thi();


        ContentValues values = new ContentValues();
        values.put(KEY_SBD, sbd);
        values.put(KEY_TENMON,ten_mon);
        values.put(KEY_NGAY_THI, ngay_thi);
        values.put(KEY_GIOTHI, gio_thi);
        values.put(KEY_CATHI, ca_thi);
        values.put(KEY_PHONGTHI, phong_thi);
        values.put(KEY_HINHTHUCTHI, hinh_thuc_thi);


        // Inserting Row
        db.insert(TABLE_SCHEDULES, null, values);
        db.close(); // Closing database connection


    }


    public List<TeacherInfo> searchCustomer(String inputText) throws SQLException {
        //Log.w(TAG, inputText);
        List<TeacherInfo> result = new ArrayList<TeacherInfo>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * " +

                "FROM " + TABlE_TEACHER +
                " WHERE " +  KEY_TENGIANGVIEN + " MATCH '" + inputText + "';";
        //Log.w(TAG, query);
        Cursor mCursor = db.rawQuery(query,null);

        if (mCursor.moveToFirst()) {
            do {



                String mgv = mCursor.getString(mCursor.getColumnIndex(KEY_MAGIANGVIEN));
                String tgv = mCursor.getString(mCursor.getColumnIndex(KEY_TENGIANGVIEN));

                TeacherInfo te = new TeacherInfo(mgv,tgv);

                // adding to todo list
               result.add(te);
            } while (mCursor.moveToNext());
        }
        db.close();
        mCursor.close();

        return result;

    }

    public List<SubjectInfor> searchSubjectInAllSubject(String searchTerm) {

        List<SubjectInfor> recordsList = new ArrayList<SubjectInfor>();

        // select query
        String sql = "";
        sql += "SELECT * FROM " + TABLE_ALLSUBJECT;
        sql += " WHERE " + KEY_TENMON + " LIKE '%" + searchTerm + "%'";
        sql += " ORDER BY " + KEY_GIANGDUONG ;


        SQLiteDatabase db = this.getWritableDatabase();

        // execute the query
        Cursor cursor = db.rawQuery(sql, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                // int productId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(fieldProductId)));
                String malop = cursor.getString(cursor.getColumnIndex(KEY_MALOP));
                String tietbatdau = cursor.getString(cursor.getColumnIndex(KEY_TIETBATDAU));
                String tietketthuc =  cursor.getString(cursor.getColumnIndex(KEY_TIETKETTHUC));
                String giangduong = cursor.getString(cursor.getColumnIndex(KEY_GIANGDUONG));
                String thu  =  cursor.getString(cursor.getColumnIndex(KEY_THU));
                String mamon =  cursor.getString(cursor.getColumnIndex(KEY_MAMON));
                String tenmon =  cursor.getString(cursor.getColumnIndex(KEY_TENMON));
                String madanhgia = cursor.getString(cursor.getColumnIndex(KEY_MADANHGIA));
                String mssv = cursor.getString(cursor.getColumnIndex(KEY_MSSV));
                String ghichu = cursor.getString(cursor.getColumnIndex(KEY_GHI_CHU));
                SubjectInfor td = new SubjectInfor( malop, tietbatdau,tietketthuc, giangduong, thu,mamon,tenmon, madanhgia,mssv,ghichu);


                // add to list
                recordsList.add(td);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return the list of records
        return recordsList;
    }





    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            //user.put("email", cursor.getString(2));
            //user.put("uid", cursor.getString(3));
            user.put("token", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
    //them ket qua danh gia
    public void addEvaluateResult(EvaluateResult s)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String ketqua = s.getKetqua();
        String comment = s.getCommment();
        String magiangvien = s.getMagiangvien();
        String tengiangvien = s.getTengiangvien();
        String tenmon = s.getTenmon();
        String malop = s.getMalop();
        String mssv =s.getMa_sinh_vien();
        String ma_danh_gia_mon = s.getMa_danh_gia_mon();
        String ma_danh_gia_lop = s.getMa_danh_gia_lop();


        ContentValues values = new ContentValues();
        values.put(KEY_KETQUA, ketqua);
        values.put(KEY_COMMENT, comment);
        values.put(KEY_MAGIANGVIEN, magiangvien);
        values.put(KEY_TENGIANGVIEN, tengiangvien);
        values.put(KEY_TENMON, tenmon);
        values.put(KEY_MALOP, malop);
        values.put(KEY_MSSV, mssv);
        values.put(KEY_MDG_MON, ma_danh_gia_mon);
        values.put(KEY_MDG_LOP, ma_danh_gia_lop);



        // Inserting Row
        db.insert(TABLE_EVALUATED, null, values);
        db.close(); // Closing database connection


    }
    // them data mon hoc
    public void addTimeTable(SubjectInfor s)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String malop = s.getMalop();
        String tietbatdau = s.getTietbatdau();
        String tietketthuc = s.getTietketthuc();
        String giangduong = s.getGiangduong();
        String thu = s.getThu();
        String mamon = s.getMamon();
        String tenmon =s.getTenmon();
        String madanhgia = s.getMadanhgia();
        String mssv = s.getMssv();
        String ghichu = s.getGhichu();
        ContentValues values = new ContentValues();
        values.put(KEY_MALOP, malop);
        values.put(KEY_TIETBATDAU, tietbatdau);
        values.put(KEY_TIETKETTHUC, tietketthuc);
        values.put(KEY_GIANGDUONG, giangduong);
        values.put(KEY_THU, thu);
        values.put(KEY_MAMON, mamon);
        values.put(KEY_TENMON, tenmon);
        values.put(KEY_MADANHGIA, madanhgia);
        values.put(KEY_MSSV, mssv);
        values.put(KEY_GHI_CHU,ghichu);


        // Inserting Row
        db.insert(TABLE_TIMETABLE, null, values);
        db.close(); // Closing database connection
    }


    public void addSubjectToAllSubjectTable(SubjectInfor s)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String malop = s.getMalop();
        String tietbatdau = s.getTietbatdau();
        String tietketthuc = s.getTietketthuc();
        String giangduong = s.getGiangduong();
        String thu = s.getThu();
        String mamon = s.getMamon();
        String tenmon =s.getTenmon();
        String madanhgia = s.getMadanhgia();
        String mssv = s.getMssv();
        String ghichu = s.getGhichu();

        ContentValues values = new ContentValues();
        values.put(KEY_MALOP, malop);
        values.put(KEY_TIETBATDAU, tietbatdau);
        values.put(KEY_TIETKETTHUC, tietketthuc);
        values.put(KEY_GIANGDUONG, giangduong);
        values.put(KEY_THU, thu);
        values.put(KEY_MAMON, mamon);
        values.put(KEY_TENMON, tenmon);
        values.put(KEY_MADANHGIA, madanhgia);
        values.put(KEY_MSSV, mssv);
        values.put(KEY_GHI_CHU,ghichu);


        // Inserting Row
        db.insert(TABLE_ALLSUBJECT, null, values);
        db.close(); // Closing database connection
    }


    //lay data danh gia
    public  List<EvaluateResult> getEvaluateResults()
    {
        List<EvaluateResult> results = new ArrayList<EvaluateResult>();
        String selectQuery = "SELECT  * FROM " + TABLE_EVALUATED;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {


                String id = cursor.getString(cursor.getColumnIndex(KEY_ID));
                String kq = cursor.getString(cursor.getColumnIndex(KEY_KETQUA));
                String cmt = cursor.getString(cursor.getColumnIndex(KEY_COMMENT));
                String magv =  cursor.getString(cursor.getColumnIndex(KEY_MAGIANGVIEN));
                String tengv = cursor.getString(cursor.getColumnIndex(KEY_TENGIANGVIEN));
                String tenmon  =  cursor.getString(cursor.getColumnIndex(KEY_TENMON));
                String malop =  cursor.getString(cursor.getColumnIndex(KEY_MALOP));
                String mssv =  cursor.getString(cursor.getColumnIndex(KEY_MSSV));
                String ma_danh_gia_mon =  cursor.getString(cursor.getColumnIndex(KEY_MDG_MON));
                String ma_danh_gia_lop =  cursor.getString(cursor.getColumnIndex(KEY_MDG_LOP));

                EvaluateResult td = new EvaluateResult(id, kq,cmt,magv,tengv,tenmon, malop,mssv,ma_danh_gia_mon,ma_danh_gia_lop);

                // adding to todo list
                results.add(td);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return results;
    }
    // lay data mon hoc
    public List<SubjectInfor> getTimeTable()
    {
        List<SubjectInfor> timetable = new ArrayList<SubjectInfor>();
        String selectQuery = "SELECT  * FROM " + TABLE_TIMETABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {



                String malop = cursor.getString(cursor.getColumnIndex(KEY_MALOP));
                String tietbatdau = cursor.getString(cursor.getColumnIndex(KEY_TIETBATDAU));
                String tietketthuc =  cursor.getString(cursor.getColumnIndex(KEY_TIETKETTHUC));
                String giangduong = cursor.getString(cursor.getColumnIndex(KEY_GIANGDUONG));
                String thu  =  cursor.getString(cursor.getColumnIndex(KEY_THU));
                String mamon =  cursor.getString(cursor.getColumnIndex(KEY_MAMON));
                String tenmon =  cursor.getString(cursor.getColumnIndex(KEY_TENMON));
                String madanhgia = cursor.getString(cursor.getColumnIndex(KEY_MADANHGIA));
                String mssv =  cursor.getString(cursor.getColumnIndex(KEY_MSSV));
                String ghichu = cursor.getString(cursor.getColumnIndex(KEY_GHI_CHU));
                SubjectInfor td = new SubjectInfor( malop, tietbatdau,tietketthuc, giangduong, thu,mamon,tenmon, madanhgia,mssv,ghichu);

                // adding to todo list
                timetable.add(td);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return timetable;
    }



    public List<SubjectInfor> getAllSubject()
    {
        List<SubjectInfor> allsubject = new ArrayList<SubjectInfor>();
        String selectQuery = "SELECT  * FROM " + TABLE_ALLSUBJECT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {



                String malop = cursor.getString(cursor.getColumnIndex(KEY_MALOP));
                String tietbatdau = cursor.getString(cursor.getColumnIndex(KEY_TIETBATDAU));
                String tietketthuc =  cursor.getString(cursor.getColumnIndex(KEY_TIETKETTHUC));
                String giangduong = cursor.getString(cursor.getColumnIndex(KEY_GIANGDUONG));
                String thu  =  cursor.getString(cursor.getColumnIndex(KEY_THU));
                String mamon =  cursor.getString(cursor.getColumnIndex(KEY_MAMON));
                String tenmon =  cursor.getString(cursor.getColumnIndex(KEY_TENMON));
                String madanhgia = cursor.getString(cursor.getColumnIndex(KEY_MADANHGIA));
                String mssv =  cursor.getString(cursor.getColumnIndex(KEY_MSSV));
                String ghichu = cursor.getString(cursor.getColumnIndex(KEY_GHI_CHU));

                SubjectInfor td = new SubjectInfor( malop, tietbatdau,tietketthuc, giangduong, thu,mamon,tenmon, madanhgia,mssv,ghichu);

                // adding to todo list
                allsubject.add(td);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return allsubject;
    }
    public String getToken()
    {

        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE id = 1";
        String token = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            token = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return token;

    }

    /**
     * Getting user login status
     * return true if rows are there in table
     * */




    public List<TeacherInfo> getAllTeacher()
    {
        List<TeacherInfo> teachers = new ArrayList<TeacherInfo>();
        String selectQuery = "SELECT  * FROM " + TABlE_TEACHER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {



                String magv = cursor.getString(cursor.getColumnIndex(KEY_MAGIANGVIEN));
                String tengv = cursor.getString(cursor.getColumnIndex(KEY_TENGIANGVIEN));

                TeacherInfo tea = new TeacherInfo( magv, tengv);

                // adding to todo list
                teachers.add(tea);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return teachers;

    }
    public List<FinalExamInfo> getAllFinalExam()
    {
        List<FinalExamInfo> l = new ArrayList<FinalExamInfo>();
        String selectQuery = "SELECT  * FROM " + TABLE_SCHEDULES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {




                String sbd =  cursor.getString(cursor.getColumnIndex(KEY_SBD));
                String ten_mon =  cursor.getString(cursor.getColumnIndex(KEY_TENMON));
                String ngay_thi =  cursor.getString(cursor.getColumnIndex(KEY_NGAY_THI));
                String gio_thi =  cursor.getString(cursor.getColumnIndex(KEY_GIOTHI));
                String ca_thi = cursor.getString(cursor.getColumnIndex(KEY_CATHI));
                String  phong_thi = cursor.getString(cursor.getColumnIndex(KEY_PHONGTHI));
                String hinh_thuc_thi = cursor.getString(cursor.getColumnIndex(KEY_HINHTHUCTHI));

                FinalExamInfo exam = new FinalExamInfo( sbd,ten_mon,ngay_thi,gio_thi,ca_thi,phong_thi,hinh_thuc_thi);

                // adding to todo list
                l.add(exam);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();

        return l;

    }


    //delete subjwct in timetalbe

    public boolean deleteSubjectFromTiemtable(String malop,String ghichu )
    {
       SQLiteDatabase db  = this.getWritableDatabase();

        return db.delete(TABLE_TIMETABLE, KEY_MALOP + "='" + malop + "' and "+ KEY_GHI_CHU + "='" +ghichu+"'", null) > 0;
    }

    public boolean deleteSubjectFromEvaluate(String id)
    {
        SQLiteDatabase db  = this.getWritableDatabase();

        return db.delete(TABLE_EVALUATED, KEY_ID + "=" + id , null) > 0;
    }
    public int getRowCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public int getSubjectCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_TIMETABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public int getAllSubjectCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_ALLSUBJECT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public String getMssv()
    {
        String mssv = "";
        String countQuery = "SELECT "+ KEY_MSSV + " FROM " + TABLE_TIMETABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst())
        {
             mssv = cursor.getString(cursor.getColumnIndex(KEY_MSSV));

        }
        db.close();
        cursor.close();
        return mssv;
    }




    public int getEvaluateCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_EVALUATED;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;

    }
    public int getTeacherCount()
    {
        String countQuery = "SELECT  * FROM " + TABlE_TEACHER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    public int getExamCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_SCHEDULES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }


    /**
     * Re crate database
     * Delete all tables and create them again
     * */

     public void resetTables()
     {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.delete(TABLE_TIMETABLE,null,null);
        db.delete(TABLE_EVALUATED,null,null);
        db.delete(TABLE_ALLSUBJECT,null,null);
        db.delete(TABlE_TEACHER,null,null);
        db.delete(TABLE_SCHEDULES,null,null);
        db.close();
    }
    public void resetEvaluateTable()
    {
        SQLiteDatabase da = this.getWritableDatabase();
        da.delete(TABLE_EVALUATED,null,null);
    }
    public boolean resetTeacher()
    {
        SQLiteDatabase da = this.getWritableDatabase();
        int doneDelete = 0;
        doneDelete = da.delete(TABlE_TEACHER, null , null);
        Log.w("delete", Integer.toString(doneDelete));
        return doneDelete > 0;

    }

    public boolean resetTimetable()
    {
        SQLiteDatabase da = this.getWritableDatabase();
        int doneDelete = 0;
        doneDelete = da.delete(TABLE_TIMETABLE, null , null);
        Log.w("delete timetable", Integer.toString(doneDelete));
        return doneDelete > 0;

    }

}
