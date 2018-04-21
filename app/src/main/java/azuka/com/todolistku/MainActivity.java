package azuka.com.todolistku;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    String[] daftarTodo;
    ListView listView;
    protected Cursor cursor;
    DatabaseHelper databaseHelper;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ma = this;
        databaseHelper = new DatabaseHelper(this);
        RefreshList();
    }

    public void addTodo(View view) {
        Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
        startActivity(intent);
    }

    public void RefreshList(){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABEL_TODO , null);
        daftarTodo = new String[cursor.getCount()];
        cursor.moveToFirst();
        for (int c=0; c<cursor.getCount(); c++){
            cursor.moveToPosition(c);
            daftarTodo[c] = cursor.getString(1).toString();
        }

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarTodo));
        listView.setSelected(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String selection = daftarTodo[position];
                final CharSequence[] dialogItem = {"Lihat Detail", "Edit Todo", "Hapus Todo"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilih Aksi");
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0 :
                                Intent lihatDetail = new Intent(getApplicationContext(), LihatDetailActivity.class);
                                lihatDetail.putExtra("judulTodo", selection);
                                startActivity(lihatDetail);
                                break;
                            case 1 :
                                Intent editTodo = new Intent(getApplicationContext(), UpdateTodoActivity.class);
                                editTodo.putExtra("judulTodo", selection);
                                startActivity(editTodo);
                                break;
                            case 2 :
                                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                                db.execSQL("DELETE FROM t_todo WHERE title_todo = '" + selection + "'");
                                RefreshList();
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter)listView.getAdapter()).notifyDataSetInvalidated();
    }
}
