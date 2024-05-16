package com.example.w1830958_assignment2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    //Declare button globally
    private lateinit var addMeal: Button
    private lateinit var searchByIngredient: Button
    private lateinit var searchByMeal: Button
lateinit var searchByString2:Button
    private var MealsList = arrayListOf<String?>("1","Sweet and Sour Pork",
        "Preparation\r\n1. Crack the egg into a bowl. Separate the egg white and yolk.\r\n\r\nSweet and Sour Pork\r\n2. Slice the pork tenderloin into ips.\r\n\r\n3. Prepare the marinade using a pinch of salt, one teaspoon of starch, two teaspoons of light soy sauce, and an egg white.\r\n\r\n4. Marinade the pork ips for about 20 minutes.\r\n\r\n5. Put the remaining starch in a bowl. Add some water and vinegar to make a starchy sauce.\r\n\r\nSweet and Sour Pork\r\nCooking Inuctions\r\n1. Pour the cooking oil into a wok and heat to 190\u00b0C (375\u00b0F). Add the marinated pork ips and fry them until they turn brown. Remove the cooked pork from the wok and place on a plate.\r\n\r\n2. Leave some oil in the wok. Put the tomato sauce and white sugar into the wok, and heat until the oil and sauce are fully combined.\r\n\r\n3. Add some water to the wok and thoroughly heat the sweet and sour sauce before adding the pork ips to it.\r\n\r\n4. Pour in the starchy sauce. Stir-fry all the ingredients until the pork and sauce are thoroughly mixed together.\r\n\r\n5. Serve on a plate and add some coriander for decoration.",
        "https://www.themealdb.com/images/media/meals/1529442316.jpg",
        "Pork", "Egg", "Water", "Salt", "Sugar", "Soy Sauce", "Starch", "Tomato Puree", "Vinegar", "Coriander",
        "200g", "1", "Dash", "1/2 tsp", "1 tsp ", "10g", "10g", "30g", "10g", "Dash",
        "2", "Chicken Marengo",
        "Heat the oil in a large flameproof casserole dish and stir-fry the mushrooms until they start to soften. Add the chicken legs and cook briefly on each side to colour them a little.\r\nPour in the passata, crumble in the stock cube and stir in the olives. Season with black pepper \u2013 you shouldn\u2019t need salt. Cover and simmer for 40 mins until the chicken is tender. Sprinkle with parsley and serve with pasta and a salad, or mash and green veg, if you like.",
        "https://www.themealdb.com/images/media/meals/qpxvuq1511798906.jpg",

        "Olive Oil", "Mushrooms", "Chicken Legs", "Passata", "Chicken Stock Cube", "Black Olives", "Parsley", "", "", "",
        "1 tbs", "300g", "4", "500g", "1", "100g ", "Chopped", "", "", "",
        "3", "Beef Banh Mi Bowls with Sriracha Mayo, Carrot & Pickled Cucumber",
        "Add'l ingredients: mayonnaise, siracha\r\n\r\n1\r\n\r\nPlace rice in a fine-mesh sieve and rinse until water runs clear. Add to a small pot with 1 cup water (2 cups for 4 servings) and a pinch of salt. Bring to a boil, then cover and reduce heat to low. Cook until rice is tender, 15 minutes. Keep covered off heat for at least 10 minutes or until ready to serve.\r\n\r\n2\r\n\r\nMeanwhile, wash and dry all produce. Peel and finely chop garlic. Zest and quarter lime (for 4 servings, zest 1 lime and quarter both). Trim and halve cucumber lengthwise; thinly slice crosswise into half-moons. Halve, peel, and medium dice onion. Trim, peel, and grate carrot.\r\n\r\n3\r\n\r\nIn a medium bowl, combine cucumber, juice from half the lime, \u00bc tsp sugar (\u00bd tsp for 4 servings), and a pinch of salt. In a small bowl, combine mayonnaise, a pinch of garlic, a squeeze of lime juice, and as much sriracha as you\u2019d like. Season with salt and pepper.\r\n\r\n4\r\n\r\nHeat a drizzle of oil in a large pan over medium-high heat. Add onion and cook, stirring, until softened, 4-5 minutes. Add beef, remaining garlic, and 2 tsp sugar (4 tsp for 4 servings). Cook, breaking up meat into pieces, until beef is browned and cooked through, 4-5 minutes. Stir in soy sauce. Turn off heat; taste and season with salt and pepper.\r\n\r\n5\r\n\r\nFluff rice with a fork; stir in lime zest and 1 TBSP butter. Divide rice between bowls. Arrange beef, grated carrot, and pickled cucumber on top. Top with a squeeze of lime juice. Drizzle with sriracha mayo.",
        "https://www.themealdb.com/images/media/meals/z0ageb1583189517.jpg",
        "Rice", "Onion", "Lime", "Garlic Clove", "Cucumber", "Carrots", "Ground Beef", "Soy Sauce", "","",
        "White", "1", "1", "3", "1", "3 oz ", "1 lb", "2 oz ", "","",
        "4", "Leblebi Soup",
        "Heat the oil in a large pot. Add the onion and cook until translucent.\r\nDrain the soaked chickpeas and add them to the pot together with the vegetable stock. Bring to the boil, then reduce the heat and cover. Simmer for 30 minutes.\r\nIn the meantime toast the cumin in a small ungreased frying pan, then grind them in a mortar. Add the garlic and salt and pound to a fine paste.\r\nAdd the paste and the harissa to the soup and simmer until the chickpeas are tender, about 30 minutes.\r\nSeason to taste with salt, pepper and lemon juice and serve hot.",
        "https://www.themealdb.com/images/media/meals/x2fw9e1560460636.jpg",
        "Olive Oil", "Onion", "Chickpeas", "Vegetable Stock", "Cumin", "Garlic", "Salt", "Harissa Spice", "Pepper", "Lime", "2 tbs", "1 medium finely diced", "250g", "1.5L", "1 tsp ", "5 cloves", "1/2 tsp", "1 tsp ", "Pinch", "1/2 ",
        )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Initialise Buttons
        addMeal = findViewById(R.id.addMeal)
        searchByIngredient = findViewById(R.id.searchIngrident)
        searchByMeal = findViewById(R.id.searchMeal)
        searchByString2=findViewById(R.id.searchByString)
        //add to database button with function inside it
        addMeal.setOnClickListener {
            addToDatabase()
        }
//intent creation to go to next activity
        searchByMeal.setOnClickListener {
            val intergovernmental = Intent(this, MealRetireveActivity::class.java)
            startActivity(intergovernmental)
        }
        searchByIngredient.setOnClickListener {
            val ingredientRetriever = Intent(this, ingredientActivity::class.java)
            startActivity(ingredientRetriever)
        }
        searchByString2.setOnClickListener{
            val SearchByStringActivity = Intent(this, searchByString::class.java)
            startActivity(SearchByStringActivity)
        }

    }

    //this function is for accessing the database and checking if the database is empty or not and if its empty
    //it will full it default data to the database and if it is not empty it will add data to the database.
    //i use the classes such as meal object along with the room function and DAO to declare the database and use it to check the data and populate the database.
    //the toast will be used to display the outcome to the user.
    private fun addToDatabase() {
        var count=0

        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java,
            "myDatabase"
        ).build()
        runBlocking {
            launch {


                val dao = db.mealDao()
                val meals: List<Meals> = dao.getAll()
                if (meals.isEmpty()) {
                    while (MealsList.size > count) {
                        val meal = Meals(
                            idMeal = MealsList[count].toString().toInt(),
                            strMeal = MealsList[count+1].toString(),
                            strInstructions = MealsList[count+2].toString(),
                            strMealThumb = MealsList[count+3].toString(),
                            strIngredient1 = MealsList[count+4].toString(),
                            strIngredient2 = MealsList[count+5].toString(),
                            strIngredient3 = MealsList[count+6].toString(),
                            strIngredient4 = MealsList[count+7].toString(),
                            strIngredient5 = MealsList[count+8].toString(),
                            strIngredient6 = MealsList[count+9].toString(),
                            strIngredient7 = MealsList[count+10].toString(),
                            strIngredient8 = MealsList[count+11].toString(),
                            strIngredient9 = MealsList[count+12].toString(),
                            strIngredient10 = MealsList[count+13].toString(),
                            strMeasure1 = MealsList[count+14].toString(),
                            strMeasure2 = MealsList[count+15].toString(),
                            strMeasure3 = MealsList[count+16].toString(),
                            strMeasure4 = MealsList[count+17].toString(),
                            strMeasure5 = MealsList[count+18].toString(),
                            strMeasure6 = MealsList[count+19].toString(),
                            strMeasure7 = MealsList[count+20].toString(),
                            strMeasure8 = MealsList[count+21].toString(),
                            strMeasure9 = MealsList[count+22].toString(),
                            strMeasure10 = MealsList[count+23].toString()
                        )
                        count+=24
                        dao.insertMeals(meal)

                }
                    Toast.makeText(applicationContext, "data has been added", Toast.LENGTH_SHORT)
                        .show()}
                    else {
                    Toast.makeText(
                        applicationContext,
                        "Data already exist in database",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }
}
