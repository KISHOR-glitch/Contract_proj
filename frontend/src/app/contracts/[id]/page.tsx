import { api } from '@/lib/api';
import { StatusBadge } from '@/components/StatusBadge';
import { WorkflowTimeline } from '@/components/WorkflowTimeline';
import { ErrorState } from '@/components/ErrorState';
import Link from 'next/link';

export default async function ContractDetailsPage({
  params,
}: {
  params: { id: string };
}) {
  try {
    // Fetch contract details and history in parallel
    const [contract, history] = await Promise.all([
      api.getContract(params.id),
      api.getWorkflowHistory(params.id),
    ]);

    return (
      <div className="px-4 sm:px-6 lg:px-8 max-w-5xl mx-auto">
        <div className="mb-6">
          <Link
            href="/"
            className="flex items-center text-sm font-medium text-indigo-600 hover:text-indigo-500"
          >
            <svg className="mr-1 h-4 w-4" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" d="M10.5 19.5L3 12m0 0l7.5-7.5M3 12h18" />
            </svg>
            Back to Contracts
          </Link>
        </div>

        <div className="overflow-hidden bg-white shadow sm:rounded-lg">
          <div className="px-4 py-6 sm:px-6">
            <div className="flex items-center justify-between">
              <div>
                <h3 className="text-base font-semibold leading-7 text-gray-900">Contract Information</h3>
                <p className="mt-1 max-w-2xl text-sm leading-6 text-gray-500">Details and current status.</p>
              </div>
              <div>
                <StatusBadge status={contract.status} />
              </div>
            </div>
          </div>
          <div className="border-t border-gray-100">
            <dl className="divide-y divide-gray-100">
              <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                <dt className="text-sm font-medium text-gray-900">Title</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{contract.title}</dd>
              </div>
              <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                <dt className="text-sm font-medium text-gray-900">Owner</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">{contract.ownerName}</dd>
              </div>
              <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                <dt className="text-sm font-medium text-gray-900">Created Date</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                  {new Date(contract.createdAt).toLocaleString()}
                </dd>
              </div>
              <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                <dt className="text-sm font-medium text-gray-900">Last Updated</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                  {new Date(contract.updatedAt).toLocaleString()}
                </dd>
              </div>
              <div className="px-4 py-6 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
                <dt className="text-sm font-medium text-gray-900">Description</dt>
                <dd className="mt-1 text-sm leading-6 text-gray-700 sm:col-span-2 sm:mt-0">
                  {contract.description || 'No description provided.'}
                </dd>
              </div>
            </dl>
          </div>
        </div>

        <div className="mt-8 overflow-hidden bg-white shadow sm:rounded-lg">
          <div className="px-4 py-6 sm:px-6">
            <h3 className="text-base font-semibold leading-7 text-gray-900">Workflow History</h3>
            <p className="mt-1 max-w-2xl text-sm leading-6 text-gray-500">Timeline of status changes.</p>
          </div>
          <div className="border-t border-gray-100 px-4 py-6 sm:px-6">
            <WorkflowTimeline history={history} />
          </div>
        </div>
      </div>
    );
  } catch (error) {
    return (
      <div className="px-4 sm:px-6 lg:px-8 max-w-5xl mx-auto">
        <div className="mb-6">
          <Link
            href="/"
            className="flex items-center text-sm font-medium text-indigo-600 hover:text-indigo-500"
          >
            <svg className="mr-1 h-4 w-4" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
              <path strokeLinecap="round" strokeLinejoin="round" d="M10.5 19.5L3 12m0 0l7.5-7.5M3 12h18" />
            </svg>
            Back to Contracts
          </Link>
        </div>
        <ErrorState message={(error as Error).message} />
      </div>
    );
  }
}
