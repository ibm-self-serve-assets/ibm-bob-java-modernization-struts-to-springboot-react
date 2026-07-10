/**
 * Shared UI primitives — Card, Button, Input, Select, Badge, Alert, Spinner, PageHeader
 */
import React from 'react';

/* ── Card ─────────────────────────────────────────────── */
export function Card({ children, style }) {
  return (
    <div style={{
      background: 'var(--color-surface)',
      border: '1px solid var(--color-border)',
      borderRadius: 'var(--radius)',
      boxShadow: 'var(--shadow-sm)',
      padding: '24px',
      ...style,
    }}>
      {children}
    </div>
  );
}

/* ── Button ───────────────────────────────────────────── */
const btnBase = {
  display: 'inline-flex', alignItems: 'center', justifyContent: 'center',
  gap: 6, padding: '9px 18px', borderRadius: 6, border: 'none',
  fontWeight: 600, fontSize: 13, lineHeight: 1, cursor: 'pointer',
  transition: 'background .15s, opacity .15s',
};
const variants = {
  primary:  { background: 'var(--color-primary)',  color: '#fff' },
  danger:   { background: 'var(--color-danger)',   color: '#fff' },
  ghost:    { background: '#f1f5f9', color: 'var(--color-text)', border: '1px solid var(--color-border)' },
  success:  { background: 'var(--color-success)',  color: '#fff' },
};

export function Button({ children, variant = 'primary', style, fullWidth, disabled, ...props }) {
  return (
    <button
      style={{
        ...btnBase,
        ...variants[variant],
        ...(fullWidth ? { width: '100%' } : {}),
        ...(disabled ? { opacity: .5, cursor: 'not-allowed' } : {}),
        ...style,
      }}
      disabled={disabled}
      {...props}
    >
      {children}
    </button>
  );
}

/* ── FormField ────────────────────────────────────────── */
export function FormField({ label, children, error }) {
  return (
    <div style={{ marginBottom: 14 }}>
      {label && <label style={{ display: 'block', marginBottom: 4, fontWeight: 500, fontSize: 13, color: 'var(--color-text)' }}>{label}</label>}
      {children}
      {error && <p style={{ color: 'var(--color-danger)', fontSize: 12, marginTop: 3 }}>{error}</p>}
    </div>
  );
}

const inputStyle = {
  width: '100%', padding: '8px 10px', border: '1px solid var(--color-border)',
  borderRadius: 6, fontSize: 14, background: '#fff', color: 'var(--color-text)',
  outline: 'none',
};

export function Input({ ...props }) {
  return <input style={inputStyle} {...props} />;
}

export function Select({ children, ...props }) {
  return <select style={{ ...inputStyle, cursor: 'pointer' }} {...props}>{children}</select>;
}

/* ── Alert ────────────────────────────────────────────── */
const alertColors = {
  success: { bg: '#f0fdf4', border: '#86efac', text: '#166534' },
  error:   { bg: '#fef2f2', border: '#fca5a5', text: '#991b1b' },
  info:    { bg: '#eff6ff', border: '#93c5fd', text: '#1e40af' },
  warning: { bg: '#fffbeb', border: '#fcd34d', text: '#92400e' },
};

export function Alert({ type = 'info', children }) {
  const c = alertColors[type];
  return (
    <div style={{
      padding: '10px 14px', borderRadius: 6, marginBottom: 16,
      background: c.bg, border: `1px solid ${c.border}`, color: c.text,
      fontSize: 13,
    }}>
      {children}
    </div>
  );
}

/* ── Badge ────────────────────────────────────────────── */
const badgeColors = {
  blue:   { bg: '#dbeafe', text: '#1e40af' },
  green:  { bg: '#dcfce7', text: '#166534' },
  red:    { bg: '#fee2e2', text: '#991b1b' },
  yellow: { bg: '#fef9c3', text: '#854d0e' },
  gray:   { bg: '#f1f5f9', text: '#475569' },
};

export function Badge({ color = 'blue', children }) {
  const c = badgeColors[color];
  return (
    <span style={{
      display: 'inline-block', padding: '2px 9px', borderRadius: 12,
      fontSize: 11, fontWeight: 700, background: c.bg, color: c.text,
    }}>
      {children}
    </span>
  );
}

/* ── Spinner ──────────────────────────────────────────── */
export function Spinner() {
  return (
    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', padding: 40 }}>
      <div style={{
        width: 32, height: 32, border: '3px solid var(--color-border)',
        borderTop: '3px solid var(--color-primary)', borderRadius: '50%',
        animation: 'spin .7s linear infinite',
      }} />
      <style>{`@keyframes spin{to{transform:rotate(360deg)}}`}</style>
    </div>
  );
}

/* ── PageHeader ───────────────────────────────────────── */
export function PageHeader({ title, subtitle, action }) {
  return (
    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: 24 }}>
      <div>
        <h1 style={{ fontSize: 20, fontWeight: 700, marginBottom: 2 }}>{title}</h1>
        {subtitle && <p style={{ color: 'var(--color-muted)', fontSize: 13 }}>{subtitle}</p>}
      </div>
      {action}
    </div>
  );
}

/* ── StatCard ─────────────────────────────────────────── */
export function StatCard({ label, value, color = 'var(--color-primary)', icon }) {
  return (
    <Card style={{ textAlign: 'center' }}>
      {icon && <div style={{ fontSize: 28, marginBottom: 8 }}>{icon}</div>}
      <div style={{ fontSize: 28, fontWeight: 700, color }}>{value}</div>
      <div style={{ color: 'var(--color-muted)', fontSize: 13, marginTop: 4 }}>{label}</div>
    </Card>
  );
}
