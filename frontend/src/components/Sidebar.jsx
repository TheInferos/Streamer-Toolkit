import React from 'react'

const Sidebar = ({ isOpen, currentView, setCurrentView }) => {
  const menuItems = [
    { id: 'games', label: 'Games', icon: '🎮' },
    { id: 'streams', label: 'Streams', icon: '📹' },
    { id: 'viewers', label: 'Viewers', icon: '👥' },
  ]

  return (
    <aside className={`sidebar ${isOpen ? 'open' : 'closed'}`}>
      <nav className="sidebar-nav">
        <ul className="nav-list">
          {menuItems.map((item) => (
            <li key={item.id}>
              <button
                className={`nav-item ${currentView === item.id ? 'active' : ''}`}
                onClick={() => setCurrentView(item.id)}
              >
                <span className="nav-icon">{item.icon}</span>
                <span className="nav-label">{item.label}</span>
              </button>
            </li>
          ))}
        </ul>
      </nav>
      
      <div className="sidebar-footer">
        <div className="user-info">
          <div className="user-avatar">
            <img src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=32&h=32&fit=crop&crop=face" alt="User" />
          </div>
          <div className="user-details">
            <p className="user-name">John Doe</p>
            <p className="user-role">Admin</p>
          </div>
        </div>
      </div>
    </aside>
  )
}

export default Sidebar
