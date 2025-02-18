# Taste Tracker

## Overview :
  The of the *Tast Tracker* project is to assist users in effectively managing and keeping track of their Planed meals. 
        It offers a user-friendly interface for goal-setting, activity tracking, and progress management over time.

## Features :
  1.  **Activity Tracking:** Easily log and monitor daily activities.
  2.  **Data Export:** Export your tracked data in JSON format.
  3.  **User-Friendly Interface:** Simple and intuitive design for seamless navigation.
  4.  **API Integration:** Fetch and send data using Retrofit.
  5. **Offline Support:** Store data locally using Room Database.
  6. **Firebase Authentication:** Secure user authentication using Firebase.

## Implementation Platform :
 The project uses the following libraries and dependencies:

 #### Firebase
        implementation libs.firebase.bom //Manages Firebase library versions.
        implementation libs.firebase.analytics //Tracks user interactions and app usage.
        implementation libs.firebase.auth.v2320 //Handles user authentication.
        implementation libs.firebase.database //Stores and syncs data in real-time.
        implementation libs.play.services.auth.v2040 //Enables Google Sign-In.
#### UI Libraries
        implementation libs.material.v150 // Provides Material Design components.
        implementation libs.material.v1110 // Provides Material Design components.
        implementation libs.circleimageview //Displays circular images.
        implementation libs.lottie //Adds animations to the app.
#### Navigation 
        implementation libs.navigation.fragment //Handles fragment navigation.
        implementation libs.navigation.ui //Provides UI components for navigation.
#### Networking
        implementation libs.glide //Loads and caches images.
        implementation libs.converter.gson //Converts JSON to Java/Kotlin objects.
        implementation libs.retrofit //Handles API requests.
#### Stores data locally.
        implementation libs.room.rxjava3 //Enables reactive programming with Room.
        implementation libs.room.runtime
        annotationProcessor libs.room.compiler //Stores data locally.
#### Reactive Programming
        implementation libs.adapter.rxjava3 //Integrates Retrofit with RXJava.
        implementation libs.rxandroid // Provides Android-specific bindings for RXJava.
        implementation libs.rxjava //implementation libs.rxjava
## Installation
### Prerequisites
Before installing Taste Tracker, ensure you have the following installed on your system:
* **Android Studio** (latest version recommended)
* **Firebase Account** (for authentication and database)
* **Google Play Services** (for Google Sign-In)
### Steps
* **Clone the repository:**
          git clone https://github.com/Nour230/Tracker.git
* **Open the project in Android Studio:**
  1. Launch Android Studio and select "Open an Existing Project."
  2. Navigate to the cloned repository and open it.
* **Set up Firebase:**
   1. Go to the Firebase Console.
   2. Create a new project and add an Android app.
   3. Download the google-services.json file and place it in the app directory of the project.
* **Build and run the project:**
  1. Connect an Android device or emulator.
  2. Click on "Run" in Android Studio to build and launch the app.

## Usage
#### Sign Up/Log In
 1.Use Firebase Authentication to create an account or log in with Google.

#### Logging Activities
  1. Click on the "Add Activity" button.
  2. Fill in the activity details (e.g., name, duration, category).
  3. Save the activity to track it.
#### Viewing Progress
1. Navigate to the "Dashboard" tab.
2. View your progress through visual charts and summaries.









        
     
