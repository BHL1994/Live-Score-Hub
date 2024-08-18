import { NavLink, useNavigate } from "react-router-dom";
import logo from "../images/logo.png";
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
        <nav className="navbar navbar-expand-lg bg-body-tertiary">
        <div className="container-fluid">
            <NavLink className="navbar-brand" to="/">
            <img src={logo} className="nav-logo" alt="Logo" />
            </NavLink>
            <ul className="navbar-nav me-auto mb-2 mb-lg-0">
            <li className="nav-item">
                <NavLink className="nav-link" to="/games">
                NBA
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink className="nav-link" to="/games">
                MLB
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink className="nav-link" to="/games">
                NFL
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink className="nav-link" to="/games">
                NHL
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink className="nav-link" to="/games">
                NCAAF
                </NavLink>
            </li>
            <li className="nav-item">
                <NavLink className="nav-link" to="/games">
                NCAAB
                </NavLink>
            </li>
            {auth.user && (
                <li className="nav-item">
                <NavLink className="nav-link" to="/games">
                My Games
                </NavLink>
                </li>
            )}
            </ul>
            <ul className="navbar-nav ms-auto mb-2 mb-lg-0">
                {auth.user ? (
                    <>
                        <li className="nav-item">
                            <span className="nav-link">
                                Welcome {auth.user.username}
                            </span>
                        </li>
                        <li className="nav-item">
                            <button className="nav-link" onClick={handleLogout}>
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
        </nav>
    );
}
