import React, { useState, useEffect } from 'react'

const Games = () => {
  const [games, setGames] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchGames()
  }, [])

  const fetchGames = async () => {
    try {
      setLoading(true)
      // Replace with your actual backend API endpoint
      const response = await fetch('http://localhost:8080/api/game/all')
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      
      const data = await response.json()
      setGames(data)
    } catch (err) {
      setError(err.message)
      console.error('Error fetching games:', err)
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="page-container">
        <div className="page-header">
          <h2>Games</h2>
          <p>Loading games...</p>
        </div>
        <div className="loading-spinner">🔄</div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="page-container">
        <div className="page-header">
          <h2>Games</h2>
          <p>Error loading games</p>
        </div>
        <div className="error-message">
          <p>❌ Error: {error}</p>
          <button onClick={fetchGames} className="btn primary">
            Try Again
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="page-container">
      <div className="page-header">
        <h2>Games</h2>
        <p>Game List</p>
      </div>

      <div className="data-section">
        <div className="section-header">
          <h3>All Games ({games.length})</h3>
          <button onClick={fetchGames} className="btn secondary">
            Refresh
          </button>
        </div>

        {games.length === 0 ? (
          <div className="empty-state">
            <p>No games found</p>
            <p>Add some games to get started!</p>
          </div>
        ) : (
          <div className="games-grid">
            {games.map((game) => (
              <div key={game.id} className="game-card">
                <div className="game-header">
                  <h4>{game.name}</h4>
                </div>
                <div className="game-details">
                  <p><strong>Genre:</strong> {game.genreList.join(', ')}</p>
                  {game.description && (
                    <p><strong>Description:</strong> {game.description}</p>
                  )}
                </div>
                <div className="game-actions">
                  <button className="btn primary">Edit</button>
                  <button className="btn secondary">View Details</button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default Games
