export default function VehicleCard({
  vehicle,
  isAdmin,
  isAuthenticated,
  onPurchase,
  onDelete,
  onRestock,
  onEdit,
  onLoginRequired,
}) {
  const inStock = vehicle.quantity > 0;

  return (
    <article className="vehicle-card">
      <div className="vehicle-card-header">
        <span className="vehicle-category">{vehicle.category}</span>
        <span className={`stock-badge ${inStock ? 'in-stock' : 'out-of-stock'}`}>
          {inStock ? `${vehicle.quantity} in stock` : 'Out of stock'}
        </span>
      </div>
      <h3>
        {vehicle.make} <span>{vehicle.model}</span>
      </h3>
      <p className="vehicle-price">
        ${vehicle.price?.toLocaleString(undefined, { minimumFractionDigits: 0 })}
      </p>
      <div className="vehicle-actions">
        {isAuthenticated ? (
          <button
            type="button"
            className="btn btn-primary"
            disabled={!inStock}
            onClick={() => onPurchase(vehicle.id)}
          >
            Purchase
          </button>
        ) : (
          <button type="button" className="btn btn-secondary" onClick={onLoginRequired}>
            Sign in to buy
          </button>
        )}
        {isAdmin && (
          <div className="admin-actions">
            <button type="button" className="btn btn-ghost" onClick={() => onEdit(vehicle)}>
              Edit
            </button>
            <button type="button" className="btn btn-ghost" onClick={() => onRestock(vehicle)}>
              Restock
            </button>
            <button
              type="button"
              className="btn btn-danger"
              onClick={() => onDelete(vehicle.id)}
            >
              Delete
            </button>
          </div>
        )}
      </div>
    </article>
  );
}
