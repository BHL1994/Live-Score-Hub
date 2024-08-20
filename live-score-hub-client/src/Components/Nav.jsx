import { NavLink, useNavigate } from "react-router-dom";
import logo from "../images/logo.png";
import logo2 from "../images/logo-2.png";
import AuthContext from "../Context/AuthContext";
import { useContext } from "react";

export default function Nav() {
    const auth = useContext(AuthContext);
    const navigate = useNavigate();

    const handleLogout = () => {
        auth.logout(); 
        navigate('/');
    };

    return (
        <nav className="navbar navbar-expand-lg" style={{ backgroundColor: 'rgb(4, 30, 66)' }}>
            <div className="container-fluid">
                <NavLink className="navbar-brand" to="/">
                    <img src={logo2} className="nav-logo" alt="Logo" style={{ maxHeight: '40px' }} />
                </NavLink>
                <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span className="navbar-toggler-icon"></span>
                </button>
                <div className="collapse navbar-collapse" id="navbarNav">
                    <ul className="navbar-nav me-auto mb-2 mb-lg-0">
                        <li className="nav-item">
                            <NavLink className="nav-link text-white" to="/games/NBA">
                                NBA
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link text-white" to="/games/MLB">
                                MLB
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link text-white" to="/games/NFL">
                                NFL
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link text-white" to="/games/NHL">
                                NHL
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link text-white" to="/games/NCAAF">
                                NCAAF
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link text-white" to="/games/NCAAB">
                                NCAAB
                            </NavLink>
                        </li>
                        {auth.user && (
                            <li className="nav-item">
                                <NavLink className="nav-link text-white" to="/mygames">
                                    My Games
                                </NavLink>
                            </li>
                        )}
                    </ul>
                    <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
                        {auth.user ? (
                            <>
                                <li className="nav-item">
                                    <span className="nav-link text-white">
                                        Welcome {auth.user.username}
                                    </span>
                                </li>
                                <li className="nav-item">
                                    <button className="btn btn-link nav-link text-white" onClick={handleLogout}>
                                        Log Out
                                    </button>
                                </li>
                            </>
                        ) : (
                            <>
                                <li className="nav-item">
                                    <NavLink className="nav-link text-white" to="/signup">
                                        Sign Up
                                    </NavLink>
                                </li>
                                <li className="nav-item">
                                    <NavLink className="nav-link text-white" to="/login">
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
