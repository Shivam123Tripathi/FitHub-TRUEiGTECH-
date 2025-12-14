import React from "react";
import { useNavigate } from "react-router-dom";
import "./SignupSelection.css";

/**
 * Signup Selection Component
 * Allows users to choose between signing up as a User or Trainer
 */
const SignupSelection = () => {
  const navigate = useNavigate();

  const handleUserSignup = () => {
    navigate("/signup/user");
  };

  const handleTrainerSignup = () => {
    navigate("/signup/trainer");
  };

  return (
    <div className="signup-selection">
      <div className="selection-container">
        <h1 className="selection-title">Join FitPlanHub</h1>
        <p className="selection-subtitle">Choose how you want to get started</p>

        <div className="selection-cards">
          {/* User Signup Card */}
          <div className="selection-card" onClick={handleUserSignup}>
            <div className="card-icon">🏃</div>
            <h2 className="card-title">Sign up as User</h2>
            <p className="card-description">
              Start your fitness journey with personalized plans and expert
              trainers
            </p>
            <ul className="card-features">
              <li>Access to fitness plans</li>
              <li>Connect with trainers</li>
              <li>Track your progress</li>
              <li>Join fitness community</li>
            </ul>
            <button className="card-button">Get Started</button>
          </div>

          {/* Trainer Signup Card */}
          <div className="selection-card" onClick={handleTrainerSignup}>
            <div className="card-icon">💪</div>
            <h2 className="card-title">Sign up as Trainer</h2>
            <p className="card-description">
              Share your expertise and help others achieve their fitness goals
            </p>
            <ul className="card-features">
              <li>Create fitness plans</li>
              <li>Build your client base</li>
              <li>Earn from your expertise</li>
              <li>Grow your brand</li>
            </ul>
            <button className="card-button trainer-button">
              Join as Trainer
            </button>
          </div>
        </div>

        <div className="back-link">
          <button onClick={() => navigate("/")} className="back-button">
            ← Back to Home
          </button>
        </div>
      </div>
    </div>
  );
};

export default SignupSelection;
