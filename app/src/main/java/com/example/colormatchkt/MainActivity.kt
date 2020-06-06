package com.example.colormatchkt

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.layout_buttons.*
import java.util.*

@Suppress("NAME_SHADOWING")
class MainActivity : AppCompatActivity() {
    private val STATE_BLUE = 1
    private val STATE_RED = 2
    private val STATE_YELLOW = 3
    private val STATE_GREEN = 4

    var buttonState=STATE_BLUE
    var arrowState=STATE_BLUE
    var currentTime = 4000
    var startTime = 4000
    var currentPoints = 0
    private var position = 0
    var handler=Handler()
    var runnable: Runnable? = null
    var r=Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var iv_button = findViewById<ImageView>(R.id.iv_button)
        var iv_arrow = findViewById<ImageView>(R.id.iv_arrow)
        var tv_points = findViewById<TextView>(R.id.tv_points)
        var progressBar = findViewById<ProgressBar>(R.id.progressBar)

        progressBar.setMax(startTime)
        progressBar.setProgress(startTime)

        tv_points.setText("POINTS: $currentPoints")

        r = Random()
        arrowState = r.nextInt(4) + 1
        setArrowImage(arrowState)


        iv_button.setOnClickListener(View.OnClickListener { setButtonImage(setButtonPosition(buttonState)) })

        handler = Handler()
        runnable = Runnable {
            currentTime = currentTime - 100
            progressBar.setProgress(currentTime)
            if (currentTime > 0) handler.postDelayed(runnable, 100) else if (buttonState == arrowState) {
                currentPoints = currentPoints + 1
                tv_points.setText("POINTS:$currentPoints")
                startTime = startTime - 100
                if (startTime < 1000) startTime = 2000
                progressBar.setMax(startTime)
                currentTime = startTime
                progressBar.setProgress(currentTime)
                arrowState = r.nextInt(4) + 1
                setArrowImage(arrowState)
                handler.postDelayed(runnable, 100)
            } else {
                iv_button.setEnabled(false)
                Toast.makeText(this@MainActivity, "GAME OVER", Toast.LENGTH_SHORT).show()
            }
        }


        handler.postDelayed(runnable, 100)


    }

    private fun setArrowImage(state: Int) {
        when (state) {
       STATE_BLUE -> {
                arrowState = STATE_BLUE
                iv_arrow.setImageResource(R.drawable.blue)
            }
            STATE_RED -> {
                arrowState = STATE_RED
                iv_arrow.setImageResource(R.drawable.red)
            }
            STATE_GREEN -> {
                arrowState = STATE_GREEN
                iv_arrow.setImageResource(R.drawable.green)
            }
           STATE_YELLOW -> {
                arrowState = STATE_YELLOW
                iv_arrow.setImageResource(R.drawable.yellow)
            }
        }
    }

    private fun setRotation(image: ImageView, drawable: Int) {
       val rotateAnimation = RotateAnimation(0F,
           90F, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 100
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                image.setImageResource(drawable)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        image.startAnimation(rotateAnimation)
    }

    private fun setButtonPosition(position: Int): Int {
        var position = position
        this.position = position
        position = position + 1
        if (position == 5) {
            position = 1
        }
        return position
    }

    private fun setButtonImage(state: Int) {
        when (state) {
            STATE_BLUE -> {
                buttonState = STATE_BLUE
                setRotation(iv_button, R.drawable.bluetop)
            }
            STATE_RED -> {
                buttonState = STATE_RED
                setRotation(iv_button, R.drawable.redtop)
            }
            STATE_GREEN -> {
                buttonState = STATE_GREEN
                setRotation(iv_button, R.drawable.greentop)
            }
           STATE_YELLOW -> {
                buttonState = STATE_YELLOW
                setRotation(iv_button, R.drawable.yellowtop)
            }
        }
    }
}


