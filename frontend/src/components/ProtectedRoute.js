import React from "react";
import { Navigate } from "react-router-dom";

/**
 * Protected Route Component
 * Prevents unauthorized access to role-specific routes
 *
 * @param {string} requiredRole - "USER" or "TRAINER"
 * @param {ReactNode} children - Component to render if authorized
 */
const ProtectedRoute = ({ requiredRole, children }) => {
  const token = localStorage.getItem("token");
  const userRole =
    localStorage.getItem("userRole") || localStorage.getItem("trainerRole");

  // If not logged in, redirect to login page
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // If role doesn't match, redirect to appropriate dashboard or home
  if (requiredRole && userRole !== requiredRole) {
    if (userRole === "USER") {
      return <Navigate to="/user/dashboard" replace />;
    } else if (userRole === "TRAINER") {
      return <Navigate to="/trainer/dashboard" replace />;
    } else {
      return <Navigate to="/" replace />;
    }
  }

  // Authorized - render the component
  return children;
};

export default ProtectedRoute;
