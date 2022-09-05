package com.heka.watchnext.data.fake

import com.heka.watchnext.model.MediaType
import com.heka.watchnext.model.WatchMedia

val fakeWatchMovie1 = WatchMedia(
    id = 634649L,
    originalTitle = "Spider-Man: No Way Home",
    title = "Spider-Man: No Way Home",
    overview = "Peter Parker es desenmascarado y por tanto no es capaz de separar su vida normal de los enormes riesgos que conlleva ser un súper héroe. Cuando pide ayuda a Doctor Strange, los riesgos pasan a ser aún más peligrosos, obligándole a descubrir lo que realmente significa ser Spider-Man.",
    releaseDate = "2021-12-15",
    voteAverage = 8.045f,
    posterPath = "/miZFgV81xG324rpUknQX8dtXuBl.jpg",
    backdropPath = "/14QbnygCuTO0vl7CAFmPf1fgZfV.jpg",
    mediaType = MediaType.Movie
)

val fakeWatchMovie2 = WatchMedia(
    id = 507086L,
    originalTitle = "Jurassic World Dominion",
    title = "Jurassic World: Dominion",
    overview = "Cuatro años después de la destrucción de Isla Nublar, los dinosaurios conviven – y cazan – con los seres humanos en todo el mundo. Este frágil equilibrio cambiará el futuro y decidirá, de una vez por todas, si los seres humanos seguirán en la cúspide de los depredadores en un planeta que comparten con los animales más temibles de la creación.",
    releaseDate = "2022-06-01",
    voteAverage = 7.1f,
    posterPath = "/sXeWfpT1EhG7f4uBouqraOhmouH.jpg",
    backdropPath = "/jauI01vUIkPA0xVsamGj0Gs1nNL.jpg",
    mediaType = MediaType.Movie
)

val fakeWatchTv1 = WatchMedia(
    id = 84773L,
    originalTitle = "The Lord of the Rings: The Rings of Power",
    title = "El Señor de los Anillos: Los anillos de poder",
    overview = "Serie de televisión basada en El señor de los anillos, ambientada durante el período de 3.441 años, conocida como la Era de Númenor, o la Segunda Edad.",
    releaseDate = "2022-09-01",
    voteAverage = 7.144f,
    posterPath = "/vet2jdd7A7rI4aswv32SxsZtnjX.jpg",
    backdropPath = "/pdfCr8W0wBCpdjbZXSxnKhZtosP.jpg",
    mediaType = MediaType.Tv
)

val fakeWatchTv2 = WatchMedia(
    id = 766507L,
    originalTitle = "House of the Dragon",
    title = "La casa del dragón",
    overview = "Basada en el libro 'Fuego y Sangre' de George R.R. Martin. La serie se centra en la casa Targaryen, trescientos años antes de los eventos vistos en 'Juego de Tronos'.",
    releaseDate = "2022-08-21",
    voteAverage = 8.789f,
    posterPath = "/xiB0hsxMpgxpJehYxUDhiUkg2w.jpg",
    backdropPath = "/Aa9TLpNpBMyRkD8sPJ7ACKLjt0l.jpg",
    mediaType = MediaType.Tv
)

val fakeWatchMediaList = listOf(
    fakeWatchMovie1,
    fakeWatchMovie2,
    fakeWatchTv1,
    fakeWatchTv2,
    fakeWatchMovie1.copy( id = 1L ),
    fakeWatchMovie2.copy( id = 2L ),
    fakeWatchTv1.copy( id = 3L ),
    fakeWatchTv2.copy( id = 4L )
)