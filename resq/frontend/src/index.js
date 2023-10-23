import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import SignUp from './SignUp';
import 'bootstrap/dist/css/bootstrap.min.css';

ReactDOM.render(
  <React.StrictMode>
    <SignUp /> {/* Render the App component here */}
  </React.StrictMode>,
  document.getElementById('root')
);
