package com.innovidio.androidbootstrap.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.innovidio.androidbootstrap.db.converters.EnumTypeConverters;
import com.innovidio.androidbootstrap.db.dao.AlarmDao;
import com.innovidio.androidbootstrap.db.dao.CarDao;
import com.innovidio.androidbootstrap.db.dao.FormDao;
import com.innovidio.androidbootstrap.db.dao.FuelDao;
import com.innovidio.androidbootstrap.db.dao.MaintenanceDao;
import com.innovidio.androidbootstrap.db.dao.TripDao;
import com.innovidio.androidbootstrap.entity.Alarm;
import com.innovidio.androidbootstrap.entity.Car;
import com.innovidio.androidbootstrap.entity.Form;
import com.innovidio.androidbootstrap.entity.FuelUp;
import com.innovidio.androidbootstrap.entity.Maintenance;
import com.innovidio.androidbootstrap.entity.Trip;
import com.innovidio.androidbootstrap.entity.models.Feed;
import com.innovidio.androidbootstrap.db.converters.DateConverter;
import com.innovidio.androidbootstrap.db.converters.IntegerListConverter;
import com.innovidio.androidbootstrap.db.converters.StringListConverter;
import com.innovidio.androidbootstrap.db.dao.FeedDao;

import java.io.IOException;
import java.io.InputStream;

@Database(
        entities = {
                //@TODO add your Entity classes here
                Feed.class,
                Alarm.class,
                Car.class,
                Form.class,
                FuelUp.class,
                Maintenance.class,
                Trip.class

        },
        version = 1,
        exportSchema = false
)
@TypeConverters(
        {
                //TODO add you typeConverters here
                IntegerListConverter.class, DateConverter.class, StringListConverter.class, EnumTypeConverters.class
        }
)

public abstract class AppDatabase extends RoomDatabase {

    //TODO define you DAO against Entity here
    public abstract FeedDao getFeedDao();

    public abstract AlarmDao getAlarmDao();
//
    public abstract CarDao getCarDao();

    public abstract FormDao getFormDao();

    public abstract FuelDao getFuelDao();

    public abstract MaintenanceDao getMaintenanceDao();

    public abstract TripDao getTripDao();


    private static final String TAG = "AppDatabase";
    private static AppDatabase instance = null;
    private static SupportSQLiteDatabase sQLiteDatabase = null;
    private static Context context = null;

    public static synchronized AppDatabase getInstance(Context _context) {
        context = _context;
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, "database_name")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addMigrations()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            sQLiteDatabase = db;
                            populateData();
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                        }
                    }).build();
        }
        return instance;
    }

    public static void populateData() {

        // for processing direct query
        /*
        StringBuilder sb = new StringBuilder();
        String data = null;
        sb.append("INSERT INTO Mushroom VALUES ");
        data = loadStringDataFromAsset("mushrooms/mushInsert_en");
        sb.append(data);
        sQLiteDatabase.execSQL(sb.toString());
        */

// for processing JSON datat
       /* String json = loadStringDataFromAsset("asset_filename");
        Type type = Types.newParameterizedType(List.class, Breed.class, BreedDetail.class, PuppyName.class);
        Moshi moshi = new Moshi.Builder().build();

        JsonAdapter<List<Breed>> jsonAdapter = moshi.adapter(type);
        List<Breed> breeds = jsonAdapter.fromJson(json);
        return breeds;

        */

    }

    private static String loadStringDataFromAsset(String fileName) {
        String jsonStr = null;
        try {

            InputStream inputStream = context.getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            jsonStr = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "loadStringDataFromAsset: ", e.getCause());
            return null;
        }
        return jsonStr;
    }

}