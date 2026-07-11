import { useCallback, useEffect, useState } from 'react';
import * as api from './api/api';
import { AuthProvider, useAuth } from './context/AuthContext.jsx';
import AuthModal from './components/AuthModal.jsx';
import VehicleCard from './components/VehicleCard.jsx';
import RestockModal from './components/RestockModal.jsx';
import VehicleFormModal from './components/VehicleFormModal.jsx';
import './App.css';

const initialSearch = {
  make: '',
  model: '',
  category: '',
  minPrice: '',
  maxPrice: '',
};

function DealershipApp() {
  const { isAuthenticated, isAdmin, email, login, register, logout } = useAuth();
  const [vehicles, setVehicles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [toast, setToast] = useState('');
  const [authModal, setAuthModal] = useState(null);
  const [authError, setAuthError] = useState('');
  const [authLoading, setAuthLoading] = useState(false);
  const [restockTarget, setRestockTarget] = useState(null);
  const [restockLoading, setRestockLoading] = useState(false);
  const [vehicleForm, setVehicleForm] = useState(null);
  const [vehicleFormLoading, setVehicleFormLoading] = useState(false);
  const [vehicleFormError, setVehicleFormError] = useState('');
  const [search, setSearch] = useState(initialSearch);

  const showToast = useCallback((message) => {
    setToast(message);
    setTimeout(() => setToast(''), 3200);
  }, []);

  const loadVehicles = useCallback(async () => {
    setLoading(true);
    setError('');
    try {
      const data = await api.fetchVehicles(search);
      setVehicles(data);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }, [search]);

  useEffect(() => {
    loadVehicles();
  }, [loadVehicles]);

  function updateSearch(field, value) {
    setSearch((current) => ({ ...current, [field]: value }));
  }

  function clearSearch() {
    setSearch(initialSearch);
  }

  async function handleAuthSubmit({ name, email: userEmail, password }) {
    setAuthLoading(true);
    setAuthError('');
    try {
      if (authModal === 'login') {
        await login(userEmail, password);
        showToast('Signed in successfully');
      } else {
        await register(name, userEmail, password);
        showToast('Account created — please sign in');
        setAuthModal('login');
        setAuthLoading(false);
        return;
      }
      setAuthModal(null);
    } catch (err) {
      setAuthError(err.message);
    } finally {
      setAuthLoading(false);
    }
  }

  async function handlePurchase(id) {
    try {
      const result = await api.purchaseVehicle(id);
      showToast(result.message);
      loadVehicles();
    } catch (err) {
      showToast(err.message);
    }
  }

  async function handleDelete(id) {
    if (!window.confirm('Delete this vehicle from inventory?')) return;
    try {
      const result = await api.deleteVehicle(id);
      showToast(result.message);
      loadVehicles();
    } catch (err) {
      showToast(err.message);
    }
  }

  async function handleRestock(quantity) {
    setRestockLoading(true);
    try {
      const result = await api.restockVehicle(restockTarget.id, quantity);
      showToast(result.message);
      setRestockTarget(null);
      loadVehicles();
    } catch (err) {
      showToast(err.message);
    } finally {
      setRestockLoading(false);
    }
  }

  async function handleVehicleFormSubmit(vehicleData) {
    setVehicleFormLoading(true);
    setVehicleFormError('');
    try {
      const result = vehicleForm?.id
        ? await api.updateVehicle(vehicleForm.id, vehicleData)
        : await api.addVehicle(vehicleData);
      showToast(result.message);
      setVehicleForm(null);
      loadVehicles();
    } catch (err) {
      setVehicleFormError(err.message);
    } finally {
      setVehicleFormLoading(false);
    }
  }

  return (
    <div className="app">
      <header className="header">
        <div className="header-brand">
          <div className="logo-mark">CD</div>
          <div>
            <h1>CarDeal</h1>
            <p>Premium vehicles, delivered.</p>
          </div>
        </div>
        <div className="header-actions">
          {isAuthenticated ? (
            <div className="user-chip">
              <span>{email}</span>
              {isAdmin && <span className="admin-badge">Admin</span>}
              <button type="button" className="btn btn-ghost" onClick={logout}>
                Sign out
              </button>
            </div>
          ) : (
            <div className="header-auth-buttons">
              <button
                type="button"
                className="btn btn-ghost"
                onClick={() => setAuthModal('login')}
              >
                Sign in
              </button>
              <button
                type="button"
                className="btn btn-primary"
                onClick={() => setAuthModal('register')}
              >
                Register
              </button>
            </div>
          )}
          {isAdmin && (
            <button
              type="button"
              className="btn btn-primary"
              onClick={() => {
                setVehicleFormError('');
                setVehicleForm({});
              }}
            >
              Add vehicle
            </button>
          )}
        </div>
      </header>

      <section className="hero">
        <div className="hero-content">
          <h2>Find your next ride</h2>
          <p>
            Browse our curated inventory of quality vehicles. Purchase instantly or manage
            stock as an admin.
          </p>
        </div>
        <div className="hero-stats">
          <div>
            <strong>{vehicles.length}</strong>
            <span>Vehicles listed</span>
          </div>
          <div>
            <strong>{vehicles.filter((v) => v.quantity > 0).length}</strong>
            <span>Available now</span>
          </div>
        </div>
      </section>

      <section className="filters">
        <input
          type="text"
          placeholder="Filter by make…"
          value={search.make}
          onChange={(e) => updateSearch('make', e.target.value)}
        />
        <input
          type="text"
          placeholder="Filter by model…"
          value={search.model}
          onChange={(e) => updateSearch('model', e.target.value)}
        />
        <input
          type="text"
          placeholder="Filter by category…"
          value={search.category}
          onChange={(e) => updateSearch('category', e.target.value)}
        />
        <input
          type="number"
          placeholder="Min price"
          value={search.minPrice}
          onChange={(e) => updateSearch('minPrice', e.target.value)}
        />
        <input
          type="number"
          placeholder="Max price"
          value={search.maxPrice}
          onChange={(e) => updateSearch('maxPrice', e.target.value)}
        />
        <button type="button" className="btn btn-secondary" onClick={loadVehicles}>
          Search
        </button>
        <button type="button" className="btn btn-ghost" onClick={clearSearch}>
          Clear
        </button>
      </section>

      <main className="inventory">
        {loading && <p className="status-message">Loading inventory…</p>}
        {error && <p className="status-message error">{error}</p>}
        {!loading && !error && vehicles.length === 0 && (
          <p className="status-message">No vehicles match your search.</p>
        )}
        <div className="vehicle-grid">
          {vehicles.map((vehicle) => (
            <VehicleCard
              key={vehicle.id}
              vehicle={vehicle}
              isAdmin={isAdmin}
              isAuthenticated={isAuthenticated}
              onPurchase={handlePurchase}
              onDelete={handleDelete}
              onRestock={setRestockTarget}
              onEdit={(target) => {
                setVehicleFormError('');
                setVehicleForm(target);
              }}
              onLoginRequired={() => setAuthModal('login')}
            />
          ))}
        </div>
      </main>

      {authModal && (
        <AuthModal
          mode={authModal}
          onClose={() => {
            setAuthModal(null);
            setAuthError('');
          }}
          onSwitchMode={() => {
            setAuthModal(authModal === 'login' ? 'register' : 'login');
            setAuthError('');
          }}
          onSubmit={handleAuthSubmit}
          error={authError}
          loading={authLoading}
        />
      )}

      {restockTarget && (
        <RestockModal
          vehicle={restockTarget}
          onClose={() => setRestockTarget(null)}
          onConfirm={handleRestock}
          loading={restockLoading}
        />
      )}

      {vehicleForm && (
        <VehicleFormModal
          vehicle={vehicleForm.id ? vehicleForm : null}
          onClose={() => {
            setVehicleForm(null);
            setVehicleFormError('');
          }}
          onSubmit={handleVehicleFormSubmit}
          loading={vehicleFormLoading}
          error={vehicleFormError}
        />
      )}

      {toast && <div className="toast">{toast}</div>}
    </div>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <DealershipApp />
    </AuthProvider>
  );
}
