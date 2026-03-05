# Artist Challenge

Android application that integrates with the Discogs API to search artists, explore their details, and navigate their discography.

The project focuses on clean architecture, maintainability, and modern Android development practices.
---

# Features

• Search for artists using the Discogs API  
• Display search results with artist thumbnail and name  
• Artist detail screen with relevant artist information  
• Display band members when the artist is a band  
• Browse the artist's album releases  
• Album list sorted by release date (newest → oldest)  
• Filter albums by:
- Year
- Genre
- Label
• Pagination
• Loading, empty, and error state handling  
• Modern UI built with Jetpack Compose

## Screenshots

| Search Screen | Artist Detail | Albums |
|---------------|---------------|--------|
| <img width="392" height="880" alt="image" src="https://github.com/user-attachments/assets/d944ec9b-3be8-465d-966f-dd7c3f1f45bb" />|<img width="392" height="881" alt="image" src="https://github.com/user-attachments/assets/6769fa00-ffe2-4ad8-8bd2-f9e0c1eb166c" /> | <img width="393" height="882" alt="image" src="https://github.com/user-attachments/assets/320af13b-cd69-4ba6-b23a-06a6e2089716" /> |

---

# Tech Stack

**Language**
- Kotlin

**UI**
- Jetpack Compose
- Material 3

**Architecture**
- MVVM
- Clean Architecture principles

**Dependency Injection**
- Hilt

**Networking**
- Retrofit
- OkHttp

**Image Loading**
- Coil

**Testing**
- JUnit
- MockK

**Static Analysis**
- Detekt

---

# Architecture

The project follows **Clean Architecture with MVVM**, ensuring separation of concerns and maintainability.

`UI → ViewModel → UseCase → Repository → API `

### Presentation Layer
Responsible for UI rendering and state management.

Components:
- Jetpack Compose screens
- ViewModels
- UI State classes

### Domain Layer
Contains the core business logic.

Components:
- UseCases
- Domain models
- Repository interfaces

### Data Layer
Handles data retrieval and mapping.

Components:
- Retrofit API service
- DTO models
- Repository implementations
- Network interceptors

---

# API Integration

The application integrates with the **Discogs API**.

### Pagination

The Discogs API supports pagination with `page` and `per_page` parameters.

A custom pagination mechanism was implemented in the ViewModel to load additional data when the user scrolls near the end of the list.

Each request retrieves **30 elements per page**, as required by the challenge.

The project implements a **custom pagination strategy**.

The ViewModel keeps track of:

- current page
- loading state
- end-of-list detection

When the user scrolls near the bottom of the list, the next page is requested and appended to the existing list.

This approach keeps the pagination logic simple while maintaining full control over the loading flow.

---

# Error Handling

The application handles the following scenarios:

• No search results  
• Network errors  
• API failures  

Each screen exposes a **UI state** that can represent:
- Loading
- Success
- Empty
- Error

This ensures the UI always reflects the correct application state.
---

# Static Code Analysis

The project uses **Detekt** for static code analysis.

Detekt helps enforce Kotlin best practices and detect potential code smells.

Configuration is located in: `config/detekt/detekt.yml`

Some rules were adjusted to better support **Jetpack Compose patterns**, such as allowing longer functions for composables.

Run the analysis with:`./gradlew detekt`


---

# Requirements

• Android Studio Hedgehog or newer  
• Android SDK 24+ (Android 7.0)  

---

# Setup

1. Clone the repository
``git clone https://github.com/EstebanPosada/Artist-Challenge.git``


2. Open the project in Android Studio

3. Add your Discogs token to `local.properties`
   `DISCOGS_TOKEN=your_token_here`
   
4. Build and run the project

---

# Testing

Unit tests are included for critical parts of the application, including:

- ViewModels
- UseCases
- Repository logic

Tests use:

- JUnit
- MockK

Run tests with: `./gradlew test`


---

# Development Process

The development of the application followed an iterative approach, focusing first on the project structure and UI flow before fully integrating the API.

1. **Project Initialization**

The project was started by defining the package structure following Clean Architecture principles.  
Modules for presentation, domain, and data layers were created.

2. **Initial UI & Navigation**

   Basic screens and navigation were implemented using Jetpack Compose.  
   This allowed the main user flow to be validated early:

   - Artist search screen
   - Artist detail screen
   - Artist albums screen

3. **Dependency Injection Setup**

   Dependency Injection was introduced using **Hilt**.  
   Modules for networking, repositories, and use cases were created.  
   At this stage, basic error handling placeholders were implemented while the API integration was still in progress.

4. **API Integration**

   Once the UI structure was in place, the Discogs API was integrated using **Retrofit** & **OkHttp**.

   The main features implemented were:

   - Searching artists
   - Retrieving artist details
   - Fetching artist releases

   During this phase, the UI models and domain models were refined to match the API responses.

5. **Discography & Refactoring**

   While implementing the albums screen, some responsibilities initially placed in the artist detail screen were moved to a dedicated albums screen, improving separation of concerns.
   This resulted in a refactor that better aligned the UI with the application flow and improved separation of concerns.

6. **UI Improvements**

   Once the core functionality was working, the UI was refined to support:

   - Loading states
   - Empty states
   - Error handling
   - Sorting and filtering of album lists
   -
   After the core functionality was working, the models, UI states, and screen logic were refactored to better match the API structure and improve maintainability.

7. **Static Analysis**

   After the main functionality was working, **Detekt** was added as a static analysis tool to help maintain code quality and enforce Kotlin best practices.  
   Some rules were adjusted to better support Jetpack Compose patterns.

8. **Testing**

   Unit tests were then introduced for critical parts of the application, starting with:

   - Domain layer (UseCases)
   - Data layer (Repositories)
   - ViewModels

   This approach ensured that the business logic was verified independently from the UI.

9. **Final Refactoring**

   In the final stage, several improvements were made:

   - Refactoring of models and UI states
   - Code cleanup
   - Improved error handling
   - Extraction of secrets and configuration values

The final result is a modular and maintainable codebase that follows modern Android development practices.
