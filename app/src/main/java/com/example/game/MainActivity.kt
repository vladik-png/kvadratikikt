package com.example.game

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val grid = findViewById<GridLayout>(R.id.myGrid)
        for (i in 0..14) {
            val square = grid.getChildAt(i)
            val startColor = randomColor()
            paintSquare(square, startColor)
            square.setOnClickListener {
                changeColor(square)
            }
        }
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

        for (i in 0..14) {
            val square = grid.getChildAt(i)

            if (square.tag != targetColor) {
                return
            }
        }
        dialogRestart()
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
}
