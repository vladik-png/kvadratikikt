package com.example.game

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    ///var currectLevel = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       /* fun startlevel(){
            val tvLevel = findViewById<TextView>(R.id.tvLevel)
            tvLevel.text = "Level $currectLevel"
        }*/
        val grid = findViewById<GridLayout>(R.id.myGrid)
        for (i in 0..14) {
            val square = grid.getChildAt(i)
            val startColor = randomColor()
            paintSquare(square, startColor)
            square.setOnClickListener {
                changeColor(square)
            }
        }
        //startlevel()
    }


    fun randomColor(): Int {
        return (0..2).random()
    }

    fun changeColor(square: View) {
        val oldColor = square.tag as Int
        var newColor = oldColor + 1
        if (newColor == 3) newColor = 0
        paintSquare(square, newColor)
        checkWin()
    }

    fun paintSquare(v: View, color: Int) {
        if (color == 0) v.setBackgroundColor(Color.RED)
        if (color == 1) v.setBackgroundColor(Color.YELLOW)
        if (color == 2) v.setBackgroundColor(Color.GREEN)
        v.tag = color
    }

    fun checkWin() {
        val grid = findViewById<GridLayout>(R.id.myGrid)
        val firstSquare = grid.getChildAt(0)
        val targetColor = firstSquare.tag as Int
        var yellow = 0
        var red = 0
        var green = 0
        var result = 0

        for (i in 0..14) {
            val square = grid.getChildAt(i)
            when (square.tag) {
                0 -> ++red
                1 -> ++yellow
                2 -> ++green
            }
        }

        if (yellow >= 7) {
            ++result
        }
        if (red >= 7)
        {
            ++result
        }
        if (green >= 7)
        {
            ++result
        }

        if (result >= 2)
        {
            dialogRestart()
        }

    }

    fun dialogRestart() {
        AlertDialog.Builder(this)
            .setTitle("Peremoga!")
            .setMessage("Почати знову?")
            .setPositiveButton("Так") { _, _ ->
                recreate()
            }
            .show()
    }

   /* fun checkWin() {
        val = isWin = false
        if (currentLevel == 1) isWin = checkLevel1()
        if (currentLevel == 2) isWin = checkLevel2()
        if (currentLevel == 3) isWin = checkLevel3()
        dialogRestart()
        if (isWin) {

        }
    } */
}
