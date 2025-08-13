import React from 'react'

const Sidebar = ({ isOpen, currentView, setCurrentView }) => {
  const menuItems = [
    { id: 'games', label: 'Games', icon: '🎮' },
    { id: 'streams', label: 'Streams', icon: '📹' },
    { id: 'viewers', label: 'Viewers', icon: '👥' },
    { id: 'punishment-wheel', label: 'Punishment Wheel', icon: '⛓️' },

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
    </aside>
  )
}

export default Sidebar
