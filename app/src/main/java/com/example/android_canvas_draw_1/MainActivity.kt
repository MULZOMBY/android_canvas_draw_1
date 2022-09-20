package com.example.android_canvas_draw_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var canvasView:CanvasView
    private lateinit var layout:LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        canvasView= CanvasView(this)
        layout=findViewById(R.id.layout)
        layout.addView(canvasView)
    }
}