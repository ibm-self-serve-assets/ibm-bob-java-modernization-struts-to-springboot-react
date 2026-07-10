import React, { createContext, useContext, useState } from 'react';
import { authService } from '../services/api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => {
    const ssn = sessionStorage.getItem('ssn');
    const role = sessionStorage.getItem('role');
    return ssn ? { ssn, role } : null;
  });

  const login = async (ssn, password) => {
    const res = await authService.login(ssn, password);
    const { token, role } = res.data;
    sessionStorage.setItem('token', token);
    sessionStorage.setItem('ssn', ssn);
    sessionStorage.setItem('role', role);
    setUser({ ssn, role });
    return role;
  };

  const logout = () => {
    sessionStorage.clear();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);
