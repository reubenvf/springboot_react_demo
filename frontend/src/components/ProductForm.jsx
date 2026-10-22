import { Button, Col, Form, Row, Spinner } from 'react-bootstrap';

export default function ProductForm({ form, isEditing, saving, error, onChange, onSubmit, onCancel }) {
  return (
    <div className="shadow-sm border-0 form-card card">
      <div className="card-body">
        <h2 className="section-title">{isEditing ? 'Update Product' : 'Add New Product'}</h2>

        <Form onSubmit={onSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Product name</Form.Label>
            <Form.Control
              type="text"
              name="name"
              value={form.name}
              onChange={onChange}
              placeholder="e.g. Laptop"
              required
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Description</Form.Label>
            <Form.Control
              as="textarea"
              rows={3}
              name="description"
              value={form.description}
              onChange={onChange}
              placeholder="Optional details"
            />
          </Form.Group>

          <Row>
            <Col md={6}>
              <Form.Group className="mb-3">
                <Form.Label>Quantity</Form.Label>
                <Form.Control
                  type="number"
                  min="0"
                  name="quantity"
                  value={form.quantity}
                  onChange={onChange}
                  required
                />
              </Form.Group>
            </Col>

            <Col md={6}>
              <Form.Group className="mb-3">
                <Form.Label>Price</Form.Label>
                <Form.Control
                  type="number"
                  min="0"
                  step="0.01"
                  name="price"
                  value={form.price}
                  onChange={onChange}
                  required
                />
              </Form.Group>
            </Col>
          </Row>

          {error && <div className="alert alert-danger py-2 mb-3">{error}</div>}

          <div className="d-flex gap-2">
            <Button type="submit" variant="primary" className="flex-fill" disabled={saving}>
              {saving ? <Spinner animation="border" size="sm" /> : isEditing ? 'Save Changes' : 'Add Product'}
            </Button>
            {isEditing && (
              <Button type="button" variant="outline-secondary" onClick={onCancel}>
                Cancel
              </Button>
            )}
          </div>
        </Form>
      </div>
    </div>
  );
}
