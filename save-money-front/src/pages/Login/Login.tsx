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
    API.get(
      `/token/?id=${LoginData.id}&password=${sha256(btoa(LoginData.password))}`
    ).then((res) => {
      console.log(res);
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
    <section>
      <article>
        <input
          value={LoginData.id}
          onChange={(e) => {
            handleLoginData("id", e.target.value);
          }}
        />
        <input
          value={LoginData.password}
          onChange={(e) => {
            handleLoginData("password", e.target.value);
          }}
        />
        <button onClick={submitLogin}>Login</button>
        <button onClick={submitRegister}>Register</button>
      </article>
    </section>
  );
};

export default Login;
