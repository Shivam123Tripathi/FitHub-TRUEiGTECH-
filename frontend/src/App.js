import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import LandingPage from "./pages/LandingPage";
import SignupSelection from "./pages/SignupSelection";
import UserSignup from "./pages/UserSignup";
import TrainerSignup from "./pages/TrainerSignup";
import LoginSelection from "./pages/LoginSelection";
import UserLogin from "./pages/UserLogin";
import TrainerLogin from "./pages/TrainerLogin";
import UserFeed from "./pages/UserFeed";
import TrainerDashboard from "./pages/TrainerDashboard";
import PlanDetails from "./pages/PlanDetails";
import ProtectedRoute from "./components/ProtectedRoute";
import "./App.css";

/**
 * Main App component with routing configuration
 * Handles navigation with role-based protected routes
 */
function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          {/* Landing page route */}
          <Route path="/" element={<LandingPage />} />

          {/* Signup flow routes */}
          <Route path="/signup" element={<SignupSelection />} />
          <Route path="/signup/user" element={<UserSignup />} />
          <Route path="/signup/trainer" element={<TrainerSignup />} />

          {/* Login flow routes */}
          <Route path="/login" element={<LoginSelection />} />
          <Route path="/login/user" element={<UserLogin />} />
          <Route path="/login/trainer" element={<TrainerLogin />} />

          {/* Protected User routes - require USER role */}
          <Route
            path="/user/feed"
            element={
              <ProtectedRoute requiredRole="USER">
                <UserFeed />
              </ProtectedRoute>
            }
          />
          <Route
            path="/user/dashboard"
            element={
              <ProtectedRoute requiredRole="USER">
                <UserFeed />
              </ProtectedRoute>
            }
          />

          {/* Protected Trainer routes - require TRAINER role */}
          <Route
            path="/trainer/dashboard"
            element={
              <ProtectedRoute requiredRole="TRAINER">
                <TrainerDashboard />
              </ProtectedRoute>
            }
          />

          {/* Plan details route - accessible to all authenticated users */}
          <Route
            path="/plans/:planId"
            element={
              <ProtectedRoute>
                <PlanDetails />
              </ProtectedRoute>
            }
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;