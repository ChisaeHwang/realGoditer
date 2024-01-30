import { ReactElement } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Signup from "./pages/signup";
import Workspace from "./pages/workspace";
import HeaderBar from "./components/header";
import ChannelBar from "./components/channel";
import MainSpace from "./pages/mainspace";
import CalculateSpace from "./pages/calculatespace";

const App = (): ReactElement => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/home" element={<Workspace />} />
        <Route path="/calculate" element={<CalculateSpace />} />
        <Route path="/header" element={<HeaderBar />} />
        <Route path="/channel" element={<ChannelBar />} />
        <Route path="/main" element={<MainSpace />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
