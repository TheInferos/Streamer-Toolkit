import React, { useState } from 'react'

const Wheel = ({ 
  items = [], 
  type = 'punishment', // 'punishment' or 'viewer'
  title = 'Selection Wheel',
  getItemName = (item) => item.name, // Function to get display name
  getItemColor = (index, total) => `hsl(${(360 / total) * index}, 70%, 60%)`, // Function to get color
  getItemWeight = (item) => item.weight || 1, // Function to get weight (for punishments)
  onItemSelected = () => {} // Callback when item is selected
}) => {
  const [isSpinning, setIsSpinning] = useState(false)
  const [selectedItem, setSelectedItem] = useState(null)
  const [rotation, setRotation] = useState(0)

  const spinWheel = () => {
    if (items.length === 0) return
    
    setIsSpinning(true)
    setSelectedItem(null)
    
    // Random rotation between 5-10 full spins plus random final position
    const spins = Math.random() * 5 + 5
    const finalRotation = Math.random() * 360
    const totalRotation = (spins * 360) + finalRotation
    
    setRotation(prev => prev + totalRotation)
    
    // Calculate which item was selected based on final position
    setTimeout(() => {
      const normalizedRotation = (rotation + totalRotation) % 360
      
      if (type === 'punishment') {
        // For punishments, use weighted selection
        const totalWeight = items.reduce((sum, item) => sum + getItemWeight(item), 0)
        let currentAngle = 0
        
        for (const item of items) {
          const segmentAngle = (getItemWeight(item) / totalWeight) * 360
          if (normalizedRotation >= currentAngle && normalizedRotation < currentAngle + segmentAngle) {
            setSelectedItem(item)
            onItemSelected(item)
            break
          }
          currentAngle += segmentAngle
        }
      } else {
        // For viewers, use equal segments
        const segmentAngle = 360 / items.length
        
        for (let i = 0; i < items.length; i++) {
          const startAngle = i * segmentAngle
          const endAngle = (i + 1) * segmentAngle
          
          if (normalizedRotation >= startAngle && normalizedRotation < endAngle) {
            setSelectedItem(items[i])
            onItemSelected(items[i])
            break
          }
        }
      }
      
      setIsSpinning(false)
    }, 3000)
  }

  if (items.length === 0) {
    return (
      <div className="wheel-section">
        <h3>{title}</h3>
        <div className="wheel-container">
          <div className="wheel">
            <div className="wheel-empty">
              <p>No {type === 'punishment' ? 'punishments' : 'viewers'} yet</p>
              <p>Add some {type === 'punishment' ? 'punishments' : 'viewers'} to spin the wheel!</p>
            </div>
          </div>
        </div>
        <p>Add {type === 'punishment' ? 'punishments' : 'viewers'} below to enable the wheel</p>
      </div>
    )
  }

  return (
    <div className="wheel-section">
      <h3>{title}</h3>
      <div className="wheel-container">
        <div className="wheel-pointer">▼</div>
        <div
          className="wheel"
          style={{
            transform: `rotate(${rotation}deg)`,
            transition: isSpinning ? 'transform 3s cubic-bezier(0.25, 0.46, 0.45, 0.94)' : 'none'
          }}
        >
          {items.map((item, index) => {
            let startAngle, segmentAngle
            
            if (type === 'punishment') {
              // For punishments, calculate weighted segments
              const totalWeight = items.reduce((sum, i) => sum + getItemWeight(i), 0)
              startAngle = items.slice(0, index).reduce((sum, i) => sum + (getItemWeight(i) / totalWeight) * 360, 0)
              segmentAngle = (getItemWeight(item) / totalWeight) * 360
            } else {
              // For viewers, use equal segments
              segmentAngle = 360 / items.length
              startAngle = index * segmentAngle
            }

            return (
              <div
                key={item.id}
                className="wheel-segment"
                style={{
                  transform: `rotate(${startAngle}deg)`,
                  backgroundColor: getItemColor(index, items.length),
                }}
              >
                <span style={{ transform: `rotate(${segmentAngle / 2}deg)` }}>
                  {getItemName(item)}
                </span>
              </div>
            )
          })}
        </div>
      </div>
      
      <div className="wheel-controls">
        <button 
          onClick={spinWheel} 
          disabled={isSpinning} 
          className="btn primary"
        >
          {isSpinning ? 'Spinning...' : 'Spin the Wheel!'}
        </button>
      </div>

      {selectedItem && (
        <div className="selected-item">
          <h3>🎯 Selected {type === 'punishment' ? 'Punishment' : 'Viewer'}:</h3>
          {type === 'punishment' ? (
            <>
              <p><strong>{selectedItem.name}</strong></p>
              <p>Weight: {selectedItem.weight}</p>
            </>
          ) : (
            <>
              <p><strong>@{selectedItem.twitchHandle}</strong></p>
              {selectedItem.name && <p>Name: {selectedItem.name}</p>}
              {selectedItem.status && <p>Status: {selectedItem.status}</p>}
              {selectedItem.watchTime && <p>Watch Time: {selectedItem.watchTime}</p>}
              {selectedItem.messageCount && <p>Messages: {selectedItem.messageCount}</p>}
            </>
          )}
        </div>
      )}
    </div>
  )
}

export default Wheel
