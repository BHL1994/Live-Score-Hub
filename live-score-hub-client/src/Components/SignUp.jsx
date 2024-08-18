import { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthContext from '../Context/AuthContext';

export default function SignUp() {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState([]);
  const navigate = useNavigate();

  const auth = useContext(AuthContext);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setErrors([]);

    if (password !== confirmPassword) {
      setErrors(['Passwords do not match']);
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/create_account', {
        method: 'POST',
        headers: { 
          'Content-Type': 'application/json' 
        },
        body: JSON.stringify({ 
          username, 
          email, 
          password 
        }),
      });

      if (response.status === 201) {
        const loginResponse = await fetch('http://localhost:8080/authenticate', {
          method: 'POST',
          headers: { 
            'Content-Type': 'application/json' 
          },
          body: JSON.stringify({ 
            username, 
            password 
          }),
        });

        if (loginResponse.status === 200) {
          const { jwt_token } = await loginResponse.json();
          auth.login(jwt_token);
          navigate("/");
        } else if(loginResponse.status === 403) {
          setErrors(['Login failed after signup.']);
        } else {
          setErrors(['Unknown error occurred during login.']);
        }
      } else if (response.status === 400) {
            const errorData = await response.json();
            setErrors([errorData]);
      } else {
            setErrors(['Unknown error occurred during signup.']);
      }
    } catch (error) {
        setErrors([`An unexpected error occurred: ${error.message || error.toString()}`]);
    }
  };

  return (
    <div className="m-3 d-flex">
      <div className="w-50">
        <h2>Sign Up</h2>
        {errors.map((error, i) => (
          <div key={i} className="alert alert-danger">{error}</div>
        ))}
        <form onSubmit={handleSubmit}>
          <div className="form-group mt-4">
            <label className="form-label mb-1" htmlFor="username">Username</label>
            <input
              type="text"
              id="username"
              value={username}
              className="form-control mb-3"
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="form-group mb-1">
            <label className="form-label" htmlFor="email">Email</label>
            <input
              type="email"
              className="form-control mb-3"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label mb-1" htmlFor="password">Password</label>
            <input
              type="password"
              className="form-control mb-3"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="confirm-password">Confirm Password</label>
            <input
              type="password"
              className="form-control"
              id="confirm-password"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-primary mt-3 mb-3" type="submit">Sign Up</button>
        </form>
        <p>
          Already have an account? <a href="/login">Log in here</a>
        </p>
      </div>
    </div>
  );
}
