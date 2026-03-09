# SpaceFlightNewsApp

An Android application that displays spaceflight news from the Spaceflight News API. The app features a news list with search, article details, and local favorites storage.

## Features

- **News List**: Scrollable list of spaceflight articles with images, titles, summaries, and dates
- **Article Detail**: Full article view with image, title, summary, publication date, and external link
- **Favorites**: Save and manage favorite articles locally with Room database
- **Search**: Search articles by keyword with debounced input
- **Pull to Refresh**: Swipe down to refresh the news list
- **Loading States**: Visual feedback during network operations
- **Error Handling**: User-friendly error messages with retry option
- **Material Design**: Modern UI following Material Design guidelines

## Architecture

The application follows a layered structure with clear separation of concerns:

### Layer Structure

- **UI Layer**: Fragments, ViewModels, Adapters
- **Data Layer**: Repository implementations, API services, Room database, data models

### Architectural Patterns

- **MVVM (Model-View-ViewModel)**: Separation between UI and business logic
- **Repository Pattern**: Abstraction layer for remote and local data operations
- **LiveData**: Reactive state management for UI updates
- **Kotlin Coroutines**: Asynchronous programming for network and database operations

## Technology Stack

### Core Technologies
- **Kotlin**: 100% Kotlin codebase
- **Android SDK**: Min SDK 24, Target SDK 35
- **Gradle**: Kotlin DSL build system

### Architecture and State
- **ViewModel**: UI state management
- **LiveData**: Reactive state observation
- **Kotlin Coroutines**: Asynchronous operations

### Networking
- **Retrofit**: Type-safe HTTP client for REST API
- **OkHttp**: HTTP client with logging interceptor
- **Gson**: JSON serialization and deserialization

### Local Storage
- **Room**: SQLite database for favorites persistence

### UI Libraries
- **Material Design Components**: Modern UI components
- **ViewBinding**: Type-safe view references
- **Navigation Component**: Fragment-based navigation
- **Coil**: Image loading library
- **RecyclerView**: List display with DiffUtil support
- **SwipeRefreshLayout**: Pull-to-refresh functionality

## Project Structure

```
app/src/main/java/com/example/spaceflightnewsapp/
├── data/
│   ├── local/
│   │   ├── dao/                 # Room DAOs
│   │   ├── entity/              # Room entities
│   │   └── AppDatabase.kt       # Room database
│   ├── model/                   # Data models (Article, ApiResponse)
│   ├── remote/
│   │   ├── ApiClient.kt         # Retrofit client
│   │   └── SpaceFlightNewsApi.kt # API interface
│   └── repository/              # Repository implementations
├── ui/
│   ├── articledetail/           # Article detail screen
│   ├── favorites/               # Favorites screen
│   └── newslist/                # News list screen
├── util/                        # Utilities (DateUtils, ErrorHandler)
├── MainActivity.kt
└── SpaceFlightNewsApplication.kt
```

## Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK with API level 24+

### Installation

1. Clone the repository:

```
git clone https://github.com/ihtiyargurkan/SpaceFlightNewsApp.git
cd SpaceFlightNewsApp
```

2. Open the project in Android Studio

3. Sync Gradle files and wait for dependencies to download

4. Run the application on an emulator or physical device

## Usage

### Viewing News
- Upon launch, the app fetches and displays the latest spaceflight articles
- Each item shows an image, title, summary snippet, and publication date
- Articles are loaded from `https://api.spaceflightnewsapi.net/v4/`

### Article Detail
- Tap any article to view full details
- Use "Read full article" to open the original source in a browser

### Favorites
- Tap the star icon in the article detail screen to add or remove from favorites
- Access favorites from the star icon in the news list toolbar

### Search
- Use the search icon in the toolbar to filter articles by keyword
- Search is debounced for better performance

## UI/UX Features

- **Material Design**: Consistent UI components and theming
- **Toolbar**: Per-fragment toolbar with navigation and actions
- **Edge-to-edge Layout**: Status bar integration with proper icon visibility
- **Progress Indicators**: Loading states during data fetch
- **Error Handling**: Retry buttons and empty state messages
- **API Attribution**: Credit to Spaceflight News API at bottom of list

## Configuration

### Network Configuration
Network settings are defined in `ApiClient.kt`:
- **Base URL**: `https://api.spaceflightnewsapi.net/v4/`
- **Logging**: OkHttp logging interceptor for debugging

### String Resources
All user-facing strings are managed in `strings.xml`, ready for localization.

## Testing

The project includes unit tests for:
- DateUtils formatting
- ErrorHandler message mapping
- Article model
- NewsListViewModel

Run tests with:

```
./gradlew test
```

## Development

This project was developed as an educational study demonstrating:
- MVVM architecture with ViewModel and LiveData
- Retrofit and Room integration
- Navigation Component usage
- Material Design implementation

## Screenshots
<img width="317" height="702" alt="Screen Shot 2026-03-10 at 02 51 21" src="https://github.com/user-attachments/assets/330b2ef5-d134-4fcb-a94a-3ac249056f38" />
<img width="317" height="702" alt="Screen Shot 2026-03-10 at 02 51 26" src="https://github.com/user-attachments/assets/5be3e3bc-fde9-4068-9dbd-ddf09c2a0d1f" />
<img width="317" height="702" alt="Screen Shot 2026-03-10 at 02 51 34" src="https://github.com/user-attachments/assets/5607c62d-6fd2-4915-93b7-9ae2b1b3ace6" />
<img width="317" height="702" alt="Screen Shot 2026-03-10 at 02 51 48" src="https://github.com/user-attachments/assets/240c3869-a063-489d-8ea2-630e2b8e9144" />


## License

This project is developed for educational purposes.
