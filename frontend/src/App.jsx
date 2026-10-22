import { useEffect, useState } from 'react';
import { Card, Col, Container, Row, Spinner } from 'react-bootstrap';
import ProductForm from './components/ProductForm';
import ProductTable from './components/ProductTable';
import { createProduct, deleteProduct, fetchProducts, updateProduct } from './services/productService';
import './App.css';

const emptyForm = {
  id: null,
  name: '',
  description: '',
  quantity: 0,
  price: 0,
};

function App() {
  const [products, setProducts] = useState([]);
  const [form, setForm] = useState(emptyForm);
  const [isEditing, setIsEditing] = useState(false);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState('');

  const loadProducts = async () => {
    try {
      setLoading(true);
      const data = await fetchProducts();
      setProducts(data.filter((product) => Number(product.quantity) > 0));
      setError('');
    } catch (err) {
      setError(err.message || 'Something went wrong while loading the product list.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadProducts();
  }, []);

  const handleFormChange = (event) => {
    const { name, value } = event.target;
    setForm((prev) => ({
      ...prev,
      [name]: ['quantity', 'price'].includes(name) ? Number(value) : value,
    }));
  };

  const resetForm = () => {
    setForm(emptyForm);
    setIsEditing(false);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    setSaving(true);
    setError('');

    const payload = {
      name: form.name.trim(),
      description: form.description.trim(),
      quantity: Math.max(0, Number(form.quantity) || 0),
      price: Number(form.price) || 0,
    };

    try {
      const nextProduct = isEditing
        ? await updateProduct(form.id, payload)
        : await createProduct(payload);

      setProducts((prev) => {
        const nextList = isEditing
          ? prev.map((product) => (product.id === nextProduct.id ? nextProduct : product))
          : [nextProduct, ...prev];
        return nextList.filter((product) => Number(product.quantity) > 0);
      });

      resetForm();
    } catch (err) {
      setError(err.message || 'Unable to save product.');
    } finally {
      setSaving(false);
    }
  };

  const handleEdit = (product) => {
    setIsEditing(true);
    setForm({
      id: product.id,
      name: product.name,
      description: product.description || '',
      quantity: Number(product.quantity) || 0,
      price: Number(product.price) || 0,
    });
  };

  const handleQuantityChange = async (product, delta) => {
    const nextQuantity = Math.max(0, Number(product.quantity) + delta);

    try {
      const savedProduct = await updateProduct(product.id, {
        name: product.name,
        description: product.description || '',
        quantity: nextQuantity,
        price: Number(product.price) || 0,
      });

      setProducts((prev) =>
        prev
          .map((item) => (item.id === savedProduct.id ? savedProduct : item))
          .filter((item) => Number(item.quantity) > 0)
      );
    } catch (err) {
      setError(err.message || 'Unable to update quantity');
    }
  };

  const handleDelete = async (productId) => {
    try {
      await deleteProduct(productId);
      setProducts((prev) => prev.filter((product) => product.id !== productId));
    } catch (err) {
      setError(err.message || 'Unable to delete product');
    }
  };

  return (
    <Container className="py-4 product-shell">
      <Row className="mb-4">
        <Col>
          <div className="page-header">
            <div>
              <p className="eyebrow">Inventory</p>
              <h1>Inventory Dashboard</h1>
            </div>
            <span className="product-counter">{products.length} products</span>
          </div>
        </Col>
      </Row>

      <Row className="g-4">
        <Col lg={4}>
          <ProductForm
            form={form}
            isEditing={isEditing}
            saving={saving}
            error={error}
            onChange={handleFormChange}
            onSubmit={handleSubmit}
            onCancel={resetForm}
          />
        </Col>

        <Col lg={8}>
          <Card className="shadow-sm border-0 list-card">
            <Card.Body>
              <div className="list-header">
                <h2 className="section-title">Product Listing</h2>
              </div>

              {loading ? (
                <div className="text-center py-5">
                  <Spinner animation="border" />
                </div>
              ) : (
                <ProductTable
                  products={products}
                  onDecrease={(product) => handleQuantityChange(product, -1)}
                  onIncrease={(product) => handleQuantityChange(product, 1)}
                  onEdit={handleEdit}
                  onDelete={handleDelete}
                />
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
}

export default App;
