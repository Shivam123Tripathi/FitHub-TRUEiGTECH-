import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import heroImage from "../img/hero-img.jpg";
import "./LandingPage.css";

// Main landing page - shows hero, popular plans, features etc
const LandingPage = () => {
  const navigate = useNavigate();
  const [popularPlans, setPopularPlans] = useState([]);
  const [isLoadingPlans, setIsLoadingPlans] = useState(true);

  // grab the popular plans when page loads
  useEffect(() => {
    loadPopularPlans();
  }, []);

  const loadPopularPlans = async () => {
    setIsLoadingPlans(true);
    try {
      const resp = await fetch("http://localhost:8080/api/public/plans?limit=4");
      const json = await resp.json();

      if (json.success && json.data) {
        setPopularPlans(json.data);
      }
    } catch (err) {
      console.error("Failed to fetch plans:", err);
      setPopularPlans([]); // just show empty state if something breaks
    } finally {
      setIsLoadingPlans(false);
    }
  };

  // features we want to highlight on the landing page
  const features = [
    {
      id: 1,
      title: "Certified Trainers",
      description: "Work with experienced and certified fitness professionals.",
      icon: "🏋️"
    },
    {
      id: 2,
      title: "Personalized Fitness Plans",
      description: "Get customized workout plans tailored to your goals.",
      icon: "📋"
    },
    {
      id: 3,
      title: "Secure Subscriptions",
      description: "Safe and reliable payment processing for all subscriptions.",
      icon: "🔒"
    },
    {
      id: 4,
      title: "Track Progress Easily",
      description: "Monitor your fitness journey with comprehensive tracking tools.",
      icon: "📊"
    },
    {
      id: 5,
      title: "Follow Your Favorite Trainers",
      description: "Stay connected with trainers who inspire and motivate you.",
      icon: "⭐"
    },
  ];

  const handleGetStarted = () => navigate("/signup");
  const handleLogin = () => navigate("/login");

  return (
    <div className="landing-page">
      {/* Navigation Bar */}
      <nav className="navbar">
        <div className="navbar-container">
          <h2 className="navbar-brand">FitPlanHub</h2>
          <button className="navbar-login-btn" onClick={handleLogin}>
            Login
          </button>
        </div>
      </nav>

      {/* Hero Section */}
      <section className="hero-section">
        <div className="hero-container">
          <div className="hero-content">
            <h1 className="hero-title">
              Transform Your Body,
              <br />
              Transform Your Life
            </h1>
            <p className="hero-description">
              Join FitPlanHub and discover personalized fitness plans from
              certified trainers. Whether you're starting your fitness journey
              or pushing to the next level, we've got the perfect plan for you.
            </p>
            <button className="cta-button" onClick={handleGetStarted}>
              Get Started
            </button>
          </div>
          <div className="hero-image-placeholder">
            <img
              src={heroImage}
              alt="Fitness Training"
              className="hero-image"
            />
          </div>
        </div>
      </section>

      {/* Popular Plans Section */}
      <section className="popular-plans-section">
        <div className="section-container">
          <h2 className="section-title">Popular Fitness Plans</h2>
          
          {isLoadingPlans ? (
            <p className="loading-text" style={{ textAlign: "center", color: "rgba(255, 255, 255, 0.7)" }}>
              Loading plans...
            </p>
          ) : popularPlans.length === 0 ? (
            <p className="empty-text" style={{ textAlign: "center", color: "rgba(255, 255, 255, 0.7)" }}>
              No plans available at the moment. Check back soon!
            </p>
          ) : (
            <div className="plans-grid">
              {popularPlans.map((plan) => (
                <div key={plan.id} className="plan-card">
                  <h3 className="plan-title">{plan.title}</h3>
                  <p className="plan-description">{plan.description}</p>
                  <div className="plan-details">
                    <span className="plan-price">${plan.price}</span>
                    <span className="plan-duration">{plan.duration} days</span>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </section>

      {/* Features Section */}
      <section className="features-section">
        <div className="section-container">
          <h2 className="section-title">Why Choose FitPlanHub?</h2>
          <div className="features-grid">
            {features.map((feature) => (
              <div key={feature.id} className="feature-card">
                <div className="feature-icon">{feature.icon}</div>
                <h3 className="feature-title">{feature.title}</h3>
                <p className="feature-description">{feature.description}</p>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* About Us Section */}
      <section className="about-section">
        <div className="section-container">
          <h2 className="section-title">About FitPlanHub</h2>
          <div className="about-content">
            <p>
              FitPlanHub is a comprehensive fitness platform that connects users
              with certified trainers and personalized workout plans. Our
              mission is to make fitness accessible, enjoyable, and effective
              for everyone, regardless of their starting point.
            </p>
            <p>
              We believe in the power of consistency and proper guidance. Our
              ecosystem brings together experienced trainers who create tailored
              fitness plans and motivated users who are ready to transform their
              lives. Whether you're looking to lose weight, build muscle,
              improve flexibility, or boost your overall health, FitPlanHub
              provides the tools, support, and community you need to succeed.
            </p>
            <p>
              Join thousands of users who have already started their fitness
              journey with us. Your transformation begins today!
            </p>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="footer">
        <div className="footer-container">
          <div className="footer-content">
            <h3 className="footer-brand">FitPlanHub</h3>
            <p className="footer-tagline">Your Fitness Journey Starts Here</p>
          </div>
          <div className="footer-links">
            <a href="#about" className="footer-link">
              About
            </a>
            <a href="#contact" className="footer-link">
              Contact
            </a>
            <a href="#privacy" className="footer-link">
              Privacy Policy
            </a>
            <a href="#terms" className="footer-link">
              Terms of Service
            </a>
          </div>
          <div className="footer-copyright">
            <p>
              &copy; {new Date().getFullYear()} FitPlanHub. All rights reserved.
            </p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default LandingPage;
