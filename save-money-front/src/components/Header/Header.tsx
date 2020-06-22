import React from "react";
import { Link } from "react-router-dom";
import cx from "classnames";

import { RouteComponentProps, withRouter } from "react-router";

const Header = (props) => {
  return (
    <header>
      <div id="header"></div>
      <nav>
        <h1>SAVE - MONEY</h1>
        <ul>
          <li>
            <Link
              to="/dashboard"
              className={cx({
                on: props.location.pathname.includes("/dashboard"),
              })}
            >
              DashBaord
            </Link>
          </li>
          <li>
            <Link
              to="/gamelist"
              className={cx({
                on: props.location.pathname.includes("/gamelist"),
              })}
            >
              GameList
            </Link>
          </li>
          <li>
            <Link
              to="/setting"
              className={cx({
                on: props.location.pathname.includes("/setting"),
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

export default withRouter(Header);
