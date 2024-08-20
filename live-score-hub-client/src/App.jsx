import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import Header from './Components/Header';
import LogIn from './Components/LogIn';
import Home from './Components/Home';
import SignUp from './Components/SignUp';
import Games from './Components/Games';
import AuthContext from './Context/AuthContext';
import { jwtDecode } from 'jwt-decode';
import Error from './Components/Error';

const LOCAL_STORAGE_TOKEN_KEY = "liveScoreHubToken";
 

export default function App() {
  const [user, setUser] = useState(null);


  const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
    if (token) {
      login(token);
    }
    setRestoreLoginAttemptCompleted(true);
  }, []);

  const login = (token) => {
    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

    const { sub: username, authorities: authoritiesString } = jwtDecode(token);

    const user = {
      username,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };

    console.log(user);
    setUser(user);
    return user;
  }

  const logout = () => {
    setUser(null);
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
  };

  const auth = {
    user: user ? {...user} : null,
    login,
    logout
  };

  if(!restoreLoginAttemptCompleted) {
    return null;
  }


  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <Header/>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/games/:league" element={<Games />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/error" element={<Error />}/>
          <Route path="/login" element={!user ? <LogIn /> : <Navigate to="/" replace={true} /> } />
        </Routes>
      </Router>
    </AuthContext.Provider>
  );
}
