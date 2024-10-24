import React from 'react';

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './oauth/LoginPage';
import HomePage from './oauth/HomePage';

const App = () => {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<LoginPage />} />
          <Route path="/home" element={<HomePage />} />
        </Routes>
      </Router>
  );
};

export default App;