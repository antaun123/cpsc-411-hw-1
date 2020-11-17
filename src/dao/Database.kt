package org.csuf.cpsc411.dao
import com.almworks.sqlite4java.SQLiteConnection
import java.io.File
class Database constructor (var dbName : String = "") {
    init {
        //Create the database, create tables and keeps the db connection
        dbName = "C:\\Users\\Anthony Aun\\Desktop\\hw1DB2.db"
        val dbConn = SQLiteConnection(File(dbName))
        //open the database
        dbConn.open()
        val sqlStat = "create table if not exists claim (id text, title text, date text, isSolved text)"
        //execute the SQL statement
        dbConn.exec(sqlStat)
    }
    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }
    //Single object path
    //single object pattern
    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database? {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj
        }
    }
}
