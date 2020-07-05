import React from "react";
import { sha256 } from "js-sha256";

import { API } from "@components/axios";
import "./login.scss";

const Login: React.FC = () => {
  const [LoginData, setLoginData] = React.useState({ id: "", password: "" });
  const [tabMenu, setTabMenu] = React.useState("LOGIN");
  const [registerData, setRegisterData] = React.useState({
    id: "",
    password: "",
    email: "",
    name: "",
    passwordCheck: "",
  });

  const handleLoginData = (type: "id" | "password", value: string) => {
    setLoginData({
      ...LoginData,
      [type]: value,
    });
  };
  const handleRegisterData = (
    type: "id" | "password" | "name" | "email" | "passwordCheck",
    value: string
  ) => {
    setRegisterData({
      ...registerData,
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
    if (registerData.password !== registerData.passwordCheck) {
      alert("비밀번호 확인을 다시 해 주십시오");
      return;
    }
    if (!registerData.id.length) {
      alert("id 을(를) 다시 확인해 주십시오");
      return true;
    }
    if (!registerData.password.length) {
      alert("password 을(를) 다시 확인해 주십시오");
      return true;
    }
    if (!registerData.email.length) {
      alert("email 을(를) 다시 확인해 주십시오");
      return true;
    }
    if (!registerData.name.length) {
      alert("name 을(를) 다시 확인해 주십시오");
      return true;
    }
    API.post(
      `/user/?id=${registerData.id}&password=${btoa(
        registerData.password
      )}&name=${registerData.name}&email=${registerData.email}`
    ).then((res) => {
      alert("회원가입 성공");
      window.location.reload();
    });
  };
  return (
    <div className="login-page">
      <article>
        {tabMenu == "LOGIN" ? (
          <>
            <div>
              <div>
                <input
                  className="login-input"
                  placeholder="ID"
                  onChange={(e) => {
                    handleLoginData("id", e.target.value);
                  }}
                  value={LoginData.id}
                />
              </div>
              <div>
                <input
                  className="login-input"
                  placeholder="PW"
                  onChange={(e) => {
                    handleLoginData("password", e.target.value);
                  }}
                  value={LoginData.password}
                  type={"password"}
                />
              </div>
            </div>
            <div className="login-buttons">
              <button onClick={submitLogin}>Login</button>
              <button
                onClick={() => {
                  setTabMenu("REGISTER");
                }}
              >
                Register
              </button>
            </div>
          </>
        ) : (
          <>
            <div>
              <div>
                <input
                  className="login-input"
                  placeholder="ID"
                  onChange={(e) => {
                    handleRegisterData("id", e.target.value);
                  }}
                  value={registerData.id}
                />
              </div>
              <div>
                <input
                  className="login-input"
                  placeholder="Password"
                  onChange={(e) => {
                    handleRegisterData("password", e.target.value);
                  }}
                  value={registerData.password}
                  type={"password"}
                />
              </div>
              <div>
                <input
                  className="login-input"
                  placeholder="PasswordCheck"
                  onChange={(e) => {
                    handleRegisterData("passwordCheck", e.target.value);
                  }}
                  value={registerData.passwordCheck}
                  type={"password"}
                />
              </div>
              <div>
                <input
                  className="login-input"
                  placeholder="Email"
                  onChange={(e) => {
                    handleRegisterData("email", e.target.value);
                  }}
                  value={registerData.email}
                />
              </div>
              <div>
                <input
                  className="login-input"
                  placeholder="Name"
                  onChange={(e) => {
                    handleRegisterData("name", e.target.value);
                  }}
                  value={registerData.name}
                />
              </div>
            </div>
            <div className="login-buttons">
              <button onClick={submitRegister}>Register</button>
              <button
                onClick={() => {
                  setTabMenu("LOGIN");
                }}
              >
                Back
              </button>
            </div>
          </>
        )}
      </article>
    </div>
  );
};

export default Login;
