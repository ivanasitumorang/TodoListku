package azuka.com.todolistku;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateTodoActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper databaseHelper;
    EditText judulTodo, deskripsiTodo;
    Button btnKembali, btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_todo);

        databaseHelper = new DatabaseHelper(this);
        judulTodo = (EditText)findViewById(R.id.judulTodo);
        deskripsiTodo = (EditText)findViewById(R.id.deskripsiTodo);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM t_todo WHERE title_todo = '" +
                getIntent().getStringExtra("judulTodo") + "'", null);
        cursor.moveToFirst();

        if (cursor.getCount()>0){
            cursor.moveToPosition(0);
            judulTodo.setText(cursor.getString(1));
            deskripsiTodo.setText(cursor.getString(2));
        }

        btnSimpan = (Button)findViewById(R.id.updateSimpan);
        btnKembali = (Button)findViewById(R.id.updateKembali);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.execSQL("UPDATE t_todo SET title_todo='" +
                        judulTodo.getText().toString() + "', deskripsi_todo='" +
                        deskripsiTodo.getText().toString() + "' WHERE id='" +
                        cursor.getString(0) + "'");
                Toast.makeText(getApplicationContext(), "Berhasil mengubah data", Toast.LENGTH_LONG);
                MainActivity.ma.RefreshList();
                finish();
            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
