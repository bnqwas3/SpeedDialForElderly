package emergency.applications.speeddialforelderly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelperWhite extends SQLiteOpenHelper {
    public DBHelperWhite(Context context) {
        super(context, "myDBwhite", null, 1);
    }
    @Override

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(this.getClass().getName(), "--- onCreate database ---");
        sqLiteDatabase.execSQL("create table mytablewhite ("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "phone text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
