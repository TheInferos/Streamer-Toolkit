import React, { useState, useEffect } from 'react'

const Streams = () => {
  const [streams, setStreams] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchStreams()
  }, [])

  const fetchStreams = async () => {
    try {
      setLoading(true)
      // Replace with your actual backend API endpoint
      const response = await fetch('http://localhost:8080/api/stream/all')
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      
      const data = await response.json()
      setStreams(data)
    } catch (err) {
      setError(err.message)
      console.error('Error fetching streams:', err)
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="page-container">
        <div className="page-header">
          <h2>Streams</h2>
          <p>Loading streams...</p>
        </div>
        <div className="loading-spinner">🔄</div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="page-container">
        <div className="page-header">
          <h2>Streams</h2>
          <p>Error loading streams</p>
        </div>
        <div className="error-message">
          <p>❌ Error: {error}</p>
          <button onClick={fetchStreams} className="btn primary">
            Try Again
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="page-container">
      <div className="page-header">
        <h2>Streams</h2>
        <p>Manage your live streams and recordings</p>
      </div>

      <div className="data-section">
        <div className="section-header">
          <h3>All Streams ({streams.length})</h3>
          <button onClick={fetchStreams} className="btn secondary">
            Refresh
          </button>
        </div>

        {streams.length === 0 ? (
          <div className="empty-state">
            <p>No streams found</p>
            <p>Start streaming to see your content here!</p>
          </div>
        ) : (
          <div className="streams-grid">
            {streams.map((stream) => (
              <div key={stream.id} className="stream-card">
                <div className="stream-header">
                  <h4>{stream.title}</h4>
                  <span className={`stream-status ${stream.status}`}>
                    {stream.status}
                  </span>
                </div>
                <div className="stream-details">
                  <div className="stream-header">
                    <h4>{stream.name}</h4>
                  </div>
                  <p><strong>Games:</strong> {stream.games.map(game => game.name).join(', ')}</p>
                  <p><strong>Viewers:</strong> {stream.viewers.length}</p>
                  <p><strong>URL:</strong> <a href={stream.url} target="_blank" rel="noopener noreferrer">{stream.url}</a></p>
                  {stream.description && (
                    <p><strong>Description:</strong> {stream.description}</p>
                  )}
                  <p><strong>Tags:</strong> {stream.tags.join(', ')}</p>
                </div>
                <div className="stream-actions">
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

export default Streams
