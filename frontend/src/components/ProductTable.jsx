import { Button, Table } from 'react-bootstrap';

export default function ProductTable({ products, onDecrease, onIncrease, onEdit, onDelete }) {
  if (products.length === 0) {
    return (
      <div className="empty-state">
        <p>Your inventory is empty.</p>
        <small>Products with zero quantity are automatically removed from the list.</small>
      </div>
    );
  }

  return (
    <Table responsive hover className="mb-0 product-table">
      <thead>
        <tr>
          <th>Product</th>
          <th>Quantity</th>
          <th>Price</th>
          <th className="text-center">Actions</th>
        </tr>
      </thead>
      <tbody>
        {products.map((product) => (
          <tr key={product.id}>
            <td>
              <div className="product-name-wrap">
                <strong>{product.name}</strong>
                {product.description && <small>{product.description}</small>}
              </div>
            </td>
            <td>
              <div className="quantity-control">
                <Button
                  variant="outline-secondary"
                  size="sm"
                  onClick={() => onDecrease(product)}
                  disabled={Number(product.quantity) <= 0}
                >
                  -
                </Button>
                <span>{product.quantity}</span>
                <Button variant="outline-secondary" size="sm" onClick={() => onIncrease(product)}>
                  +
                </Button>
              </div>
            </td>
            <td>${Number(product.price).toFixed(2)}</td>
            <td>
              <div className="action-buttons">
                <Button variant="outline-primary" size="sm" onClick={() => onEdit(product)}>
                  Edit
                </Button>
                <Button variant="outline-danger" size="sm" onClick={() => onDelete(product.id)}>
                  Delete
                </Button>
              </div>
            </td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
}
