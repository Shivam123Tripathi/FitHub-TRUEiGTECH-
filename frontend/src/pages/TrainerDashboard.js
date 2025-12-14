import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./TrainerDashboard.css";

// Trainer dashboard - create, edit, delete plans
const TrainerDashboard = () => {
  const navigate = useNavigate();
  const [trainerName, setTrainerName] = useState("");
  const [plans, setPlans] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingPlan, setEditingPlan] = useState(null);
  const [form, setForm] = useState({
    title: "",
    description: "",
    price: "",
    duration: "",
  });
  const [error, setError] = useState("");

  useEffect(() => {
    const name = localStorage.getItem("trainerName");
    if (name) {
      setTrainerName(name);
    }
    loadPlans();
  }, []);

  const loadPlans = async () => {
    try {
      const token = localStorage.getItem("token");
      const resp = await fetch("http://localhost:8080/api/trainer/plans", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      const json = await resp.json();
      if (json.success) {
        setPlans(json.data);
      }
    } catch (err) {
      console.error("Error fetching plans:", err);
    } finally {
      setIsLoading(false);
    }
  };

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  const openCreateModal = () => {
    setEditingPlan(null);
    setForm({ title: "", description: "", price: "", duration: "" });
    setShowModal(true);
    setError("");
  };

  const openEditModal = (plan) => {
    setEditingPlan(plan);
    setForm({
      title: plan.title,
      description: plan.description,
      price: plan.price,
      duration: plan.duration,
    });
    setShowModal(true);
    setError("");
  };

  const closeModal = () => {
    setShowModal(false);
    setEditingPlan(null);
    setForm({ title: "", description: "", price: "", duration: "" });
    setError("");
  };

  const updateField = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const savePlan = async (e) => {
    e.preventDefault();
    setError("");

    const token = localStorage.getItem("token");
    const url = editingPlan
      ? `http://localhost:8080/api/trainer/plans/${editingPlan.id}`
      : "http://localhost:8080/api/trainer/plans";

    const method = editingPlan ? "PUT" : "POST";

    try {
      const resp = await fetch(url, {
        method: method,
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          ...form,
          price: parseFloat(form.price),
          duration: parseInt(form.duration),
        }),
      });

      const json = await resp.json();

      if (json.success) {
        loadPlans();
        closeModal();
      } else {
        setError(json.message || "Failed to save plan");
      }
    } catch (err) {
      setError("Unable to connect to server");
    }
  };

  const deletePlan = async (planId) => {
    // double check before deleting
    if (!window.confirm("Are you sure you want to delete this plan?")) {
      return;
    }

    const token = localStorage.getItem("token");

    try {
      const resp = await fetch(
        `http://localhost:8080/api/trainer/plans/${planId}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const json = await resp.json();

      if (json.success) {
        loadPlans();
      } else {
        alert(json.message || "Failed to delete plan");
      }
    } catch (err) {
      alert("Unable to connect to server");
    }
  };

  return (
    <div className="trainer-dashboard">
      <nav className="dashboard-nav">
        <h2>FitPlanHub Trainer</h2>
        <button onClick={logout} className="logout-btn">
          Logout
        </button>
      </nav>

      <div className="dashboard-container">
        <div className="dashboard-header">
          <h1>Welcome, {trainerName}!</h1>
          <button onClick={openCreateModal} className="create-btn">
            + Create New Plan
          </button>
        </div>

        {isLoading ? (
          <p>Loading plans...</p>
        ) : plans.length === 0 ? (
          <div className="empty-state">
            <p>You haven't created any fitness plans yet.</p>
            <button onClick={openCreateModal} className="create-btn-secondary">
              Create Your First Plan
            </button>
          </div>
        ) : (
          <div className="plans-grid">
            {plans.map((plan) => (
              <div key={plan.id} className="plan-card">
                <h3>{plan.title}</h3>
                <p className="plan-description">{plan.description}</p>
                <div className="plan-details">
                  <span className="plan-price">${plan.price}</span>
                  <span className="plan-duration">{plan.duration} days</span>
                </div>
                <div className="plan-actions">
                  <button onClick={() => openEditModal(plan)} className="edit-btn">
                    Edit
                  </button>
                  <button onClick={() => deletePlan(plan.id)} className="delete-btn">
                    Delete
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      {/* Modal for Create/Edit Plan */}
      {showModal && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <h2>{editingPlan ? "Edit Plan" : "Create New Plan"}</h2>

            <form onSubmit={savePlan}>
              {error && <div className="error-message">{error}</div>}

              <div className="form-group">
                <label>Title</label>
                <input
                  type="text"
                  name="title"
                  value={form.title}
                  onChange={updateField}
                  required
                  placeholder="e.g., Weight Loss Program"
                />
              </div>

              <div className="form-group">
                <label>Description</label>
                <textarea
                  name="description"
                  value={form.description}
                  onChange={updateField}
                  required
                  rows="4"
                  placeholder="Describe your fitness plan..."
                />
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label>Price ($)</label>
                  <input
                    type="number"
                    name="price"
                    value={form.price}
                    onChange={updateField}
                    required
                    min="0"
                    step="0.01"
                    placeholder="49.99"
                  />
                </div>

                <div className="form-group">
                  <label>Duration (days)</label>
                  <input
                    type="number"
                    name="duration"
                    value={form.duration}
                    onChange={updateField}
                    required
                    min="1"
                    placeholder="30"
                  />
                </div>
              </div>

              <div className="modal-actions">
                <button
                  type="button"
                  onClick={closeModal}
                  className="cancel-btn"
                >
                  Cancel
                </button>
                <button type="submit" className="submit-btn">
                  {editingPlan ? "Update Plan" : "Create Plan"}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default TrainerDashboard;
