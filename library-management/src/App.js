import { useState } from 'react';
import LoginPage from './components/login-page';
import BooksPage from './components/books-page';
import AuthorsPage from './components/authors-page';
import BookFormPage from './components/book-form-page';
import ErrorPage from './components/error-page';
import PrivateRoute from './components/PrivateRoute';
import { StatusContext } from './hooks/StatusContext';
import StatusMessage from './components/status-message';
import {BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';


function App() {
  const [status, setStatus] = useState({ message: '', type: '' });

  const showStatus = (message, type = 'info',duration = 3000) => {
    setStatus({ message, type });
    setTimeout(() =>{
      setStatus({ message: '', type: '' });
    }, duration); 
  };

  return (
    <StatusContext.Provider value={{ status, showStatus }}>
      <StatusMessage />
     <Router>
      <Routes>
        <Route path="/" element={<PrivateRoute><LoginPage /></PrivateRoute>} />
        <Route path="/login" element={<PrivateRoute><LoginPage /></PrivateRoute>} />
        <Route path="/books" element={<PrivateRoute><BooksPage /></PrivateRoute>} />
        <Route path="/authors" element={<PrivateRoute><AuthorsPage /></PrivateRoute>} />
        <Route path="/addBook" element={<PrivateRoute><BookFormPage /></PrivateRoute>}/>
        <Route path="/updateBook" element={<PrivateRoute><BookFormPage /></PrivateRoute>}/>
        <Route path="/error" element={<PrivateRoute><ErrorPage /></PrivateRoute>}/>
        <Route path="*" element={<Navigate to="/error" replace />} />
      </Routes>
     </Router>
    </StatusContext.Provider>
  );
}

export default App;
