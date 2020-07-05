import React from "react";
import { sha256 } from "js-sha256";

import { API } from "@components/axios";

const Login: React.FC = () => {
  const [LoginData, setLoginData] = React.useState({ id: "", password: "" });
  const [registerData, setRegisterData] = React.useState({
    id: "",
    password: "",
    email: "",
    name: "",
  });

  const handleLoginData = (type: "id" | "password", value: string) => {
    setLoginData({
      ...LoginData,
      [type]: value,
    });
  };

  const submitLogin = () => {
    API.post(
      `/token/?id=${LoginData.id}&password=${sha256(btoa(LoginData.password))}`
    ).then((res) => {
      window.location.reload();
    });
  };
  const submitRegister = () => {
    API.post(
      `/user/?id=${"gostroad"}&password=${btoa(
        "123123123"
      )}&name=${"헌터"}&email=${"gostroad@naver.com"}`
    ).then((res) => {
      console.log(res);
    });
  };
  return (
    <div
      style={{
        width: "100%",
        height: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#eef2f7",
      }}
    >
      <article>
        <div>
          <div>
            <input
              value={LoginData.id}
              onChange={(e) => {
                handleLoginData("id", e.target.value);
              }}
            />
          </div>
          <div>
            <input
              value={LoginData.password}
              onChange={(e) => {
                handleLoginData("password", e.target.value);
              }}
            />
          </div>
        </div>
        <div>
          <button onClick={submitLogin}>Login</button>
          <button onClick={submitRegister}>Register</button>
        </div>
      </article>
    </div>
  );
};

export default Login;
