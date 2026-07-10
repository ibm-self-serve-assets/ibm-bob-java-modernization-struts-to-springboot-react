import React from 'react';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import NavBar from './components/NavBar';
import Login from './pages/Login';
import Register from './pages/Register';
import Home from './pages/Home';
import VehicleRegister from './pages/VehicleRegister';
import Quote from './pages/Quote';
import ProfileUpdate from './pages/ProfileUpdate';
import AdminDashboard from './pages/AdminDashboard';

function ProtectedRoute({ children, requireAdmin = false }) {
  const { user } = useAuth();
  if (!user) return <Navigate to="/login" replace />;
  if (requireAdmin && user.role !== 'ROLE_ADMIN') return <Navigate to="/home" replace />;
  return children;
}

const layout = {
  minHeight: 'calc(100vh - 56px)',
  maxWidth: 1100,
  margin: '0 auto',
  padding: '28px 20px',
};

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <NavBar />
        <main style={layout}>
          <Routes>
            <Route path="/" element={<Navigate to="/login" replace />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="/home" element={<ProtectedRoute><Home /></ProtectedRoute>} />
            <Route path="/vehicle" element={<ProtectedRoute><VehicleRegister /></ProtectedRoute>} />
            <Route path="/quote" element={<ProtectedRoute><Quote /></ProtectedRoute>} />
            <Route path="/profile" element={<ProtectedRoute><ProfileUpdate /></ProtectedRoute>} />
            <Route path="/admin" element={<ProtectedRoute requireAdmin><AdminDashboard /></ProtectedRoute>} />
          </Routes>
        </main>
      </BrowserRouter>
    </AuthProvider>
  );
}
