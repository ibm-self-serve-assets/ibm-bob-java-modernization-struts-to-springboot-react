import React, { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { userService } from '../services/api';
import { useNavigate } from 'react-router-dom';
import { Card, Button, FormField, Input, Select, Alert, PageHeader, Spinner } from '../components/UI';

const BLOOD_GROUPS = ['A+','A-','B+','B-','AB+','AB-','O+','O-'];

export default function ProfileUpdate() {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState(null);
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    userService.getProfile(user.ssn).then(res => setForm(res.data));
  }, [user.ssn]);

  const handle = e => setForm(f => ({ ...f, [e.target.name]: e.target.value }));

  const handleSubmit = async (e) => {
    e.preventDefault();
    setMessage('');
    setError('');
    setLoading(true);
    try {
      await userService.updateProfile(user.ssn, form);
      setMessage('Profile updated successfully.');
    } catch (err) {
      setError(err.response?.data?.message || 'Update failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  if (!form) return <Spinner />;

  return (
    <div style={{ maxWidth: 600, margin: '0 auto' }}>
      <PageHeader
        title="Update Profile"
        subtitle="Keep your personal information up to date"
        action={<Button variant="ghost" onClick={() => navigate('/home')}>← Back</Button>}
      />

      <Card>
        {message && <Alert type="success">{message}</Alert>}
        {error && <Alert type="error">{error}</Alert>}

        <form onSubmit={handleSubmit}>
          {/* Read-only identity */}
          <div style={{
            background: '#f8fafc', borderRadius: 6, padding: '10px 14px', marginBottom: 20,
            border: '1px solid var(--color-border)', fontSize: 13, color: 'var(--color-muted)',
          }}>
            <strong style={{ color: 'var(--color-text)' }}>SSN:</strong> {form.ssn}
            &nbsp;&nbsp;|&nbsp;&nbsp;
            <strong style={{ color: 'var(--color-text)' }}>Role:</strong> {user.role === 'ROLE_ADMIN' ? 'Administrator' : 'Customer'}
          </div>

          <p style={{ fontWeight: 700, fontSize: 13, color: 'var(--color-muted)', marginBottom: 14, textTransform: 'uppercase', letterSpacing: .5 }}>
            Personal Information
          </p>
          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '0 16px' }}>

            <FormField label="First Name">
              <Input name="firstName" value={form.firstName || ''} onChange={handle} />
            </FormField>

            <FormField label="Last Name">
              <Input name="lastName" value={form.lastName || ''} onChange={handle} />
            </FormField>

            <FormField label="Mobile Number">
              <Input name="mobileNo" type="tel" value={form.mobileNo || ''} onChange={handle} />
            </FormField>

            <FormField label="Email Address">
              <Input name="email" type="email" value={form.email || ''} onChange={handle} />
            </FormField>

            <FormField label="City">
              <Input name="city" value={form.city || ''} onChange={handle} />
            </FormField>

            <FormField label="Blood Group">
              <Select name="bloodGroup" value={form.bloodGroup || 'O+'} onChange={handle}>
                {BLOOD_GROUPS.map(bg => <option key={bg} value={bg}>{bg}</option>)}
              </Select>
            </FormField>

            <FormField label="Driving Licence" style={{ gridColumn: '1 / -1' }}>
              <Input name="drivingLicence" value={form.drivingLicence || ''} onChange={handle} />
            </FormField>
          </div>

          <div style={{ display: 'flex', gap: 10, marginTop: 8 }}>
            <Button type="submit" fullWidth disabled={loading}>
              {loading ? 'Saving…' : 'Save Changes'}
            </Button>
            <Button type="button" variant="ghost" fullWidth onClick={() => navigate('/home')}>
              Cancel
            </Button>
          </div>
        </form>
      </Card>
    </div>
  );
}
