import { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import AuthContext from '../Context/AuthContext';
import Error from './Error';
import '../Games.css';

export default function LogIn() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState([]);

  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    const response = await fetch('http://localhost:8080/authenticate', {
      method: 'POST',
      headers: { 
        'Content-Type': 'application/json' 
      },
      body: JSON.stringify({ 
        username, 
        password 
      }),
    });

    if (response.status === 200) {
      const { jwt_token } = await response.json();
      console.log(jwt_token);
      auth.login(jwt_token);
      navigate("/");
    } else if (response.status === 403) {
      setErrors(['Login failed. Incorrect username or password.']);
    } else {
      setErrors(['Unknown error.']);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center min-vh-100">
      <div className="card p-4 shadow-sm" style={{ maxWidth: '400px', width: '100%' }}>
        <h2 className="login-title text-center mb-1">Log In</h2>
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
          <div className="form-group">
            <label className="form-label mb-1" htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              value={password}
              className="form-control mb-3"
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-primary w-100 mt-3 mb-3" type="submit">Log In</button>
          <p className="text-center">
            Don't have an account? <a href="/signup">Sign up here</a>
          </p>
        </form>
      </div>
    </div>
  );
}
