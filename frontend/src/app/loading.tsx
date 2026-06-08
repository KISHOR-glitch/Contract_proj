import { LoadingSpinner } from '@/components/LoadingSpinner';

export default function Loading() {
  return (
    <div className="px-4 sm:px-6 lg:px-8 mt-12">
      <LoadingSpinner />
      <p className="text-center text-gray-500 mt-4 text-sm">Loading contracts...</p>
    </div>
  );
}
