package com.kevinbuenano.fidofriend.ui.mascota

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kevinbuenano.fidofriend.database.appDatabase
import com.kevinbuenano.fidofriend.database.entities.MascotaEntity
import com.kevinbuenano.fidofriend.database.repository.mascotaRepository
import com.kevinbuenano.fidofriend.databinding.FragmentMostrarMejoraBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use the [MostrarMejoraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MostrarMejoraFragment : Fragment() {
    private lateinit var binding: FragmentMostrarMejoraBinding
    private val args: MostrarMejoraFragmentArgs by navArgs()
    lateinit var navController: NavController
    private var numComida = 0
    private var numPaseo = 0
    private var mejora = ""
    private var idMascota = 0
    private lateinit var db: appDatabase
    lateinit var repository: mascotaRepository
    lateinit var mascotaEntity: MascotaEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMostrarMejoraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val mascotaActivity = activity as MascotaActivity
        if (mascotaActivity != null) {
            db = mascotaActivity.getDB()
            idMascota = mascotaActivity.getMascotaId()
        }
        repository = mascotaRepository(db.mascotaDao())
        numComida = args.numComida
        numPaseo = args.numPaseo
        mejora = args.mejora
        cargar()
    }

    override fun onResume() {
        super.onResume()
        cargar()
    }

    private fun cargar() {
        viewLifecycleOwner.lifecycleScope.launch {
            mascotaEntity = withContext(Dispatchers.IO) {
                repository.getMascotaById(idMascota)
            }
            cargarRecomendaciones()
        }
    }

    private fun cargarRecomendaciones() {
        if (mascotaEntity.perroGato == 1) {
                    when(mascotaEntity.edad) {
                        in 0..4 -> {
                            if (mascotaEntity.porte == "Pequeño") {
                                binding.tViewMostrarNum.text = "90"
                            }else if(mascotaEntity.porte == "Mediano"){
                                binding.tViewMostrarNum.text = "200"
                            }else{
                                binding.tViewMostrarNum.text = "530"
                            }
                            when(mejora) {
                                "Actividad" ->{
                                    if (numPaseo >= 3){
                                        binding.tViewMostrarInfo.text = "Su mascota ya es activa, se recomienda que coma 90 gramos de comida. Siga sacándolo tres veces o más al día, como es un cachorro necesita una buena actividad, así luego no será tan imperactivo en su hogar."
                                    }else if(numPaseo < 3 && mascotaEntity.estado == "Obeso"){
                                        binding.tViewMostrarInfo.text = "Su mascota debe salir como mínimo 3 veces al día, tienen que comer los gramos de comida recomendados. Si es sedentaria debe controlar su comida. Una manera de sacarlo a pasear es llevarlo a parques de perros dónde pueda coger el gusto a salir."
                                    }else if(numPaseo < 3 && mascotaEntity.estado == "Cachorro"){
                                        binding.tViewMostrarInfo.text = "Su mascota aún es menor por lo tanto se debe centrar en educarlo más que en salir, puede salir para cansarlo y que haga sus necesidades."
                                    }else if(numPaseo < 3 && mascotaEntity.estado == "Enfermo"){
                                        binding.tViewMostrarInfo.text = "Debe llevarlo al veterinario para que pueda recomendar la mejor solución a su mascota, luego que se recupere puede centrarse en sacarlo a pasear."
                                    }else{
                                        binding.tViewMostrarInfo.text = "Debe sacar a pasear a su mascota mínimo 3 veces al día. "
                                    }

                                    if (mascotaEntity.actividad == "Muy activo"){
                                        binding.tViewTextActividad.text = "Ya es muy activa su mascota simplemente debe controlar su comida."
                                    }else if (mascotaEntity.actividad == "Moderado"){
                                        binding.tViewTextActividad.text = "Debe realizar un poco más de actividad, por ejemplo puede salir con su mascota y llevarla a correr con usted, como es un cachorro es necesario cansarlo."
                                    }else{
                                        binding.tViewTextActividad.text = "Para empezar puede sacarlo a pasear mínimo 3 veces al día, y luego poco a poco llevarlo a correr o paseos en parques de perros dónde pueda coger el gusto a salir."
                                    }
                                }
                                "Peso" -> {
                                    when(mascotaEntity.porte) {
                                        "Pequeño" -> {
                                            if (mascotaEntity.peso < 5 ) {
                                                binding.tViewMostrarInfo.text = "Su mascota tiene muy poco peso, deberá pasearlo las tres veces al día. Y como se muestra arriba comer esa cantidad. Puede ser necesario una revisión al veterinario dónde le puede recomendar alimentos para su mascota."
                                            }else{
                                                binding.tViewMostrarInfo.text = "Su mascota tiene un peso más normal, la recomendacion sería sacarlo a pasear las tres veces al día."
                                            }
                                        }
                                        "Mediano" -> {
                                            if (mascotaEntity.peso < 14 ) {
                                                binding.tViewMostrarInfo.text = "Su mascota tiene muy poco peso, deberá pasearlo las tres veces al día. Y como se muestra arriba comer esa cantidad. Puede ser necesario una revisión al veterinario dónde le puede recomendar alimentos para su mascota."

                                            }else{
                                                binding.tViewMostrarInfo.text = "Su mascota tiene un peso más normal, la recomendacion sería sacarlo a pasear las tres veces al día."
                                            }
                                        }
                                        "Grande" -> {
                                            if (mascotaEntity.peso < 34) {
                                                binding.tViewMostrarInfo.text = "Su mascota tiene muy poco peso, deberá pasearlo las tres veces al día. Y como se muestra arriba comer esa cantidad. Puede ser necesario una revisión al veterinario dónde le puede recomendar alimentos para su mascota."
                                            } else {
                                                binding.tViewMostrarInfo.text = "Su mascota tiene un peso más normal, la recomendacion sería sacarlo a pasear las tres veces al día."
                                            }
                                        }

                                    }

                                }
                                "Tranquilizar" -> {
                                    if (mascotaEntity.estado == "Cachorro") {
                                        binding.tViewMostrarInfo.text =
                                            "Si quiere que su mascota se tranquilice puede sacarlo a pasear las tres veces al día. Es normal que sea imperactivo ya que es un cachorro debe cansarlo y aprender a educarlo tambíen aún es un cachorro y tiene mucha energia en todo momento."
                                    }else if (mascotaEntity.estado == "Obeso") {
                                        binding.tViewMostrarInfo.text = "Al revés su mascota está obesa debe intentar ponerlo en actividad. Si el problema de obesidad persiste aún siguiendo las recomendaciones debe llevarlo al veterinario a que lo revisen."
                                    }else{
                                        binding.tViewMostrarInfo.text = "Debe sacarlo y cansarlo, pruebe llevarlo a un parque de perros o de senderismo actividades que hagan que se canse y al llegar al hogar llegue a descansar."
                                    }
                                    if (mascotaEntity.actividad == "Muy activo"){
                                        binding.tViewTextActividad.text = "Ya es muy activa su mascota simplemente debe controlar su comida."
                                    }else if (mascotaEntity.actividad == "Moderado"){
                                        binding.tViewTextActividad.text = "Debe realizar un poco más de actividad, por ejemplo puede salir con su mascota y llevarla a correr con usted, como es un cachorro es necesario cansarlo."
                                    }else{
                                        binding.tViewTextActividad.text = "Para empezar puede sacarlo a pasear mínimo 3 veces al día, y luego poco a poco llevarlo a correr o paseos en parques de perros dónde pueda coger el gusto a salir."
                                    }
                                }
                                "Paseo" -> {
                                    binding.tViewMostrarInfo.text = "Para unos buenos paseos puede ser interesante llevarlo a una playa de perros, o un gran parque de perros, seguro que al volver de pasear querrá dormir."
                                }
                                "Rutina" -> {
                                    binding.tViewMostrarInfo.text = "Por la mañana sería perfecto sacarlo a un paseo normal, sin prisa dónde pueda hacer sus necesidades. Por la tarde a un parque de perros dónde pueda jugar con otros perros. Y por la noche otro paseo para que haga sus necesidades. Como es un cachorro es necesario primero que se le eduque."
                                }
                            }
                        }
                        else -> {
                            when(mejora) {
                                "Actividad" ->{
                                    if (numPaseo >= 3){
                                        binding.tViewMostrarInfo.text = "Su mascota ya es activa, se recomienda que coma 90 gramos de comida. Siga sacándolo tres veces o más al día, se recomienda también que lo saque a correr para que mantenga una buena salud."
                                    }else{
                                        binding.tViewMostrarInfo.text = "Su mascota debe salir como mínimo 3 veces al día, tienen que comer 90 gramos de comida. Si es sedentaria debe controlar su comida. Una manera de sacarlo a pasear es llevarlo a parques de perros dónde pueda coger el gusto a salir."
                                    }
                                    if (mascotaEntity.edad > 14) {
                                        binding.tViewTextActividad.text = "${mascotaEntity.nombre} es mayor por lo tanto se debe preocupar únicamente de sacarlo 3 veces al día y controlar su peso. Aún así se recomienda que algún día a la semana salga a correr o realizar un paseo largo."
                                    }else if (mascotaEntity.actividad == "Muy activo"){
                                        binding.tViewTextActividad.text = "Ya es muy activa su mascota simplemente debe controlar su comida."
                                    }else if (mascotaEntity.actividad == "Moderado"){
                                        binding.tViewTextActividad.text = "Debe realizar un poco más de actividad, por ejemplo puede salir con su mascota y llevarla a correr con usted."
                                    }else{
                                        binding.tViewTextActividad.text = "Para empezar puede sacarlo a pasear mínimo 3 veces al día, y luego poco a poco llevarlo a correr o paseos en parques de perros dónde pueda coger el gusto a salir."
                                    }
                                }
                                "Peso" -> {
                                    when(mascotaEntity.porte) {
                                        "Pequeño" -> {
                                            if (mascotaEntity.peso < 5 ) {
                                                binding.tViewMostrarInfo.text = "Su mascota tiene muy poco peso, deberá pasearlo las tres veces al día. Y como se muestra arriba comer esa cantidad. Puede ser necesario una revisión al veterinario dónde le puede recomendar alimentos para su mascota."
                                            }else{
                                                binding.tViewMostrarInfo.text = "Su mascota tiene un peso más normal, la recomendacion sería sacarlo a pasear las tres veces al día."
                                            }
                                        }
                                        "Mediano" -> {
                                            if (mascotaEntity.peso < 14 ) {
                                                binding.tViewMostrarInfo.text = "Su mascota tiene muy poco peso, deberá pasearlo las tres veces al día. Y como se muestra arriba comer esa cantidad. Puede ser necesario una revisión al veterinario dónde le puede recomendar alimentos para su mascota."

                                            }else{
                                                binding.tViewMostrarInfo.text = "Su mascota tiene un peso más normal, la recomendacion sería sacarlo a pasear las tres veces al día."
                                            }
                                        }
                                        "Grande" -> {
                                            if (mascotaEntity.peso < 34) {
                                                binding.tViewMostrarInfo.text = "Su mascota tiene muy poco peso, deberá pasearlo las tres veces al día. Y como se muestra arriba comer esa cantidad. Puede ser necesario una revisión al veterinario dónde le puede recomendar alimentos para su mascota."
                                            } else {
                                                binding.tViewMostrarInfo.text = "Su mascota tiene un peso más normal, la recomendacion sería sacarlo a pasear las tres veces al día."
                                            }
                                        }

                                    }

                                }
                                "Tranquilizar" -> {
                                    binding.tViewMostrarInfo.text = "Si quiere que su mascota se tranquilice puede sacarlo a pasear las tres veces al día. LLevarlo a actividades o sitios como parques de perros o de senderismo pueden ser buenas opciones para cansarlo, importante educarlo para que no sea muy imperactivo y pueda controlar sus impulsos."

                                    if (mascotaEntity.actividad == "Muy activo"){
                                        binding.tViewTextActividad.text = "Ya es muy activa su mascota simplemente debe controlar su comida."
                                    }else if (mascotaEntity.actividad == "Moderado"){
                                        binding.tViewTextActividad.text = "Debe realizar un poco más de actividad, por ejemplo puede salir con su mascota y llevarla a correr con usted."
                                    }else{
                                        binding.tViewTextActividad.text = "Para empezar puede sacarlo a pasear mínimo 3 veces al día, y luego poco a poco llevarlo a correr o paseos en parques de perros dónde pueda coger el gusto a salir."
                                    }
                                }
                                "Paseo" -> {
                                    binding.tViewMostrarInfo.text = "Para unos buenos paseos puede ser interesante llevarlo a una playa de perros, o un gran parque de perros, seguro que al volver de pasear querrá dormir."
                                }
                                "Rutina" -> {
                                    binding.tViewMostrarInfo.text = "Por la mañana sería perfecto sacarlo a un paseo normal o si puede de senderismo también, dónde pueda hacer sus necesidades. Por la tarde a un parque de perros dónde pueda jugar con otros perros. Y por la noche otro paseo para que haga sus necesidades."
                                }
                            }
                        }

            }
        }else {

                    if (mascotaEntity.porte == "Pequeño") {
                        binding.tViewMostrarNum.text = "40"
                    }else if(mascotaEntity.porte == "Mediano"){
                        binding.tViewMostrarNum.text = "60"
                    }else{
                        binding.tViewMostrarNum.text = "80"
                    }
                    when(mejora){
                        "Actividad" ->{
                            if (mascotaEntity.estado == "Obeso"){
                                binding.tViewMostrarInfo.text = "Su gato debe realizar actividades que lo hagan correr y saltar. También debe seguir laa cantidades de comida que se recomiendan. Debe rellenarle el comedero dos veces con esas cantidades."
                            }else if(mascotaEntity.estado == "Enfermo"){
                                binding.tViewMostrarInfo.text = "Debe realizar alguna actividad que lo haga moverse tampoco le fatigues demasiado tiene que tener una buena recuperación, consulte al veterinario si tiene complicaciones para seguir la comida o para realizar actividades."
                            }
                            if (mascotaEntity.actividad == "Muy activo"){
                                binding.tViewTextActividad.text = "Ya es muy activa su mascota simplemente debe controlar su comida."
                            }else if (mascotaEntity.actividad == "Moderado"){
                                binding.tViewTextActividad.text = "Debe realizar un poco más de actividad, por ejemplo puede jugar con él para salir de su zona de confort."
                            }else{
                                binding.tViewTextActividad.text = "Asegurate de tener espacio en tu hogar, el gato debe ser libre de poder correr y saltar, que hayan lugares altos es buena opción les encanta. Luego proporcionarle juguetes para que corra y se divierta tambien."
                            }
                        }
                        "Peso" -> {
                            if (mascotaEntity.peso < 3.5){
                                binding.tViewMostrarInfo.text = "Tiene muy poco peso se le recomienda que lo alimente como se muestra arriba y a la vez se fortalezca corriendo y saltando, juegue con él, si aún así se siente decaído y sin energías debe llevarlo al veterinario. Su peso ideal debe ser mínimo de 4 kg."
                            }else{
                                binding.tViewMostrarInfo.text = "Su mascota tiene un peso normal, la recomendacion sería que tuviera una actividad moderada, para controlar su peso."
                            }

                        }
                        "Tranquilizar" -> {
                            if (mascotaEntity.estado == "Obeso"){
                                binding.tViewMostrarInfo.text = "Debe realizar actividad no tranquilizarlo si se le tranquiliza con obesidad puede llegar a ser sedentario."
                            }else if (mascotaEntity.actividad == "Sedentario" && mascotaEntity.edad > 14){
                                binding.tViewMostrarInfo.text = "${mascotaEntity.nombre} ya es mayor por lo tanto es normal que sea más sedentario, tiene menos energía que de joven. Lo que tiene que preocuparse es controlar su peso. Un rascador es buena opción. "
                            }else{
                                binding.tViewMostrarInfo.text = "Si su gato es imperactivo lo más probable es que quiera jugar con usted. También puede proporcionarle un rascador."
                            }
                        }
                        "Paseo" -> {
                            binding.tViewMostrarInfo.text = "Difícil es encontrar un sitio dónde llevar a un gato, pero si inciste en llevarlo a un sitio una opción seria llevarlo a una playa tranquila, importante llevarlo con el transportin y una correa de gatos."
                        }
                        "Rutina" -> {
                            binding.tViewMostrarInfo.text = "No es necesaria una rutina para un gato, pero lo que si puede hacer es establecer un tiempo cada día para jugar con su gato y que no esté todo el día durmiendo, por último rellenarle su comedero dos veces al día con lo que se indica."
                        }
                    }

                }
            }
        }

