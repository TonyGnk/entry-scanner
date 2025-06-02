# 🎫 Entry Scanner

## 🌟 Overview

Entry Scanner is a modern Android application designed for efficient event management and participant tracking. Built with Jetpack Compose and Firebase, this app provides a streamlined solution for checking in team members and participants at events through QR code scanning technology. The application offers real-time synchronization and an intuitive interface for managing team arrivals and attendance.

## 📄 Functionality

The core functionality revolves around team-based event management with QR code scanning capabilities. The app supports Firebase Firestore for real-time data synchronization, allowing multiple administrators to track participant arrivals simultaneously. Each participant receives a unique QR code that encodes their team ID and member index, enabling quick and accurate check-ins.

## 🪄 Graphical User Interface (GUI)

The application features a beautiful and modern Material Design 3 interface built with Jetpack Compose. The GUI provides:

- **Team List View**: Displays all registered teams with expandable member details
- **Real-time Arrival Status**: Visual indicators showing who has arrived and who hasn't
- **QR Scanner Interface**: Clean camera preview with animated scan feedback
- **Member Statistics**: Live count of arrived vs total members
- **Responsive Design**: Adapts to different screen sizes and orientations

## ✨ Features

- **QR Code Scanning**: Fast and accurate barcode detection using ML Kit
- **Team Management**: Organized view of teams and their members
- **Real-time Updates**: Live synchronization with Firebase Firestore
- **Arrival Tracking**: Mark participants as arrived/not arrived
- **Offline Detection**: Handles network connectivity changes gracefully
- **Camera Permissions**: Smooth permission handling with user-friendly prompts
- **Material Design 3**: Modern and accessible user interface
- **Admin Controls**: Manual arrival status management through intuitive dialogs
- **Visual Feedback**: Animated indicators for scan states (scanning, scanned, error)

## ⚙️ Technical Architecture

### **Framework & Libraries**
- **Kotlin & Jetpack Compose**: Modern Android UI toolkit
- **Firebase Firestore**: Real-time NoSQL database
- **CameraX**: Camera integration for QR scanning
- **ML Kit Barcode Scanning**: Google's machine learning barcode detection
- **Navigation Compose**: Type-safe navigation
- **Coroutines & Flow**: Reactive programming and async operations

### **Architecture Pattern**
- **MVVM (Model-View-ViewModel)**: Clean separation of concerns
- **Repository Pattern**: Data abstraction layer
- **Dependency Injection**: Modular and testable code structure

### **Data Models**
- **Participant**: Contains member details (name, email, school, subject, arrival status)
- **Team**: Groups participants with metadata (team name, category, creation timestamp)

## 🔧 Key Components

### **QR Code Format**
- First 20 characters: Team ID
- Remaining characters: Member index within the team

### **Core Screens**
- **Team List Screen**: Overview of all teams and arrival statistics
- **QR Scanner Screen**: Camera-based scanning interface
- **No Internet Screen**: Fallback for connectivity issues

### **State Management**
- Real-time team data synchronization
- Connectivity status monitoring
- Camera permission state handling

## 🚀 Getting Started

### **Prerequisites**
- Android Studio Arctic Fox or later
- Android SDK 26 (Android 8.0) or higher
- Firebase project setup
- Camera-enabled Android device for testing

### **Installation**
1. Clone the repository
2. Open the project in Android Studio
3. Configure Firebase (add your `google-services.json`)
4. Build and run the application

## 📱 Permissions

The app requires the following permissions:
- **Camera**: For QR code scanning functionality
- **Internet**: For Firebase synchronization
- **Network State**: For connectivity monitoring

## 🔒 Security & Privacy

- Participant data is securely stored in Firebase Firestore
- Privacy policy compliance built into team registration
- Secure QR code generation and validation
- Network security with Firebase Authentication integration

## 📊 Data Structure

### **Firebase Collections**
- `participants`: Stores team and member information
- Real-time listeners for live updates
- Structured data validation and error handling

## 📄 License

This project is licensed under the [MIT License](LICENSE).
