import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export default function SignUp() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    const response = await fetch('http://localhost:8080/create_account', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ username, email, password }),
    });

    if (response.ok) {
      const loginResponse = await fetch('http://localhost:8080/authenticate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });

      if (loginResponse.ok) {
        const loginData = await loginResponse.json();
        localStorage.setItem('token', loginData.token);

        navigate('/');
      } else {
        setError('Login failed after signup');
      }
    } else {
      const errorMsg = await response.text();
      setError('Sign up failed: ' + errorMsg);
    }
  };

  return (
    <div className="m-3 d-flex">
        <div className="w-50">
        <h2>Sign Up</h2>
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
            <label className="form-label"  htmlFor="email">Email</label>
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
            <label className="form-label mb-1"  htmlFor="password">Password</label>
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
            <label className="form-label"  htmlFor="confirm-password">Confirm Password</label>
            <input
                type="password"
                className="form-control"
                id="confirm-password"
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
            />
            </div>
            {error && <p className="error-message">{error}</p>}
            <button className="btn btn-primary mt-3 mb-3" type="submit">Sign Up</button>
        </form>
        <p>
            Already have an account? <a href="/login">Log in here</a>
        </p>
        </div>
    </div>
  );
}
