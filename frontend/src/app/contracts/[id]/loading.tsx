import { LoadingSpinner } from '@/components/LoadingSpinner';

export default function Loading() {
  return (
    <div className="px-4 sm:px-6 lg:px-8 mt-12 max-w-5xl mx-auto">
      <div className="bg-white shadow sm:rounded-lg p-12 flex flex-col items-center justify-center">
        <LoadingSpinner />
        <p className="text-gray-500 mt-4 text-sm">Loading contract details...</p>
      </div>
    </div>
  );
}
