import { createContext, useContext, useState, useCallback } from 'react';
import * as api from '../api/api';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem('token'));
  const [role, setRole] = useState(() => localStorage.getItem('role'));
  const [email, setEmail] = useState(() => localStorage.getItem('email'));

  const isAuthenticated = Boolean(token);
  const isAdmin = role === 'ADMIN';

  const login = useCallback(async (emailInput, password) => {
    const data = await api.login(emailInput, password);
    localStorage.setItem('token', data.token);
    localStorage.setItem('role', data.role);
    localStorage.setItem('email', emailInput);
    setToken(data.token);
    setRole(data.role);
    setEmail(emailInput);
    return data;
  }, []);

  const register = useCallback(async (name, emailInput, password) => {
    return api.register(name, emailInput, password);
  }, []);

  const logout = useCallback(() => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
    setToken(null);
    setRole(null);
    setEmail(null);
  }, []);

  return (
    <AuthContext.Provider
      value={{ token, role, email, isAuthenticated, isAdmin, login, register, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
}
