import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { vehicleService } from '../services/api';
import { useNavigate } from 'react-router-dom';
import { Card, Button, FormField, Input, Select, Alert, PageHeader } from '../components/UI';

const MAKES = ['Maruti', 'Toyota', 'Honda', 'Ford', 'BMW', 'Hyundai', 'Chevrolet', 'Nissan', 'Volkswagen', 'Other'];
const CURRENT_YEAR = new Date().getFullYear();
const YEARS = Array.from({ length: 30 }, (_, i) => CURRENT_YEAR - i);

export default function VehicleRegister() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({
    type: '', make: '', model: '', regNo: '',
    mfYear: String(CURRENT_YEAR - 5), totalAccident: '0',
    policyType: 'Collision', policyAmount: '5000',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handle = e => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await vehicleService.registerVehicle({ ...form, ssn: user.ssn });
      navigate('/home');
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to register vehicle. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 640, margin: '0 auto' }}>
      <PageHeader
        title="Register Your Vehicle"
        subtitle="Add vehicle details to generate your insurance quote"
      />

      <Card>
        {error && <Alert type="error">{error}</Alert>}
        <form onSubmit={handleSubmit}>

          {/* Vehicle Details Section */}
          <p style={{ fontWeight: 700, fontSize: 13, color: 'var(--color-muted)', marginBottom: 14, textTransform: 'uppercase', letterSpacing: .5 }}>
            Vehicle Details
          </p>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '0 16px' }}>

            <FormField label="Vehicle Type *">
              <Select name="type" value={form.type} onChange={handle} required>
                <option value="">Select type…</option>
                <option value="2">Two Wheeler</option>
                <option value="4">Four Wheeler</option>
                <option value="6">Heavy Vehicle</option>
              </Select>
            </FormField>

            <FormField label="Make *">
              <Select name="make" value={form.make} onChange={handle} required>
                <option value="">Select make…</option>
                {MAKES.map(m => <option key={m} value={m.toLowerCase()}>{m}</option>)}
              </Select>
            </FormField>

            <FormField label="Model *">
              <Input name="model" value={form.model} onChange={handle} placeholder="e.g. Camry, Civic" required />
            </FormField>

            <FormField label="Manufacturing Year *">
              <Select name="mfYear" value={form.mfYear} onChange={handle}>
                {YEARS.map(y => <option key={y} value={y}>{y}</option>)}
              </Select>
            </FormField>

            <FormField label="Registration Number *">
              <Input name="regNo" value={form.regNo} onChange={handle} placeholder="e.g. KA-01-MH-1234" required />
            </FormField>

            <FormField label="Prior Accidents">
              <Select name="totalAccident" value={form.totalAccident} onChange={handle}>
                {[...Array(11).keys()].map(n => (
                  <option key={n} value={n}>{n === 0 ? 'None' : n}</option>
                ))}
              </Select>
            </FormField>
          </div>

          {/* Policy Section */}
          <p style={{ fontWeight: 700, fontSize: 13, color: 'var(--color-muted)', margin: '16px 0 14px', textTransform: 'uppercase', letterSpacing: .5 }}>
            Policy Selection
          </p>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '0 16px' }}>

            <FormField label="Policy Type">
              <Select name="policyType" value={form.policyType} onChange={handle}>
                <option>Bodily Injury</option>
                <option>Collision</option>
                <option>Medical Payment</option>
                <option>Physical Damage</option>
                <option>Comprehensive</option>
              </Select>
            </FormField>

            <FormField label="Coverage Amount">
              <Select name="policyAmount" value={form.policyAmount} onChange={handle}>
                <option value="5000">$5,000</option>
                <option value="10000">$10,000</option>
                <option value="15000">$15,000</option>
                <option value="25000">$25,000</option>
                <option value="50000">$50,000</option>
              </Select>
            </FormField>
          </div>

          {/* Summary */}
          <div style={{
            background: '#f8fafc', border: '1px solid var(--color-border)',
            borderRadius: 6, padding: '12px 16px', margin: '16px 0',
            fontSize: 13, color: 'var(--color-muted)',
          }}>
            💡 Based on your details, a premium quote will be calculated automatically after registration.
          </div>

          <div style={{ display: 'flex', gap: 10 }}>
            <Button type="submit" fullWidth disabled={loading}>
              {loading ? 'Registering…' : 'Register Vehicle'}
            </Button>
            <Button type="button" variant="ghost" onClick={() => navigate('/home')}>
              Cancel
            </Button>
          </div>
        </form>
      </Card>
    </div>
  );
}
