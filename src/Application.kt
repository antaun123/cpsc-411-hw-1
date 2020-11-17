package org.csuf.cpsc411

import io.ktor.application.*
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.utils.io.*
import com.google.gson.Gson
import org.csuf.cpsc411.dao.Database
import org.csuf.cpsc411.dao.claim.Claim
import org.csuf.cpsc411.dao.claim.ClaimDao
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)
@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    routing {
        this.post("/ClaimService/add"){
            //When POST method used, recover the JSON format data and store into string
            val contType = call.request.contentType()
            val data = call.request.receiveChannel()
            val dataLength = data.availableForRead
            val output = ByteArray(dataLength)
            data.readAvailable(output)
            val str = String(output)
            //JSON Deserialization
            val gsonString = Gson().fromJson(str, Claim::class.java)
            //Create a claim object and store the data from Postman into title and data
            //as well as initializing the UUID and isSolved accordingly
            val cObj1 = Claim(UUID.randomUUID(), gsonString.title, gsonString.date, isSolved = false)
            //call addClaim function which inserts the object into the database
            val dao = ClaimDao().addClaim(cObj1)
            val dbObj = Database.getInstance()
            println("HTTP message is using POST method with /post ${contType},${str}")
            call.respondText("Post request was successful", status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }
        get("/ClaimService/getAll") {
            //call getAll function and
            val cList = ClaimDao().getAll()
            //JSON Serialization/Deserialization
            val respJsonStr = Gson().toJson(cList)
            call.respondText(respJsonStr, status= HttpStatusCode.OK, contentType = ContentType.Application.Json)
        }
    }
}

