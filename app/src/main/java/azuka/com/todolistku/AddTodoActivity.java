package azuka.com.todolistku;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTodoActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText judulTodo, deskripsiTodo;
    Button btnBack, btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        databaseHelper = new DatabaseHelper(this);
        judulTodo = (EditText)findViewById(R.id.addJudulTodo);
        deskripsiTodo = (EditText)findViewById(R.id.addDeskripsiTodo);
        btnBack = (Button)findViewById(R.id.addKembali);
        btnSimpan = (Button)findViewById(R.id.addSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.execSQL("INSERT INTO "+ DatabaseHelper.TABEL_TODO +" ("+ DatabaseHelper.KOLOM_JUDUL +", "+ DatabaseHelper.KOLOM_DESKRIPSI +") VALUES ('" +
                        judulTodo.getText().toString() + "', '" +
                        deskripsiTodo.getText().toString() + "')");
                Toast.makeText(getApplicationContext(), "Berhasil menyimpan data", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList();
                finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
