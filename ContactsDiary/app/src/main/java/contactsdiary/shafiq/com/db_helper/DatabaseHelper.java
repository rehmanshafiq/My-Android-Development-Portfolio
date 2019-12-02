package contactsdiary.shafiq.com.db_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts_table";
    private static final String COL_0 = "ID";
    private static final String COL_1 = "NAME";
    private static final String COL_2 = "PHONE_NUMBER";
    private static final String COL_3 = "DEVICE";
    private static final String COL_4 = "EMAIL";
    private static final String COL_5 = "PROFILE_PHOTO";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " +
                TABLE_NAME + " ( " +
                COL_0 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_1 + " TEXT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT, " +
                COL_5 + " TEXT )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
