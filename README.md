# Planning-Poker

## 🎯 Overview

**Planning Poker** is a collaborative game used in **Agile software development** to estimate the effort required for tasks or features. By playing this game, teams can reach a consensus on task complexity in a fun, efficient, and democratic way.

This project provides a **digital implementation** of the Planning Poker technique,
making it easy for Agile teams to estimate tasks remotely or in person.

The application supports localization in three languages: **English**, **Ukrainian**, and **Swedish**,
allowing teams from different regions to use it seamlessly.

---

## 🛠️ Why This Project Is Important

Accurate **task estimation** is essential for successful project management in Agile environments. Poor estimates can lead to:

- **Missed deadlines**
- **Ineffective resource allocation**
- **Unclear project expectations**

**Planning Poker** solves these issues by:

- **Promoting collaboration**: Every team member contributes to the estimation process.
- **Reducing bias**: Estimates are made anonymously, preventing dominant voices from influencing others.
- **Improving accuracy**: A collective approach ensures more accurate estimations and early risk detection.
- **Making estimation fun**: The game-like format reduces stress, making the process more enjoyable for the team.

---

## 🧰 Tech Stack

### **Core Technologies**
- **Java 17**: The programming language for the application.
- **Spring Boot**:
    - **Spring Data JPA**: For database interactions.
    - **Spring Security**: For authentication and authorization.
    - **Spring Web**: For REST APIs and web services.
    - **Spring WebSocket**: To support real-time communication.
    - **Spring Events**: For event-driven architecture.

### **Database and Migrations**
- **PostgreSQL**: Relational database for data storage.
- **Flyway**: Database migration tool for version control.

### **Frontend & UI**
- **Thymeleaf**: Template engine for dynamic HTML rendering.
- **WebJars**: For managing JavaScript and CSS dependencies.
    - **Bootstrap**: For responsive design.
    - **jQuery**: For dynamic front-end interactions.
    - **Moment.js**: For date and time formatting.

### **Utilities and Libraries**
- **Lombok**: Reduces boilerplate code with annotations.
- **MapStruct**: For object mapping between layers.

### **Testing**
- **JUnit**: For unit and integration testing.
- **Mockito**: For mocking dependencies in tests.
- **TestContainers**: For testing with PostgreSQL in isolated containers.

### **DevOps and Deployment**
- **Docker**: To containerize the application and ensure consistency across environments.

### **ORM and Build Tools**
- **Hibernate**: For object-relational mapping.
- **Gradle**: Build automation tool for managing dependencies and tasks.



## 🚀 Installation and Launch Guide

Follow these steps to set up and run the project:

### 1. **Clone the Repository**
Clone the repository to your local machine using SSH:

```bash
git clone git@github.com:Volodymyr-Petrunin/Rock-Paper-Scissors.git
```

### 2. **Build the project** 🛠️  
   Run the following command to clean and build the project:  
   
```bash
./gradlew clean build
```

### 3. **Start Docker** 🐳  
   Make sure Docker is running.

### 4. **Start Docker Compose** 🔥  
   Use Docker Compose to bring up the containers.
    
```bash
docker-compose -f ./docker/docker-compose.yml up
```

### 5. **Open the app** 🌐  
   Go to [http://localhost:8080/](http://localhost:8080/) in your browser.

### 6. **Login using mock data** 🔑  
Use the following credentials to log in to the application:

| Email                   | Password   |
|-------------------------|------------|
| admin@example.com       | adminPASS_ |
| user@example.com        | userPASS_  |
| user2@example.com       | user2PASS_ |
| user3@example.com       | user3PASS_ |

## And that's it! Congratulations on the successful setup Launch Guide! 🎉


## 📬 Contact
For any questions or feedback, feel free to reach out:
- **LinkedIn:** [LinkedIn Profile](https://www.linkedin.com/in/volodymyr-petrunin/)
- **Instagram:** [Instagram Profile](https://www.instagram.com/vovapetrunin/)
- **Email:** petruninvolodymyr@gmail.com
- **Telegram:** [Telegram Profile](https://t.me/VolodymyrPetrunin)
- **GitHub:** [GitHub Profile](https://github.com/Volodymyr-Petrunin)
