package dev.abdujabbor.intellejideasaydulloaka

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyDeviceAdminReceiver : DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        // Code to be executed when device admin is enabled
        Toast.makeText(context, "Device admin enabled", Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        // Code to be executed when device admin is disabled
        Toast.makeText(context, "Device admin disabled", Toast.LENGTH_SHORT).show()
    }
}