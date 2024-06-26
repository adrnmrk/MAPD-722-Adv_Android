package com.example.animationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.SeekBar
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.example.animationdemo.R

class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    Animation.AnimationListener {
    // Declare textStatus and imageView as class-level properties
    private lateinit var textStatus: TextView
    private lateinit var imageView: ImageView

    var seekSpeedProgress: Int = 0

    private lateinit var animFadeIn: Animation
    private lateinit var animFadeOut: Animation
    private lateinit var animFadeInOut: Animation

    private lateinit var animZoomIn: Animation
    private lateinit var animZoomOut: Animation

    private lateinit var animLeftRight: Animation
    private lateinit var animRightLeft: Animation
    private lateinit var animTopBottom: Animation

    private lateinit var animBounce: Animation
    private lateinit var animFlash: Animation

    private lateinit var animRotateLeft: Animation
    private lateinit var animRotateRight: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Declare variables for buttons
        val btnFadeIn: View by lazy { findViewById<View>(R.id.btnFadeIn) }
        val btnFadeOut: View by lazy { findViewById<View>(R.id.btnFadeOut) }
        val btnFadeInOut: View by lazy { findViewById<View>(R.id.btnFadeInOut) }
        val btnZoomIn: View by lazy { findViewById<View>(R.id.btnZoomIn) }
        val btnZoomOut: View by lazy { findViewById<View>(R.id.btnZoomOut) }
        val btnLeftRight: View by lazy { findViewById<View>(R.id.btnLeftRight) }
        val btnRightLeft: View by lazy { findViewById<View>(R.id.btnRightLeft) }
        val btnTopBottom: View by lazy { findViewById<View>(R.id.btnTopBottom) }
        val btnBounce: View by lazy { findViewById<View>(R.id.btnBounce) }
        val btnFlash: View by lazy { findViewById<View>(R.id.btnFlash) }
        val btnRotateLeft: View by lazy { findViewById<View>(R.id.btnRotateLeft) }
        val btnRotateRight: View by lazy { findViewById<View>(R.id.btnRotateRight) }
        val seekBarSpeed: SeekBar by lazy { findViewById<SeekBar>(R.id.seekBarSpeed) }
        val textSeekerSpeed: TextView by lazy { findViewById<TextView>(R.id.textSeekerSpeed) }
        // Initialize textStatus and imageView using findViewById
        textStatus = findViewById(R.id.textStatus)
        imageView = findViewById(R.id.imageView)


        loadAnimations()

        btnFadeIn.setOnClickListener(this)
        btnFadeOut.setOnClickListener(this)
        btnFadeInOut.setOnClickListener(this)
        btnZoomIn.setOnClickListener(this)
        btnZoomOut.setOnClickListener(this)
        btnLeftRight.setOnClickListener(this)
        btnRightLeft.setOnClickListener(this)
        btnTopBottom.setOnClickListener(this)
        btnBounce.setOnClickListener(this)
        btnFlash.setOnClickListener(this)
        btnRotateLeft.setOnClickListener(this)
        btnRotateRight.setOnClickListener(this)

        seekBarSpeed.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar, value: Int, fromUser: Boolean
                ) {
                    seekSpeedProgress = value
                    var max = seekBarSpeed.max;
                    textSeekerSpeed.text =
                        "$seekSpeedProgress of $max"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

    }

    private fun loadAnimations() {

        animFadeIn = AnimationUtils.loadAnimation(
            this, R.anim.fade_in
        )
        animFadeIn.setAnimationListener(this)
        animFadeOut = AnimationUtils.loadAnimation(
            this, R.anim.fade_out
        )
        animFadeInOut = AnimationUtils.loadAnimation(
            this, R.anim.fade_in_out
        )

        animZoomIn = AnimationUtils.loadAnimation(
            this, R.anim.zoom_in
        )
        animZoomOut = AnimationUtils.loadAnimation(
            this, R.anim.zoom_out
        )

        animLeftRight = AnimationUtils.loadAnimation(
            this, R.anim.left_right
        )
        animRightLeft = AnimationUtils.loadAnimation(
            this, R.anim.right_left
        )
        animTopBottom = AnimationUtils.loadAnimation(
            this, R.anim.top_bot
        )

        animBounce = AnimationUtils.loadAnimation(
            this, R.anim.bounce
        )
        animFlash = AnimationUtils.loadAnimation(
            this, R.anim.flash
        )

        animRotateLeft = AnimationUtils.loadAnimation(
            this, R.anim.rotate_left
        )
        animRotateRight = AnimationUtils.loadAnimation(
            this, R.anim.rotate_right
        )
    }


    override fun onAnimationEnd(animation: Animation) {
        textStatus.text = "STOPPED"
    }

    override fun onAnimationRepeat(animation: Animation) {
    }

    override fun onAnimationStart(animation: Animation) {
        textStatus.text = "RUNNING"
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnFadeIn -> {
                animFadeIn.duration = seekSpeedProgress.toLong()
                animFadeIn.setAnimationListener(this)
                imageView.startAnimation(animFadeIn)
            }

            R.id.btnFadeOut -> {
                animFadeOut.duration = seekSpeedProgress.toLong()
                animFadeOut.setAnimationListener(this)
                imageView.startAnimation(animFadeOut)
            }

            R.id.btnFadeInOut -> {

                animFadeInOut.duration = seekSpeedProgress.toLong()
                animFadeInOut.setAnimationListener(this)
                imageView.startAnimation(animFadeInOut)
            }

            R.id.btnZoomIn -> {
                animZoomIn.duration = seekSpeedProgress.toLong()
                animZoomIn.setAnimationListener(this)
                imageView.startAnimation(animZoomIn)
            }

            R.id.btnZoomOut -> {
                animZoomOut.duration = seekSpeedProgress.toLong()
                animZoomOut.setAnimationListener(this)
                imageView.startAnimation(animZoomOut)
            }


            R.id.btnLeftRight -> {
                animLeftRight.duration = seekSpeedProgress.toLong()
                animLeftRight.setAnimationListener(this)
                imageView.startAnimation(animLeftRight)
            }

            R.id.btnRightLeft -> {
                animRightLeft.duration = seekSpeedProgress.toLong()
                animRightLeft.setAnimationListener(this)
                imageView.startAnimation(animRightLeft)
            }

            R.id.btnTopBottom -> {
                animTopBottom.duration = seekSpeedProgress.toLong()
                animTopBottom.setAnimationListener(this)
                imageView.startAnimation(animTopBottom)
            }

            R.id.btnBounce -> {
                /*
              Divide seekSpeedProgress by 10 because with
              the seekbar having a max value of 5000 it
              will make the animations range between
              almost instant and half a second
              5000 /  10 = 500 milliseconds
              */
                animBounce.duration = (seekSpeedProgress / 10).toLong()
                animBounce.setAnimationListener(this)
                imageView.startAnimation(animBounce)
            }

            R.id.btnFlash -> {
                animFlash.duration = (seekSpeedProgress / 10).toLong()
                animFlash.setAnimationListener(this)
                imageView.startAnimation(animFlash)
            }

            R.id.btnRotateLeft -> {
                animRotateLeft.duration = seekSpeedProgress.toLong()
                animRotateLeft.setAnimationListener(this)
                imageView.startAnimation(animRotateLeft)
            }

            R.id.btnRotateRight -> {
                animRotateRight.duration = seekSpeedProgress.toLong()
                animRotateRight.setAnimationListener(this)
                imageView.startAnimation(animRotateRight)
            }
        }

    }
}
