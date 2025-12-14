# FitPlanHub Frontend

React-based frontend for the FitPlanHub fitness platform.

## Features

- **Landing Page**: Hero section, popular plans, features, about section, and footer
- **Signup Flow**: User and Trainer registration with validation
- **Responsive Design**: Mobile-friendly UI
- **React Router**: Client-side routing

## Tech Stack

- React 18
- React Router DOM v6
- CSS Modules
- Functional Components & Hooks

## Project Structure

```
frontend/
├── public/
│   └── index.html
├── src/
│   ├── pages/
│   │   ├── LandingPage.js
│   │   ├── LandingPage.css
│   │   ├── SignupSelection.js
│   │   ├── SignupSelection.css
│   │   ├── UserSignup.js
│   │   ├── UserSignup.css
│   │   ├── TrainerSignup.js
│   │   ├── TrainerSignup.css
│   │   ├── UserFeed.js
│   │   ├── UserFeed.css
│   │   ├── TrainerDashboard.js
│   │   └── TrainerDashboard.css
│   ├── App.js
│   ├── App.css
│   ├── index.js
│   └── index.css
└── package.json
```

## Setup Instructions

### Prerequisites

- Node.js (v14 or higher)
- npm or yarn

### Installation

1. Navigate to the frontend directory:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the development server:

   ```bash
   npm start
   ```

4. Open your browser and visit: `http://localhost:3000`

## API Configuration

The frontend is configured to connect to the backend at `http://localhost:8080`.

To change this, update the API URLs in:

- `src/pages/UserSignup.js`
- `src/pages/TrainerSignup.js`

## Available Routes

- `/` - Landing Page
- `/signup` - Signup Type Selection
- `/signup/user` - User Signup Form
- `/signup/trainer` - Trainer Signup Form
- `/user/feed` - User Dashboard (Placeholder)
- `/trainer/dashboard` - Trainer Dashboard (Placeholder)

## Build for Production

```bash
npm run build
```

This creates an optimized production build in the `build` folder.

## Notes

- Image placeholders are used throughout the app
- Dashboards are placeholder pages (functionality to be implemented)
- All styling is done with vanilla CSS (no external UI libraries)
