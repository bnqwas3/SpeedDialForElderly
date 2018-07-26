package emergency.applications.speeddialforelderly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }
    @Override

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(this.getClass().getName(), "--- onCreate database ---");
        // создаем таблицу с полями
        sqLiteDatabase.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "phone text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
