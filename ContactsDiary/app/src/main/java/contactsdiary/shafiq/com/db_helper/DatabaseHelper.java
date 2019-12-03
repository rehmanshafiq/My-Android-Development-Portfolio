package contactsdiary.shafiq.com.db_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import contactsdiary.shafiq.com.models.Contact;

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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Insert a new contact into database
     * @param contact
     * @return
     */
    public boolean addContact(Contact contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, contact.getName());
        contentValues.put(COL_2, contact.getPhoneNumber());
        contentValues.put(COL_3, contact.getDevice());
        contentValues.put(COL_4, contact.getEmail());
        contentValues.put(COL_5, contact.getProfileImage());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Retrieve all contacts from database
     * @return
     */
    public Cursor getAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /**
     * Update a contact where id = @param 'id'
     * Replace the current contact with @param 'contact'
     * @param contact
     * @param id
     * @return
     */
    public boolean updateContact(Contact contact, int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, contact.getName());
        contentValues.put(COL_2, contact.getPhoneNumber());
        contentValues.put(COL_3, contact.getDevice());
        contentValues.put(COL_4, contact.getEmail());
        contentValues.put(COL_5, contact.getProfileImage());

        int update = db.update(TABLE_NAME, contentValues, COL_0 + " = ? ", new String[]{String.valueOf(id)});

        if (update != 1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Retrieve the contact unique id from the database using @param
     * @param contact
     * @return
     */
    public Cursor getContactID(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "SELECT * FROM " + TABLE_NAME +
                " WHERE " + COL_1 + " = '" + contact.getName() + "'" +
                " AND " + COL_2 + " = '" + contact.getPhoneNumber() + "'";

        return db.rawQuery(sql, null);
    }
}
