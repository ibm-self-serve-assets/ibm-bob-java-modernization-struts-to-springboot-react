import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Card, Button, FormField, Input, Alert } from '../components/UI';

export default function Login() {
  const [ssn, setSsn] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      const role = await login(ssn, password);
      navigate(role === 'ROLE_ADMIN' ? '/admin' : '/home');
    } catch (err) {
      setError(err.response?.data?.message || 'Invalid SSN or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 420, margin: '48px auto' }}>
      {/* Header */}
      <div style={{ textAlign: 'center', marginBottom: 28 }}>
        <div style={{ fontSize: 48, marginBottom: 8 }}>🛡️</div>
        <h1 style={{ fontSize: 22, fontWeight: 700 }}>PKS Auto Insurance</h1>
        <p style={{ color: 'var(--color-muted)', fontSize: 13, marginTop: 4 }}>Sign in to your account</p>
      </div>

      <Card>
        {error && <Alert type="error">{error}</Alert>}
        <form onSubmit={handleSubmit}>
          <FormField label="SSN / User ID">
            <Input
              value={ssn}
              onChange={e => setSsn(e.target.value)}
              placeholder="e.g. 111-22-3333"
              required
              autoFocus
            />
          </FormField>

          <FormField label="Password">
            <Input
              type="password"
              value={password}
              onChange={e => setPassword(e.target.value)}
              placeholder="••••••••"
              required
            />
          </FormField>

          <Button type="submit" fullWidth disabled={loading} style={{ marginTop: 6, padding: '10px 0' }}>
            {loading ? 'Signing in…' : 'Sign In'}
          </Button>
        </form>

        <p style={{ textAlign: 'center', marginTop: 16, color: 'var(--color-muted)', fontSize: 13 }}>
          New customer?{' '}
          <Link to="/register" style={{ fontWeight: 600 }}>Create an account</Link>
        </p>
      </Card>
    </div>
  );
}
