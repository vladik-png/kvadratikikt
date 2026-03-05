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
    var currentLevel = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        startLevel()
    }

    fun startLevel() {
        val tvLevel = findViewById<TextView>(R.id.tvLevel)
        tvLevel.text = "level $currentLevel"
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
        var isWin = false
        if (currentLevel == 1) isWin = checkLevel1()
        if (currentLevel == 2) isWin = checkLevel2()
        if (currentLevel == 3) isWin = checkLevel3()
        if (currentLevel == 4) isWin = checkLevel4()
        if (isWin) {
            dialogWin()
        }
    }

    fun checkLevel4(): Boolean {
        val grid = findViewById<GridLayout>(R.id.myGrid)
        val targetColor = grid.getChildAt(0).tag as Int
        for (i in 0..14) {
            val currentColor = grid.getChildAt(i).tag as Int
            if (currentColor != targetColor) {
                return false
            }
        }
        return true
    }

    fun checkLevel2(): Boolean {
        val grid = findViewById<GridLayout>(R.id.myGrid)
        for (col in 0..2) {
            val topColor = grid.getChildAt(col).tag as Int
            for (row in 0..4) {
                val index = row * 3 + col
                val currentColor = grid.getChildAt(index).tag as Int
                if (currentColor != topColor) {
                    return false
                }
            }
        }
        val col0 = grid.getChildAt(0).tag as Int
        val col1 = grid.getChildAt(1).tag as Int
        val col2 = grid.getChildAt(2).tag as Int
        if (col0 == col1 || col1 == col2 || col0 == col2) {
            return false
        }
        return true
    }

    fun checkLevel3(): Boolean {
        val grid = findViewById<GridLayout>(R.id.myGrid)

        for (row in 0..4) {
            var redCount = 0

            for (col in 0..2) {
                val index = row * 3 + col
                val currentColor = grid.getChildAt(index).tag as Int

                if (currentColor == 0) {
                    redCount++
                }
            }
            if (redCount != 1) {
                return false
            }
        }
        return true
    }

    fun checkLevel1(): Boolean {
        val grid = findViewById<GridLayout>(R.id.myGrid)
        var yellowCount = 0
        for (row in 0..4)
        {
            for (col in 0..2) {
                val index = row * 3 + col
                val myColor = grid.getChildAt(index).tag as Int
                if (myColor == 1) {
                    yellowCount++

                    if (col < 2) {
                        val rightNeighborIndex = row * 3 + (col + 1)
                        val rightColor = grid.getChildAt(rightNeighborIndex).tag as Int
                        if (rightColor == 1) {
                            return false
                        }
                    }

                    if (row < 4) {
                        val bottomNeighborIndex = (row + 1) * 3 + col
                        val bottomColor = grid.getChildAt(bottomNeighborIndex).tag as Int
                        if (bottomColor == 1) {
                            return false
                        }
                    }
                }
            }
        }
        if (yellowCount == 0) {
            return false
        }
        return true
    }

//    fun checkLevel4(): Boolean {
//        val grid = findViewById<GridLayout>(R.id.myGrid)
//        for (row in 0..4) {
//            for (col in 0..2) {
//                val index = row * 3 + col
//                val myColor = grid.getChildAt(index).tag as Int
//                if ((row == 0 || row == 2 || row == 4) && (col == 0 || col == 2)) {
//                    if (myColor != 1) {
//                        return false
//                    }
//                }
//                else {
//                        if (myColor == 1) {
//                            return false
//                        }
//                    }
//                }
//            }
//        return true
//    }

    fun dialogWin() {
        if (currentLevel < 4) {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Level Completed!")
            alert.setMessage("Next Level ?")
            alert.setPositiveButton("Next") { _, _ ->
                currentLevel++
                startLevel()
            }
            alert.setNegativeButton("Exit") { _, _ ->
                finish()
            }

            alert.show()
        } else {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("End!")
            alert.setMessage("Completed all level, restart ?")

            alert.setPositiveButton("Restart") { _, _ ->
                currentLevel = 1
                startLevel()
            }

            alert.setNegativeButton("End") { _, _ ->
                finish()
            }

            alert.show()
        }
    }
}
