package com.example.w1830958_assignment2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.room.Room
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class searchByString : AppCompatActivity() {
    //globalised declaration of widgets


    private lateinit var searchMeal: Button
    private lateinit var saveToDatabase: Button
    private lateinit var inputMeal: EditText
    private lateinit var tv3: TextView
    private var finalData: StringBuilder = StringBuilder("")
    private var ingredientRetrieved = arrayListOf<String?>()

    lateinit var back: Button
    private var stb: StringBuilder = StringBuilder("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_string)
        //initialise declared
        inputMeal = findViewById(R.id.inputmeal3)
        searchMeal = findViewById(R.id.retireveBtn3)
        back = findViewById(R.id.backBtn3)
        back.setOnClickListener {
            val backToMain = Intent(this, MainActivity::class.java)
            startActivity(backToMain)
        }
        tv3 = findViewById(R.id.text)
        //listener object for search meal button.
        searchMeal.setOnClickListener {
            if (inputMeal.text.toString()==""){
                tv3.text="please Enter Ingredient Name:"
            }else{
            ingredientRetrieved.clear()
            finalData.clear()
            stb.clear()
            retrieveData()}
        }


    }
    //retireve data is a retireve the data from the link then convert the json data into a string that will be stored into arraylist and saved later.
    //i will also append data into the string builder to display it to the user to what he is looking for to confirm then save to database later.


    private fun retrieveData() {
        //string builders for retrieved data from link and read from the website
        //second string builder is for the final data after stored in first string builder after finishing retrieve it

        //edit text extracted data.
        val ingredientSearch = inputMeal.text.toString()
        //run block and launch for data retrieval
        runBlocking {
            launch {
// run the code of the coroutine in a new thread
                withContext(Dispatchers.IO) {
                    //link to data with user input at the end and trimmed unneed part at the end.
                    val linkString =
                        "https://www.themealdb.com/api/json/v1/1/search.php?s=" + ingredientSearch.trim()
                    //link initialisation
                    val url = URL(linkString)
                    //access data and retireve data
                    val con = url.openConnection() as HttpURLConnection
                    val bf: BufferedReader = BufferedReader(InputStreamReader(con.inputStream))
//read the data and store it to first string build called stb
                    var line: String? = bf.readLine()
                    while (line != null) {
                        stb.append(line + "\n")
                        line = bf.readLine()
                    }
                    //if the data retireve is null exit and print the message to user
                    if (stb.contains("{\"meals\":null}")) {
                        finalData.append("meal does not exist")
                        return@withContext
                    }
                    val json = JSONObject(stb.toString())
                    stb.clear()
                    val jsonArray = json.getJSONArray("meals")
                    var count = 0
                    for (i in 0 until jsonArray.length()) {
                        try {
                            val meal: JSONObject = jsonArray[i] as JSONObject
                            jsonConverter(meal)
                        } catch (e: Exception) {

                        }
                        try {
                            finalData.append("Meal Name: " + ingredientRetrieved[count + 1].toString() + " \n\n")

                            count += 24
                        } catch (e: java.lang.Exception) {

                        }
                    }
                }

                tv3?.setText(finalData.toString())
            }

        }

    }


    //this method will convert the json object to string that is nullable and save it to arraylist for later access
    private fun jsonConverter(meal: JSONObject) {
        ingredientRetrieved.add(meal["idMeal"] as String?)
        ingredientRetrieved.add(meal["strMeal"] as String?)
        ingredientRetrieved.add(meal["strInstructions"] as String?)
        ingredientRetrieved.add(meal["strMealThumb"] as String?)
        ingredientRetrieved.add(meal["strIngredient1"] as String?)
        ingredientRetrieved.add(meal["strIngredient2"] as String?)
        ingredientRetrieved.add(meal["strIngredient3"] as String?)
        ingredientRetrieved.add(meal["strIngredient4"] as String?)
        ingredientRetrieved.add(meal["strIngredient5"] as String?)
        ingredientRetrieved.add(meal["strIngredient6"] as String?)
        ingredientRetrieved.add(meal["strIngredient7"] as String?)
        ingredientRetrieved.add(meal["strIngredient8"] as String?)
        ingredientRetrieved.add(meal["strIngredient9"] as String?)
        ingredientRetrieved.add(meal["strIngredient10"] as String?)
        ingredientRetrieved.add(meal["strMeasure1"] as String?)
        ingredientRetrieved.add(meal["strMeasure2"] as String?)
        ingredientRetrieved.add(meal["strMeasure3"] as String?)
        ingredientRetrieved.add(meal["strMeasure4"] as String?)
        ingredientRetrieved.add(meal["strMeasure5"] as String?)
        ingredientRetrieved.add(meal["strMeasure6"] as String?)
        ingredientRetrieved.add(meal["strMeasure7"] as String?)
        ingredientRetrieved.add(meal["strMeasure8"] as String?)
        ingredientRetrieved.add(meal["strMeasure9"] as String?)
        ingredientRetrieved.add(meal["strMeasure10"] as String)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
        outState.putString("text",tv3.text.toString())
    }
    //restore instance is a function that will ensure that the stored data before rotating are called again and displayed once the device finish rotating
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        tv3.setText(savedInstanceState.getString("text"))
    }
}