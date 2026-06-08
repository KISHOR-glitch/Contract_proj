import { ContractDetail, ContractSummary, PagedResponse, WorkflowHistory } from '../types';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api';

class ApiError extends Error {
  constructor(public status: number, message: string) {
    super(message);
    this.name = 'ApiError';
  }
}

async function fetchWithHandleError<T>(url: string, options?: RequestInit): Promise<T> {
  const response = await fetch(url, options);
  if (!response.ok) {
    let message = 'An error occurred while fetching data.';
    try {
      const errorData = await response.json();
      message = errorData.message || message;
    } catch (e) {
      // Ignore
    }
    throw new ApiError(response.status, message);
  }
  return response.json();
}

export const api = {
  async getContracts(
    page: number = 0,
    size: number = 10,
    search?: string,
    status?: string
  ): Promise<PagedResponse<ContractSummary>> {
    const params = new URLSearchParams({
      page: page.toString(),
      size: size.toString(),
    });
    if (search) params.append('search', search);
    if (status) params.append('status', status);

    return fetchWithHandleError<PagedResponse<ContractSummary>>(
      `${API_BASE_URL}/contracts?${params.toString()}`,
      { cache: 'no-store' }
    );
  },

  async getContract(id: string): Promise<ContractDetail> {
    return fetchWithHandleError<ContractDetail>(`${API_BASE_URL}/contracts/${id}`, { cache: 'no-store' });
  },

  async getWorkflowHistory(id: string): Promise<WorkflowHistory[]> {
    return fetchWithHandleError<WorkflowHistory[]>(`${API_BASE_URL}/contracts/${id}/history`, { cache: 'no-store' });
  },
};
