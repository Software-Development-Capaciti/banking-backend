 🔧 `backend/README.md`

 🏦 Banking System – Backend

This is the **Java + Spring Boot backend** for the Banking System project. It exposes RESTful APIs to manage accounts, handle transactions, and perform user authentication.

## ⚙️ Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- Maven

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven

### Installation

1. Clone the repository and navigate to the backend folder:

git clone https://github.com/Software-Development-Capaciti/banking-backend.git

cd banking-backend

2. Configure your application.properties:
   
spring.datasource.url=jdbc:mysql://localhost:3306/bankdb
spring.datasource.username=your-username
spring.datasource.password=your-password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080
Build and run the application:

3. Build and run the application:
mvn clean install
mvn spring-boot:run

