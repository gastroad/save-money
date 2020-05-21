import React from "react"

import {
    BrowserRouter as Router,
    Redirect,
    Route,
    Switch
} from "react-router-dom"
import DashBoard from "./pages/DashBoard"
import GameList from "./pages/GameList"
import Setting from "./pages/Setting"



const App = () => {
    const renderRoute = (Component) => {
        return (props) => {
            const newProps = {
                ...props,
            }
            return <Component {...newProps} />
        }
    }
    return (
        <Router>
            <>
                <div id={"root-modal"} />
                <Switch>
                    <Route
                        path="/dashboard"
                        component={renderRoute(DashBoard)}
                    />
                    <Route
                        path="/gamelist"
                        component={renderRoute(GameList)}
                    />
                    <Route
                        path="/setting"
                        component={renderRoute(Setting)}
                    />
                </Switch>
            </>
        </Router>
    )
}

export default App

// git test
