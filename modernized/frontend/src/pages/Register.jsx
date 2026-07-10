import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authService } from '../services/api';
import { Card, Button, FormField, Input, Select, Alert, PageHeader } from '../components/UI';

const BLOOD_GROUPS = ['A+','A-','B+','B-','AB+','AB-','O+','O-'];

export default function Register() {
  const [form, setForm] = useState({
    ssn: '', password: '', firstName: '', lastName: '',
    gender: 'M', dateOfBirth: '', mobileNo: '', email: '',
    city: '', bloodGroup: 'O+', drivingLicence: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handle = e => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    try {
      await authService.register(form);
      navigate('/login');
    } catch (err) {
      setError(err.response?.data?.message || 'Registration failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ maxWidth: 600, margin: '0 auto' }}>
      <PageHeader
        title="Customer Registration"
        subtitle="Create your PKS Auto Insurance account"
      />

      <Card>
        {error && <Alert type="error">{error}</Alert>}
        <form onSubmit={handleSubmit}>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '0 16px' }}>

            <FormField label="SSN *">
              <Input name="ssn" value={form.ssn} onChange={handle} placeholder="111-22-3333" required />
            </FormField>

            <FormField label="Password *">
              <Input name="password" type="password" value={form.password} onChange={handle} placeholder="Min 8 characters" required />
            </FormField>

            <FormField label="First Name *">
              <Input name="firstName" value={form.firstName} onChange={handle} required />
            </FormField>

            <FormField label="Last Name *">
              <Input name="lastName" value={form.lastName} onChange={handle} required />
            </FormField>

            <FormField label="Date of Birth *">
              <Input name="dateOfBirth" type="date" value={form.dateOfBirth} onChange={handle} required />
            </FormField>

            <FormField label="Gender">
              <Select name="gender" value={form.gender} onChange={handle}>
                <option value="M">Male</option>
                <option value="F">Female</option>
                <option value="O">Other</option>
              </Select>
            </FormField>

            <FormField label="Mobile Number">
              <Input name="mobileNo" type="tel" value={form.mobileNo} onChange={handle} placeholder="+1 555 000 0000" />
            </FormField>

            <FormField label="Email Address">
              <Input name="email" type="email" value={form.email} onChange={handle} placeholder="you@example.com" />
            </FormField>

            <FormField label="City">
              <Input name="city" value={form.city} onChange={handle} />
            </FormField>

            <FormField label="Blood Group">
              <Select name="bloodGroup" value={form.bloodGroup} onChange={handle}>
                {BLOOD_GROUPS.map(bg => <option key={bg} value={bg}>{bg}</option>)}
              </Select>
            </FormField>

            <FormField label="Driving Licence No." style={{ gridColumn: '1 / -1' }}>
              <Input name="drivingLicence" value={form.drivingLicence} onChange={handle} />
            </FormField>
          </div>

          <div style={{ display: 'flex', gap: 10, marginTop: 8 }}>
            <Button type="submit" fullWidth disabled={loading}>
              {loading ? 'Registering…' : 'Create Account'}
            </Button>
            <Button type="button" variant="ghost" fullWidth onClick={() => navigate('/login')}>
              Cancel
            </Button>
          </div>
        </form>

        <p style={{ textAlign: 'center', marginTop: 16, color: 'var(--color-muted)', fontSize: 13 }}>
          Already have an account?{' '}
          <Link to="/login" style={{ fontWeight: 600 }}>Sign in</Link>
        </p>
      </Card>
    </div>
  );
}
