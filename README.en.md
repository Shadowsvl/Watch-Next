# Watch Next

Sample project developed using [Jetpack Compose](https://developer.android.com/jetpack/compose). With the aim of showing the current capabilities UI creation, and its use in conjunction with the rest of the libraries for navigation, dependency injection, consumption of REST services and local data persistence. Following the current practices recommended by the [Architecture Guide](https://developer.android.com/topic/architecture).

Get to know the latest in movies and series, trends and similar content.
Check the endless list for movies and series.
Add content to your list so you don't miss a thing.

## Features

The application consists of five screens:
1. Main view divided by sections of recent content for movies and series, as well as the most recent ones added to your list.
2. Detail with the information and complete summary, which has a section with similar content.
3. List that shows movies or series, which loads more content as you scroll to the bottom.
4. List of saved movies and series.
5. Search for content by title.

Each screen shows a preview with the information of the selected content, which allows you to easily add it to your list, as well as navigate to the detail screen.

#### Main dependencies
* **Compose** - UI
* **Hilt** - Dependency Injection
* **Room** - Local data persistence (SQLite)
* **Retrofit** - HTTP client
* **Coil** - Image loading

## Requisitos

The project uses [The Movie DB API](https://www.themoviedb.org/documentation/api). You can sign up for an API key.

You need to add the key to your **local.properties** file

`API_KEY=<YourAPIKey>`

## Capturas

<img src="https://drive.google.com/uc?id=1LTUZVzOR54af9KFk6iLg4zlGVJci87lH" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1yBbd_UHjMc60B4Fe8BPwAEW5GbM5Dj7q" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1spFhmeRyxs3yPte2F1t-ptIzWrcVVgFM" alt="Screenshot">
<img src="https://drive.google.com/uc?id=10HLrOA8Q2ce9aCgh3oVySC5mnY357oii" alt="Screenshot">
<img src="https://drive.google.com/uc?id=10rNluzKwdwR4geqTmw7MBxn3fVYmYwgV" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1Lz53P3-FbCMnzy5TaDgvGsVw9S2l40cD" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1QArKjQFUK5TstumqLBSGM9DhVy12Afkr" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1bTwXAI8-_QgfD6JzerW22bnDxr027RkQ" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1jp93S5v4mPK9xYCb6iC0nSLNOU7DFga5" alt="Screenshot">
<img src="https://drive.google.com/uc?id=1F3kctUHH6G96G0OB7N1525HkfhzChtxp" alt="Screenshot">
<img src="https://drive.google.com/uc?id=19LvZMtz6uBUkyKTMbNbOlr9mFrvl8gui" alt="Screenshot">