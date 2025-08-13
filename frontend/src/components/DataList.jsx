import React from 'react'
import Card from './Card'

const DataList = ({ 
  items, 
  type, 
  onEditClick,
  emptyStateMessage = 'No items found',
  emptyStateSubmessage = 'Start adding items to get started!'
}) => {
  if (items.length === 0) {
    return (
      <div className="empty-state">
        <p>{emptyStateMessage}</p>
        <p>{emptyStateSubmessage}</p>
      </div>
    )
  }

  return (
    <div className={`${type}s-grid`}>
      {items.map((item) => (
        <Card 
          key={item.id} 
          item={item}
          type={type}
          onEditClick={onEditClick}
        />
      ))}
    </div>
  )
}

export default DataList
