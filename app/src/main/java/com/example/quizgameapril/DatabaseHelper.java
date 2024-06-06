// DatabaseHelper.java
package com.example.quizgameapril;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "HighScoresDB";
    private static final String TABLE_HIGH_SCORES = "HighScores";
    private static final String KEY_ID = "id";
    private static final String KEY_PLAYER_NAME = "player_name";
    private static final String KEY_SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_HIGH_SCORES_TABLE = "CREATE TABLE " + TABLE_HIGH_SCORES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_PLAYER_NAME + " TEXT,"
                + KEY_SCORE + " INTEGER" + ")";
        db.execSQL(CREATE_HIGH_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGH_SCORES);
        onCreate(db);
    }

    public void addHighScore(String playerName, int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PLAYER_NAME, playerName);
        values.put(KEY_SCORE, score);
        db.insert(TABLE_HIGH_SCORES, null, values);
        db.close();
    }

    public List<HighScore> getAllHighScores() {
        List<HighScore> highScores = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HIGH_SCORES + " ORDER BY " + KEY_SCORE + " DESC LIMIT 10";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HighScore highScore = new HighScore(cursor.getString(1), cursor.getInt(2));
                highScores.add(highScore);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return highScores;
    }
}
