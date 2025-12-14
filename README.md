# FitPlanHub

A comprehensive fitness platform connecting users with certified trainers and personalized workout plans.

## Project Overview

FitPlanHub is a full-stack web application that helps fitness enthusiasts achieve their goals through:

- Personalized fitness plans from certified trainers
- User progress tracking
- Trainer-client connections
- Subscription-based fitness programs

## Project Structure

```
FitPlanHub/
├── frontend/          # React frontend application
│   ├── public/
│   ├── src/
│   │   ├── pages/    # Page components
│   │   ├── App.js
│   │   └── index.js
│   ├── package.json
│   └── README.md
│
├── backend/           # Spring Boot backend API
│   ├── src/
│   │   └── main/
│   │       ├── java/com/fitplanhub/
│   │       │   ├── config/
│   │       │   ├── controller/
│   │       │   ├── dto/
│   │       │   ├── entity/
│   │       │   ├── repository/
│   │       │   ├── service/
│   │       │   └── FitPlanHubApplication.java
│   │       └── resources/
│   │           └── application.properties
│   ├── pom.xml
│   └── README.md
│
└── README.md          # This file
```

## Current Features (Phase 1)

### Landing Page ✅

- Hero section with CTA
- Popular fitness plans showcase
- Features highlight section
- About Us section
- Footer with links
- Login button in navigation

### Authentication System ✅

- **Signup Flow:**
  - Signup type selection (User/Trainer)
  - User registration with fitness details
  - Trainer registration with professional info
  - Form validation
  - BCrypt password hashing
- **Login Flow:**
  - Login type selection (User/Trainer)
  - User login with JWT authentication
  - Trainer login with JWT authentication
  - Token storage in localStorage
  - Secure session management

### Backend APIs ✅

- User signup endpoint
- Trainer signup endpoint
- User login endpoint with JWT
- Trainer login endpoint with JWT
- BCrypt password hashing
- Email uniqueness validation
- MySQL database integration
- Role-based access control

## Tech Stack

### Frontend

- React 18
- React Router DOM v6
- CSS (Vanilla)
- Fetch API

### Backend

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security (BCrypt)
- JWT (JSON Web Tokens)
- MySQL Database
- Maven

### Prerequisites

- Node.js (v14+)
- Java 17+
- Maven 3.6+
- MySQL Server 8.0+
- MySQL Workbench (recommended)

### Database Setup

#### 1. Install MySQL

- Download and install MySQL Server
- Install MySQL Workbench for easy database management

#### 2. Create Database

Open MySQL Workbench and run:

```sql
CREATE DATABASE fitplanhub;
```

Or use the provided SQL script:

```bash
mysql -u root -p < backend/database_setup.sql
```

#### 3. Configure Backend

Update `backend/src/main/resources/application.properties` with your MySQL credentials:

````properties
spring.datasource.username=your_username
spring.datasource.password=your_password
### Accessing the Application

1. Open your browser and visit: `http://localhost:3000`
2. Explore the landing page
3. Click "Get Started" to access the signup flow
4. Or click "Login" to access the login flow
5. Choose User or Trainer
6. Fill out the form and submit
7. You'll be logged in and redirected to your dashboard

### Database Access (MySQL Workbench)

1. Open MySQL Workbench
2. Connect to your local MySQL server
3. Select the `fitplanhub` database
## API Documentation

### User Signup
**Endpoint**: `POST /api/auth/signup/user`

**Request**:
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
````

**Response**:

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

### Trainer Signup

**Endpoint**: `POST /api/auth/signup/trainer`

**Request**:

```json
{
  "fullName": "Jane Smith",
  "email": "jane@example.com",
  "password": "password123",
  "yearsOfExperience": 5,
  "specializations": "Weight Loss, Strength Training, Yoga",
  "bio": "Certified fitness trainer"
}
```

**Response**:

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

### User Login

**Endpoint**: `POST /api/auth/login/user`

**Request**:

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response**:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "id": 1,
  "email": "john@example.com",
  "role": "USER",
  "fullName": "John Doe"
}
```

### Trainer Login

**Endpoint**: `POST /api/auth/login/trainer`

**Request**:

```json
{
  "email": "jane@example.com",
  "password": "password123"
## Future Development (Phase 2+)

- [ ] User dashboard with actual functionality
- [ ] Trainer dashboard with client management
- [ ] Protected routes with authentication
- [ ] Token refresh mechanism
- [ ] Password reset functionality
- [ ] Email verification
- [ ] Fitness plan creation and management
- [ ] Subscription system
- [ ] Progress tracking
- [ ] Messaging between users and trainers
- [ ] Payment integration
- [ ] Profile management
- [ ] Search and filter trainers
- [ ] Reviews and ratings

## Security Features

✅ BCrypt password hashing (industry standard)
✅ JWT token authentication
✅ Role-based access control (USER/TRAINER)
✅ Token expiration (24 hours)
✅ CORS configuration
✅ Input validation on frontend and backend
✅ Secure password storage
✅ Protected API endpoints

## Documentation

- **Main README**: Project overview and setup
- **Frontend README**: React app documentation
- **Backend README**: Spring Boot API documentation
- **Login System Documentation**: Complete authentication guide (`LOGIN_SYSTEM_DOCUMENTATION.md`)
    "email": "john@example.com",
    "fullName": "John Doe",
    "fitnessGoal": "weight_loss"
  }
}
```

### Trainer Signup

**Endpoint**: `POST /api/auth/signup/trainer`

**Request**:

```json
{
  "fullName": "Jane Smith",
  "email": "jane@example.com",
  "password": "password123",
  "yearsOfExperience": 5,
  "specializations": "Weight Loss, Strength Training, Yoga",
  "bio": "Certified fitness trainer"
}
```

**Response**:

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

## Future Development (Phase 2+)

- [ ] User dashboard
- [ ] Trainer dashboard
- [ ] Fitness plan creation and management
- [ ] Subscription system
- [ ] Progress tracking
- [ ] Messaging between users and trainers
- [ ] Payment integration
- [ ] Authentication with JWT
- [ ] Profile management
- [ ] Search and filter trainers
- [ ] Reviews and ratings

## Contributing

This is a learning/portfolio project. Feel free to fork and enhance!

## License

This project is open source and available for educational purposes.

## Contact

For questions or suggestions, please open an issue in the repository.
