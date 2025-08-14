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
           // Adjust for pointer at top (12 o'clock) - add 180 degrees to align with segment positioning
           const adjustedRotation = (normalizedRotation + 180) % 360
           if (adjustedRotation >= currentAngle && adjustedRotation < currentAngle + segmentAngle) {
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
           // Adjust for pointer at top (12 o'clock) - add 180 degrees to align with segment positioning
           const adjustedRotation = (normalizedRotation + 180) % 360
           
           if (adjustedRotation >= startAngle && adjustedRotation < endAngle) {
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
         <div className="wheel-wrapper">
           <div className="wheel-pointer">▼</div>
           <svg
             className="wheel"
             width="300"
             height="300"
             viewBox="0 0 300 300"
             style={{
               transform: `rotate(${rotation}deg)`,
               transition: isSpinning ? 'transform 3s cubic-bezier(0.25, 0.46, 0.45, 0.94)' : 'none'
             }}
           >
           <circle cx="150" cy="150" r="150" fill="none" stroke="#e2e8f0" strokeWidth="8"/>
           {items.map((item, index) => {
             let startAngle, segmentAngle
             
             if (type === 'punishment') {
               // For punishments, calculate weighted segments
               const totalWeight = items.reduce((sum, i) => sum + getItemWeight(i), 0)
               // Calculate cumulative weight up to this index
               const cumulativeWeight = items.slice(0, index).reduce((sum, i) => sum + getItemWeight(i), 0)
               startAngle = (cumulativeWeight / totalWeight) * 360
               segmentAngle = (getItemWeight(item) / totalWeight) * 360
             } else {
               // For viewers, use equal segments
               segmentAngle = 360 / items.length
               startAngle = index * segmentAngle
             }

             // Convert angles to radians and calculate SVG path (clockwise direction)
             const startRad = (90 - startAngle) * Math.PI / 180
             const endRad = (90 - startAngle - segmentAngle) * Math.PI / 180
             
             const x1 = 150 + 150 * Math.cos(startRad)
             const y1 = 150 + 150 * Math.sin(startRad)
             const x2 = 150 + 150 * Math.cos(endRad)
             const y2 = 150 + 150 * Math.sin(endRad)
             
             // Create the pie slice path
             const largeArcFlag = segmentAngle > 180 ? 1 : 0
             const pathData = [
               `M 150 150`,
               `L ${x1} ${y1}`,
               `A 150 150 0 ${largeArcFlag} 0 ${x2} ${y2}`,
               `Z`
             ].join(' ')

             return (
               <g key={item.id}>
                 <path
                   d={pathData}
                   fill={getItemColor(index, items.length)}
                   stroke="white"
                   strokeWidth="2"
                 />
                 <text
                   x={150 + 100 * Math.cos((90 - startAngle - segmentAngle / 2) * Math.PI / 180)}
                   y={150 + 100 * Math.sin((90 - startAngle - segmentAngle / 2) * Math.PI / 180)}
                   textAnchor="middle"
                   dominantBaseline="middle"
                   fill="white"
                   fontSize="12"
                   fontWeight="bold"
                   style={{ textShadow: '1px 1px 2px rgba(0, 0, 0, 0.8)' }}
                 >
                   {getItemName(item)}
                 </text>
               </g>
             )
                        })}
           </svg>
         </div>
         
         {/* Legend */}
         {type === 'punishment' && items.length > 0 && (
           <div className="wheel-legend">
             <h4>Legend</h4>
             {items.map((item, index) => {
               const totalWeight = items.reduce((sum, i) => sum + getItemWeight(i), 0)
               const percentage = ((getItemWeight(item) / totalWeight) * 100).toFixed(1)
               
               return (
                 <div key={item.id} className="legend-item">
                   <div 
                     className="legend-color" 
                     style={{ backgroundColor: getItemColor(index, items.length) }}
                   />
                   <span className="legend-name">{getItemName(item)}</span>
                   <span className="legend-percentage">{percentage}%</span>
                 </div>
               )
             })}
           </div>
         )}
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
