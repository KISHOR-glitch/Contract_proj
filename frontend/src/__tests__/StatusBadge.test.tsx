import React from 'react';
import { render, screen } from '@testing-library/react';
import { StatusBadge } from '../components/StatusBadge';

describe('StatusBadge', () => {
  it('renders the status text', () => {
    render(<StatusBadge status="APPROVED" />);
    expect(screen.getByTestId('status-badge')).toHaveTextContent('APPROVED');
  });

  it('applies the correct styling for APPROVED', () => {
    render(<StatusBadge status="APPROVED" />);
    const badge = screen.getByTestId('status-badge');
    expect(badge).toHaveClass('bg-green-100');
    expect(badge).toHaveClass('text-green-800');
  });

  it('applies the correct styling for DRAFT', () => {
    render(<StatusBadge status="DRAFT" />);
    const badge = screen.getByTestId('status-badge');
    expect(badge).toHaveClass('bg-gray-100');
    expect(badge).toHaveClass('text-gray-800');
  });

  it('applies a fallback style for unknown statuses', () => {
    render(<StatusBadge status="UNKNOWN_STATUS" />);
    const badge = screen.getByTestId('status-badge');
    expect(badge).toHaveClass('bg-gray-100');
    expect(badge).toHaveClass('text-gray-800');
  });
});
