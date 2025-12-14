import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./PlanDetails.css";

/**
 * Plan Details Component
 * Shows preview for non-subscribers, full details for subscribers
 */
const PlanDetails = () => {
  const { planId } = useParams();
  const navigate = useNavigate();
  const [plan, setPlan] = useState(null);
  const [isSubscribed, setIsSubscribed] = useState(false);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchPlanDetails();
  }, [planId]);

  const fetchPlanDetails = async () => {
    try {
      const token = localStorage.getItem("token");
      const headers = token ? { Authorization: `Bearer ${token}` } : {};

      const response = await fetch(
        `http://localhost:8080/api/plans/${planId}`,
        {
          headers,
        }
      );

      const data = await response.json();

      if (data.success) {
        setPlan(data.data);
        // Check if plan has full details (subscribed) or just preview
        setIsSubscribed(data.data.description !== undefined);
      } else {
        setError(data.message || "Plan not found");
      }
    } catch (err) {
      setError("Unable to load plan details");
    } finally {
      setLoading(false);
    }
  };

  const handleSubscribe = async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/login");
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/user/subscribe/${planId}`,
        {
          method: "POST",
          headers: { Authorization: `Bearer ${token}` },
        }
      );

      const data = await response.json();

      if (data.success) {
        alert("Successfully subscribed!");
        fetchPlanDetails(); // Refresh to show full details
      } else {
        alert(data.message || "Failed to subscribe");
      }
    } catch (err) {
      alert("Unable to connect to server");
    }
  };

  const handleBack = () => {
    navigate(-1);
  };

  if (loading) {
    return (
      <div className="plan-details-page">
        <p className="loading-text">Loading...</p>
      </div>
    );
  }

  if (error || !plan) {
    return (
      <div className="plan-details-page">
        <div className="error-container">
          <h2>⚠️ {error || "Plan not found"}</h2>
          <button onClick={handleBack} className="back-btn">
            Go Back
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="plan-details-page">
      <div className="plan-details-container">
        <button onClick={handleBack} className="back-link">
          ← Back
        </button>

        <div className="plan-header">
          <h1>{plan.title}</h1>
          <p className="trainer-info">by {plan.trainerName}</p>
        </div>

        {isSubscribed ? (
          // Full details for subscribers
          <div className="plan-content">
            <div className="subscribed-badge">
              ✓ You're subscribed to this plan
            </div>

            <div className="plan-section">
              <h3>Plan Details</h3>
              <div className="plan-meta">
                <div className="meta-item">
                  <span className="meta-label">Price:</span>
                  <span className="meta-value">${plan.price}</span>
                </div>
                <div className="meta-item">
                  <span className="meta-label">Duration:</span>
                  <span className="meta-value">{plan.duration} days</span>
                </div>
              </div>
            </div>

            <div className="plan-section">
              <h3>Description</h3>
              <p className="full-description">{plan.description}</p>
            </div>

            <div className="plan-section">
              <h3>What's Included</h3>
              <ul className="included-list">
                <li>Full access to all workout routines</li>
                <li>Personalized nutrition guidance</li>
                <li>Progress tracking tools</li>
                <li>Direct support from trainer</li>
              </ul>
            </div>
          </div>
        ) : (
          // Preview for non-subscribers
          <div className="plan-content">
            <div className="preview-notice">
              🔒 Subscribe to view full plan details
            </div>

            <div className="plan-section">
              <h3>Plan Overview</h3>
              <div className="plan-meta">
                <div className="meta-item">
                  <span className="meta-label">Price:</span>
                  <span className="meta-value highlight">${plan.price}</span>
                </div>
              </div>
            </div>

            <div className="plan-section">
              <p className="preview-text">
                This is a premium fitness plan created by{" "}
                <strong>{plan.trainerName}</strong>. Subscribe now to unlock
                full details, workout routines, and personalized guidance!
              </p>
            </div>

            <button onClick={handleSubscribe} className="subscribe-btn-large">
              Subscribe Now - ${plan.price}
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default PlanDetails;
