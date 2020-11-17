package org.csuf.cpsc411.dao.claim

import org.csuf.cpsc411.dao.Dao
import org.csuf.cpsc411.dao.Database
import java.util.*

class ClaimDao : Dao() {
    fun addClaim(cObj : Claim) {
        //1. Get db Connection
        val conn = Database.getInstance()?.getDbConnection()
        //2. prepare the sql statement
        sqlStmt = "insert into claim (id, title, date, isSolved) values ('${cObj.id}', '${cObj.title}', '${cObj.date}', '${cObj.isSolved}')"
        //3. submit the sql statement
        conn?.exec(sqlStmt)
    }
    fun getAll() : List<Claim> {
        //1. Get db Connection
        val conn = Database.getInstance()?.getDbConnection()
        //2. prepare the sql statement
        sqlStmt = "select id, title, date, isSolved from claim"
        //3. submit the sql statement
        var claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)
        //convert to Kotlin object format
        while (st?.step()!!) {     //traverse record step
            val id = UUID.fromString(st.columnString(0))
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnString(3)
            claimList.add(Claim(id, title, date, isSolved = false))
        }
        return claimList
    }
}