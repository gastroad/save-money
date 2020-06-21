import React from "react";
import { Link } from "react-router-dom";

const Header = () => {
  return (
    <header>
      <div id="header"></div>
      <nav>
        <h1>SAVE - MONEY</h1>
        <ul>
          <li>
            <Link to="/dashbaord" className="on">
              DashBaord
            </Link>
          </li>
          <li>
            <Link to="/gamelist">GameList</Link>
          </li>
          <li>
            <Link to="/setting">Setting</Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
