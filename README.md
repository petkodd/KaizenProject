# Sports Events Viewer

An Android application for displaying sports events, built with a focus on clean architecture, modern Android development practices, and Jetpack Compose.

## Tech Stack & Architecture

This project follows a **layered clean architecture**, guided by **SOLID principles**. While the code currently resides in a single module for simplicity, it’s structured in a way that supports future modularization with minimal changes.

### Technologies Used

- **Kotlin** for the entire codebase  
- **Jetpack Compose** for UI development  
- **Hilt** for dependency injection  
- **Coroutines and Flow** for asynchronous programming and reactive streams  
- **Room** for local data persistence and handling favorite events  
- **Unit tests** to validate ViewModel logic and ensure maintainability  

## Architecture Overview

### Data Layer
- **DTOs** for mapping network responses  
- **Mappers** to convert DTOs into domain entities  
- **Data sources** for abstracting access to remote and local data  
- **Repository** as a single source of truth for upper layers  

### Domain Layer
- **Use cases** encapsulate business logic and handle interaction with repositories  

### Presentation Layer
- **ViewModels** manage UI-related logic and expose UI state using `StateFlow`  
- A **base ViewModel class** helps reduce boilerplate  
- Unlike traditional MVI, no `Effects` are used—simplifying the state handling while maintaining clarity  

## Purpose

This project serves as a demonstration of:

- Practical implementation of modern Android development standards  
- Scalable UI architecture using Jetpack Compose and StateFlow  
- Clean, testable, and maintainable codebase  
- Readiness for modularization and large-scale app architecture
