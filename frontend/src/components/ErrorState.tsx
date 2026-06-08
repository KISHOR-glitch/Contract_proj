import React from 'react';
import Link from 'next/link';

interface ErrorStateProps {
  message?: string;
  retryUrl?: string;
}

export const ErrorState: React.FC<ErrorStateProps> = ({ 
  message = "An error occurred while fetching data.", 
  retryUrl 
}) => {
  return (
    <div className="rounded-md bg-red-50 p-4 my-6 border border-red-200">
      <div className="flex">
        <div className="flex-shrink-0">
          <svg className="h-5 w-5 text-red-400" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
            <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.28 7.22a.75.75 0 00-1.06 1.06L8.94 10l-1.72 1.72a.75.75 0 101.06 1.06L10 11.06l1.72 1.72a.75.75 0 101.06-1.06L11.06 10l1.72-1.72a.75.75 0 00-1.06-1.06L10 8.94 8.28 7.22z" clipRule="evenodd" />
          </svg>
        </div>
        <div className="ml-3 flex-1 md:flex md:justify-between">
          <p className="text-sm text-red-700">{message}</p>
          {retryUrl && (
            <p className="mt-2 text-sm md:ml-6 md:mt-0">
              <Link
                href={retryUrl}
                className="whitespace-nowrap font-medium text-red-700 hover:text-red-600 underline"
              >
                Try again
              </Link>
            </p>
          )}
        </div>
      </div>
    </div>
  );
};
