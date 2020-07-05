import React from "react";
import { Link } from "react-router-dom";
import cx from "classnames";

import { useLocation } from "react-router";

const Header: React.FunctionComponent = () => {
  const location = useLocation();

  return (
    <header
      style={{
        position: "fixed",
        zIndex: 1,
      }}
    >
      <nav>
        <h1>SAVE - MONEY</h1>
        <ul>
          <li>
            <Link
              to="/dashboard"
              className={cx({
                on: location.pathname.includes("/dashboard"),
              })}
            >
              DashBaord
            </Link>
          </li>
          <li>
            <Link
              to="/gamelist"
              className={cx({
                on: location.pathname.includes("/gamelist"),
              })}
            >
              GameList
            </Link>
          </li>
          <li>
            <Link
              to="/setting"
              className={cx({
                on: location.pathname.includes("/setting"),
              })}
            >
              Setting
            </Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
