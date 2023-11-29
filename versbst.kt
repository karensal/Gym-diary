import java.util.Scanner

// Clase Usuario 
data class Usuario(
    var nombre: String,
    var correo: String,
    var contraseña: String,
    var edad: Int,
    var genero: String,
    var masaCorporal: Double,
    var altura: Double,
    var objetivos: String = ""
) {
    fun editarPerfil() {
        println("Editar perfil:")
        print("Nueva contraseña (Deja en blanco para mantener la actual): ")
        val nuevaContraseña = readLine()
        if (nuevaContraseña?.isNotBlank() == true) {
            contraseña = nuevaContraseña
        }

        print("Nueva edad: ")
        val nuevaEdad = readLine()?.toIntOrNull()
        if (nuevaEdad != null) {
            edad = nuevaEdad
        }

        print("Nuevo peso (kg): ")
        val nuevoPeso = readLine()?.toDoubleOrNull()
        if (nuevoPeso != null) {
            masaCorporal = nuevoPeso
        }

        // Recalcula el IMC
        val imc = calcularIMC()
        println("Tu nuevo IMC es: $imc")
    }

    fun verPerfil() {
        println("Perfil de usuario:")
        println("Nombre: $nombre")
        println("Correo: $correo")
        println("Edad: $edad")
        println("Género: $genero")
        println("Masa Corporal: $masaCorporal kg")
        println("Altura: $altura m")
        val imc = calcularIMC()
        println("IMC: $imc")
    }

    fun calcularIMC(): Double {
        return masaCorporal / (altura * altura)
    }

    fun establecerObjetivos() {
        println("Establecer Objetivos:")
        print("Objetivos (por ejemplo: perder peso, ganar masa muscular, mantenerse en forma, etc.): ")
        val nuevosObjetivos = readLine()
        if (nuevosObjetivos?.isNotBlank() == true) {
            objetivos = nuevosObjetivos
            println("Objetivos actualizados exitosamente.")
        } else {
            println("No se han realizado cambios en los objetivos.")
        }
    }
}

// Clase Ejercicio 
class Ejercicio(
    var nombreEjercicio: String,
    var series: Int,
    var repeticiones: Int,
    var descanso: Int,
    var musculoTrabajado: String,
    var tiempoRealizacion: Int,
    var validador: Boolean = false
) {
    fun validar() {
        if (nombreEjercicio.isNotEmpty() && series > 0 && repeticiones > 0 && descanso >= 0 && musculoTrabajado.isNotEmpty() && tiempoRealizacion > 0) {
            validador = true
        }
    }

    fun validarEjercicioRealizado() {
        println("Validar ejercicio realizado:")
        print("Número de series realizadas: ")
        val seriesRealizadas = readLine()?.toIntOrNull() ?: 0
        print("Número de repeticiones realizadas: ")
        val repeticionesRealizadas = readLine()?.toIntOrNull() ?: 0

        if (seriesRealizadas == series && repeticionesRealizadas == repeticiones) {
            println("Has completado todas las series y repeticiones del ejercicio correctamente. ¡Buen trabajo!")
        } else {
            println("No has completado todas las series y repeticiones del ejercicio. Sigue trabajando en ello.")
        }
    }

    // uso el constructor para crear instancias
}

// Clase Rutina 
class Rutina(
    var nombreRutina: String,
    var duracionTotal: Int,
    var listaActividades: MutableList<Ejercicio> = mutableListOf()
) {
    fun validarRutina(): Boolean {
        for (ejercicio in listaActividades) {
            if (!ejercicio.validador) {
                return false
            }
        }
        return true
    }

    fun agregarActividadFisica(ejercicio: Ejercicio) {
        listaActividades.add(ejercicio)
        duracionTotal += ejercicio.series * (ejercicio.repeticiones + ejercicio.descanso)
    }

    fun validarRutinaPorInput() {
        println("Validación de la rutina '$nombreRutina':")
        for (ejercicio in listaActividades) {
            print("¿Has completado el ejercicio '${ejercicio.nombreEjercicio}'? (Sí/No): ")
            val respuesta = readLine()?.trim()?.toLowerCase()
            if (respuesta == "si") {
                ejercicio.validador = true
            }
        }

        val rutinaValidada = validarRutina()
        if (rutinaValidada) {
            println("¡La rutina ha sido validada con éxito!")
        } else {
            println("La rutina no ha sido validada. Asegúrate de completar todos los ejercicios propuestos.")
        }
    }

    fun calcularDuracionTotal(): Int {
        var duracionTotal = 0
        for (ejercicio in listaActividades) {
            // Calcula la duración de cada ejercicio y agrégala a la duración total
            val duracionEjercicio = ejercicio.series * (ejercicio.repeticiones + ejercicio.descanso + ejercicio.tiempoRealizacion)
            duracionTotal += duracionEjercicio
        }
        return duracionTotal
    }

    // En lugar de ingresarRutina, puedes usar el constructor para crear instancias
}

// Clase RegistroDeCargas con sus atributos y funciones
class RegistroDeCargas(
    var fecha: String,
    var peso: Double,
    var anotaciones: String,
    var ejercicio: Ejercicio
) {
    fun ingresarRegistroDeCargas() {
        println("Ingresar un registro de cargas:")
        print("Fecha (año-mes-día): ")
        val fecha = readLine() ?: ""
        print("Peso (kg): ")
        val peso = readLine()?.toDoubleOrNull() ?: 0.0
        print("Anotaciones: ")
        val anotaciones = readLine() ?: ""

        val nuevoRegistro = RegistroDeCargas(fecha, peso, anotaciones, ejercicio)
    }
}

class Historial {
    private val historialEjercicios: MutableList<Ejercicio> = mutableListOf()
    private val historialRutinas: MutableList<Rutina> = mutableListOf()
    private val historialRegistrosCargas: MutableList<RegistroDeCargas> = mutableListOf()

    fun agregarEjercicioAlHistorial(ejercicio: Ejercicio) {
        historialEjercicios.add(ejercicio)
    }

    fun agregarRutinaAlHistorial(rutina: Rutina) {
        historialRutinas.add(rutina)
    }

    fun agregarRegistroCargasAlHistorial(registroCargas: RegistroDeCargas) {
        historialRegistrosCargas.add(registroCargas)
    }

    fun mostrarHistoria() {

        if (historialEjercicios.isNotEmpty()) {
            println("Historial de Ejercicios:")
            for (ejercicio in historialEjercicios) {
                println("- Nombre: ${ejercicio.nombreEjercicio}, Series: ${ejercicio.series}, Repeticiones: ${ejercicio.repeticiones}")
            }
        }

        if (historialRutinas.isNotEmpty()) {
            println("Historial de Rutinas:")
            for (rutina in historialRutinas) {
                println("- Nombre de Rutina: ${rutina.nombreRutina}, Duración Total: ${rutina.duracionTotal}")
                for (ejercicio in rutina.listaActividades) {
                    println("  - Ejercicio: ${ejercicio.nombreEjercicio}, Series: ${ejercicio.series}, Repeticiones: ${ejercicio.repeticiones}")
                }
            }
        }

        if (historialRegistrosCargas.isNotEmpty()) {
            println("Historial de Registros de Cargas:")
            for (registroCargas in historialRegistrosCargas) {
                println("- Fecha: ${registroCargas.fecha}, Peso: ${registroCargas.peso} kg")
                println("  - Anotaciones: ${registroCargas.anotaciones}")
                println("  - Ejercicio: ${registroCargas.ejercicio.nombreEjercicio}")
            }
        }
    }
}

class NodoArbolBinario(
    var usuario: Usuario,
    var izquierda: NodoArbolBinario? = null,
    var derecha: NodoArbolBinario? = null,
    var altura: Int = 1
)

class ArbolBinarioBusqueda {
    var raiz: NodoArbolBinario? = null

    fun insertar(usuario: Usuario) {
        raiz = insertarRecursivo(raiz, usuario)
    }

    private fun insertarRecursivo(nodo: NodoArbolBinario?, usuario: Usuario): NodoArbolBinario {
        if (nodo == null) {
            return NodoArbolBinario(usuario)
        }

        if (usuario.correo < nodo.usuario.correo) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, usuario)
        } else if (usuario.correo > nodo.usuario.correo) {
            nodo.derecha = insertarRecursivo(nodo.derecha, usuario)
        }

        // Actualizar la altura del nodo actual
        nodo.altura = 1 + maxOf(obtenerAltura(nodo.izquierda), obtenerAltura(nodo.derecha))

        // Realizar el balanceo
        return balancear(nodo)
    }

    fun buscar(correo: String, contraseña: String): Usuario? {
        return buscarRecursivo(raiz, correo, contraseña)?.usuario
    }
    
    private fun buscarRecursivo(nodo: NodoArbolBinario?, correo: String, contraseña: String): NodoArbolBinario? {
        if (nodo == null || (correo == nodo.usuario.correo && contraseña == nodo.usuario.contraseña)) {
            return nodo
        }
    
        return if (correo < nodo.usuario.correo) {
            buscarRecursivo(nodo.izquierda, correo, contraseña)
        } else {
            buscarRecursivo(nodo.derecha, correo, contraseña)
        }
    }
    

    private fun obtenerAltura(nodo: NodoArbolBinario?): Int {
        return nodo?.altura ?: 0
    }

    private fun obtenerFactorBalance(nodo: NodoArbolBinario?): Int {
        return obtenerAltura(nodo?.izquierda) - obtenerAltura(nodo?.derecha)
    }

    private fun balancear(nodo: NodoArbolBinario?): NodoArbolBinario {
        val factorBalance = obtenerFactorBalance(nodo)

        // Casos de desequilibrio
        if (factorBalance > 1) {
            // Rotación a la derecha
            if (obtenerFactorBalance(nodo?.izquierda) < 0) {
                nodo?.izquierda = rotarIzquierda(nodo?.izquierda)
            }
            return rotarDerecha(nodo)
        }
        if (factorBalance < -1) {
            // Rotación a la izquierda
            if (obtenerFactorBalance(nodo?.derecha) > 0) {
                nodo?.derecha = rotarDerecha(nodo?.derecha)
            }
            return rotarIzquierda(nodo)
        }

        return nodo ?: NodoArbolBinario(Usuario("", "", "", 0, "", 0.0, 0.0))
    }

    private fun rotarDerecha(y: NodoArbolBinario?): NodoArbolBinario {
        val x = y?.izquierda
        val T2 = x?.derecha

        x?.derecha = y
        y?.izquierda = T2

        // Actualizar alturas
        y?.altura = 1 + maxOf(obtenerAltura(y?.izquierda), obtenerAltura(y?.derecha))
        x?.altura = 1 + maxOf(obtenerAltura(x?.izquierda), obtenerAltura(x?.derecha))

        return x ?: NodoArbolBinario(Usuario("", "", "", 0, "", 0.0, 0.0))
    }

    private fun rotarIzquierda(x: NodoArbolBinario?): NodoArbolBinario {
        val y = x?.derecha
        val T2 = y?.izquierda

        y?.izquierda = x
        x?.derecha = T2

        // Actualizar alturas
        x?.altura = 1 + maxOf(obtenerAltura(x?.izquierda), obtenerAltura(x?.derecha))
        y?.altura = 1 + maxOf(obtenerAltura(y?.izquierda), obtenerAltura(y?.derecha))

        return y ?: NodoArbolBinario(Usuario("", "", "", 0, "", 0.0, 0.0))
    }

}


// Permite a los usuarios registrarse, ingresar, editar su perfil, ver su perfil, establecer objetivos, 
//ingresar rutinas y mostrar su historial. El programa se ejecuta en un bucle mientras el usuario no seleccione la opción de salir
fun main() {
    val arbolUsuarios = ArbolBinarioBusqueda()
    var menuPrincipal = 0
    var menuSecundario = 0
    var usuario: Usuario? = null
    val historial = Historial() // Nueva instancia de la clase Historial


    while (menuPrincipal != 3) {
        println("Menú Principal:")
        println("1. Registrarse")
        println("2. Ingresar")
        println("3. Salir")
        print("Selecciona una opción del menú principal: ")
        menuPrincipal = readLine()?.toIntOrNull() ?: 0

        when (menuPrincipal) {
            1 -> {
                // Registrar usuario
                print("Nombre: ")
                val nombre = readLine() ?: ""
                print("Correo: ")
                val correo = readLine() ?: ""
                print("Contraseña: ")
                val contraseña = readLine() ?: ""
                print("Edad: ")
                val edad = readLine()?.toIntOrNull() ?: 0
                print("Género: ")
                val genero = readLine() ?: ""
                print("Masa Corporal (kg): ")
                val masaCorporal = readLine()?.toDoubleOrNull() ?: 0.0
                print("Altura (m): ")
                val altura = readLine()?.toDoubleOrNull() ?: 0.0

                val nuevoUsuario = Usuario(nombre, correo, contraseña, edad, genero, masaCorporal, altura)
                arbolUsuarios.insertar(nuevoUsuario)
                println("Usuario registrado exitosamente.")
            }
            2 -> {
                // Ingresar usuario
                print("Correo: ")
                val correo = readLine() ?: ""
                print("Contraseña: ")
                val contraseña = readLine() ?: ""

                usuario = arbolUsuarios.buscar(correo, contraseña)

                if (usuario != null) {
                    println("Bienvenido, ${usuario.nombre}!")

                    while (menuSecundario != 6) {
                        println("Menú Secundario:")
                        println("1. Editar perfil")
                        println("2. Ver perfil")
                        println("3. Establecer objetivos")
                        println("4. Ingresar rutina")
                        println("5. Mostrar historia")
                        println("6. Volver al Menú Principal")
                        print("Selecciona una opción del menú secundario: ")
                        menuSecundario = readLine()?.toIntOrNull() ?: 0

                        when (menuSecundario) {
                            1 -> {
                                // Editar perfil del usuario actual
                                usuario.editarPerfil()
                            }
                            2 -> {
                                // Ver perfil del usuario actual
                                usuario.verPerfil()
                            }
                            3 -> {
                                // Establecer objetivos
                                usuario.establecerObjetivos()
                            }
                            4 -> {
                                // Ingresar rutina
                                val nuevaRutina = Rutina("Mi Rutina", 0)
                                while (true) {
                                    val nuevoEjercicio = Ejercicio("", 0, 0, 0, "", 0)
                                    println("Ingresar los datos del ejercicio:")
                                    print("Nombre del ejercicio (Deja en blanco para finalizar): ")
                                    val nombreEjercicio = readLine()
                                    if (nombreEjercicio.isNullOrBlank()) {
                                        break
                                    }
                                    nuevoEjercicio.nombreEjercicio = nombreEjercicio
                                    print("Número de series: ")
                                    nuevoEjercicio.series = readLine()?.toIntOrNull() ?: 0
                                    print("Número de repeticiones: ")
                                    nuevoEjercicio.repeticiones = readLine()?.toIntOrNull() ?: 0
                                    print("Tiempo de descanso (segundos): ")
                                    nuevoEjercicio.descanso = readLine()?.toIntOrNull() ?: 0
                                    print("Músculo trabajado: ")
                                    nuevoEjercicio.musculoTrabajado = readLine() ?: ""
                                    print("Tiempo de realización (segundos): ")
                                    nuevoEjercicio.tiempoRealizacion = readLine()?.toIntOrNull() ?: 0
                                    nuevoEjercicio.validar()

                                    // Agregar el ejercicio a la rutina
                                    nuevaRutina.agregarActividadFisica(nuevoEjercicio)
                                    historial.agregarRutinaAlHistorial(nuevaRutina)

                                    // Ingresar registro de cargas para el ejercicio
                                    val registroCargas = RegistroDeCargas("", 0.0, "", nuevoEjercicio)
                                    registroCargas.ingresarRegistroDeCargas()
                                    historial.agregarRegistroCargasAlHistorial(registroCargas)
                                }
                            }
                            5 -> {
                                // Mostrar historia
                                historial.mostrarHistoria()
                            }
                            6 -> {
                                println("Volviendo al Menú Principal.")
                            }
                            else -> println("Opción no válida en el Menú Secundario.")
                        }
                    }
                    menuSecundario = 0 // Reiniciar el menú secundario para futuros usos.
                }
            }
            3 -> {
                // Salir
                println("Saliendo del programa.")
            }
            else -> println("Opción no válida en el Menú Principal.")
        }
    }
}
