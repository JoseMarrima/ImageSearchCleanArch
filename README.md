# ImageSearchCleanArch
Readme

In this project I used MVVM with Clean Architecture. MVVM  provides a clean separation of concerns between user interface and domain logic. This project is divided into 3 different layers.

Presentation Layer (ui): Where fragments & activities live in this project and it only contains ui logic. All of the rendering happens here.

Domain Layer: All the business logic of this project exists here. This is where interactors (use cases) live in the project.

Data Layer: As the name suggest, all the data for this project comes from here via a ImageRepositoryImpl which uses Repository pattern. (ImageRepository resides in the domain layer)

The reason I chose to architect the app this way is because it is easy to maintain, test, extremely cohesive & decoupled.


The app is composed of 2 main screens.

SearchImageFragment
Allows you to search for images. At app startup, the screen displays the search results for the keyword “fruits”.

ImageDetailsFragment
This screen provides the details of the searched item which includes tags, username, image and number of likes, downloads and comments on the image.

This project has following features. I’ve tried to use the mentioned libraries.

Libraries 
* Android Support Library 
* Android Architecture Components 
* Android Data Binding 
* Rx Java 
* Paging 3 to load data from network
* Mapper 
* Dagger Hilt for dependency injection * Retrofit for REST api communication 
* Glide for image loading 
* Timber for logging
