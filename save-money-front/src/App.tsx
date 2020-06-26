import React from "react";

import {
  BrowserRouter as Router,
  Redirect,
  Route,
  Switch,
} from "react-router-dom";
import Header from "./components/Header";
import DashBoard from "./pages/DashBoard";
import GameList from "./pages/GameList";
import Setting from "./pages/Setting";

const App = () => {
  const renderRoute = (Component: React.FC) => {
    return (props: any) => {
      const newProps = {
        ...props,
      };
      return <Component {...newProps} />;
    };
  };

  return (
    <Router>
      <Header />
      <>
        <div id={"root-modal"} />
        <Switch>
          <Route path="/dashboard" component={DashBoard} />
          <Route path="/gamelist" component={renderRoute(GameList)} />
          <Route path="/setting" component={renderRoute(Setting)} />
          <Redirect to="/dashboard" />;
        </Switch>
      </>
    </Router>
  );
};

export default App;
