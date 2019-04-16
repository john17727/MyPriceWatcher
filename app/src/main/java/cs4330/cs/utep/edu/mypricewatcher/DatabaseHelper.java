package cs4330.cs.utep.edu.mypricewatcher;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "allItems";
    static final String DB_TABLE = "items";
    static final int DB_VERSION = 1;

    static final String ID = "id";
    static final String NAME = "name";
    static final String URL = "url";
    static final String INITPRICE = "initPrice";

    static final String CREATE_TABLE = "CREATE TABLE " +
            DB_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT NOT NULL, " +
            URL + " TEXT NOT NULL, " +
            INITPRICE + " DOUBLE NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(db);
    }

    public boolean insertData(String name, String url, String initPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, name);
        contentValues.put(URL, url);
        contentValues.put(INITPRICE, initPrice);

        long result = db.insert(DB_TABLE, null, contentValues);

        return result != -1;
    }

    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + DB_TABLE;

        return db.rawQuery(query, null);
    }

    public boolean deleteItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DB_TABLE, ID + "=" + id, null) > 0;
    }
}