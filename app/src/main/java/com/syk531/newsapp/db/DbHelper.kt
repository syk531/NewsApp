package com.syk531.newsapp.db

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper : SQLiteOpenHelper  {
    constructor(context: Context, version: Int) : super(context, "news.db", null, version) {
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE News(company TEXT, cnt INT, link TEXT)");
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS Person");
        onCreate(db);
    }

    // Person Table 데이터 입력
    fun insert(company: String, cnt: Int, link: String) {
        val db = writableDatabase
        db.execSQL("INSERT INTO News VALUES('$company', $cnt, '$link')")
        db.close()
    }

    // Person Table 조회
    fun getResult(): String? {
        // 읽기가 가능하게 DB 열기
        val db = readableDatabase
        var result = ""

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        val cursor: Cursor = db.rawQuery("SELECT * FROM News", null)
        while (cursor.moveToNext()) {
            result += (" TEXT : " + cursor.getString(0)
                .toString() + ", CNT : "
                    + cursor.getInt(1)
                .toString() + ", LINK : "
                    + cursor.getString(2)
                .toString() + "\n")
        }
        return result
    }
}