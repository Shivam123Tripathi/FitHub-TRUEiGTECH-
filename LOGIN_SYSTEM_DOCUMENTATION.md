# FitPlanHub - Login System Documentation

## ✅ Implementation Complete

### Backend Configuration

#### 1. MySQL Database Setup

**Database Name:** `fitplanhub`

**Configuration in `application.properties`:**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fitplanhub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

**MySQL Workbench Setup:**

1. Open MySQL Workbench
2. Create a new connection (or use existing)
3. Create database: `CREATE DATABASE fitplanhub;`
4. Tables will be auto-created when you run the Spring Boot application

#### 2. Database Tables

**Users Table:**

- `id` - BIGINT (Primary Key, Auto-increment)
- `full_name` - VARCHAR(255)
- `email` - VARCHAR(255) (Unique)
- `password` - VARCHAR(255) (BCrypt hashed)
- `age` - INT
- `gender` - VARCHAR(255)
- `height` - DOUBLE
- `weight` - DOUBLE
- `fitness_goal` - VARCHAR(255)
- `role` - VARCHAR(255) (Default: "USER")
- `created_at` - TIMESTAMP
- `updated_at` - TIMESTAMP

**Trainers Table:**

- `id` - BIGINT (Primary Key, Auto-increment)
- `full_name` - VARCHAR(255)
- `email` - VARCHAR(255) (Unique)
- `password` - VARCHAR(255) (BCrypt hashed)
- `years_of_experience` - INT
- `specializations` - VARCHAR(500)
- `bio` - VARCHAR(1000)
- `role` - VARCHAR(255) (Default: "TRAINER")
- `created_at` - TIMESTAMP
- `updated_at` - TIMESTAMP

#### 3. Security Implementation

**BCrypt Password Hashing:**

- All passwords are hashed using BCrypt before storage
- Strength: 10 rounds (default)
- Passwords are never stored in plain text

**JWT Configuration:**

- Secret Key: Stored in `application.properties`
- Token Expiration: 24 hours (86400000 ms)
- Algorithm: HS256

#### 4. API Endpoints

##### User Login

**Endpoint:** `POST /api/auth/login/user`

**Request:**

```json
{
  "email": "john@example.com",
  "password": "password123"
}
```

**Success Response (200):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJqb2huQGV4YW1wbGUuY29tIiwicm9sZSI6IlVTRVIiLCJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAyNTAwMDAwLCJleHAiOjE3MDI1ODY0MDB9.signature",
  "id": 1,
  "email": "john@example.com",
  "role": "USER",
  "fullName": "John Doe"
}
```

**Error Response (401):**

```json
{
  "success": false,
  "message": "Invalid email or password",
  "data": null
}
```

##### Trainer Login

**Endpoint:** `POST /api/auth/login/trainer`

**Request:**

```json
{
  "email": "jane@example.com",
  "password": "password123"
}
```

**Success Response (200):**

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZW1haWwiOiJqYW5lQGV4YW1wbGUuY29tIiwicm9sZSI6IlRSQUlORVIiLCJzdWIiOiJqYW5lQGV4YW1wbGUuY29tIiwiaWF0IjoxNzAyNTAwMDAwLCJleHAiOjE3MDI1ODY0MDB9.signature",
  "id": 1,
  "email": "jane@example.com",
  "role": "TRAINER",
  "fullName": "Jane Smith"
}
```

### Frontend Implementation

#### 1. Login Flow Pages

**Login Selection Page:** `/login`

- Allows user to choose between User or Trainer login
- Links to respective login forms

**User Login Page:** `/login/user`

- Email and password fields
- Form validation
- Error message display
- Redirects to `/user/dashboard` on success

**Trainer Login Page:** `/login/trainer`

- Email and password fields
- Form validation
- Error message display
- Redirects to `/trainer/dashboard` on success

#### 2. JWT Token Storage

**localStorage Keys:**

- `token` - JWT authentication token
- `userId` / `trainerId` - User/Trainer ID
- `userRole` / `trainerRole` - Role (USER/TRAINER)
- `userEmail` / `trainerEmail` - Email address
- `userName` / `trainerName` - Full name

**Example Storage After Login:**

```javascript
localStorage.setItem("token", data.token);
localStorage.setItem("userId", data.id);
localStorage.setItem("userRole", data.role);
localStorage.setItem("userEmail", data.email);
localStorage.setItem("userName", data.fullName);
```

#### 3. Dashboard Pages

**User Dashboard:** `/user/dashboard`

- Displays welcome message with user name
- Shows "Coming Soon" features
- Logout button (clears localStorage)

**Trainer Dashboard:** `/trainer/dashboard`

- Displays welcome message with trainer name
- Shows "Coming Soon" features
- Logout button (clears localStorage)

### Complete Route Map

```
/                           → Landing Page (with Login button in nav)
/signup                     → Signup Type Selection
/signup/user                → User Signup Form
/signup/trainer             → Trainer Signup Form
/login                      → Login Type Selection
/login/user                 → User Login Form
/login/trainer              → Trainer Login Form
/user/dashboard             → User Dashboard (requires login)
/user/feed                  → User Feed (alias for dashboard)
/trainer/dashboard          → Trainer Dashboard (requires login)
```

### How to Run the Application

#### Backend Setup

1. **Install MySQL:**

   - Download and install MySQL Server
   - Install MySQL Workbench

2. **Create Database:**

   ```sql
   CREATE DATABASE fitplanhub;
   ```

3. **Update Configuration:**

   - Edit `backend/src/main/resources/application.properties`
   - Update MySQL username and password if different from defaults

4. **Run Spring Boot:**

   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

   Backend will start on: `http://localhost:8080`

5. **Verify Database Connection:**
   - Check console logs for successful connection
   - Open MySQL Workbench and verify tables are created

#### Frontend Setup

1. **Install Dependencies:**

   ```bash
   cd frontend
   npm install
   ```

2. **Start React App:**

   ```bash
   npm start
   ```

   Frontend will start on: `http://localhost:3000`

### Testing the Login System

#### 1. Create a User Account

1. Navigate to `http://localhost:3000`
2. Click "Get Started" button
3. Select "Sign up as User"
4. Fill out the form and submit
5. You'll be redirected to the user dashboard

#### 2. Test User Login

1. Click "Logout" on the dashboard
2. Click "Login" in the navbar
3. Select "Login as User"
4. Enter your credentials
5. You'll be logged in and redirected to dashboard

#### 3. Verify Database (MySQL Workbench)

1. Open MySQL Workbench
2. Connect to your database
3. Run query: `SELECT * FROM users;`
4. Verify:
   - User record exists
   - Password is hashed (BCrypt)
   - Role is set to "USER"

#### 4. Verify JWT Token

1. After login, open browser Developer Tools (F12)
2. Go to Application → Local Storage
3. Verify stored values:
   - `token` (JWT string)
   - `userId`, `userEmail`, `userName`, `userRole`

### Error Handling

**Invalid Credentials:**

- Returns 401 Unauthorized
- Message: "Invalid email or password"

**Email Not Found:**

- Returns 401 Unauthorized
- Message: "Invalid email or password" (security: don't reveal if email exists)

**Server Connection Error:**

- Displays: "Unable to connect to server. Please try again later."

**Validation Errors:**

- Empty fields → "Email/Password is required"
- Invalid email format → "Email should be valid"

### Security Features

✅ BCrypt password hashing (10 rounds)
✅ JWT token authentication
✅ CORS configuration for frontend
✅ Input validation on both frontend and backend
✅ Password never exposed in API responses
✅ Role-based access control (USER/TRAINER)
✅ Token expiration (24 hours)

### Architecture

```
Backend Structure:
├── config/
│   ├── CorsConfig.java          → CORS configuration
│   └── SecurityConfig.java      → BCrypt bean
├── controller/
│   └── AuthController.java      → Login & Signup endpoints
├── dto/
│   ├── LoginRequest.java        → Login request DTO
│   ├── LoginResponse.java       → Login response DTO
│   ├── UserSignupRequest.java
│   ├── TrainerSignupRequest.java
│   └── ApiResponse.java
├── entity/
│   ├── User.java                → User entity (with role)
│   └── Trainer.java             → Trainer entity (with role)
├── repository/
│   ├── UserRepository.java
│   └── TrainerRepository.java
├── service/
│   ├── UserService.java         → User signup (BCrypt)
│   ├── TrainerService.java      → Trainer signup (BCrypt)
│   ├── UserAuthService.java     → User login + JWT
│   └── TrainerAuthService.java  → Trainer login + JWT
└── util/
    └── JwtUtil.java             → JWT generation & validation

Frontend Structure:
└── src/
    └── pages/
        ├── LandingPage.js       → Homepage with Login button
        ├── LoginSelection.js    → Login type choice
        ├── UserLogin.js         → User login form
        ├── TrainerLogin.js      → Trainer login form
        ├── UserFeed.js          → User dashboard
        └── TrainerDashboard.js  → Trainer dashboard
```

### Dependencies Added

**Backend (pom.xml):**

- `spring-security-crypto` → BCrypt
- `jjwt-api` → JWT API
- `jjwt-impl` → JWT Implementation
- `jjwt-jackson` → JWT JSON processing

**Frontend (package.json):**

- No new dependencies required (using existing React Router)

### Next Steps (Not Implemented Yet)

- [ ] Protected routes (redirect to login if not authenticated)
- [ ] Token refresh mechanism
- [ ] Remember me functionality
- [ ] Password reset via email
- [ ] Account verification
- [ ] Session management
- [ ] Activity logging
- [ ] Multi-factor authentication

### Troubleshooting

**Issue: Cannot connect to MySQL**

- Solution: Verify MySQL is running, check credentials in `application.properties`

**Issue: Tables not created**

- Solution: Check `spring.jpa.hibernate.ddl-auto=update` is set

**Issue: CORS error in browser**

- Solution: Verify backend CorsConfig allows `http://localhost:3000`

**Issue: JWT token expired**

- Solution: Token expires after 24 hours, user needs to login again

**Issue: Password doesn't match**

- Solution: Remember all new signups use BCrypt, old SHA-256 passwords won't work

---

## 🎉 System Ready!

The complete login system with MySQL database connection, BCrypt password hashing, and JWT authentication is now fully implemented and ready to use!
