# Shopping Mall System

This project is a Spring Boot application for building an online shopping mall management system for small businesses. Users are authenticated via JWT, can create and manage their shops, and other users can buy and sell products through the system. Administrators can approve or reject shop creation and closure requests.

## Technology Stack
- Java 17
- Spring Boot 3.3.3
- Spring Security: For handling authentication with JWT.
- JWT (JSON Web Token): Used for issuing and validating authentication tokens.
- JPA/Hibernate: For database interaction.
- SQLite: The database used during development.
- Gradle: The project build tool.

## Installation and Running
### Prerequisites
 1. Java 17
 2. Gradle
### Installation Steps
 1. Clone this repository.
 2. Open the cloned folder using Intellij IDEA.
 3. Run the main method in project3Application.java.
Since the project uses SQLite and includes a data.sql file to insert test data, you can immediately test the features upon running the application.

## Postman API Testing
#### You can test the following key endpoints using Postman:
### Users
1. User Registration: POST /users/register
2. Login: POST /token/issue
3. Update: Put /users/update

### Shop and item 
1. Update shop's information: PUT /shop/update/{userId}
2. Sending shop opening reuqest: POST /shop/request-open/{userId}
3. Sending shop closing reuqest: POST /shop/request-close/{userId}
4. Search shop: GET /shop/search/shop/by-name
5. Search item: GET /shop/search/item

Additionally, there are many more CRUD endpoints available, covering various aspects of user, shop, and item management throughout the system.








