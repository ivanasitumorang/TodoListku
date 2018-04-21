package azuka.com.todolistku;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LihatDetailActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper databaseHelper;
    TextView judulTodo, deskripsiTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_detail);

        databaseHelper = new DatabaseHelper(this);
        judulTodo = (TextView)findViewById(R.id.judulTodo);
        deskripsiTodo = (TextView)findViewById(R.id.deskripsiTodo);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABEL_TODO + " WHERE " + DatabaseHelper.KOLOM_JUDUL + " = '" +
                getIntent().getStringExtra("judulTodo") + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount()>0) {
            cursor.moveToPosition(0);
            judulTodo.setText(cursor.getString(1));
            deskripsiTodo.setText(cursor.getString(2));
        }
    }

    public void kembali(View view) {
        finish();
    }
}
