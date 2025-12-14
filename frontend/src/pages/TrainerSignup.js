import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./TrainerSignup.css";

/**
 * Trainer Signup Component
 * Handles trainer registration with professional information
 */
const TrainerSignup = () => {
  const navigate = useNavigate();

  // Form state management
  const [formData, setFormData] = useState({
    fullName: "",
    email: "",
    password: "",
    yearsOfExperience: "",
    specializations: "",
    bio: "",
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
        "http://localhost:8080/api/auth/signup/trainer",
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
        console.log("Trainer registered successfully:", data);
        navigate("/login/trainer");
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
    <div className="trainer-signup-page">
      <div className="trainer-signup-container">
        <div className="trainer-signup-header">
          <h1 className="trainer-signup-title">Become a Trainer</h1>
          <p className="trainer-signup-subtitle">
            Share your expertise and inspire others
          </p>
        </div>

        <form className="trainer-signup-form" onSubmit={handleSubmit}>
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
              placeholder="Enter your professional email"
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

          {/* Years of Experience */}
          <div className="form-group">
            <label htmlFor="yearsOfExperience" className="form-label">
              Years of Experience
            </label>
            <input
              type="number"
              id="yearsOfExperience"
              name="yearsOfExperience"
              value={formData.yearsOfExperience}
              onChange={handleChange}
              className="form-input"
              required
              min="0"
              max="50"
              placeholder="Enter your years of experience"
            />
          </div>

          {/* Specializations */}
          <div className="form-group">
            <label htmlFor="specializations" className="form-label">
              Specializations
            </label>
            <input
              type="text"
              id="specializations"
              name="specializations"
              value={formData.specializations}
              onChange={handleChange}
              className="form-input"
              required
              placeholder="e.g., Weight Loss, Strength Training, Yoga (comma-separated)"
            />
            <small className="form-hint">
              Enter your areas of expertise separated by commas
            </small>
          </div>

          {/* Bio (Optional) */}
          <div className="form-group">
            <label htmlFor="bio" className="form-label">
              Short Bio <span className="optional-label">(Optional)</span>
            </label>
            <textarea
              id="bio"
              name="bio"
              value={formData.bio}
              onChange={handleChange}
              className="form-textarea"
              rows="4"
              placeholder="Tell us about your training philosophy, achievements, and what makes you unique..."
            />
            <small className="form-hint">
              This will be displayed on your trainer profile
            </small>
          </div>

          {/* Submit Button */}
          <button
            type="submit"
            className="trainer-submit-button"
            disabled={loading}
          >
            {loading ? "Creating Account..." : "Create Trainer Account"}
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

export default TrainerSignup;
