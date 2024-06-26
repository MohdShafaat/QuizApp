package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.quizapp.databinding.ActivityQuizBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class QuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuizBinding
    private lateinit var list: ArrayList<QuestionModel>
    private var count:Int = 0
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = ArrayList()
        Firebase.firestore.collection("quiz")
            .get().addOnSuccessListener {
                duct ->
                list.clear()
                for(i in duct.documents) {
                    val questionModel = i.toObject(QuestionModel::class.java)
                    list.add(questionModel!!)
                }
                binding.question.text = list[0].question
                binding.option1.text = list[0].option1
                binding.option2.text = list[0].option2
                binding.option3.text = list[0].option3
                binding.option4.text = list[0].option4
            }

        binding.option1.setOnClickListener{
            nextData(binding.option1.text.toString())
        }
        binding.option2.setOnClickListener{
            nextData(binding.option2.text.toString())
        }
        binding.option3.setOnClickListener{
            nextData(binding.option3.text.toString())
        }
        binding.option4.setOnClickListener{
            nextData(binding.option4.text.toString())
        }

    }

    private fun nextData(i: String) {
        if(count < list.size) {
            if (list[count].ans == i) {
                score++
            }
        }
        count++
        if(count >= list.size) {
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra("SCORE", score)
            startActivity(intent)
            finish()
        } else {
            binding.question.text = list[count].question
            binding.option1.text = list[count].option1
            binding.option2.text = list[count].option2
            binding.option3.text = list[count].option3
            binding.option4.text = list[count].option4
        }
    }
}