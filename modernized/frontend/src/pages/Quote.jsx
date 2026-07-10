import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { premiumService } from '../services/api';
import { Card, Spinner, Alert, PageHeader, Button } from '../components/UI';
import { useNavigate } from 'react-router-dom';

function QuoteRow({ label, value, highlight }) {
  return (
    <div style={{
      display: 'flex', justifyContent: 'space-between', alignItems: 'center',
      padding: '10px 0', borderBottom: '1px solid var(--color-border)',
    }}>
      <span style={{ color: 'var(--color-muted)', fontSize: 13 }}>{label}</span>
      <span style={{ fontWeight: highlight ? 700 : 500, fontSize: highlight ? 16 : 14, color: highlight ? 'var(--color-primary)' : 'var(--color-text)' }}>
        {value}
      </span>
    </div>
  );
}

export default function Quote() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [quote, setQuote] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    premiumService.getQuote(user.ssn)
      .then(res => setQuote(res.data))
      .catch(err => setError(err.response?.data?.message || 'No quote found. Please register your vehicle first.'))
      .finally(() => setLoading(false));
  }, [user.ssn]);

  if (loading) return <Spinner />;

  return (
    <div style={{ maxWidth: 640, margin: '0 auto' }}>
      <PageHeader
        title="Your Premium Quote"
        subtitle="Based on your vehicle and profile information"
        action={
          <Button variant="ghost" onClick={() => navigate('/home')}>← Back</Button>
        }
      />

      {error && (
        <div>
          <Alert type="warning">{error}</Alert>
          <Button onClick={() => navigate('/vehicle')}>Register a Vehicle</Button>
        </div>
      )}

      {quote && (
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: 16 }}>

          {/* Customer Details */}
          <Card>
            <h3 style={{ fontWeight: 700, fontSize: 14, marginBottom: 14, color: 'var(--color-muted)', textTransform: 'uppercase', letterSpacing: .5 }}>
              Customer
            </h3>
            <QuoteRow label="Name" value={`${quote.firstName || ''} ${quote.lastName || ''}`.trim() || '—'} />
            <QuoteRow label="SSN" value={quote.ssn} />
            <QuoteRow label="Policy Type" value={quote.policyType || '—'} />
          </Card>

          {/* Vehicle Details */}
          <Card>
            <h3 style={{ fontWeight: 700, fontSize: 14, marginBottom: 14, color: 'var(--color-muted)', textTransform: 'uppercase', letterSpacing: .5 }}>
              Vehicle
            </h3>
            <QuoteRow label="Make" value={quote.vehicleMake || '—'} />
            <QuoteRow label="Model" value={quote.vehicleModel || '—'} />
            <QuoteRow label="Reg No." value={quote.regNo || '—'} />
          </Card>

          {/* Premium Breakdown — full width */}
          <Card style={{ gridColumn: '1 / -1' }}>
            <h3 style={{ fontWeight: 700, fontSize: 14, marginBottom: 14, color: 'var(--color-muted)', textTransform: 'uppercase', letterSpacing: .5 }}>
              Premium Breakdown
            </h3>
            <QuoteRow label="Coverage Amount" value={`$${(quote.policyAmount || 0).toLocaleString()}`} />
            <QuoteRow label="Monthly Premium"    value={`$${(quote.premiumMonthly    || 0).toLocaleString()}`} />
            <QuoteRow label="Quarterly Premium"  value={`$${(quote.premiumQuarterly  || 0).toLocaleString()}`} />
            <QuoteRow label="Annual Premium"     value={`$${(quote.premiumAnnually   || 0).toLocaleString()}`} highlight />

            <div style={{ marginTop: 20, padding: '14px 16px', background: '#eff6ff', borderRadius: 6 }}>
              <p style={{ fontSize: 13, color: 'var(--color-primary)', fontWeight: 600 }}>
                ✅ Quote generated successfully
              </p>
              <p style={{ fontSize: 12, color: 'var(--color-muted)', marginTop: 4 }}>
                This quote is valid for 30 days. Contact support to bind the policy.
              </p>
            </div>
          </Card>
        </div>
      )}
    </div>
  );
}
