import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';
import { Card, Button, PageHeader, Alert, Spinner, Badge } from '../components/UI';

export default function AdminDashboard() {
  const { user } = useAuth();
  const [customers, setCustomers] = useState([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState({ text: '', type: 'success' });

  const load = async (ssn = '') => {
    setLoading(true);
    try {
      const res = await api.get(ssn ? `/api/users?ssn=${ssn}` : '/api/users');
      setCustomers(Array.isArray(res.data) ? res.data : []);
    } catch {
      setMessage({ text: 'Failed to load users', type: 'error' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { load(); }, []);

  const handleDelete = async (ssn) => {
    if (!window.confirm(`Remove user ${ssn}? This cannot be undone.`)) return;
    try {
      await api.delete(`/api/users/${ssn}`);
      setMessage({ text: `User ${ssn} removed successfully`, type: 'success' });
      load();
    } catch {
      setMessage({ text: 'Failed to remove user', type: 'error' });
    }
  };

  return (
    <div>
      <PageHeader
        title="User Administration"
        subtitle={`${customers.length} registered customer${customers.length !== 1 ? 's' : ''}`}
      />

      {message.text && <Alert type={message.type}>{message.text}</Alert>}

      {/* Stats row */}
      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(160px, 1fr))', gap: 16, marginBottom: 24 }}>
        {[
          { label: 'Total Users',  value: customers.length,                              color: 'var(--color-primary)' },
          { label: 'Admins',       value: customers.filter(c => c.role === 'ROLE_ADMIN').length, color: '#d97706' },
          { label: 'Active',       value: customers.filter(c => c.enabled !== false).length,     color: 'var(--color-success)' },
        ].map(s => (
          <Card key={s.label} style={{ textAlign: 'center', padding: 16 }}>
            <div style={{ fontSize: 26, fontWeight: 700, color: s.color }}>{s.value}</div>
            <div style={{ color: 'var(--color-muted)', fontSize: 12, marginTop: 4 }}>{s.label}</div>
          </Card>
        ))}
      </div>

      {/* Search */}
      <Card style={{ marginBottom: 20 }}>
        <div style={{ display: 'flex', gap: 10, alignItems: 'center' }}>
          <input
            value={search}
            onChange={e => setSearch(e.target.value)}
            onKeyDown={e => e.key === 'Enter' && load(search)}
            placeholder="🔍  Search by SSN…"
            style={{
              flex: 1, padding: '8px 12px', border: '1px solid var(--color-border)',
              borderRadius: 6, fontSize: 14, outline: 'none',
            }}
          />
          <Button onClick={() => load(search)}>Search</Button>
          <Button variant="ghost" onClick={() => { setSearch(''); load(''); }}>Reset</Button>
        </div>
      </Card>

      {/* Table */}
      <Card style={{ padding: 0, overflow: 'hidden' }}>
        {loading ? (
          <Spinner />
        ) : customers.length === 0 ? (
          <p style={{ padding: 24, textAlign: 'center', color: 'var(--color-muted)' }}>No users found.</p>
        ) : (
          <div style={{ overflowX: 'auto' }}>
            <table style={{ width: '100%', borderCollapse: 'collapse', fontSize: 13 }}>
              <thead>
                <tr style={{ background: '#f8fafc', borderBottom: '2px solid var(--color-border)' }}>
                  {['SSN', 'Name', 'Email', 'City', 'Status', 'Actions'].map(h => (
                    <th key={h} style={{ padding: '10px 14px', fontWeight: 600, textAlign: 'left', color: 'var(--color-muted)', fontSize: 12, textTransform: 'uppercase', letterSpacing: .4 }}>
                      {h}
                    </th>
                  ))}
                </tr>
              </thead>
              <tbody>
                {customers.map((c, i) => (
                  <tr key={c.ssn} style={{ borderBottom: '1px solid var(--color-border)', background: i % 2 === 0 ? '#fff' : '#fafbfc' }}>
                    <td style={{ padding: '10px 14px', fontWeight: 600, color: 'var(--color-primary)' }}>{c.ssn}</td>
                    <td style={{ padding: '10px 14px' }}>{[c.firstName, c.lastName].filter(Boolean).join(' ') || '—'}</td>
                    <td style={{ padding: '10px 14px', color: 'var(--color-muted)' }}>{c.email || '—'}</td>
                    <td style={{ padding: '10px 14px' }}>{c.city || '—'}</td>
                    <td style={{ padding: '10px 14px' }}>
                      <Badge color={c.enabled !== false ? 'green' : 'red'}>
                        {c.enabled !== false ? 'Active' : 'Disabled'}
                      </Badge>
                    </td>
                    <td style={{ padding: '10px 14px' }}>
                      {c.ssn !== 'adminadmin' && (
                        <Button
                          variant="danger"
                          style={{ padding: '4px 12px', fontSize: 12 }}
                          onClick={() => handleDelete(c.ssn)}
                        >
                          Delete
                        </Button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </Card>
    </div>
  );
}
