import { useState } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../../services/authService";
import "../../styles/Login.css";
import "../../styles/animation.css";
import { setToken, setUser } from "../../utils/token";

const Login = () => {
  const navigate = useNavigate();

  const [showPassword, setShowPassword] = useState(false);

  const [formData, setFormData] = useState({
    role: "ROLE_ADMIN",
    username: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const data = await authService.login(
        formData.username,
        formData.password,
      );

      // Check selected role with backend role
      if (data.role !== formData.role) {
        alert("Selected role is incorrect.");

        return;
      }

      setToken(data.token);

      setUser({
        username: data.username,
        role: data.role,
        token: data.token,
      });

      switch (data.role) {
        case "ROLE_ADMIN":
          navigate("/admin");
          break;

        case "ROLE_FACULTY":
          navigate("/faculty");
          break;

        case "ROLE_STUDENT":
          navigate("/student");
          break;

        default:
          alert("Unknown Role");
      }
    } catch (error) {
      console.log(error);

      alert(error.response?.data?.message || "Invalid Username or Password");
    }

    //  try {
    //    const data = await authService.login(
    //      formData.username,
    //      formData.password,
    //      formData.role,
    //    );

    //    setToken(data.token);
    //    setUser({
    //      username: data.username,
    //      role: data.role,
    //      token: data.token,
    //    });

    //    switch (data.role) {
    //      case "ROLE_ADMIN":
    //        navigate("/admin");
    //        break;

    //      case "ROLE_FACULTY":
    //        navigate("/faculty");
    //        break;

    //      case "ROLE_STUDENT":
    //        navigate("/student");
    //        break;

    //      default:
    //        alert("Unknown Role");
    //    }
    //  } catch (error) {
    //    console.log(error);

    //    alert(error.response?.data?.message || "Invalid Username or Password");
    //  }
  };

  return (
    <div className="login-container">
      <div className="background-shapes">
        <span className="circle c1"></span>
        <span className="circle c2"></span>
        <span className="circle c3"></span>
        <span className="circle c4"></span>
        <span className="circle c5"></span>
        <span className="circle c6"></span>
      </div>

      <div className="login-card">
        <button className="back-btn" onClick={() => navigate("/")}>
          ← Back to Home
        </button>
        <h1>Welcome User</h1>

        <form onSubmit={handleLogin}>
          <label>Role</label>

          <select name="role" value={formData.role} onChange={handleChange}>
            <option value="ROLE_ADMIN">Admin</option>

            <option value="ROLE_FACULTY">Faculty</option>

            <option value="ROLE_STUDENT">Student</option>
          </select>

          <label>Username</label>

          <input
            type="text"
            name="username"
            placeholder="Enter Username"
            value={formData.username}
            onChange={handleChange}
            required
          />

          <label>Password</label>

          <div className="password-box">
            <input
              type={showPassword ? "text" : "password"}
              name="password"
              placeholder="Enter Password"
              value={formData.password}
              onChange={handleChange}
              required
            />

            <span
              className="eye"
              onClick={() => setShowPassword(!showPassword)}
            >
              {showPassword ? "🙈" : "👁"}
            </span>
          </div>

          <button className="login" type="submit">
            Login
          </button>
        </form>
      </div>
    </div>
  );
};

export default Login;
