import "core-js";
import "regenerator-runtime/runtime";

import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import Login from "./pages/Login";
import "../assets/css/default.scss";
import { RecoilRoot } from "recoil";

if (localStorage.getItem("XAuthToken")) {
  ReactDOM.render(
    <RecoilRoot>
      <App />
    </RecoilRoot>,
    document.getElementById("root")
  );
} else {
  ReactDOM.render(
    <RecoilRoot>
      <Login />
    </RecoilRoot>,
    document.getElementById("root")
  );
}
