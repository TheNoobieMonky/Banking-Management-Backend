# Banking-Management-Backend
A secure and modular Spring Boot backend for managing users, bank accounts, and transactions.
This project currently includes only the backend, and a frontend can be added later if needed (React, Angular, etc.).

## Features

### Authentication & Security
  
- Login using JWT tokens
  
- Password hashing
  
- Role-based security
  
- Request filtering using custom JWT filter

### User Management

- Register users

- Login users

- Fetch user details

### Account Management

- Create accounts

- Check balance

- Fetch account details

### Transaction Management

- Deposit money

- Withdraw money

- Transfer money between accounts

- View transaction history

## Tech Stack

- Java 17+

- Spring Boot

- Spring Web

- Spring Security (JWT)

- Spring Data JPA (Hibernate)

- Maven

- MySQL / PostgreSQL

## Folder Structure
``` markdown
src
 ├── main
 │   ├── java/com/banking/BankingManagement
 │   │   ├── Controllers/
 │   │   ├── Services/
 │   │   ├── Repository/
 │   │   ├── Model/
 │   │   ├── Config/
 │   │   ├── Filter/
 │   │   └── BankingManagementApplication.java
 │   └── resources
 │       └── application.properties
 └── test
```
