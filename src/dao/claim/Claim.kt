package org.csuf.cpsc411.dao.claim
import java.util.*
import java.util.UUID.randomUUID

//create a class and declare 4 variables id, title, date, and isSolved
data class Claim(var id: UUID?, var title:String?, var date:String?, var isSolved:Boolean = false)
