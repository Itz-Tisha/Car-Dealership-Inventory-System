import { useState } from 'react';
import './AuthModal.css';

const emptyForm = {
  make: '',
  model: '',
  category: '',
  price: '',
  quantity: '',
};

export default function VehicleFormModal({ vehicle, onClose, onSubmit, loading, error }) {
  const isEdit = Boolean(vehicle);
  const [form, setForm] = useState(() =>
    vehicle
      ? {
          make: vehicle.make || '',
          model: vehicle.model || '',
          category: vehicle.category || '',
          price: String(vehicle.price ?? ''),
          quantity: String(vehicle.quantity ?? ''),
        }
      : emptyForm
  );

  function handleChange(field) {
    return (event) => {
      setForm((current) => ({ ...current, [field]: event.target.value }));
    };
  }

  function handleSubmit(event) {
    event.preventDefault();
    onSubmit({
      make: form.make.trim(),
      model: form.model.trim(),
      category: form.category.trim(),
      price: Number(form.price),
      quantity: Number(form.quantity),
    });
  }

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal vehicle-form-modal" onClick={(e) => e.stopPropagation()}>
        <button type="button" className="modal-close" onClick={onClose} aria-label="Close">
          ×
        </button>
        <h2>{isEdit ? 'Edit vehicle' : 'Add vehicle'}</h2>
        <p className="modal-subtitle">
          {isEdit
            ? 'Update the vehicle details in inventory'
            : 'Add a new vehicle to the dealership inventory'}
        </p>
        <form onSubmit={handleSubmit} className="auth-form">
          <label>
            Make
            <input
              type="text"
              required
              value={form.make}
              onChange={handleChange('make')}
              placeholder="Toyota"
            />
          </label>
          <label>
            Model
            <input
              type="text"
              required
              value={form.model}
              onChange={handleChange('model')}
              placeholder="Fortuner"
            />
          </label>
          <label>
            Category
            <input
              type="text"
              required
              value={form.category}
              onChange={handleChange('category')}
              placeholder="SUV"
            />
          </label>
          <label>
            Price
            <input
              type="number"
              min="1"
              step="0.01"
              required
              value={form.price}
              onChange={handleChange('price')}
              placeholder="4500000"
            />
          </label>
          <label>
            Quantity
            <input
              type="number"
              min="0"
              required
              value={form.quantity}
              onChange={handleChange('quantity')}
              placeholder="5"
            />
          </label>
          {error && <p className="form-error">{error}</p>}
          <div className="modal-actions">
            <button type="button" className="btn btn-ghost" onClick={onClose}>
              Cancel
            </button>
            <button type="submit" className="btn btn-primary" disabled={loading}>
              {loading ? 'Saving…' : isEdit ? 'Save changes' : 'Add vehicle'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
