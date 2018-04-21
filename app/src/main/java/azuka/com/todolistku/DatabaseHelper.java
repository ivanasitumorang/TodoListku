package azuka.com.todolistku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "todo.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABEL_TODO = "t_todo";
    public static final String KOLOM_ID = "id";
    public static final String KOLOM_JUDUL = "judul_todo";
    public static final String KOLOM_DESKRIPSI = "deskripsi_todo";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABEL_TODO + "(" + KOLOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KOLOM_JUDUL + " TEXT NULL, " + KOLOM_DESKRIPSI + " TEXT NULL);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Nothing
    }
}
