import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./UserFeed.css";

// User dashboard - shows plans, followed trainers, etc
const UserFeed = () => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState("");
  const [plans, setPlans] = useState([]);
  const [subscribedPlanIds, setSubscribedPlanIds] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [activeTab, setActiveTab] = useState("all");

  // for the trainer search tab
  const [trainers, setTrainers] = useState([]);
  const [searchKeyword, setSearchKeyword] = useState("");
  const [isLoadingTrainers, setIsLoadingTrainers] = useState(false);

  useEffect(() => {
    const name = localStorage.getItem("userName");
    if (name) setUserName(name);

    // load different data depending on which tab is active
    if (activeTab === "trainers") {
      loadTrainers();
    } else {
      loadPlansData();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [activeTab]);

  const loadPlansData = async () => {
    setIsLoading(true);
    try {
      const token = localStorage.getItem("token");

      // decide which endpoint based on the tab
      const endpoint = activeTab === "feed" 
        ? "http://localhost:8080/api/user/feed"
        : "http://localhost:8080/api/user/plans";

      const [plansResp, subsResp] = await Promise.all([
        fetch(endpoint, {
          headers: { Authorization: `Bearer ${token}` }
        }),
        fetch("http://localhost:8080/api/user/subscriptions", {
          headers: { Authorization: `Bearer ${token}` }
        })
      ]);

      const plansJson = await plansResp.json();
      const subsJson = await subsResp.json();

      if (plansJson.success) setPlans(plansJson.data);
      if (subsJson.success) setSubscribedPlanIds(subsJson.data);
    } catch (err) {
      console.error("Failed to load plans:", err);
    } finally {
      setIsLoading(false);
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/");
  };

  const loadTrainers = async () => {
    setIsLoadingTrainers(true);
    try {
      const token = localStorage.getItem("token");
      const resp = await fetch("http://localhost:8080/api/user/trainers", {
        headers: { Authorization: `Bearer ${token}` }
      });

      const json = await resp.json();
      if (json.success) {
        setTrainers(json.data);
      }
    } catch (err) {
      console.error("Couldn't load trainers:", err);
    } finally {
      setIsLoadingTrainers(false);
    }
  };

  const searchTrainers = async () => {
    if (!searchKeyword.trim()) {
      loadTrainers(); // empty search just shows all
      return;
    }

    setIsLoadingTrainers(true);
    try {
      const token = localStorage.getItem("token");
      const resp = await fetch(
        `http://localhost:8080/api/user/trainers/search?keyword=${encodeURIComponent(searchKeyword)}`,
        {
          headers: { Authorization: `Bearer ${token}` }
        }
      );

      const json = await resp.json();
      if (json.success) setTrainers(json.data);
    } catch (err) {
      console.error("Search failed:", err);
    } finally {
      setIsLoadingTrainers(false);
    }
  };

  const followTrainer = async (trainerId) => {
    try {
      const token = localStorage.getItem("token");
      const resp = await fetch(
        `http://localhost:8080/api/user/trainers/follow/${trainerId}`,
        {
          method: "POST",
          headers: { Authorization: `Bearer ${token}` }
        }
      );

      const json = await resp.json();
      if (json.success) {
        alert("Successfully followed trainer!");
        loadTrainers(); // reload to show updated status
      } else {
        alert(json.message || "Failed to follow trainer");
      }
    } catch (err) {
      alert("Unable to connect to server");
    }
  };

  const unfollowTrainer = async (trainerId) => {
    try {
      const token = localStorage.getItem("token");
      const resp = await fetch(
        `http://localhost:8080/api/user/trainers/follow/${trainerId}`,
        {
          method: "DELETE",
          headers: { Authorization: `Bearer ${token}` }
        }
      );

      const json = await resp.json();
      if (json.success) {
        alert("Successfully unfollowed trainer!");
        loadTrainers();
      } else {
        alert(json.message || "Failed to unfollow trainer");
      }
    } catch (err) {
      alert("Unable to connect to server");
    }
  };

  const handleSubscribe = async (planId) => {
    try {
      const token = localStorage.getItem("token");
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
        loadPlansData(); // Refresh data
      } else {
        alert(data.message || "Failed to subscribe");
      }
    } catch (err) {
      alert("Unable to connect to server");
    }
  };

  const handleViewPlan = (planId) => {
    navigate(`/plans/${planId}`);
  };

  return (
    <div className="user-feed">
      <nav className="feed-nav">
        <h2>FitPlanHub</h2>
        <div className="nav-right">
          <span className="user-greeting">Hi, {userName}</span>
          <button onClick={handleLogout} className="logout-btn">
            Logout
          </button>
        </div>
      </nav>

      <div className="feed-container">
        <div className="feed-header">
          <h1>My Fitness Feed</h1>
          <div className="tabs">
            <button
              className={activeTab === "all" ? "tab active" : "tab"}
              onClick={() => setActiveTab("all")}
            >
              All Plans
            </button>
            <button
              className={activeTab === "feed" ? "tab active" : "tab"}
              onClick={() => setActiveTab("feed")}
            >
              Following
            </button>
            <button
              className={activeTab === "trainers" ? "tab active" : "tab"}
              onClick={() => setActiveTab("trainers")}
            >
              Find Trainers
            </button>
          </div>
        </div>

        {/* Plans Section */}
        {activeTab !== "trainers" && (
          <>
            {isLoading ? (
              <p className="loading-text">Loading plans...</p>
            ) : plans.length === 0 ? (
              <div className="empty-state">
                <p>
                  {activeTab === "feed"
                    ? "You're not following any trainers yet. Browse all plans to find trainers!"
                    : "No plans available at the moment."}
                </p>
              </div>
            ) : (
              <div className="plans-grid">
                {plans.map((plan) => {
                  const isSubscribed = subscribedPlanIds.includes(plan.id);
                  return (
                    <div key={plan.id} className="plan-card">
                      {isSubscribed && (
                        <div className="purchased-badge">✓ Subscribed</div>
                      )}
                      <h3>{plan.title}</h3>
                      <p className="trainer-name">by {plan.trainerName}</p>
                      <p className="plan-description">{plan.description}</p>
                      <div className="plan-details">
                        <span className="plan-price">${plan.price}</span>
                        <span className="plan-duration">
                          {plan.duration} days
                        </span>
                      </div>
                      <div className="plan-actions">
                        {isSubscribed ? (
                          <button
                            onClick={() => handleViewPlan(plan.id)}
                            className="view-btn"
                          >
                            View Details
                          </button>
                        ) : (
                          <>
                            <button
                              onClick={() => handleViewPlan(plan.id)}
                              className="preview-btn"
                            >
                              Preview
                            </button>
                            <button
                              onClick={() => handleSubscribe(plan.id)}
                              className="subscribe-btn"
                            >
                              Subscribe
                            </button>
                          </>
                        )}
                      </div>
                    </div>
                  );
                })}
              </div>
            )}
          </>
        )}

        {/* Trainer Search Section */}
        {activeTab === "trainers" && (
          <>
            <div className="search-section">
              <input
                type="text"
                placeholder="Search trainers by name or specialization..."
                value={searchKeyword}
                onChange={(e) => setSearchKeyword(e.target.value)}
                onKeyPress={(e) => e.key === "Enter" && searchTrainers()}
                className="search-input"
              />
              <button onClick={searchTrainers} className="search-btn">
                Search
              </button>
            </div>

            {isLoadingTrainers ? (
              <p className="loading-text">Loading trainers...</p>
            ) : trainers.length === 0 ? (
              <div className="empty-state">
                <p>No trainers found. Try a different search term.</p>
              </div>
            ) : (
              <div className="trainers-grid">
                {trainers.map((trainer) => (
                  <div key={trainer.trainerId} className="trainer-card">
                    <div className="trainer-info">
                      <h3>{trainer.name}</h3>
                      <p className="trainer-specializations">
                        <strong>Specializations:</strong> {trainer.specializations}
                      </p>
                      <p className="trainer-experience">
                        <strong>Experience:</strong> {trainer.experience} years
                      </p>
                      {trainer.bio && (
                        <p className="trainer-bio">{trainer.bio}</p>
                      )}
                    </div>
                    <button
                      onClick={() =>
                        trainer.following
                          ? unfollowTrainer(trainer.trainerId)
                          : followTrainer(trainer.trainerId)
                      }
                      className={trainer.following ? "unfollow-btn" : "follow-btn"}
                    >
                      {trainer.following ? "Unfollow" : "Follow"}
                    </button>
                  </div>
                ))}
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default UserFeed;
