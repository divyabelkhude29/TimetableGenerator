import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../../services/authService"; // Update path if different
import "../../styles/Home.css";

const Home = () => {
  const navigate = useNavigate();

  const [loading, setLoading] = useState(true);
  const [adminExists, setAdminExists] = useState(false);

  useEffect(() => {
    checkAdminStatus();
  }, []);

  const checkAdminStatus = async () => {
    try {
      const response = await authService.checkAdminExists();

      // backend returns true/false
      setAdminExists(response.data.exists);
    } catch (error) {
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <div className="loading-container">
        <div className="spinner"></div>
        <h3>Loading...</h3>
      </div>
    );
  }

  return (
    <div className="home-container">
      <div className="contain">
        {/* Left Section */}
        <div className="left-section">
          <div className="content">
            <h1>Get Started</h1>

            <h2>Generate Automatic Timetable</h2>

            <p>
              Smart Timetable Generator provides a fast, reliable automated
              timetable generation system for administrators, faculty and
              students.
            </p>
          </div>
        </div>

        {/* Right Section */}
        <div className="right-section">
          <div className="card">
            <h2>Welcome User</h2>

            <p>
              {adminExists
                ? "Administrator account already exists."
                : "No administrator found. Register the first administrator to get started."}
            </p>

            {adminExists ? (
              <button
                className="primary-btn"
                onClick={() => navigate("/login")}
              >
                Login
              </button>
            ) : (
              <button
                className="primary-btn"
                onClick={() => navigate("/register")}
              >
                Register
              </button>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
