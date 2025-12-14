# Fit Tube

A comprehensive fitness platform connecting users with certified trainers and personalized workout plans.

## Project Overview

Fit Tube is a full-stack web application that helps fitness enthusiasts achieve their goals through:

- Personalized fitness plans from certified trainers
- User progress tracking and subscription management
- Trainer search and follow functionality
- Trainer-client connections through a personalized feed
- Dynamic plan browsing and subscription system

## Project Structure

```
FitTubeHub/
├── frontend/          # React frontend application
│   ├── public/
│   ├── src/
│   │   ├── pages/    # Page components (LandingPage, UserFeed, TrainerDashboard, etc.)
│   │   ├── img/      # Images and assets
│   │   ├── App.js
│   │   └── index.js
│   ├── package.json
│   └── README.md
│
├── backend/           # Spring Boot backend API
│   ├── src/
│   │   └── main/
│   │       ├── java/com/fitplanhub/
│   │       │   ├── config/       # Security, JWT configuration
│   │       │   ├── controller/   # REST API controllers
│   │       │   ├── dto/          # Data Transfer Objects
│   │       │   ├── entity/       # JPA entities (User, Trainer, FitnessPlan, etc.)
│   │       │   ├── repository/   # Data access layer
│   │       │   ├── service/      # Business logic layer
│   │       │   └── FitPlanHubApplication.java
│   │       └── resources/
│   │           └── application.properties
│   ├── pom.xml
│   └── README.md
│
└── README.md          # This file
```

## Implemented Features

### 1. Landing Page ✅

- Hero section with call-to-action
- **Dynamic popular fitness plans** fetched from backend API
- Features highlight section
- About Us section
- Responsive design with animations
- Public API endpoint (no authentication required)

### 2. Authentication System ✅

- **Signup Flow:**
  - Role selection (User/Trainer)
  - User registration with fitness details (age, gender, height, weight, goals)
  - Trainer registration with professional info (experience, specializations, bio)
  - Form validation and error handling
  - BCrypt password hashing for security
- **Login Flow:**
  - Login type selection (User/Trainer)
  - JWT-based authentication for both roles
  - Token storage in localStorage
  - Secure session management
  - Automatic redirection to role-specific dashboards

### 3. User Dashboard (User Feed) ✅

- **Three Tabs:**
  - **All Plans**: Browse all available fitness plans from any trainer
  - **Following**: Personalized feed showing plans from followed trainers only
  - **Find Trainers**: Search and discover trainers
- **Features:**
  - View fitness plans with pricing and duration
  - Subscribe/unsubscribe to fitness plans
  - View subscription status (Subscribed badge)
  - Search trainers by name or specialization
  - Follow/unfollow trainers
  - Real-time trainer follow status
  - Dynamic data loading from backend APIs

### 4. Trainer Dashboard ✅

- Create new fitness plans (title, description, price, duration)
- View all created plans in a grid layout
- Edit existing plans with pre-filled form
- Delete plans with confirmation dialog
- Real-time CRUD operations with backend
- Empty state for trainers without plans
- Responsive modal for create/edit operations

### 5. Trainer Search & Follow System ✅

- **Search Functionality:**
  - Search trainers by name or specialization
  - Real-time search with backend queries
  - View trainer details (experience, specializations, bio)
- **Follow System:**
  - Follow/unfollow trainers
  - Track follow relationships in database
  - Visual follow status indicators
  - Following-based personalized feed

### 6. Subscription Management ✅

- Subscribe to fitness plans
- View subscribed plans with badges
- Track subscription status
- Prevent duplicate subscriptions
- Subscription-aware UI (different buttons for subscribed/unsubscribed plans)

### 7. Backend REST APIs ✅

- **Public Endpoints:**
  - `GET /api/public/plans?limit={n}` - Fetch popular plans (no auth)
- **Authentication Endpoints:**
  - `POST /api/auth/signup/user` - User registration
  - `POST /api/auth/signup/trainer` - Trainer registration
  - `POST /api/auth/login/user` - User login with JWT
  - `POST /api/auth/login/trainer` - Trainer login with JWT
- **User Endpoints (Protected):**
  - `GET /api/user/plans` - Get all fitness plans
  - `GET /api/user/feed` - Get personalized feed from followed trainers
  - `GET /api/user/subscriptions` - Get user's subscriptions
  - `POST /api/user/subscribe/{planId}` - Subscribe to a plan
  - `GET /api/user/trainers` - Get all trainers
  - `GET /api/user/trainers/search?keyword={keyword}` - Search trainers
  - `POST /api/user/trainers/follow/{trainerId}` - Follow a trainer
  - `DELETE /api/user/trainers/follow/{trainerId}` - Unfollow a trainer
- **Trainer Endpoints (Protected):**
  - `GET /api/trainer/plans` - Get trainer's own plans
  - `POST /api/trainer/plans` - Create new fitness plan
  - `PUT /api/trainer/plans/{planId}` - Update existing plan
  - `DELETE /api/trainer/plans/{planId}` - Delete plan

### 8. Database Schema ✅

- **Users Table**: User profiles with fitness details
- **Trainers Table**: Trainer profiles with professional info
- **FitnessPlans Table**: Plans created by trainers
- **Subscriptions Table**: User-Plan relationships
- **Follow Table**: User-Trainer follow relationships
- Foreign key constraints and relationships
- Timestamps for created/updated records

## Tech Stack

### Frontend

- **React 18** - Modern UI library
- **React Router DOM v6** - Client-side routing
- **CSS3** - Custom styling with animations
- **Fetch API** - HTTP requests to backend

### Backend

- **Java 17** - Programming language
- **Spring Boot 3.2.0** - Application framework
- **Spring Data JPA** - Database access layer
- **Spring Security** - Authentication and authorization
- **JWT (jjwt)** - Token-based authentication
- **BCrypt** - Password hashing
- **MySQL** - Relational database
- **Maven** - Dependency management

## Setup and Installation

### Prerequisites

- Node.js (v14 or higher)
- Java 17 or higher
- Maven 3.6+
- MySQL Server 8.0+
- MySQL Workbench (recommended for database management)

### Database Setup

#### 1. Install MySQL

Download and install MySQL Server and MySQL Workbench.

#### 2. Create Database

Open MySQL Workbench and run:

```sql
CREATE DATABASE fitplanhub;
```

#### 3. Configure Database Connection

Update `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fitplanhub?useSSL=false&serverTimezone=UTC
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

**Note:** Replace `your_mysql_username` and `your_mysql_password` with your MySQL credentials.

### Backend Setup

1. **Navigate to backend directory:**

   ```bash
   cd backend
   ```

2. **Install dependencies:**

   ```bash
   mvn clean install
   ```

3. **Run the Spring Boot application:**

   ```bash
   mvn spring-boot:run
   ```

4. **Verify backend is running:**
   - Backend will start on `http://localhost:8080`
   - Check console for "Started FitPlanHubApplication"
   - Database tables will be auto-created by Hibernate

### Frontend Setup

1. **Navigate to frontend directory:**

   ```bash
   cd frontend
   ```

2. **Install dependencies:**

   ```bash
   npm install
   ```

3. **Start the React development server:**

   ```bash
   npm start
   ```

4. **Access the application:**
   - Frontend will open automatically at `http://localhost:3000`
   - Or manually visit `http://localhost:3000` in your browser

## How to Use the Application

### For Users:

1. **Sign Up:**

   - Visit `http://localhost:3000`
   - Click "Get Started" or "Login"
   - Select "User" role
   - Fill in your details (name, email, password, fitness info)
   - Submit to create your account

2. **Login:**

   - Click "Login" on landing page
   - Select "User" role
   - Enter email and password
   - You'll be redirected to the User Dashboard

3. **User Dashboard:**
   - **All Plans Tab:** Browse all available fitness plans
   - **Following Tab:** See plans from trainers you follow
   - **Find Trainers Tab:** Search and follow trainers
   - Click "Subscribe" to subscribe to a plan
   - Use search bar to find trainers by name or specialization
   - Click "Follow" to follow trainers you like

### For Trainers:

1. **Sign Up:**

   - Visit `http://localhost:3000`
   - Click "Get Started"
   - Select "Trainer" role
   - Fill in your professional details
   - Submit to create your trainer account

2. **Login:**

   - Click "Login" on landing page
   - Select "Trainer" role
   - Enter your credentials

3. **Trainer Dashboard:**
   - Click "+ Create New Plan" to add a fitness plan
   - Fill in plan details (title, description, price, duration)
   - View all your created plans in the grid
   - Click "Edit" to update a plan
   - Click "Delete" to remove a plan (with confirmation)

### Testing the Features:

1. **Create a trainer account** and add 2-3 fitness plans
2. **Create a user account** and browse the plans
3. **Follow the trainer** from the "Find Trainers" tab
4. **Subscribe to a plan** from "All Plans" tab
5. **Check the "Following" tab** to see the personalized feed
6. **Test search functionality** by searching for trainer name or specialization

## Database Access (MySQL Workbench)

1. Open MySQL Workbench
2. Connect to your local MySQL server
3. Select the `fitplanhub` database
4. You can view all tables: `users`, `trainers`, `fitness_plans`, `subscriptions`, `follow`
5. Run queries to inspect data:
   ```sql
   SELECT * FROM users;
   SELECT * FROM trainers;
   SELECT * FROM fitness_plans;
   SELECT * FROM subscriptions;
   SELECT * FROM follow;
   ```

## API Documentation

### Public Endpoints (No Authentication Required)

#### Get Popular Plans

**Endpoint**: `GET /api/public/plans?limit={number}`

**Description**: Fetch popular/featured fitness plans for landing page

**Response**:

```json
{
  "success": true,
  "message": "Plans retrieved successfully",
  "data": [
    {
      "id": 1,
      "title": "Weight Loss Bootcamp",
      "description": "Intensive 30-day program...",
      "price": 49.99,
      "duration": 30,
      "trainerId": 1,
      "trainerName": "Jane Smith",
      "createdAt": "2024-12-14 10:30"
    }
  ]
}
```

### Authentication Endpoints

#### User Signup

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
```

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

#### Trainer Signup

**Endpoint**: `POST /api/auth/signup/trainer`

**Request**:

```json
{
  "fullName": "Jane Smith",
  "email": "jane@example.com",
  "password": "password123",
  "yearsOfExperience": 5,
  "specializations": "Weight Loss, Strength Training, Yoga",
  "bio": "Certified fitness trainer with 5 years experience"
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

#### User Login

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

#### Trainer Login

**Endpoint**: `POST /api/auth/login/trainer`

**Request**:

```json
{
  "email": "jane@example.com",
  "password": "password123"
}
```

**Response**:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "id": 1,
  "email": "jane@example.com",
  "role": "TRAINER",
  "fullName": "Jane Smith"
}
```

### User Endpoints (Require JWT Authentication)

**Note**: Include JWT token in Authorization header: `Bearer {token}`

#### Get All Plans

**Endpoint**: `GET /api/user/plans`

**Description**: Get all available fitness plans from all trainers

#### Get Personalized Feed

**Endpoint**: `GET /api/user/feed`

**Description**: Get plans from trainers the user is following

#### Get User Subscriptions

**Endpoint**: `GET /api/user/subscriptions`

**Description**: Get list of plans the user has subscribed to

#### Subscribe to Plan

**Endpoint**: `POST /api/user/subscribe/{planId}`

**Description**: Subscribe to a fitness plan

#### Get All Trainers

**Endpoint**: `GET /api/user/trainers`

**Description**: Get list of all trainers with follow status

#### Search Trainers

**Endpoint**: `GET /api/user/trainers/search?keyword={keyword}`

**Description**: Search trainers by name or specialization

#### Follow Trainer

**Endpoint**: `POST /api/user/trainers/follow/{trainerId}`

**Description**: Follow a trainer to see their plans in personalized feed

#### Unfollow Trainer

**Endpoint**: `DELETE /api/user/trainers/follow/{trainerId}`

**Description**: Unfollow a trainer

### Trainer Endpoints (Require JWT Authentication)

#### Get Trainer's Plans

**Endpoint**: `GET /api/trainer/plans`

**Description**: Get all plans created by the logged-in trainer

#### Create Plan

**Endpoint**: `POST /api/trainer/plans`

**Request**:

```json
{
  "title": "Weight Loss Bootcamp",
  "description": "Intensive 30-day program for rapid weight loss",
  "price": 49.99,
  "duration": 30
}
```

#### Update Plan

**Endpoint**: `PUT /api/trainer/plans/{planId}`

**Description**: Update an existing plan (only if owned by trainer)

#### Delete Plan

**Endpoint**: `DELETE /api/trainer/plans/{planId}`

**Description**: Delete a plan (only if owned by trainer)

## Security Features

✅ **BCrypt Password Hashing** - Industry-standard password encryption  
✅ **JWT Token Authentication** - Secure stateless authentication  
✅ **Role-Based Access Control** - Separate USER and TRAINER roles  
✅ **Token Expiration** - 24-hour token validity  
✅ **CORS Configuration** - Properly configured for localhost development  
✅ **Input Validation** - Frontend and backend validation  
✅ **Protected API Endpoints** - Authentication required for sensitive operations  
✅ **Email Uniqueness** - Prevent duplicate accounts  
✅ **Ownership Verification** - Trainers can only edit/delete their own plans

## Project Highlights

### Code Quality

- ✅ Clean, modular architecture (MVC pattern)
- ✅ Separation of concerns (Controller → Service → Repository)
- ✅ RESTful API design
- ✅ DTO pattern for data transfer
- ✅ Proper error handling and validation
- ✅ Responsive UI design with CSS animations
- ✅ Natural, human-readable code style

### Key Technical Implementations

1. **Dynamic Data Loading**: All data fetched from backend APIs, no hardcoded values
2. **JWT Authentication**: Secure token-based auth with role separation
3. **Follow System**: Many-to-many relationship between users and trainers
4. **Subscription Management**: User-plan relationship tracking
5. **Personalized Feed**: Content filtering based on follow relationships
6. **CRUD Operations**: Full create, read, update, delete for fitness plans
7. **Search Functionality**: Real-time trainer search with database queries
8. **Public API**: Unauthenticated endpoint for landing page data

## Troubleshooting

### Backend Issues

**Problem**: "Cannot connect to database"

- **Solution**: Check MySQL is running, verify credentials in `application.properties`

**Problem**: "Port 8080 already in use"

- **Solution**: Stop other processes using port 8080 or change port in `application.properties`

**Problem**: "Table not found errors"

- **Solution**: Ensure `spring.jpa.hibernate.ddl-auto=update` in `application.properties`

### Frontend Issues

**Problem**: "Cannot connect to backend"

- **Solution**: Verify backend is running on `http://localhost:8080`

**Problem**: "CORS errors in console"

- **Solution**: Check CORS configuration in `SecurityConfig.java` includes `http://localhost:3000`

**Problem**: "JWT token expired"

- **Solution**: Login again to get a fresh token (tokens expire after 24 hours)

## Future Enhancements

- [ ] Password reset functionality
- [ ] Email verification
- [ ] User profile editing
- [ ] Trainer profile pages
- [ ] Plan reviews and ratings
- [ ] Progress tracking with charts
- [ ] In-app messaging system
- [ ] Payment integration (Stripe/PayPal)
- [ ] Workout video uploads
- [ ] Mobile app (React Native)
- [ ] Admin dashboard
- [ ] Social features (share achievements)

## Documentation Files

- **README.md** (this file) - Complete project documentation
- **frontend/README.md** - React app specific documentation
- **backend/README.md** - Spring Boot API documentation
- **LOGIN_SYSTEM_DOCUMENTATION.md** - Authentication system details
- **TRAINER_SEARCH_FEATURE.md** - Trainer search implementation guide

## Contributing

This is a learning/portfolio project demonstrating full-stack development skills. Feel free to fork and enhance!

## License

This project is open source and available for educational purposes.

## Contact

For questions or suggestions, please open an issue in the repository.

---

**Built with using React, Spring Boot, and MySQL**
