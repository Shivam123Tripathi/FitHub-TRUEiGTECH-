import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./UserSignup.css";

/**
 * User Signup Component
 * Handles user registration with fitness-related information
 */
const UserSignup = () => {
  const navigate = useNavigate();

  // Form state management
  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    password: "",
    age: "",
    gender: "",
    height: "",
    weight: "",
    fitnessGoal: "",
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // Handle input changes
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      // API call to backend
      const response = await fetch(
        "http://localhost:8080/api/auth/signup/user",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(formData),
        }
      );

      const data = await response.json();

      if (response.ok) {
        // SUCCESS: Redirect to login page after successful signup
        console.log("User registered successfully:", data);
        navigate("/login/user");
      } else {
        // Handle error response
        setError(data.message || "Signup failed. Please try again.");
      }
    } catch (err) {
      console.error("Signup error:", err);
      setError("Unable to connect to server. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="signup-page">
      <div className="signup-container">
        <div className="signup-header">
          <h1 className="signup-title">Create Your Account</h1>
          <p className="signup-subtitle">Start your fitness journey today</p>
        </div>

        <form className="signup-form" onSubmit={handleSubmit}>
          {/* Display error message if any */}
          {error && <div className="error-message">{error}</div>}

          {/* Full Name */}
          <div className="form-group">
            <label htmlFor="fullName" className="form-label">
              Full Name
            </label>
            <input
              type="text"
              id="fullName"
              name="fullName"
              value={formData.fullName}
              onChange={handleChange}
              className="form-input"
              required
              placeholder="Enter your full name"
            />
          </div>

          {/* Email */}
          <div className="form-group">
            <label htmlFor="email" className="form-label">
              Email
            </label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              className="form-input"
              required
              placeholder="Enter your email"
            />
          </div>

          {/* Password */}
          <div className="form-group">
            <label htmlFor="password" className="form-label">
              Password
            </label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              className="form-input"
              required
              minLength="6"
              placeholder="Enter your password (min 6 characters)"
            />
          </div>

          {/* Age and Gender Row */}
          <div className="form-row">
            <div className="form-group">
              <label htmlFor="age" className="form-label">
                Age
              </label>
              <input
                type="number"
                id="age"
                name="age"
                value={formData.age}
                onChange={handleChange}
                className="form-input"
                required
                min="13"
                max="120"
                placeholder="Age"
              />
            </div>

            <div className="form-group">
              <label htmlFor="gender" className="form-label">
                Gender
              </label>
              <select
                id="gender"
                name="gender"
                value={formData.gender}
                onChange={handleChange}
                className="form-input"
                required
              >
                <option value="">Select Gender</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
                <option value="other">Other</option>
              </select>
            </div>
          </div>

          {/* Height and Weight Row */}
          <div className="form-row">
            <div className="form-group">
              <label htmlFor="height" className="form-label">
                Height (cm)
              </label>
              <input
                type="number"
                id="height"
                name="height"
                value={formData.height}
                onChange={handleChange}
                className="form-input"
                required
                min="50"
                max="300"
                placeholder="Height in cm"
              />
            </div>

            <div className="form-group">
              <label htmlFor="weight" className="form-label">
                Weight (kg)
              </label>
              <input
                type="number"
                id="weight"
                name="weight"
                value={formData.weight}
                onChange={handleChange}
                className="form-input"
                required
                min="20"
                max="500"
                placeholder="Weight in kg"
              />
            </div>
          </div>

          {/* Fitness Goal */}
          <div className="form-group">
            <label htmlFor="fitnessGoal" className="form-label">
              Fitness Goal
            </label>
            <select
              id="fitnessGoal"
              name="fitnessGoal"
              value={formData.fitnessGoal}
              onChange={handleChange}
              className="form-input"
              required
            >
              <option value="">Select Your Goal</option>
              <option value="weight_loss">Weight Loss</option>
              <option value="muscle_gain">Muscle Gain</option>
              <option value="improve_fitness">Improve Overall Fitness</option>
              <option value="flexibility">Improve Flexibility</option>
              <option value="endurance">Build Endurance</option>
              <option value="strength">Increase Strength</option>
            </select>
          </div>

          {/* Submit Button */}
          <button type="submit" className="submit-button" disabled={loading}>
            {loading ? "Creating Account..." : "Sign Up"}
          </button>

          {/* Back to selection */}
          <div className="form-footer">
            <button
              type="button"
              onClick={() => navigate("/signup")}
              className="back-link-button"
            >
              ← Back to signup options
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default UserSignup;
