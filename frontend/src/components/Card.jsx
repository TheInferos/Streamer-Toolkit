import React from 'react'

const Card = ({ 
  item, 
  type, 
  onEditClick, 
  onViewDetails 
}) => {
  // Configuration for different card types
  const cardConfig = {
    stream: {
      title: item.name,
      status: item.status,
      details: [
        { label: 'Games', value: item.games?.map(game => game.name).join(', ') || 'None' },
        { label: 'Viewers', value: item.viewers?.length || 0 },
        { label: 'URL', value: item.url, isLink: true },
        { label: 'Description', value: item.description, conditional: true },
        { label: 'Tags', value: item.tags?.join(', ') || 'None' }
      ],
      primaryAction: 'Edit',
      secondaryAction: 'View Details'
    },
    game: {
      title: item.name,
      subtitle: null,
      status: null,
      statusClass: null,
      details: [
        { label: 'Genre', value: item.genreList?.join(', ') || 'None' },
        { label: 'Description', value: item.description, conditional: true }
      ],
      primaryAction: 'Edit',
      secondaryAction: 'View Details'
    },
    viewer: {
      title: item.twitchHandle,
      subtitle: null,
      status: item.status,
      details: [
        { label: 'Name', value: item.name },
      ],
      primaryAction: 'Edit',
      secondaryAction: 'View Profile'
    },
    punishment: {
      title: item.name,
      subtitle: null,
      status: null,
      details: [
        { label: 'Weight', value: item.weight || 1 },
        { label: 'ID', value: item.id }
      ],
      primaryAction: 'Edit',
      secondaryAction: 'View Details'
    }
  }

  const config = cardConfig[type]
  const cardClass = `${type}-card`

  const handleEditClick = () => {
    if (onEditClick) {
      onEditClick(item)
    }
  }

  const handleViewDetails = () => {
    if (onViewDetails) {
      onViewDetails(item)
    }
  }

  return (
    <div className={cardClass}>
      <div className={`${type}-header`}>
        <h4>{config.title}</h4>
        {config.status && (
          <span>
            {config.status}
          </span>
        )}
      </div>
      
      {config.subtitle && (
        <div className={`${type}-subtitle`}>
          <h5>{config.subtitle}</h5>
        </div>
      )}
      
      <div className={`${type}-details`}>
        {config.details.map((detail, index) => {
          // Skip conditional details if they don't have a value
          if (detail.conditional && !detail.value) {
            return null
          }
          
          return (
            <p key={index}>
              <strong>{detail.label}:</strong> {
                detail.isLink ? (
                  <a href={detail.value} target="_blank" rel="noopener noreferrer">
                    {detail.value}
                  </a>
                ) : (
                  detail.value
                )
              }
            </p>
          )
        })}
      </div>
      
      <div className={`${type}-actions`}>
        <button onClick={handleEditClick} className="btn primary">
          {config.primaryAction}
        </button>
        <button onClick={handleViewDetails} className="btn secondary">
          {config.secondaryAction}
        </button>
      </div>
    </div>
  )
}

export default Card
