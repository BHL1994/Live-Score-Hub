import React from "react";
import { NavLink } from "react-router-dom";
import '../NotFound.css';

export default function NotFound() {
    return (
        <div className="not-found-container" style={{ textAlign: 'center', padding: '50px' }}>
            <h1 style={{ fontSize: '3rem', fontWeight: 'bold' }}>404</h1>
            <p style={{ fontSize: '1.5rem', margin: '20px 0' }}>Oops! The page you're looking for doesn't exist.</p>
            <NavLink to="/" className="btn btn-primary">Go Back Home</NavLink>
        </div>
    );
}
