package com.kevinbuenano.fidofriend.ui.home

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.kevinbuenano.fidofriend.databinding.FragmentRecordatoriosBinding
import com.kevinbuenano.fidofriend.ui.home.AlarmNotificacion.Companion.NOTIFICATION_ID
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


/**
 * A simple [Fragment] subclass.
 * Use the [RecordatoriosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordatoriosFragment : Fragment() {
    private lateinit var binding: FragmentRecordatoriosBinding
    private var fechaString: String = ""
    private var fecha: LocalDate? = null
    private var dias: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecordatoriosBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        crearCanal()
        binding.edTextDia.setOnClickListener {
            val fechaActual = LocalDate.now()

            val datePickerDialog = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                fecha = LocalDate.of(year, month + 1, dayOfMonth)
                fechaString = fecha?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
            }, fechaActual.year, fechaActual.monthValue - 1, fechaActual.dayOfMonth)

            datePickerDialog.datePicker.minDate = fechaActual.toEpochDay() * 1000
            datePickerDialog.show()
            binding.edTextDia.setText(fechaString)
        }

        binding.btnAnyadirNoti.setOnClickListener {
            scheduleNotification()
            guardarNotificacion()
        }
    }

    private fun guardarNotificacion() {

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleNotification() {
        try {
            val intent =
                Intent(requireContext().applicationContext, AlarmNotificacion::class.java).apply {
                    putExtra("title", binding.edTitulo.text.toString())
                    putExtra("description", binding.edTextDescrip.text.toString())
                }
            val pendingIntent = PendingIntent.getBroadcast(
                requireContext().applicationContext,
                NOTIFICATION_ID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val notoficationFecha = fecha!!.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli()
            val alarmManager =
                requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, notoficationFecha, pendingIntent)
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Elija una fecha válida y rellene todos los campos", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast")
             private fun crearCanal() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                          val channel = NotificationChannel(
                              MY_CHANNEL_ID,
                              "Canal de FidoFriend",
                              NotificationManager.IMPORTANCE_DEFAULT
                          ).apply {
                              description = "Canal de recordatorios"
                          }
                         val notificationManager: NotificationManager =
                             requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                         notificationManager.createNotificationChannel(channel)
                     }
        }

    companion object {
        const val MY_CHANNEL_ID = "mi_canal_id"
    }
}