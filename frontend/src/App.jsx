import React, { useState } from 'react'
import './App.css'
import Header from './components/Header'
import Sidebar from './components/Sidebar'
import Games from './components/Games'
import Streams from './components/Streams'
import Viewers from './components/Viewers'
import PunishmentWheel from './components/PunishmentWheel'


function App() {
  const [currentView, setCurrentView] = useState('games')
  const [sidebarOpen, setSidebarOpen] = useState(true)

  const renderContent = () => {
    switch (currentView) {
      case 'games':
        return <Games />
      case 'streams':
        return <Streams />
      case 'viewers':
        return <Viewers />
      case 'punishment-wheel':
        return <PunishmentWheel />
      default:
        return <Games />
    }
  }

  return (
    <div className="app">
      <Header 
        sidebarOpen={sidebarOpen} 
        setSidebarOpen={setSidebarOpen} 
      />
      <div className="main-container">
        <Sidebar 
          isOpen={sidebarOpen} 
          currentView={currentView}
          setCurrentView={setCurrentView}
        />
        <main className={`content ${sidebarOpen ? 'sidebar-open' : ''}`}>
          {renderContent()}
        </main>
      </div>
    </div>
  )
}

export default App
