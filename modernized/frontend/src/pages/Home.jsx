import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Card, Badge } from '../components/UI';

const menuItems = [
  {
    to: '/vehicle',
    icon: '🚗',
    title: 'Register Vehicle',
    desc: 'Add your vehicle to get an insurance quote',
    color: '#6366f1',
  },
  {
    to: '/quote',
    icon: '📋',
    title: 'View Premium Quote',
    desc: 'See your calculated insurance premium',
    color: '#0891b2',
  },
  {
    to: '/profile',
    icon: '👤',
    title: 'Update Profile',
    desc: 'Edit your personal information',
    color: '#059669',
  },
];

export default function Home() {
  const { user } = useAuth();
  const isAdmin = user?.role === 'ROLE_ADMIN';

  return (
    <div>
      {/* Welcome Banner */}
      <div style={{
        background: 'linear-gradient(135deg, #1e3a8a 0%, #2563eb 100%)',
        borderRadius: 'var(--radius)', padding: '28px 32px', marginBottom: 28,
        color: '#fff',
      }}>
        <p style={{ opacity: .8, fontSize: 13, marginBottom: 4 }}>Welcome back,</p>
        <h1 style={{ fontSize: 24, fontWeight: 700, marginBottom: 6 }}>{user?.ssn}</h1>
        <div style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
          <Badge color={isAdmin ? 'yellow' : 'blue'}>{isAdmin ? 'Administrator' : 'Customer'}</Badge>
          <span style={{ opacity: .6, fontSize: 12 }}>PKS Auto Insurance Portal</span>
        </div>
      </div>

      {/* Quick Actions */}
      <h2 style={{ fontSize: 15, fontWeight: 700, marginBottom: 14, color: 'var(--color-muted)' }}>
        QUICK ACTIONS
      </h2>
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(220px, 1fr))', gap: 16, marginBottom: 28 }}>
        {menuItems.map(item => (
          <Link key={item.to} to={item.to} style={{ textDecoration: 'none' }}>
            <Card style={{
              cursor: 'pointer', transition: 'box-shadow .2s, transform .2s',
              ':hover': { boxShadow: 'var(--shadow-md)' },
            }}
              onMouseEnter={e => { e.currentTarget.style.transform = 'translateY(-2px)'; e.currentTarget.style.boxShadow = 'var(--shadow-md)'; }}
              onMouseLeave={e => { e.currentTarget.style.transform = ''; e.currentTarget.style.boxShadow = ''; }}
            >
              <div style={{ fontSize: 32, marginBottom: 10 }}>{item.icon}</div>
              <h3 style={{ fontSize: 14, fontWeight: 700, marginBottom: 4 }}>{item.title}</h3>
              <p style={{ color: 'var(--color-muted)', fontSize: 12, lineHeight: 1.5 }}>{item.desc}</p>
              <div style={{
                display: 'inline-block', marginTop: 12, padding: '4px 12px',
                background: item.color, color: '#fff', borderRadius: 20, fontSize: 11, fontWeight: 600,
              }}>
                Open →
              </div>
            </Card>
          </Link>
        ))}

        {isAdmin && (
          <Link to="/admin" style={{ textDecoration: 'none' }}>
            <Card style={{ borderColor: '#fcd34d', cursor: 'pointer' }}
              onMouseEnter={e => { e.currentTarget.style.transform = 'translateY(-2px)'; e.currentTarget.style.boxShadow = 'var(--shadow-md)'; }}
              onMouseLeave={e => { e.currentTarget.style.transform = ''; e.currentTarget.style.boxShadow = ''; }}
            >
              <div style={{ fontSize: 32, marginBottom: 10 }}>⚙️</div>
              <h3 style={{ fontSize: 14, fontWeight: 700, marginBottom: 4 }}>Admin Dashboard</h3>
              <p style={{ color: 'var(--color-muted)', fontSize: 12, lineHeight: 1.5 }}>Manage users and platform operations</p>
              <div style={{
                display: 'inline-block', marginTop: 12, padding: '4px 12px',
                background: '#d97706', color: '#fff', borderRadius: 20, fontSize: 11, fontWeight: 600,
              }}>
                Open →
              </div>
            </Card>
          </Link>
        )}
      </div>

      {/* Info footer */}
      <Card style={{ background: '#f8fafc' }}>
        <p style={{ color: 'var(--color-muted)', fontSize: 12, textAlign: 'center' }}>
          🔒 Your data is encrypted and securely stored &nbsp;·&nbsp;
          📞 Support: 1-800-PKS-INSURE &nbsp;·&nbsp;
          All policies backed by PKS Insurance Group
        </p>
      </Card>
    </div>
  );
}
