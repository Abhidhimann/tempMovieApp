# MovieApp 

## Tech Stacks
- Kotlin
- Jetpack Compose
- MVI + Clean Architecture
- Hilt (Dependency Injection)
- Retrofit & OkHttp
- Coroutines & Flow
- Navigation Compose
- Room
- Coil (Image Loading)
- TMDb API

## Features

- **Pull to Refresh**: Swipe down to fetch the latest movies from the network.
- **Offline Handling**: Movies are cached locally using Room, then shown to user. 
- **Error Handling**: No static "No Internet" screen; **Shows a Snackbar on refresh while allowing users to browse cached movies**. Different toast messages for various error scenarios.
- **Loading States**: Simple circular loading indicator 
- **Dark & Light Mode**: Fully supports system theme for better user experience.
- **Search Movies**: Search bar to quickly find movies.

## Demo

https://github.com/user-attachments/assets/07f8e78e-9553-44c0-a5dc-345175b27b83

https://github.com/user-attachments/assets/39e23fa0-f6b3-49fc-926a-1b93183127de


## Apk File: [Movie App](https://github.com/Abhidhimann/tempMovieApp/blob/main/movieAppAbhishek.apk) 

## How to Build the Project

To build and run the project, you need to create a `secret.properties` file in the root directory with your TMDb API key.

### 1. Obtain Your API Key
- Visit the TMDb website and sign in.
- Navigate to the API section.
- Generate your API key.

### 2. Create `secret.properties` File
Create a file named `secret.properties` in the root directory of the project (same level as `settings.gradle`).

Add the following line:

  ```properties
  TMDB_API_KEY=you_api_key
