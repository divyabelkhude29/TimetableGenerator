import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import authService from '../../services/authService';
import '../../styles/Register.css';
import '../../styles/animation.css';

const Register = () => {
   const navigate = useNavigate();

   const [showPassword, setShowPassword] = useState(false);
   const [showConfirmPassword, setShowConfirmPassword] = useState(false);

   const [loading, setLoading] = useState(false);
   const [error, setError] = useState('');
   const [success, setSuccess] = useState('');

   const [formData, setFormData] = useState({
      username: '',
      firstName: '',
      lastName: '',
      email: '',
      mobile: '',
      password: '',
      confirmPassword: '',
      role: 'ROLE_ADMIN',
   });

   const handleChange = (e) => {
      setFormData({
         ...formData,
         [e.target.name]: e.target.value,
      });
   };

   const handleSubmit = async (e) => {
      e.preventDefault();

      setError('');
      setSuccess('');

      if (formData.password !== formData.confirmPassword) {
         setError('Passwords do not match.');
         return;
      }

      if (formData.password.length < 8) {
         setError('Password must contain at least 8 characters.');
         return;
      }

      try {
         setLoading(true);

         const registerData = {
            username: formData.username,
            firstName: formData.firstName,
            lastName: formData.lastName,
            email: formData.email,
            mobile: formData.mobile,
            password: formData.password,
            role: formData.role,
         };

         const message = await authService.register(registerData);

         setSuccess(message || 'Registration Successful!');

         setTimeout(() => {
            navigate('/login');
         }, 2000);
      } catch (err) {
         console.log('Full Error:', err);
         console.log('Response:', err.response);
         console.log('Response Data:', err.response?.data);

         setError(
            err.response?.data?.message ||
               JSON.stringify(err.response?.data) ||
               'Registration Failed'
         );
      } finally {
         setLoading(false);
      }
   };

   return (
      <div className="register-container">
         <div className="background-shapes">
            <span className="circle c1"></span>
            <span className="circle c2"></span>
            <span className="circle c3"></span>
            <span className="circle c4"></span>
            <span className="circle c5"></span>
            <span className="circle c6"></span>
         </div>
         <div className="register-card">
            <button className="back-btn" onClick={() => navigate('/')}>
               ← Back to Home
            </button>

            <h1>Create Account</h1>

            <p className="subtitle">Register for Timetable Management System</p>

            {error && <div className="error">{error}</div>}

            {success && <div className="success">{success}</div>}

            <form onSubmit={handleSubmit}>
               <div className="row">
                  <div className="input-group">
                     <label>First Name</label>

                     <input
                        type="text"
                        name="firstName"
                        value={formData.firstName}
                        onChange={handleChange}
                        required
                     />
                  </div>

                  <div className="input-group">
                     <label>Last Name</label>

                     <input
                        type="text"
                        name="lastName"
                        value={formData.lastName}
                        onChange={handleChange}
                        required
                     />
                  </div>
               </div>

               <div className="row">
                  <div className="input-group">
                     <label>Username</label>

                     <input
                        type="text"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                     />
                  </div>
                  <div className="input-group">
                     <label>Role</label>

                     <select
                        name="role"
                        value={formData.role}
                        onChange={handleChange}
                     >
                        <option value="ROLE_ADMIN">Admin</option>
                        <option value="ROLE_FACULTY">Faculty</option>
                        <option value="ROLE_STUDENT">Student</option>
                     </select>
                  </div>
               </div>

               <div className="row">
                  <div className="input-group">
                     <label>Email</label>

                     <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                     />
                  </div>

                  <div className="input-group">
                     <label>Mobile Number</label>

                     <input
                        type="text"
                        name="mobile"
                        value={formData.mobile}
                        onChange={handleChange}
                        maxLength={10}
                        required
                     />
                  </div>
               </div>

               <div className="row">
                  <div className="input-group">
                     <label>Password</label>

                     <div className="password-box">
                        <input
                           type={showPassword ? 'text' : 'password'}
                           name="password"
                           value={formData.password}
                           onChange={handleChange}
                           required
                        />

                        <span
                           className="eye"
                           onClick={() => setShowPassword(!showPassword)}
                        >
                           {showPassword ? '🙈' : '👁'}
                        </span>
                     </div>
                  </div>

                  <div className="input-group">
                     <label>Confirm Password</label>

                     <div className="password-box">
                        <input
                           type={showConfirmPassword ? 'text' : 'password'}
                           name="confirmPassword"
                           value={formData.confirmPassword}
                           onChange={handleChange}
                           required
                        />

                        <span
                           className="eye"
                           onClick={() =>
                              setShowConfirmPassword(!showConfirmPassword)
                           }
                        >
                           {showConfirmPassword ? '🙈' : '👁'}
                        </span>
                     </div>
                  </div>
               </div>

               <button className="btn" type="submit" disabled={loading}>
                  {loading ? 'Registering...' : 'Register'}
               </button>
            </form>

            {/* <div className="login-link">
               Already have an account?
               <Link to="/login">Login</Link>
            </div> */}
         </div>
      </div>
   );
};

export default Register;