import axios from 'axios';

// In dev: Vite proxy forwards /api/* to the correct service (see vite.config.js)
// In prod: set VITE_API_URL to the API gateway / nginx base URL
const BASE_URL = import.meta.env.VITE_API_URL || '';

const api = axios.create({ baseURL: BASE_URL });

// Attach JWT to every request if present
api.interceptors.request.use(config => {
  const token = sessionStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export const authService = {
  login: (ssn, password) => api.post('/api/users/login', { ssn, password }),
  register: (data) => api.post('/api/users/register', data),
};

export const userService = {
  getProfile: (ssn) => api.get(`/api/users/${ssn}`),
  updateProfile: (ssn, data) => api.put(`/api/users/${ssn}`, data),
};

export const vehicleService = {
  registerVehicle: (data) => api.post('/api/vehicles', data),
  getVehicle: (ssn) => api.get(`/api/vehicles/${ssn}`),
};

export const premiumService = {
  getQuote: (ssn) => api.get(`/api/quotes/${ssn}`),
};

export default api;
