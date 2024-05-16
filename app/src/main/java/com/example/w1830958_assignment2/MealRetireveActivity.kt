package com.example.w1830958_assignment2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.room.Room
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class MealRetireveActivity : AppCompatActivity() {
    //declaring global variable
    lateinit var searchBtn: Button
    lateinit var inputText: EditText
    lateinit var printText: TextView
    lateinit var stb2: StringBuilder
    lateinit var printImage: ImageView
    lateinit var backbtn:Button
var text=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_retireve)
        //initialising declared variable
        searchBtn = findViewById(R.id.retireveBtn)
        inputText = findViewById(R.id.inputMeal)
        printText = findViewById(R.id.tv)
        printImage = findViewById(R.id.imageMeal)
         backbtn=findViewById(R.id.backBtn)
        backbtn.setOnClickListener{
            val backToMain2 = Intent(this, MainActivity::class.java)
            startActivity(backToMain2)
        }
        stb2 = StringBuilder("")
        searchBtn.setOnClickListener {

            retrieveFromDatabase()

        }
    }
//retrieve from database is a method that contain the calling of database along with run blocking and launcher to access the database
    //it will also contain the if statement that will compare against the database items to see if the meal is in the database and if not it will not retrieve anything
   //i have also used the picasso function that will allow image to be displayed from the link without the use of url and network connection as it will speed
    //the retireval process and ensure no error occurs during the retireval process
    private fun retrieveFromDatabase() {
        var found = false
        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "myDatabase"
        ).build()
        runBlocking {
            launch {

                withContext(Dispatchers.IO) {
                    val dao = db.mealDao()
                    val meal: List<Meals> = dao.getAll()


                    if (inputText.text.toString() != "") {
                        for (m in meal) {
                            if (m.strMeal.toString().contains(inputText.text.toString(), ignoreCase = true)||
                                m.strIngredient1.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient2.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient3.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient4.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient5.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient6.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient7.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient8.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient9.toString().contains(inputText.text.toString(), ignoreCase = true)
                                ||m.strIngredient10.toString().contains(inputText.text.toString(), ignoreCase = true)) {
                                found = true
                                text=m.strMealThumb.toString().trim()



                                stb2.append(
                                    "Meal ID: " + m.idMeal + "MealName: " + m.strMeal + "\n\n" + "Ingredient: " + m.strIngredient1 + ", " + m.strIngredient2 + ", " + m.strIngredient3 + ", " + m.strIngredient4 + ", " + m.strIngredient5 + ", " + m.strIngredient6 + ", " + m.strIngredient7 + ", " + m.strIngredient8 + ", " + m.strIngredient9 + ", " + m.strIngredient10 + "\n\n" + "Measurement: " + m.strMeasure1 + ", " + m.strMeasure2 + ", " + m.strMeasure3 + ", " + m.strMeasure4 + ", " + m.strMeasure5 + ", " + m.strMeasure6 + ", " + m.strMeasure7 + ", " + m.strMeasure8 + ", " + m.strMeasure9 + ", " + m.strMeasure10 + "\n\n" + "Cooking Instruction: " + m.strInstructions + "\n\n\n" + "\n"
                                )


                            }
                        }
                    }

                }
            }
        }
if (!found){
    printImage.setImageResource(0)

    stb2.append("Meal not found in the database.")
}else {
    Picasso.get().load(text).into(printImage)
}
        printText?.text = stb2.toString()
        stb2.clear()


    }
    //save instance is design to store the all the data displayed to the user when rotating the device so it will not lose that data
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
        try{
        val image1 = printImage.drawable.toBitmap(
            printImage.width,
            printImage.height,
            Bitmap.Config.RGB_565
        )
        outState.putString("text",printText.text.toString())
        outState.putParcelable("playerDice1", image1)
    }catch (e:Exception){
        print(e.stackTrace)
    }}
//restore instance is a function that will ensure that the stored data before rotating are called again and displayed once the device finish rotating
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        printImage.setImageBitmap(savedInstanceState.getParcelable("playerDice1"))
        printText.setText(savedInstanceState.getString("text"))
    }

}
