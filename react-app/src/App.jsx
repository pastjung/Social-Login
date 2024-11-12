import React from 'react';

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './oauth/pages/LoginPage.jsx';
import HomePage from './oauth/pages/HomePage.jsx';
import PingPage from './ping/pages/PingPage.jsx';

const App = () => {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/ping" element={<PingPage />} />
        </Routes>
      </Router>
  );
};

export default App;