const API_URL = 'http://localhost:8080/api/products';

export async function fetchProducts() {
  const response = await fetch(API_URL);
  if (!response.ok) {
    throw new Error('We could not load the products right now.');
  }

  return response.json();
}

export async function createProduct(payload) {
  const response = await fetch(API_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    const apiError = await response.json().catch(() => null);
    throw new Error(apiError?.error || apiError?.message || 'Unable to save product.');
  }

  return response.json();
}

export async function updateProduct(id, payload) {
  const response = await fetch(`${API_URL}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    const apiError = await response.json().catch(() => null);
    throw new Error(apiError?.error || apiError?.message || 'Unable to update product.');
  }

  return response.json();
}

export async function deleteProduct(id) {
  const response = await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
  if (!response.ok) {
    throw new Error('Unable to delete product');
  }
}
