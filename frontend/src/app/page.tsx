import { api } from '@/lib/api';
import { ContractTable } from '@/components/ContractTable';
import { SearchBar } from '@/components/SearchBar';
import { StatusFilter } from '@/components/StatusFilter';
import { Pagination } from '@/components/Pagination';
import { EmptyState } from '@/components/EmptyState';
import { ErrorState } from '@/components/ErrorState';
import Link from 'next/link';

export default async function DashboardPage({
  searchParams,
}: {
  searchParams: { page?: string; search?: string; status?: string };
}) {
  const page = searchParams.page ? parseInt(searchParams.page, 10) : 0;
  const search = searchParams.search;
  const status = searchParams.status && searchParams.status !== 'ALL' ? searchParams.status : undefined;

  try {
    const data = await api.getContracts(page, 10, search, status);

    return (
      <div className="px-4 sm:px-6 lg:px-8">
        <div className="sm:flex sm:items-center">
          <div className="sm:flex-auto">
            <h1 className="text-2xl font-semibold leading-6 text-gray-900">Contracts</h1>
            <p className="mt-2 text-sm text-gray-700">
              A list of all contracts in your account including their title, owner, and status.
            </p>
          </div>
        </div>

        <div className="mt-8 flex flex-col sm:flex-row gap-4 mb-6">
          <div className="sm:w-64">
            <SearchBar />
          </div>
          <div className="sm:w-48">
            <StatusFilter />
          </div>
          {(search || status) && (
            <div className="flex items-end mb-1">
              <Link
                href="/"
                className="text-sm font-medium text-indigo-600 hover:text-indigo-500"
              >
                Clear Filters
              </Link>
            </div>
          )}
        </div>

        {data.content.length > 0 ? (
          <>
            <ContractTable contracts={data.content} />
            <div className="mt-4">
              <Pagination
                page={data.page}
                totalPages={data.totalPages}
                totalElements={data.totalElements}
                size={data.size}
              />
            </div>
          </>
        ) : (
          <EmptyState
            clearFilterUrl={search || status ? "/" : undefined}
          />
        )}
      </div>
    );
  } catch (error) {
    return (
      <div className="px-4 sm:px-6 lg:px-8">
        <h1 className="text-2xl font-semibold leading-6 text-gray-900 mb-6">Contracts</h1>
        <ErrorState message={(error as Error).message} />
      </div>
    );
  }
}
