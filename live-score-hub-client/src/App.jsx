import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from './Components/Header';
import LogIn from './Components/LogIn';
import Home from './Components/Home';
import SignUp from './Components/SignUp';
import "./App.css";


export default function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Home></Home>} />
        <Route path="/signup" element={<SignUp></SignUp>}/>
        <Route path="/login" element={<LogIn></LogIn>} />
      </Routes>
    </Router>
  );
}