import React from 'react';
import { WorkflowHistory } from '../types';
import { StatusBadge } from './StatusBadge';

interface WorkflowTimelineProps {
  history: WorkflowHistory[];
}

export const WorkflowTimeline: React.FC<WorkflowTimelineProps> = ({ history }) => {
  if (!history || history.length === 0) {
    return <p className="text-gray-500 text-sm">No workflow history available.</p>;
  }

  return (
    <div className="flow-root">
      <ul role="list" className="-mb-8">
        {history.map((event, eventIdx) => (
          <li key={event.id}>
            <div className="relative pb-8">
              {eventIdx !== history.length - 1 ? (
                <span className="absolute left-4 top-4 -ml-px h-full w-0.5 bg-gray-200" aria-hidden="true" />
              ) : null}
              <div className="relative flex space-x-3">
                <div>
                  <span className="h-8 w-8 rounded-full bg-indigo-50 flex items-center justify-center ring-8 ring-white">
                    <svg className="h-4 w-4 text-indigo-600" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" d="M16.023 9.348h4.992v-.001M2.985 19.644v-4.992m0 0h4.992m-4.993 0l3.181 3.183a8.25 8.25 0 0013.803-3.7M4.031 9.865a8.25 8.25 0 0113.803-3.7l3.181 3.182m0-4.991v4.99" />
                    </svg>
                  </span>
                </div>
                <div className="flex min-w-0 flex-1 justify-between space-x-4 pt-1.5">
                  <div>
                    <p className="text-sm text-gray-500">
                      Status changed to <StatusBadge status={event.newStatus} /> by{' '}
                      <span className="font-medium text-gray-900">{event.changedBy}</span>
                    </p>
                    {event.previousStatus && (
                      <p className="text-xs text-gray-400 mt-1">
                        Previous status: {event.previousStatus}
                      </p>
                    )}
                  </div>
                  <div className="whitespace-nowrap text-right text-sm text-gray-500">
                    <time dateTime={event.changedAt}>
                      {new Date(event.changedAt).toLocaleString()}
                    </time>
                  </div>
                </div>
              </div>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
};
