package com.example.w1830958_assignment2


import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ingredientActivity : AppCompatActivity() {
    //globalised declaration of widgets
    private lateinit var searchMeal: Button
    private lateinit var saveToDatabase: Button
    private lateinit var inputMeal: EditText
    private lateinit var tv3: TextView
    lateinit var  back:Button
    private var stb: StringBuilder = StringBuilder("")
    private var finalData: StringBuilder = StringBuilder("")

    //array list for data
    private var ingredientRetrieved = arrayListOf<String?>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)
//initialise declared
        inputMeal = findViewById(R.id.inputmeal2)
        searchMeal = findViewById(R.id.retireveBtn2)
        saveToDatabase = findViewById(R.id.saveBtn2)
         back=findViewById(R.id.backBtn2)
        back.setOnClickListener{
            val backToMain = Intent(this, MainActivity::class.java)
            startActivity(backToMain)
        }
        tv3 = findViewById(R.id.tv2)
        //listener object for search meal button.
        searchMeal.setOnClickListener {
if (inputMeal.text.toString()==""){
    tv3.text
}else{
            ingredientRetrieved.clear()
            finalData.clear()
            stb.clear()
            retrieveData()}
        }
        saveToDatabase.setOnClickListener {
            storeToDatabase(ingredientRetrieved.get(3))
            Toast.makeText(this, "Data has been saved to database", Toast.LENGTH_SHORT).show()
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
var count=0
                    for (i in 0 until jsonArray.length()) {
                        try {
                            val meal: JSONObject = jsonArray[i] as JSONObject

                            jsonConverter(meal)
                        } catch (e: Exception) {

                        }
                        try{
                        finalData.append("Meal Name:" + ingredientRetrieved[count+1].toString() + " \n\n")
                        finalData.append(
                            "Ingredients: " + ingredientRetrieved[count+4].toString() + "," + ingredientRetrieved[count+5].toString() + ","
                                    + ingredientRetrieved[count+6].toString() + "," + ingredientRetrieved[count+7].toString() + "," + ingredientRetrieved[count+8].toString() + ","
                                    + ingredientRetrieved[count+9].toString() + "," + ingredientRetrieved[count+10].toString() + "," + ingredientRetrieved[count+11].toString() + "," +
                                    ingredientRetrieved[count+12].toString() + "," + ingredientRetrieved[count+13].toString() + " \n\n" + "Measurement: "
                                    + ingredientRetrieved[count+14].toString() + "," + ingredientRetrieved[count+15].toString() + ","
                                    + ingredientRetrieved[count+16].toString() + "," + ingredientRetrieved[count+17].toString() + ","
                                    + ingredientRetrieved[count+18].toString() + "," + ingredientRetrieved[count+19].toString() + ","
                                    + ingredientRetrieved[count+20].toString() + "," + ingredientRetrieved[count+21].toString() + ","
                                    + ingredientRetrieved[count+22].toString() + "," + ingredientRetrieved[count+23].toString() + "\n\n" + "cooking Instructions: " + ingredientRetrieved[count+2].toString() + "\n\n\n\n"

                        )
count+=24
                    }catch (e:java.lang.Exception){

                    }}
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
//store function is calling the database using the room builder along with dao and run blocking and launch
    // i ahve used a while loop to keep rotating and it ends once count and size of the arraylist are not equal.
    //i will then store the data into the database to save the data for later access.
private fun storeToDatabase(get: String?) {
    val db2 = Room.databaseBuilder(
        this, AppDatabase::class.java,
        "myDatabase"
    ).build()
    val mealDao = db2.mealDao()
    runBlocking {
        launch {
            try {
                val jobs = (0 until ingredientRetrieved.size step 24).map {
                    async {
                        try {
                            val meal = Meals(
                                idMeal = ingredientRetrieved[it].toString().toInt(),
                                strMeal = ingredientRetrieved[it + 1].toString(),
                                strInstructions = ingredientRetrieved[it + 2].toString(),
                                strMealThumb = ingredientRetrieved[it + 3].toString(),
                                strIngredient1 = ingredientRetrieved[it + 4].toString(),
                                strIngredient2 = ingredientRetrieved[it + 5].toString(),
                                strIngredient3 = ingredientRetrieved[it + 6].toString(),
                                strIngredient4 = ingredientRetrieved[it + 7].toString(),
                                strIngredient5 = ingredientRetrieved[it + 8].toString(),
                                strIngredient6 = ingredientRetrieved[it + 9].toString(),
                                strIngredient7 = ingredientRetrieved[it + 10].toString(),
                                strIngredient8 = ingredientRetrieved[it + 11].toString(),
                                strIngredient9 = "",
                                strIngredient10 = "",
                                strMeasure1 = ingredientRetrieved[it + 14].toString(),
                                strMeasure2 = ingredientRetrieved[it + 15].toString(),
                                strMeasure3 = ingredientRetrieved[it + 16].toString(),
                                strMeasure4 = ingredientRetrieved[it + 17].toString(),
                                strMeasure5 = ingredientRetrieved[it + 18].toString(),
                                strMeasure6 = ingredientRetrieved[it + 19].toString(),
                                strMeasure7 = ingredientRetrieved[it + 20].toString(),
                                strMeasure8 = ingredientRetrieved[it + 21].toString(),
                                strMeasure9 = "",
                                strMeasure10 = ""
                            )
                            mealDao.insertMeals(meal)
                        } catch (e: NumberFormatException) {
                            println("Failed to parse integer value: ${e.message}")
                        }
                    }
                }
                jobs.awaitAll()
            } catch (e: Exception) {
                println("Error: ${e.message}")
            } finally {
                db2.close()
            }
        }
    }
}



    //save instance is design to store the all the data displayed to the user when rotating the device so it will not lose that ...................data
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
