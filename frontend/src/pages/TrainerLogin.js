import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./TrainerLogin.css";

/**
 * Trainer Login Component
 * Handles trainer authentication and JWT token storage
 */
const TrainerLogin = () => {
  const navigate = useNavigate();

  // Form state management
  const [formData, setFormData] = useState({
    email: "",
    password: "",
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
      // API call to backend login endpoint
      const response = await fetch(
        "http://localhost:8080/api/auth/login/trainer",
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
        // Store JWT token and trainer data in localStorage
        localStorage.setItem("token", data.token);
        localStorage.setItem("trainerId", data.id);
        localStorage.setItem("trainerRole", data.role);
        localStorage.setItem("trainerEmail", data.email);
        localStorage.setItem("trainerName", data.fullName);

        console.log("Login successful:", data);

        // SUCCESS: Redirect to trainer dashboard after successful login
        navigate("/trainer/dashboard");
      } else {
        // Handle error response
        setError(data.message || "Invalid email or password");
      }
    } catch (err) {
      console.error("Login error:", err);
      setError("Unable to connect to server. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="trainer-login-page">
      <div className="trainer-login-container">
        <div className="trainer-login-header">
          <h1 className="trainer-login-title">Trainer Login</h1>
          <p className="trainer-login-subtitle">
            Welcome back! Manage your fitness programs
          </p>
        </div>

        <form className="trainer-login-form" onSubmit={handleSubmit}>
          {/* Display error message if any */}
          {error && <div className="error-message">{error}</div>}

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
              placeholder="Enter your password"
            />
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="trainer-login-submit-button"
            disabled={loading}
          >
            {loading ? "Logging in..." : "Login"}
          </button>

          {/* Footer links */}
          <div className="form-footer">
            <button
              type="button"
              onClick={() => navigate("/login")}
              className="back-link-button"
            >
              ← Back to login options
            </button>
            <p className="signup-prompt">
              Don't have an account?{" "}
              <span
                onClick={() => navigate("/signup/trainer")}
                className="signup-link"
              >
                Sign up
              </span>
            </p>
          </div>
        </form>
      </div>
    </div>
  );
};

export default TrainerLogin;
