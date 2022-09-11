# Watch Next
[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/Shadowsvl/Watch-Next/blob/develop/README.en.md)

Proyecto muestra desarrollado usando [Jetpack Compose](https://developer.android.com/jetpack/compose). Con el objetivo de mostrar las capacidades actuales para la creación de UI, y su uso en conjunto con el resto de librerías para navegación, inyección de dependencias, consumo de servicios REST y persistencia de datos local. Siguiendo las prácticas actuales recomendadas por la [Guía de Arquitectura](https://developer.android.com/topic/architecture).

Conoce lo más reciente en películas y series, tendencias y contenido similar.
Revisa el listado sin fin para películas y series.
Agrega contenido a tu lista para no perderte de nada.

## Características

La aplicación consta de cinco pantallas:
1. Vista principal dividida por secciones de contenido reciente para películas y series, así como los más recientes agregados a tu lista.
2. Detalle con la información y resumen completo, que cuenta con una sección de contenido similar.
3. Listado que muestra películas o series, que va cargando más contenido conforme te vas desplazando hacia el fondo.
4. Listado de películas y series guardadas.
5. Búsqueda de contenido por título.

Cada pantalla muestra una vista previa con la información del contenido seleccionado, que permite agregar fácilmente a tu listado, así como navegar a la pantalla de detalle.

#### Dependencias principales
* **Compose** - UI
* **Hilt** - Inyección de dependencias
* **Room** - Persistencia de datos local (SQLite)
* **Retrofit** - Cliente HTTP
* **Coil** - Carga de imágenes

## Requisitos

El proyecto utiliza [The Movie DB API](https://www.themoviedb.org/documentation/api). Puedes registrarte para obtener una llave de API.

Es necesario que agregues la llave a tu archivo **local.properties**

`API_KEY=<TuLlaveDeAPI>`

## Capturas

<img src="https://drive.google.com/uc?id=1kw6m4T35iObPNlAqN8QfI0xGalgi5hxM" alt="Screenshot">
<img src="https://drive.google.com/uc?id=11MvZHf3aAgBH5z1NQ_jrkaXHaYF4ga2e" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1Sc8CXLLQqgx2YONkihB2RG8dtduLNuCO" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1WwxloiqP9TULE4rGI_mJdUwkCcAOqXWB" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1jhghqq50fRs9bkJYtHUvSZK38ddYosCe" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1_fOvsmvrARopIkzV_xtkegTC2anugHhh" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1KENfZuqNZj2zGfQ6mGdp1H2xel3HWKqc" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1Pazskx7HI9XjsjExUIVq9HXZYgsH45hA" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1KZRQCRgiSBx8wfi0f5w1aBA7dWTyshsE" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1axqU2QS309WeZhYPprKTvYWwHsVOfvDO" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1fVIh6dHYprszGeB45FD84MTn1neWrFQo" alt="Screenshot">