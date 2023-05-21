package com.kevinbuenano.fidofriend.ui.home

import android.R
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.kevinbuenano.fidofriend.ui.LoginActivity

class AlarmNotificacion: BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID = 1
    }

    override fun onReceive(context: Context, intent: Intent?) {
        val titulo = intent?.getStringExtra("title").toString()
        val mensaje = intent?.getStringExtra("description").toString()
        if (titulo != null && mensaje != null) {
            crearNotificacion(context, titulo, mensaje)
        }
    }

    private fun crearNotificacion(context: Context, titulo: String, mensaje: String) {

        val intent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val notification = NotificationCompat.Builder(context,
            RecordatoriosFragment.MY_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.sym_def_app_icon)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

}