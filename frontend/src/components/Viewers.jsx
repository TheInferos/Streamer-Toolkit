import React, { useState, useEffect } from 'react'

const Viewers = () => {
  const [viewers, setViewers] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    fetchViewers()
  }, [])

  const fetchViewers = async () => {
    try {
      setLoading(true)
      // Replace with your actual backend API endpoint
      const response = await fetch('http://localhost:8080/api/viewer/all')
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      
      const data = await response.json()
      setViewers(data)
    } catch (err) {
      setError(err.message)
      console.error('Error fetching viewers:', err)
    } finally {
      setLoading(false)
    }
  }

  if (loading) {
    return (
      <div className="page-container">
        <div className="page-header">
          <h2>Viewers</h2>
          <p>Loading viewers...</p>
        </div>
        <div className="loading-spinner">🔄</div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="page-container">
        <div className="page-header">
          <h2>Viewers</h2>
          <p>Error loading viewers</p>
        </div>
        <div className="error-message">
          <p>❌ Error: {error}</p>
          <button onClick={fetchViewers} className="btn primary">
            Try Again
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="page-container">
      <div className="page-header">
        <h2>Viewers</h2>
        <p>Manage your audience and viewer analytics</p>
      </div>

      <div className="data-section">
        <div className="section-header">
          <h3>All Viewers ({viewers.length})</h3>
          <button onClick={fetchViewers} className="btn secondary">
            Refresh
          </button>
        </div>

        {viewers.length === 0 ? (
          <div className="empty-state">
            <p>No viewers found</p>
            <p>Start streaming to see your viewers here!</p>
          </div>
        ) : (
          <div className="viewers-grid">
            {viewers.map((viewer) => (
              <div key={viewer.id} className="viewer-card">
                <div className="viewer-header">
                  <h4>{viewer.username}</h4>
                  <span className={`viewer-status ${viewer.status}`}>
                    {viewer.status}
                  </span>
                </div>
                <div className="viewer-details">
                  <p><strong>Join Date:</strong> {viewer.joinDate}</p>
                  <p><strong>Watch Time:</strong> {viewer.watchTime}</p>
                  <p><strong>Messages:</strong> {viewer.messageCount}</p>
                  {viewer.lastSeen && (
                    <p><strong>Last Seen:</strong> {viewer.lastSeen}</p>
                  )}
                </div>
                <div className="viewer-actions">
                  <button className="btn primary">Message</button>
                  <button className="btn secondary">View Profile</button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  )
}

export default Viewers
