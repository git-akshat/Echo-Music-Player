package com.cruds.echo.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.cruds.echo.R

class SplashActivity : AppCompatActivity() {

    var requiredPermissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.PROCESS_OUTGOING_CALLS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (!hasPermission(this@SplashActivity, *requiredPermissions)) {
            ActivityCompat.requestPermissions(this@SplashActivity, requiredPermissions, 131)

        } else {
            Handler().postDelayed({
                val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(mainIntent)
                this.finish()
            }, 1000)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            131 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED
                    && grantResults[2] == PackageManager.PERMISSION_GRANTED
                    && grantResults[3] == PackageManager.PERMISSION_GRANTED
                    && grantResults[4] == PackageManager.PERMISSION_GRANTED
                ) {
                    Handler().postDelayed({
                        val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(mainIntent)
                        this.finish()
                    }, 1000)
                } else {
                    Toast.makeText(this@SplashActivity, "Please grant all permissions", Toast.LENGTH_SHORT).show()
                    this.finish()
                }
                return
            }
            else -> {
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                this.finish()
            }
        }
    }

    fun hasPermission(context: Context, vararg permissions: String): Boolean {
        var hasAllPermissions = true
        for (permission in permissions) {
            val res = context.checkCallingOrSelfPermission(permission)
            if (res != PackageManager.PERMISSION_GRANTED) {
                hasAllPermissions = false;
            }
        }
        return hasAllPermissions;
    }
}
