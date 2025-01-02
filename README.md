# **Notes App**

This is a notes app which you can use in your day-to-day life to take notes and save any info you want.

## **Features**

#### **Save Notes**
  - Create notes with a title, content, and an associated color.
  - Colors are randomized from a palette of five distinct options, or users can manually select their preferred color.
#### **Organize Notes**
  - Sort notes by Title, Date, or Color in both ascending and descending order.
#### **Edit Notes**
  - Modify the title, content, or color of existing notes seamlessly.
#### **Delete & Restore Note**
  - Delete notes with a single tap and restore them if needed.

--- 

## **Demo**

<video src="https://github.com/user-attachments/assets/4c47a9cd-fa67-410e-912a-629b36d881c7" controls="controls" style="max-width: 100%; height: auto;">
    Demo how the app works.
</video>

--- 
  
## **Libraries and Methods Used**
1. **Kotlin**: First-class and official programming language for Android development.
2. **Jetpack compose**: A toolkit for building Android apps that uses a declarative API to simplify and speed up UI development
3. **Material Components for Android**: For modular and customizable Material Design UI components.
4. **Dagger Hilt**: It is a dependency injection library for Android that simplifies providing and managing dependencies across the app's lifecycle.
5. **MVI**: It is an architectural pattern in Android development that ensures unidirectional data flow by processing user intents to produce a single state for the UI.
6. **Clean Architecture**: It is a design approach that separates an application into layers (e.g., presentation, domain, data) to ensure scalability, maintainability, and independence from frameworks or external libraries.
7. **Kotlin Coroutines**: They are a concurrency framework that simplifies asynchronous programming by allowing tasks to be written sequentially while managing threading and suspensions efficiently.
8. **Room**: A persistence library that provides an abstraction layer over SQLite to manage local database operations efficiently in Android applications.
9. **Abstractions**: It is the process of hiding complex implementation details and showing only the essential features of an object or system.
10. **SavedStateHandles**: It is a key-value store used to handle and retain UI-related state in Jetpack's ViewModel across configuration changes.

--- 

## Lessons Learned

While building this app, I learned about:

- Learned how to utilize Room Database for local data management, encompassing concepts such as Entities, DAO, databaseBuilder, and operations including Upsert, Delete, and various Queries.
- Gained insight into the application of abstraction in database interactions to achieve a more organized architecture.
- Investigated the functionalities of Jetpack Compose, including AnimatedVisibility, animations, SnackBar, and the management of multiple composables on a single screen.
- Understood SharedFlows and Channels for the management of one-time events, such as displaying SnackBar messages from the ViewModel.

--- 

## **Contact**
For any questions or feedback, feel free to contact me at sakhare2likhit@gmail.com and also connect with me on Twitter at https://x.com/likhit_sakhare
