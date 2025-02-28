# Bookpedia

This is a [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html) project targeting Android, iOS, and Desktop.

Bookpedia is a Kotlin Multiplatform project designed to help users explore books, check ratings, mark books as favorites, and store them locally on their device. This app supports both Android and iOS platforms, providing a seamless experience across devices.

## App Features
- **Browse Books**: Users can view a list of books with essential details like title, author, and rating.
- **Book Ratings**: Each book displays its rating to help users make informed decisions before adding it to their collection.
- **Favorite Books**: Users can mark books as favorites, which will be stored locally, making it easy to revisit them later.
- **Offline Storage**: All favorite books are saved locally on the device using a local database, ensuring users can access their favorites even without an internet connection.
- **Cross-Platform Support**: The app is built using Kotlin Multiplatform, allowing users to enjoy the same features on Android, Desktop, and iOS with shared code for business logic and data management.

## 🤖 Tech Stack

### **Front-end (UI & UX)**
- **Kotlin Multiplatform** (For shared code and business logic)
- **Jetpack Compose** (For Android UI)

### **Cross-Platform Development**
- **Kotlin** (Main language for both Android and iOS)
- **Kotlin Serialization** (For serialization across platforms)

### **Networking & Data**
- **Ktor** (For networking and API calls)
- **SQLite/Room** (For local database storage)
- **Koin** (For dependency injection)

### **Libraries & Tools**
- **Coil** (For image loading and caching)
- **KSP (Kotlin Symbol Processing)** (For compiling annotations and code generation)

### **Development Tools**
- **Android Studio**
- **Git**
---