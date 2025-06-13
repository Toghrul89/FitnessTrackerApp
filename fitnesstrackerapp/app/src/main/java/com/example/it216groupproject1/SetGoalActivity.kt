class SetGoalActivity : AppCompatActivity() {
    private lateinit var goalEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goal)

        goalEditText = findViewById(R.id.goalEditText)
        saveButton = findViewById(R.id.saveButton)

        // Get the user's name from MainActivity
        val userName = intent.getStringExtra("USER_NAME") ?: "User"
        findViewById<TextView>(R.id.userNameText).text = "Hello, $userName!"

        saveButton.setOnClickListener {
            saveGoal()
        }
    }

    private fun saveGoal() {
        val goal = goalEditText.text.toString()
        if (goal.isNotEmpty()) {
            // Save to SharedPreferences
            val sharedPref = getSharedPreferences("FitnessPrefs", MODE_PRIVATE)
            sharedPref.edit().putString("goal", goal).apply()
            Toast.makeText(this, "Goal saved!", Toast.LENGTH_SHORT).show()
            finish() // Return to MainActivity
        } else {
            Toast.makeText(this, "Please enter a goal", Toast.LENGTH_SHORT).show()
        }
    }
}