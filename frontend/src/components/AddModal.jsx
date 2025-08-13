import React, { useState, useEffect } from 'react'

const AddModal = ({ 
  isOpen, 
  type, 
  onClose, 
  onSubmit 
}) => {
  const [formData, setFormData] = useState({})

  // Reset form when modal opens/closes or type changes
  useEffect(() => {
    if (isOpen) {
      setFormData(getDefaultFormData(type))
    }
  }, [isOpen, type])

  const getDefaultFormData = (type) => {
    switch (type) {
      case 'game':
        return {
          name: '',
          description: '',
          genres: ''
        }
      case 'stream':
        return {
          title: '',
          name: '',
          description: '',
          tags: '',
          url: ''
        }
      case 'viewer':
        return {
          username: '',
          status: '',
          watchTime: '',
          messageCount: ''
        }
      default:
        return {}
    }
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setFormData(prev => ({
      ...prev,
      [name]: value
    }))
  }

  const handleSubmit = (e) => {
    e.preventDefault()
    
    // Process form data based on type
    let processedData = { ...formData }
    
    if (type === 'game' && formData.genres) {
      processedData.genreList = formData.genres.split(',').map(genre => genre.trim()).filter(genre => genre)
    }
    
    if (type === 'stream' && formData.tags) {
      processedData.tags = formData.tags.split(',').map(tag => tag.trim()).filter(tag => tag)
    }
    
    onSubmit(processedData)
    onClose()
  }

  const getFormFields = () => {
    switch (type) {
      case 'game':
        return [
          { name: 'name', label: 'Game Name', type: 'text', required: true },
          { name: 'description', label: 'Description', type: 'textarea' },
          { name: 'genres', label: 'Genres (comma-separated)', type: 'text' }
        ]
      case 'stream':
        return [
          { name: 'title', label: 'Title', type: 'text', required: true },
          { name: 'name', label: 'Name', type: 'text', required: true },
          { name: 'description', label: 'Description', type: 'textarea' },
          { name: 'tags', label: 'Tags (comma-separated)', type: 'text' },
          { name: 'url', label: 'URL', type: 'url' }
        ]
      case 'viewer':
        return [
          { name: 'username', label: 'Username', type: 'text', required: true },
          { name: 'status', label: 'Status', type: 'text' },
          { name: 'watchTime', label: 'Watch Time', type: 'text' },
          { name: 'messageCount', label: 'Message Count', type: 'number' }
        ]
      default:
        return []
    }
  }

  if (!isOpen) return null

  return (
    <div className="modal-overlay">
      <div className="modal">
        <div className="modal-header">
          <h3>Add New {type.charAt(0).toUpperCase() + type.slice(1)}</h3>
          <button onClick={onClose} className="modal-close">×</button>
        </div>
        
        <form onSubmit={handleSubmit}>
          <div className="modal-body">
            {getFormFields().map(field => (
              <div key={field.name} className="form-group">
                <label htmlFor={field.name}>{field.label}</label>
                {field.type === 'textarea' ? (
                  <textarea
                    id={field.name}
                    name={field.name}
                    value={formData[field.name] || ''}
                    onChange={handleInputChange}
                    required={field.required}
                    rows="3"
                  />
                ) : (
                  <input
                    id={field.name}
                    name={field.name}
                    type={field.type}
                    value={formData[field.name] || ''}
                    onChange={handleInputChange}
                    required={field.required}
                  />
                )}
              </div>
            ))}
          </div>
          
          <div className="modal-footer">
            <button type="button" onClick={onClose} className="btn secondary">
              Cancel
            </button>
            <button type="submit" className="btn primary">
              Add {type.charAt(0).toUpperCase() + type.slice(1)}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export default AddModal
