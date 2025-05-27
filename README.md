# 🎬 CinecodeApp

**CinecodeApp** es una aplicación móvil desarrollada con **Jetpack Compose**. Permite explorar películas, consultar funciones disponibles, ver detalles de cada película, acceder a información de los cines disponibles, conocer qué películas se proyectan en cada cine y sus respectivas funciones, así como visualizar los cines en **Google Maps**, incluyendo su ubicación. Además, integra datos enriquecidos desde **TMDB** y ofrece muchas más funcionalidades.

El proyecto fue desarrollado siguiendo los principios de la **Clean Architecture** y el patrón de diseño **MVVM**, organizando los módulos por *features* para garantizar escalabilidad, mantenimiento y claridad en el código.

---

## 📷 Capturas de pantalla

|                                                    Demo gifs                                                     |                                              Demo gifs                                               |                                              Demo gifs                                               |
|:----------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------:|
| <img src="https://raw.githubusercontent.com/RendevMq/CinecodeApp/refs/heads/master/gifs/gif1.gif" width="240px"> | <img src="https://github.com/RendevMq/CinecodeApp/blob/master/gifs/gif2.gif?raw=true" width="240px"> | <img src="https://github.com/RendevMq/CinecodeApp/blob/master/gifs/gif3.gif?raw=true" width="240px"> |
---



## 🧰 Tecnologías y herramientas utilizadas

**Frameworks y librerías principales:**
| Tecnología / Librería              | Descripción                                              |
|-----------------------------------|----------------------------------------------------------|
| **Jetpack Compose**                   | Framework UI moderno declarativo para Android            |
| **Retrofit**                         | Cliente HTTP para consumir APIs REST                      |
| **Kotlin Coroutines**                | Manejo de concurrencia asíncrona en Kotlin                |
| **Kotlin Flow**                     | Manejo reactivo de flujos de datos                         |
| **Coil**                           | Librería ligera para carga de imágenes con Kotlin          |
| **Hilt**                           | Inyección de dependencias en Android                       |
| **Google Maps SDK**                 | SDK para integrar mapas y funcionalidades geográficas     |
| **Material Icons**                 | Conjunto de íconos oficiales de Material Design           |
| **Google Fonts**                   | Servicio para cargar fuentes tipográficas                   |
| **kotlinx.parcelize**              | Plugin para simplificar parcelización de objetos Kotlin   |
| **kotlinx.serialization**         | Librería para serializar y deserializar objetos Kotlin    |


---

## 🧠 Principales Retos y Soluciones

### 1. 📱 Navegación modular con Jetpack Compose
- Implementé `androidx.navigation.compose` usando `BottomNavigationBar`.
- Organicé los **NavGraphs por feature**, asegurando modularidad.
- Implementé **paso de argumentos** (Strings y objetos serializados).
- Navegación desacoplada y segura, aplicando conceptos similares a SafeArgs.

### 2. 🔄 Limitación de APIs públicas y solución personalizada
- Las APIs oficiales no proporcionaban toda la información necesaria.
- Extraje **JSONs internos** directamente desde la web de Cineplanet.
- Relacioné tres estructuras JSON de manera lógica para obtener funciones, cines y películas.
- Se propone realizar un backend propio (Spring Boot + Java) que automatice la extracción periódica de estos JSONs desde la web.

### 3. 🌍 Enriquecimiento con la API de TMDB
- Integración con [TMDB API](https://www.themoviedb.org/documentation/api) para obtener:
    - Géneros, reparto, sinopsis extendida y más.
- Implementación de peticiones encadenadas usando Retrofit y Coroutines.

### 4. ✨ UI moderna con Jetpack Compose + Shimmer
- Usé el **efecto shimmer** para skeletons de carga en lugar de ProgressBars tradicionales.
- Mejora visual y percepción del usuario durante la carga de datos.

### 5. 🗺️ Integración con Google Maps
- Implementé **Google Maps SDK** para mostrar la ubicación de cines.
- Mejor experiencia visual e informativa para el usuario.

### 6. 🔥 Funcionalidades en desarrollo
- Integración con **Firebase** para selección y reserva de butacas.
- Mejora en la experiencia completa de compra de entradas.
- Backend en desarrollo para sincronización automática de datos.

---

## 📦 Instalación

1. Clona el repositorio:

```bash
git clone <https://github.com/RendevMq/CinecodeApp.git>
```

2. Abre el proyecto en **Android Studio**.
3. Asegúrate de tener configurado un **emulador** o **dispositivo físico**.
4. Antes de ejecutar el proyecto, agrega los siguientes secretos en tu archivo `local.properties`:

```
MAPS_API_KEY=tu_api_key_de_google_maps
MOVIE_API_TOKEN=tu_token_de_tmdb
```

5. Ejecuta el proyecto desde Android Studio.