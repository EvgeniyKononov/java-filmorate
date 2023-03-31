# Java-filmorate

Project realizing the backend for Filmorate app.

# Description
Stack: Java 11, REST based on Spring Boot, Maven, Lombock, H2 SQL, JDBC, SLF4J

ER-диаграмма:
![ER-диаграмма](Filmorate.jpg)

Project has 2 main entity's:
1. Film - described Film and connected with Genre, PG rating and Likes.
2. User - user of filmorate app. Connected to Likes for Film and his Friends.


Users also can make friends to watch list of films liked by friends to watch it.

Application developed as standard REST architecture based on Spring Boot.
DB realized via H2 SQL and can be stored in the file on PC.
Exchange of data between DB and service realized via JDBC.

# Deploying

For checking project need at least JDK 11 with Maven and Spring Boot.