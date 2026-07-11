import { useState } from 'react';
import './RestockModal.css';

export default function RestockModal({ vehicle, onClose, onConfirm, loading }) {
  const [quantity, setQuantity] = useState('');

  function handleSubmit(event) {
    event.preventDefault();
    onConfirm(Number(quantity));
  }

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal restock-modal" onClick={(e) => e.stopPropagation()}>
        <button type="button" className="modal-close" onClick={onClose} aria-label="Close">
          ×
        </button>
        <h2>Restock vehicle</h2>
        <p className="modal-subtitle">
          Add inventory for {vehicle.make} {vehicle.model} (current: {vehicle.quantity})
        </p>
        <form onSubmit={handleSubmit}>
          <label>
            Quantity to add
            <input
              type="number"
              min="1"
              required
              value={quantity}
              onChange={(e) => setQuantity(e.target.value)}
              placeholder="e.g. 10"
            />
          </label>
          <div className="modal-actions">
            <button type="button" className="btn btn-ghost" onClick={onClose}>
              Cancel
            </button>
            <button type="submit" className="btn btn-primary" disabled={loading}>
              {loading ? 'Restocking…' : 'Confirm restock'}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
