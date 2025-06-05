# Little Elephant - Frontend

## Descripción

**Little Elephant** es una aplicación educativa Android diseñada para el aprendizaje de vocabulario en inglés para niños mayores de 5 años. A través de la exploración de ecosistemas temáticos (como Granja, Océano o Jungla), los usuarios interactúan con imágenes, sonidos y preguntas dinámicas. La app se desarrolla con **Jetpack Compose** y está conectada a un backend REST desacoplado y escalable.

## Funcionalidades del Frontend

- Registro y login con validaciones integradas.
- Exploración y desbloqueo progresivo de ecosistemas educativos.
- Pantalla de preguntas con retroalimentación visual y sonora.
- Progreso visual del usuario con animaciones.
- Gestión del perfil: edición de datos y eliminación de cuenta.
- Sistema de traducción en tiempo real (español/inglés) persistente.
- Lógica dinámica de desbloqueo solo si se responden correctamente todas las preguntas.
- Pantalla de felicitación final si se completa el último ecosistema.

## Tecnologías

- **Lenguaje:** Kotlin
- **Framework:** Jetpack Compose
- **Persistencia local:** DataStore (idioma)
- **Consumo API:** Retrofit
- **Gestión de estado:** ViewModel
- **Audio:** MediaPlayer
- **Traducción dinámica:** Archivos JSON (`strings_es.json`, `strings_en.json`)

## Estructura Destacada

- `QuestionFlowScreen.kt`: flujo de preguntas, validación y desbloqueo.
- `ProgressScreen.kt`: cálculo del avance general del usuario.
- `ProfileScreen.kt`: gestión de datos del usuario.
- `ApiService.kt`: define todos los endpoints de conexión al backend.
- `TranslationManager.kt` & `DataStoreManager.kt`: sistema multilingüe persistente.

## Pantallas Diseñadas

A continuación se muestran algunas de las pantallas clave diseñadas en la aplicación:

<p align="center">
  <img src="images/favicon.png" alt="Icono app" width="300"/>
  <img src="images/LoginScreen.png" alt="Pantalla de login" width="300"/>
  <img src="images/RegisterScreen.png" alt="Registro" width="300"/>
</p>

<p align="center">
  <img src="images/RegisterScreen2.png" alt="Registro con validación" width="300"/>
  <img src="images/ValidacionErrorScreen.png" alt="Errores de validación" width="300"/>
   <img src="images/RegistrationSucessScreen.png" alt="Registro exitoso" width="300"/>
</p>

<p align="center">
  <img src="images/EcosystemScreenUnlock1.png" alt="Primer ecosistema desbloqueado" width="300"/>
  <img src="images/EcosystemsScreen.png" alt="Lista de ecosistemas" width="300"/>
  <img src="images/EcosystemScreenLanguage.png" alt="Pantalla con idioma" width="300"/>
</p>

<p align="center">
  <img src="images/QuestionScreen1.png" alt="Pregunta 1" width="300"/>
  <img src="images/QuestionScreen2.png" alt="Pregunta 2" width="300"/>
  <img src="images/QuestionScreen3.png" alt="Pregunta 3" width="300"/>
</p>

<p align="center">
  <img src="images/RetryScreen.png" alt="Pantalla de reintento" width="300"/>
  <img src="images/FinishedScreen.png" alt="Pantalla de finalización" width="300"/>
  <img src="images/CongratsScreen.png" alt="Pantalla de felicitación" width="300"/>
</p>

<p align="center">
  <img src="images/ProgressScreen.png" alt="Progreso del usuario" width="300"/>
  <img src="images/ProgressScreenComplete.png" alt="Progreso completo" width="300"/>
  <img src="images/ProfileScreen.png" alt="Perfil del usuario" width="300"/>
</p>

<p align="center">
  <img src="images/ProfileScreen2.png" alt="Edición de perfil" width="300"/>
</p>

## Requisitos Previos

- Android Studio Arctic Fox o superior.
- Dispositivo o emulador Android API 26+.
- Conexión activa al backend desplegado.

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/usuario/Little-Elephant.git
