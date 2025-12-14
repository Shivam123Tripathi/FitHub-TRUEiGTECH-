# FitPlanHub Backend

Spring Boot backend API for the FitPlanHub fitness platform.

## Features

- **User Registration**: Complete signup flow with validation
- **Trainer Registration**: Professional signup with experience tracking
- **Password Hashing**: SHA-256 password encryption
- **Input Validation**: Comprehensive validation using Jakarta Validation
- **RESTful APIs**: Clean REST API design
- **CORS Support**: Configured for React frontend communication

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (Development)
- MySQL Support (Production)
- Lombok
- Maven

## Project Structure

```
backend/
├── src/
│   └── main/
│       ├── java/com/fitplanhub/
│       │   ├── config/
│       │   │   └── CorsConfig.java
│       │   ├── controller/
│       │   │   └── AuthController.java
│       │   ├── dto/
│       │   │   ├── ApiResponse.java
│       │   │   ├── UserSignupRequest.java
│       │   │   └── TrainerSignupRequest.java
│       │   ├── entity/
│       │   │   ├── User.java
│       │   │   └── Trainer.java
│       │   ├── repository/
│       │   │   ├── UserRepository.java
│       │   │   └── TrainerRepository.java
│       │   ├── service/
│       │   │   ├── UserService.java
│       │   │   └── TrainerService.java
│       │   └── FitPlanHubApplication.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Installation

1. Navigate to the backend directory:

   ```bash
   cd backend
   ```

2. Build the project:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   mvn spring-boot:run
   ```

4. The backend will start on `http://localhost:8080`

## API Endpoints

### User Signup

**POST** `/api/auth/signup/user`

**Request Body:**

```json
{
  "fullName": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "age": 25,
  "gender": "male",
  "height": 175.5,
  "weight": 70.0,
  "fitnessGoal": "weight_loss"
}
```

**Success Response (201):**

```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "userId": 1,
    "email": "john@example.com",
    "fullName": "John Doe",
    "fitnessGoal": "weight_loss"
  }
}
```

**Error Response (400):**

```json
{
  "success": false,
  "message": "Email already registered",
  "data": null
}
```

### Trainer Signup

**POST** `/api/auth/signup/trainer`

**Request Body:**

```json
{
  "fullName": "Jane Smith",
  "email": "jane@example.com",
  "password": "password123",
  "yearsOfExperience": 5,
  "specializations": "Weight Loss, Strength Training, Yoga",
  "bio": "Certified fitness trainer with 5 years of experience"
}
```

**Success Response (201):**

```json
{
  "success": true,
  "message": "Trainer registered successfully",
  "data": {
    "trainerId": 1,
    "email": "jane@example.com",
    "fullName": "Jane Smith",
    "specializations": "Weight Loss, Strength Training, Yoga",
    "yearsOfExperience": 5
  }
}
```

## Database Configuration

### H2 Database (Development - Default)

- URL: `jdbc:h2:mem:fitplanhub`
- Console: `http://localhost:8080/h2-console`
- Username: `sa`
- Password: (empty)

### MySQL (Production)

To switch to MySQL, uncomment the MySQL configuration in `application.properties` and comment out the H2 configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fitplanhub?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

## Database Schema

### Users Table

- `id` (BIGINT, Primary Key)
- `full_name` (VARCHAR)
- `email` (VARCHAR, Unique)
- `password` (VARCHAR, Hashed)
- `age` (INT)
- `gender` (VARCHAR)
- `height` (DOUBLE)
- `weight` (DOUBLE)
- `fitness_goal` (VARCHAR)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### Trainers Table

- `id` (BIGINT, Primary Key)
- `full_name` (VARCHAR)
- `email` (VARCHAR, Unique)
- `password` (VARCHAR, Hashed)
- `years_of_experience` (INT)
- `specializations` (VARCHAR)
- `bio` (VARCHAR)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## Testing

Run tests:

```bash
mvn test
```

## Security Notes

- Passwords are hashed using SHA-256 (consider upgrading to BCrypt for production)
- CORS is configured to allow requests from `http://localhost:3000`
- Input validation is enforced using Jakarta Validation annotations
- Duplicate email detection prevents multiple accounts with same email

## Future Enhancements

- JWT authentication
- BCrypt password hashing
- Email verification
- Password reset functionality
- Role-based access control
- Additional user/trainer profile endpoints
