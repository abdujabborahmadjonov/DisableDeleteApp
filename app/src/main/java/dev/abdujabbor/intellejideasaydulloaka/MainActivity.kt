package dev.abdujabbor.intellejideasaydulloaka

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.NotificationManager.Policy.PRIORITY_CATEGORY_ALARMS
import android.app.NotificationManager.Policy.SUPPRESSED_EFFECT_SCREEN_OFF
import android.app.NotificationManager.Policy.SUPPRESSED_EFFECT_SCREEN_ON
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var componentName: ComponentName

    companion object {
        private const val REQUEST_CODE_ENABLE_ADMIN = 1
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        componentName = ComponentName(this, MyDeviceAdminReceiver::class.java)

        val btnEnableAdmin = findViewById<Button>(R.id.enable_admin_button)
        btnEnableAdmin.setOnClickListener {
            // Request Device Admin privileges
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN).apply {
                putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
                putExtra(
                    DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "Please grant Device Admin privileges to enable this feature"
                )
            }
            startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN)
        }

        val btnDisableRemoval = findViewById<Button>(R.id.disable_admin_button)
        btnDisableRemoval.setOnClickListener {
            // Check if app has been granted Device Admin privileges
            if (!devicePolicyManager.isAdminActive(componentName)) {
                Toast.makeText(
                    this,
                    "Please enable Device Admin privileges first",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            // Disable app removal
            val packageName = packageName
            devicePolicyManager.setUninstallBlocked(componentName, packageName, true)
            Toast.makeText(this, "App removal has been disabled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Device Admin privileges enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to enable Device Admin privileges", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
