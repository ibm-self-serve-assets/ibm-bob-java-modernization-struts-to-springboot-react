import React, { useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const navLinks = [
  { to: '/home',    label: 'Dashboard' },
  { to: '/vehicle', label: 'My Vehicle' },
  { to: '/quote',   label: 'Get Quote' },
  { to: '/profile', label: 'Profile' },
];

export default function NavBar() {
  const { user, logout } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();
  const [menuOpen, setMenuOpen] = useState(false);

  if (!user) return null;

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const isActive = (to) => location.pathname === to;

  return (
    <nav style={{
      background: 'var(--color-nav-bg)',
      color: 'var(--color-nav-text)',
      boxShadow: '0 2px 8px rgba(0,0,0,.2)',
      position: 'sticky', top: 0, zIndex: 100,
    }}>
      <div style={{
        maxWidth: 1100, margin: '0 auto',
        padding: '0 20px',
        display: 'flex', alignItems: 'center',
        height: 56,
      }}>
        {/* Brand */}
        <Link to="/home" style={{
          color: '#fff', fontWeight: 700, fontSize: 16,
          display: 'flex', alignItems: 'center', gap: 8, marginRight: 32,
          textDecoration: 'none',
        }}>
          <span style={{ fontSize: 20 }}>🛡️</span> PKS Insurance
        </Link>

        {/* Desktop links */}
        <div style={{ display: 'flex', gap: 4, flex: 1 }}>
          {navLinks.map(l => (
            <Link key={l.to} to={l.to} style={{
              padding: '6px 14px', borderRadius: 6, fontSize: 13, fontWeight: 500,
              color: isActive(l.to) ? '#fff' : '#94a3b8',
              background: isActive(l.to) ? 'rgba(255,255,255,.12)' : 'transparent',
              textDecoration: 'none',
              transition: 'all .15s',
            }}>
              {l.label}
            </Link>
          ))}
          {user.role === 'ROLE_ADMIN' && (
            <Link to="/admin" style={{
              padding: '6px 14px', borderRadius: 6, fontSize: 13, fontWeight: 500,
              color: isActive('/admin') ? '#fff' : '#fbbf24',
              background: isActive('/admin') ? 'rgba(251,191,36,.15)' : 'transparent',
              textDecoration: 'none',
            }}>
              ⚙ Admin
            </Link>
          )}
        </div>

        {/* User info + Logout */}
        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <span style={{ fontSize: 12, color: '#94a3b8' }}>
            <span style={{ color: '#fff', fontWeight: 600 }}>{user.ssn}</span>
            {user.role === 'ROLE_ADMIN' && (
              <span style={{ marginLeft: 6, background: '#fbbf24', color: '#1e293b', fontSize: 10, fontWeight: 700, padding: '1px 7px', borderRadius: 10 }}>ADMIN</span>
            )}
          </span>
          <button onClick={handleLogout} style={{
            background: 'rgba(255,255,255,.1)', border: '1px solid rgba(255,255,255,.15)',
            color: '#f1f5f9', borderRadius: 6, padding: '5px 12px', fontSize: 12,
            fontWeight: 600, cursor: 'pointer',
          }}>
            Logout
          </button>
        </div>
      </div>
    </nav>
  );
}
