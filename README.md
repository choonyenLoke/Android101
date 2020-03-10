# Android 101

# Task 3 - Kinda Advanced Listing App

# Description
This application allow user to search the keyword of a movie to return a list of movie that related to the keyword. In this application, I had used the Retrofit, RxJava2, Dagger2 and Gson as the third party library to retrieve the data from an API provider. 

Retrofit is used to retrieve and upload JSON via REST based webservice. RxJava2 use to write event-driven, asynchronous application and observe the sources that don't support backpressure. While Gson is used to serializing and deserialzing JSON data to a Java Object. Dagger2 is for dependency injection to the application.

So, at first the user can key in a group of word and click the search button. If it is a valid keyword, the system will return a list of movie that related to the keyword. if the keyword is not valid, which is empty or the keyword does not have related movie, the system will prompt and show to the user said that the movie is not found or the search bar edit text cannot be empty. 

As usual, users can click on each movie card view to see the detail of the movie. In the detail activity, user can view all the information that related to the movie which include the title, rating of the movie, director, actors, plot and so on. There will be a return button to allow users to go back to main page of this application.
