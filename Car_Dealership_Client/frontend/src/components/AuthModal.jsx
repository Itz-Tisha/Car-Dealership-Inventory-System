import './AuthModal.css';

export default function AuthModal({ mode, onClose, onSwitchMode, onSubmit, error, loading }) {
  const isLogin = mode === 'login';

  function handleSubmit(event) {
    event.preventDefault();
    const form = new FormData(event.target);
    onSubmit({
      name: form.get('name'),
      email: form.get('email'),
      password: form.get('password'),
    });
  }

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal" onClick={(e) => e.stopPropagation()}>
        <button type="button" className="modal-close" onClick={onClose} aria-label="Close">
          ×
        </button>
        <h2>{isLogin ? 'Welcome back' : 'Create account'}</h2>
        <p className="modal-subtitle">
          {isLogin
            ? 'Sign in to purchase vehicles and manage inventory'
            : 'Join to start browsing and buying'}
        </p>
        <form onSubmit={handleSubmit} className="auth-form">
          {!isLogin && (
            <label>
              Full name
              <input name="name" type="text" required placeholder="John Doe" />
            </label>
          )}
          <label>
            Email
            <input name="email" type="email" required placeholder="you@example.com" />
          </label>
          <label>
            Password
            <input name="password" type="password" required placeholder="••••••••" />
          </label>
          {error && <p className="form-error">{error}</p>}
          <button type="submit" className="btn btn-primary" disabled={loading}>
            {loading ? 'Please wait…' : isLogin ? 'Sign in' : 'Create account'}
          </button>
        </form>
        <p className="modal-switch">
          {isLogin ? "Don't have an account? " : 'Already have an account? '}
          <button type="button" onClick={onSwitchMode}>
            {isLogin ? 'Register' : 'Sign in'}
          </button>
        </p>
      </div>
    </div>
  );
}
