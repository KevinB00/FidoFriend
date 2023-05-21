package com.kevinbuenano.fidofriend.ui.home

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.kevinbuenano.fidofriend.databinding.FragmentRecordatoriosBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


/**
 * A simple [Fragment] subclass.
 * Use the [RecordatoriosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecordatoriosFragment : Fragment() {
    private lateinit var binding: FragmentRecordatoriosBinding
    private var fechaString: String? = null
    private var fecha: LocalDate? = null
    val notificationId = 0


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


        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            // Convierte los valores de fecha seleccionados en un objeto LocalDate
            fecha = LocalDate.of(year, month + 1, dayOfMonth)

            // Actualiza el texto del EditText con la fecha seleccionada
            val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            fechaString = fecha?.format(formato)
            binding.edTextDia.setText(fecha?.format(formato))
        }
        binding.edTextDia.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            // Crea un nuevo DatePickerDialog con la fecha actual
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                datePickerListener,
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
        }
        binding.btnAnyadirNoti.setOnClickListener {
            crearNotificacion()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ServiceCast")
    private fun crearNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "mi_canal_id"
            val channelName = "Canal de FidoFriend"
            val channelDescription = "Canal de recordatorios"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
            //Crea el canal de notificación (solo necesario para Android 8.0 en adelante)

            val builder = NotificationCompat.Builder(requireContext(), "mi_canal_id")
                .setSmallIcon(R.drawable.sym_def_app_icon)
                .setWhen(fecha!!.toEpochDay())
                .setContentTitle(binding.edTitulo.text.toString())
                .setContentText(binding.edTextDescrip.text.toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true)

            //Muestra la notificación
            with(NotificationManagerCompat.from(requireContext())) {
                notify(notificationId, builder.build())
            }
        }
            }