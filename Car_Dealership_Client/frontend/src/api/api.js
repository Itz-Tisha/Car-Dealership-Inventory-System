//const API_BASE = '/api';
const API_BASE = import.meta.env.VITE_API_BASE;
function getAuthHeaders() {
  const token = localStorage.getItem('token');
  return token ? { Authorization: `Bearer ${token}` } : {};
}

async function handleResponse(response) {
  const data = await response.json().catch(() => ({}));
  if (!response.ok) {
    throw new Error(data.message || 'Something went wrong');
  }
  return data;
}

export async function login(email, password) {
  const response = await fetch(`${API_BASE}/auth/login`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ email, password }),
  });
  return handleResponse(response);
}

export async function register(name, email, password) {
  const response = await fetch(`${API_BASE}/auth/register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email, password }),
  });
  return handleResponse(response);
}

export async function fetchVehicles(params = {}) {
  const queryParams = {};

  if (params.make?.trim()) {
    queryParams.make = params.make.trim();
  } else if (params.model?.trim()) {
    queryParams.model = params.model.trim();
  } else if (params.category?.trim()) {
    queryParams.category = params.category.trim();
  } else if (params.minPrice !== '' && params.maxPrice !== '') {
    const minPrice = Number(params.minPrice);
    const maxPrice = Number(params.maxPrice);
    if (!Number.isNaN(minPrice) && !Number.isNaN(maxPrice)) {
      queryParams.minPrice = minPrice;
      queryParams.maxPrice = maxPrice;
    }
  }

  const query = new URLSearchParams(queryParams).toString();
  const url = query ? `${API_BASE}/vehicles/search?${query}` : `${API_BASE}/vehicles`;
  const response = await fetch(url);
  return handleResponse(response);
}

export async function addVehicle(vehicle) {
  const response = await fetch(`${API_BASE}/vehicles`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      ...getAuthHeaders(),
    },
    body: JSON.stringify(vehicle),
  });
  return handleResponse(response);
}

export async function updateVehicle(id, vehicle) {
  const response = await fetch(`${API_BASE}/vehicles/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      ...getAuthHeaders(),
    },
    body: JSON.stringify(vehicle),
  });
  return handleResponse(response);
}

export async function deleteVehicle(id) {
  const response = await fetch(`${API_BASE}/vehicles/${id}`, {
    method: 'DELETE',
    headers: getAuthHeaders(),
  });
  return handleResponse(response);
}

export async function purchaseVehicle(id) {
  const response = await fetch(`${API_BASE}/vehicles/${id}/purchase`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      ...getAuthHeaders(),
    },
  });
  return handleResponse(response);
}

export async function restockVehicle(id, quantity) {
  const response = await fetch(`${API_BASE}/vehicles/${id}/restock`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      ...getAuthHeaders(),
    },
    body: JSON.stringify({ quantity }),
  });
  return handleResponse(response);
}
