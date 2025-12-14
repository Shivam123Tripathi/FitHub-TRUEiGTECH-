import React from "react";
import { useNavigate } from "react-router-dom";
import "./LoginSelection.css";

/**
 * Login Selection Component
 * Allows users to choose between logging in as a User or Trainer
 */
const LoginSelection = () => {
  const navigate = useNavigate();

  const handleUserLogin = () => {
    navigate("/login/user");
  };

  const handleTrainerLogin = () => {
    navigate("/login/trainer");
  };

  return (
    <div className="login-selection">
      <div className="login-selection-container">
        <h1 className="login-selection-title">Welcome Back!</h1>
        <p className="login-selection-subtitle">
          Choose your account type to continue
        </p>

        <div className="login-selection-cards">
          {/* User Login Card */}
          <div className="login-selection-card" onClick={handleUserLogin}>
            <div className="login-card-icon">🏃</div>
            <h2 className="login-card-title">Login as User</h2>
            <p className="login-card-description">
              Access your fitness plans and track your progress
            </p>
            <button className="login-card-button">Login</button>
          </div>

          {/* Trainer Login Card */}
          <div className="login-selection-card" onClick={handleTrainerLogin}>
            <div className="login-card-icon">💪</div>
            <h2 className="login-card-title">Login as Trainer</h2>
            <p className="login-card-description">
              Manage your clients and fitness programs
            </p>
            <button className="login-card-button trainer-button">Login</button>
          </div>
        </div>

        <div className="login-footer-links">
          <button onClick={() => navigate("/")} className="back-button">
            ← Back to Home
          </button>
          <p className="signup-link">
            Don't have an account?{" "}
            <span onClick={() => navigate("/signup")} className="link-text">
              Sign up
            </span>
          </p>
        </div>
      </div>
    </div>
  );
};

export default LoginSelection;
