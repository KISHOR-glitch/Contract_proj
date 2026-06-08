'use client';

import React from 'react';
import { useRouter, usePathname, useSearchParams } from 'next/navigation';

const STATUS_OPTIONS = ['ALL', 'DRAFT', 'REVIEW', 'APPROVED', 'REJECTED', 'TERMINATED'];

export const StatusFilter: React.FC = () => {
  const router = useRouter();
  const pathname = usePathname();
  const searchParams = useSearchParams();

  const currentStatus = searchParams.get('status') || 'ALL';

  const handleChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const value = e.target.value;
    const current = new URLSearchParams(Array.from(searchParams.entries()));

    if (value && value !== 'ALL') {
      current.set('status', value);
    } else {
      current.delete('status');
    }
    
    // Reset page to 0 on new filter
    current.set('page', '0');

    const search = current.toString();
    const query = search ? `?${search}` : '';
    router.push(`${pathname}${query}`);
  };

  return (
    <div>
      <select
        id="status"
        name="status"
        className="mt-2 block w-full rounded-md border-0 py-1.5 pl-3 pr-10 text-gray-900 ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-indigo-600 sm:text-sm sm:leading-6"
        value={currentStatus}
        onChange={handleChange}
      >
        {STATUS_OPTIONS.map((status) => (
          <option key={status} value={status}>
            {status === 'ALL' ? 'All Statuses' : status}
          </option>
        ))}
      </select>
    </div>
  );
};
