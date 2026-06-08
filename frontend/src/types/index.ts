export interface ContractSummary {
  id: string;
  title: string;
  ownerName: string;
  status: string;
  createdAt: string;
  updatedAt: string;
}

export interface ContractDetail extends ContractSummary {
  description: string;
}

export interface WorkflowHistory {
  id: string;
  contractId: string;
  previousStatus: string | null;
  newStatus: string;
  changedBy: string;
  changedAt: string;
}

export interface PagedResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}
