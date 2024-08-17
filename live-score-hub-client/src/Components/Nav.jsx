import { NavLink } from "react-router-dom";
import logo from "../images/logo.png";

export default function Nav() {
  return (
    <nav className="navbar">
      <div className="nav-start">
        <NavLink className="navbar-brand" to="/">
        <img src={logo} className="nav-logo" />
        </NavLink>
        <ul className="navbar-nav">
          <li className="nav-item">
            <NavLink className="nav-link" to="/">
              NBA
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/">
              MLB
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/">
              NFL
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/">
              NHL
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link">
              NCAAF
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink className="nav-link" to="/">
              NCAAB
            </NavLink>
          </li>
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
        </ul>
      </div>
    </nav>
  );
}
