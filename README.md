# Movie-App
MovieApp is a modern Android application designed for exploring and discovering films. Users can browse movies by genre, view comprehensive details, watch trailers, and read user reviews. The app is built with a focus on clean architecture and modern Android development practices.

# 📱 Key Features
- **Genre Discovery**: An initial screen allows users to browse and select movies by their category.
- **Movie Listings**: Displays a list of movies based on the selected genre, featuring infinite scrolling to load more content.
- **Detailed Movie View**: A rich detail screen showing the movie's poster, backdrop, rating, runtime, synopsis, official trailers, and user reviews.
- **Watch Trailers**: Opens video trailers directly in the YouTube app (if installed) or a web browser for a seamless viewing experience.
- **User Reviews**: Presents a list of user-submitted reviews with infinite scrolling and a "Read more" option for longer reviews.
- **Modern & Responsive UI**: A clean, intuitive, and visually appealing user interface built with Material Components.

<img width="400" height="1100" alt="image" src="https://github.com/user-attachments/assets/c56de9c0-70b6-4f8d-afd9-58de9bb1cfba" />
<img width="400" height="1100" alt="image" src="https://github.com/user-attachments/assets/66650875-510e-4106-8f02-0e90d8c55677" />
<img width="400" height="1100" alt="image" src="https://github.com/user-attachments/assets/38e5a8be-7cb4-4801-9e3a-959f32b61303" />
<img width="400" height="1100" alt="image" src="https://github.com/user-attachments/assets/33102967-d7e0-4c0e-a94f-57dbd96244c5" />

  
# 🛠 Tech Stack & Architecture
This application is built entirely in Kotlin and follows the MVVM (Model-View-ViewModel) architecture, promoting a separation of concerns and making the codebase scalable and maintainable.
- **UI:** XML Layouts, Material Design Components 3, ViewBinding.
- **Architecture:** MVVM, Repository Pattern.
- **Navigation:** Android Navigation Component (NavGraph, SafeArgs).
- **Networking:** Retrofit2, OkHttp3 (Logging Interceptor), Gson.
- **Dependency** Injection: Dagger 2.
- **Concurrency:** Kotlin Coroutines.
- **Image Loading:** Glide for efficiently loading, caching, and displaying images from the network.

# 📂 Project Structure
```markdown
com.example.registrationapp
├── core
├── data
│   ├── api      
│   ├── model      
│   └── repository   
├── di              
├── ui
│   ├── activity
│   ├── adapter
│   ├── fragments    
│   ├── mapper
│   ├── model
│   ├── repository
│   ├── theme
│   └── vm
└── MovieAppApplication.kt
```

# 🚀 How to Run
1. Get TMDB API key from https://www.themoviedb.org
2. Create ⁠ local.properties ⁠ in project root
3. ⁠Add: `TMDB_API_KEY=YOUR_API_KEY`
4. Sync Gradle & Run



**Developed by Rifqi Ananda**
