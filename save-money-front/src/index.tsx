import "core-js";
import "regenerator-runtime/runtime";

import React from "react";
import ReactDOM from "react-dom";
import App from "./App";
import "../assets/css/default.scss";
import { RecoilRoot } from "recoil";

ReactDOM.render(
  <RecoilRoot>
    <App />
  </RecoilRoot>,
  document.getElementById("root")
);
