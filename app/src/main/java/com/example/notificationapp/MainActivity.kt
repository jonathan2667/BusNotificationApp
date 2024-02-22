package com.example.notificationapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlin.random.Random
import android.graphics.Color
import android.widget.CheckBox
import android.content.res.ColorStateList
import androidx.core.widget.CompoundButtonCompat
import com.google.android.material.button.MaterialButton
import android.view.MotionEvent
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ClickableSpan
import android.text.method.LinkMovementMethod
import android.widget.TextView
import android.view.View
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.text.style.StyleSpan
import android.animation.ObjectAnimator
import android.animation.ArgbEvaluator
// or, if you need ValueAnimator
import android.animation.ValueAnimator
import android.graphics.BitmapFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.*
import android.util.Log
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 101
    private val MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS = 123 // Define a request code


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTermsAndConditionsClickableText()
        checkBoxColor()

        animateAllButtons()




        // Directly set the status bar color using a hex color code
        window.statusBarColor = Color.parseColor("#121212")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS)
            }
        }

        createNotificationChannel()

        sendNotificatoinButtonRevolut()
        sendNotificationButtonBCR()
        sendNotificationButtonBt()

    }

    fun animateAllButtons() {
        val button3 = findViewById<Button>(R.id.btn_button3)

        // Assuming you have defined the colors in your res/values/colors.xml
        animateButtonColorTransition(
            button = button3,
            context = this,
            defaultColorId = R.color.defaultButtonColor, // Replace with your actual color resource ID
            pressedColorId = R.color.pressedButtonColor // Replace with your actual color resource ID
        )

        val button2 = findViewById<Button>(R.id.btn_button2)

        // Assuming you have defined the colors in your res/values/colors.xml
        animateButtonColorTransition(
            button = button2,
            context = this,
            defaultColorId = R.color.defaultButtonColor, // Replace with your actual color resource ID
            pressedColorId = R.color.pressedButtonColor // Replace with your actual color resource ID
        )

        val button1 = findViewById<Button>(R.id.btn_button1)

        // Assuming you have defined the colors in your res/values/colors.xml
        animateButtonColorTransition(
            button = button1,
            context = this,
            defaultColorId = R.color.defaultButtonColor, // Replace with your actual color resource ID
            pressedColorId = R.color.pressedButtonColor // Replace with your actual color resource ID
        )
    }

    fun animateButtonColorTransition(button: Button, context: Context, defaultColorId: Int, pressedColorId: Int) {
        val defaultColor = ContextCompat.getColor(context, defaultColorId)
        val pressedColor = ContextCompat.getColor(context, pressedColorId)

        button.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    button.backgroundTintList = ColorStateList.valueOf(pressedColor)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    // Gradually return to default color
                    val colorFrom = pressedColor
                    val colorTo = defaultColor
                    val colorAnimation = ValueAnimator.ofObject(ArgbEvaluator(), colorFrom, colorTo)
                    colorAnimation.duration = 400 // milliseconds
                    colorAnimation.addUpdateListener { animator -> button.backgroundTintList = ColorStateList.valueOf(animator.animatedValue as Int) }
                    colorAnimation.start()
                }
            }
            false
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                sendNotificationRevolut()
            }
            else {
                // Permission was denied. Handle the failure to obtain permission.
            }
        }
    }


//    private fun setupTermsAndConditionsClickableText() {
//        val tvTermsConditions = findViewById<TextView>(R.id.tv_terms_conditions)
//        val fullText = "I agree to the terms and conditions."
//        val spannableString = SpannableString(fullText)
//        val clickableSpan = object : ClickableSpan() {
//            override fun onClick(widget: View) {
//                val dialogFragment = TermsAndConditionsDialogFragment()
//                dialogFragment.show(supportFragmentManager, "terms")
//            }
//        }
//
//        // Assuming "terms and conditions" is at the end
//        val startIndex = fullText.indexOf("terms and conditions")
//        val endIndex = startIndex + "terms and conditions".length
//        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//        tvTermsConditions.text = spannableString
//        tvTermsConditions.movementMethod = LinkMovementMethod.getInstance()
//        tvTermsConditions.highlightColor = Color.TRANSPARENT // Optional: Removes the default click highlight
//    }
    private fun setupTermsAndConditionsClickableText() {
        val tvTermsConditions = findViewById<TextView>(R.id.tv_terms_conditions)
        val fullText = "I agree to the terms and conditions."
        val spannableString = SpannableString(fullText)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val dialogFragment = TermsAndConditionsDialogFragment()
                dialogFragment.show(supportFragmentManager, "terms")
            }
        }

        // Assuming "terms and conditions" is at the end
        val startIndex = fullText.indexOf("terms and conditions")
        val endIndex = startIndex + "terms and conditions".length

        // Set ClickableSpan
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set ForegroundColorSpan for color
        val colorSpan = ForegroundColorSpan(Color.parseColor("#B8860B")) // Change color as per your requirement #DAA520 FFD700
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        // Set UnderlineSpan
        val boldSpan = StyleSpan(android.graphics.Typeface.BOLD)
        spannableString.setSpan(boldSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        tvTermsConditions.text = spannableString
        tvTermsConditions.movementMethod = LinkMovementMethod.getInstance()
        tvTermsConditions.highlightColor = Color.TRANSPARENT
    }

    private fun sendNotificatoinButtonRevolut() {
        val button: Button = findViewById(R.id.btn_button1)
        val checkBox: CheckBox = findViewById(R.id.chk_agree) // Assuming the ID of your CheckBox is chk_agree

        button.setOnClickListener {
            // Check if the checkbox is ticked
            if (checkBox.isChecked) {
                // Check permission and send notification if granted
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    sendNotificationRevolut()
                } else {
                    // Request permission if needed
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS)
                }
            } else {
                // Show a Toast message if the checkbox is not ticked
                Toast.makeText(this, "Please agree to the terms first.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun sendNotificationButtonBCR() {
        val button: Button = findViewById(R.id.btn_button2)
        val checkBox: CheckBox = findViewById(R.id.chk_agree) // Assuming the ID of your CheckBox is chk_agree

        button.setOnClickListener {
            // Check if the checkbox is ticked
            if (checkBox.isChecked) {
                // Check permission and send notification if granted
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    sendNotificationBCR()
                } else {
                    // Request permission if needed
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS)
                }
            } else {
                // Show a Toast message if the checkbox is not ticked
                Toast.makeText(this, "Please agree to the terms first.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendNotificationButtonBt() {
        val button: Button = findViewById(R.id.btn_button3) // Adjust the ID based on your layout
        val checkBox: CheckBox = findViewById(R.id.chk_agree) // Assuming the ID of your CheckBox is chk_agree

        button.setOnClickListener {
            // Check if the checkbox is ticked
            if (checkBox.isChecked) {
                // Permission check for POST_NOTIFICATIONS if targeting Android 13 (API 33) or above
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU || ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                    sendNotificationBt() // Replace with your actual notification sending function for BT
                } else {
                    // Request permission if needed
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), MY_PERMISSIONS_REQUEST_POST_NOTIFICATIONS)
                }
            } else {
                // Show a Toast message if the checkbox is not ticked
                Toast.makeText(this, "Please agree to the terms first.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun checkBoxColor() {
        val checkBox = findViewById<CheckBox>(R.id.chk_agree)

        // Define the colors for checked and unchecked states
        val checkedColor = Color.parseColor("#B8860B") // Yellow when checked
        val uncheckedColor = Color.parseColor("#757575") // Grey when unchecked (or any color you prefer)

        // Create a ColorStateList with states
        val colorStateList = ColorStateList(
            arrayOf(
                intArrayOf(android.R.attr.state_checked), // checked
                intArrayOf(-android.R.attr.state_checked) // unchecked
            ),
            intArrayOf(
                checkedColor, // Color for the checked state
                uncheckedColor // Color for the unchecked state
            )
        )

        CompoundButtonCompat.setButtonTintList(checkBox, colorStateList)

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title 1"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotificationRevolut() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

//        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.revolut)

        val randomAmount = Random.nextFloat() * (150 - 5) + 5
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("CTP Cluj-Napoca")
            .setContentText("\uD83D\uDE8E Paid RON3 at CTP Cluj-Napoca\n Spent today: RON${"%.2f".format(randomAmount)}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.revolut)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Automatically removes the notification when tapped

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    private fun sendNotificationBCR() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val randomAmount = Random.nextInt(10, 99)

        val currentDate = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val contentText = "\uD83D\uDCB8 Ai facut o plata de 3 RON la CTP CLUJ NAPOCA CLUJ NAPOC la $currentDate din contul RO${"%d".format(randomAmount)}XX"

        val bitmapicon = BitmapFactory.decodeResource(resources, R.drawable.george_right2)
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Info Plati")
            .setContentText(contentText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(contentText)) // Use BigTextStyle for longer text
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.george11)
            .setLargeIcon(bitmapicon) // Large icon
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }

    }

    private fun sendNotificationBt() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

//        val bitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.revolut)

        val randomAmount = Random.nextFloat() * (150 - 5) + 5
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("CTP Cluj-Napoca")
            .setContentText("\uD83D\uDE8E Paid RON3 at CTP Cluj-Napoca\n Spent today: RON${"%.2f".format(randomAmount)}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.revolut)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Automatically removes the notification when tapped

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }
}
