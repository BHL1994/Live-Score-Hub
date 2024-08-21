import { NavLink, useNavigate } from "react-router-dom";
import logo from "../images/logo.png";
import AuthContext from "../Context/AuthContext";
import { useContext } from "react";
import '../Nav.css';

export default function Nav() {
    const auth = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        auth.logout(); 
        navigate('/');
    };

    return (
        <nav className="navbar navbar-expand-lg mb-5">
            <div className="container-fluid">
                <NavLink className="navbar-brand" to="/">
                    <img src={logo} className="nav-logo" alt="Logo" />
                </NavLink>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/games/NBA">
                                NBA
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/games/MLB">
                                MLB
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/games/NFL">
                                NFL
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/games/NHL">
                                NHL
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/games/NCAAF">
                                NCAAF
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/games/NCAAB">
                                NCAAB
                            </NavLink>
                        </li>
                        {auth.user && (
                            <li className="nav-item">
                                <NavLink className="nav-link" to="/mygames">
                                    My Games
                                </NavLink>
                            </li>
                        )}
                    </ul>
                    <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
                        {auth.user ? (
                            <>
                                <li className="nav-item">
                                    <span className="nav-link welcome-text">
                                        Welcome {auth.user.username}
                                    </span>
                                </li>
                                <li className="nav-item">
                                    <button className="btn btn-link nav-link" onClick={handleLogout}>
                                        Log Out
                                    </button>
                                </li>
                            </>
                        ) : (
                            <>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/signup">
                                        Sign Up
                                    </NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link" to="/login">
                                        Log In
                                    </NavLink>
                                </li>
                            </>
                        )}
                    </ul>
                </div>
            </div>
        </nav>
    );
}
