import React from 'react'

const EditModal = ({ 
  editingItem, 
  type,
  editForm, 
  onInputChange, 
  onSubmit, 
  onCancel,
  onDelete
}) => {
  if (!editingItem) return null

  // Configuration for different modal types
  const modalConfig = {
    stream: {
      title: 'Edit Stream',
      fields: [
        {
          type: 'text',
          id: 'name',
          name: 'name',
          label: 'Name:',
          required: true,
          placeholder: ''
        },
        {
          type: 'textarea',
          id: 'description',
          name: 'description',
          label: 'Description:',
          required: false,
          placeholder: '',
          rows: 3
        },
        {
          type: 'url',
          id: 'url',
          name: 'url',
          label: 'URL:',
          required: true,
          placeholder: ''
        },
        {
          type: 'text',
          id: 'tags',
          name: 'tags',
          label: 'Tags (comma-separated):',
          required: false,
          placeholder: 'gaming, live, entertainment'
        }
      ]
    },
    game: {
      title: 'Edit Game',
      fields: [
        {
          type: 'text',
          id: 'name',
          name: 'name',
          label: 'Game Name:',
          required: true,
          placeholder: ''
        },
        {
          type: 'text',
          id: 'genres',
          name: 'genres',
          label: 'Genres (comma-separated):',
          required: false,
          placeholder: 'action, adventure, rpg'
        }
      ]
    },
    viewer: {
      title: 'Edit Viewer',
      fields: [
        {
        type: 'text',
        id: 'name',
        name: 'name',
        label: 'Name:',
        required: true,
        placeholder: ''
        },
        {
          type: 'text',
          id: 'twitchHandle',
          name: 'twitchHandle',
          label: 'Twitch Handle:',
          required: true,
          placeholder: ''
        },
      ]
    }
  }

  const config = modalConfig[type]

  const renderField = (field) => {
    const commonProps = {
      id: field.id,
      name: field.name,
      value: editForm[field.name] || '',
      onChange: onInputChange,
      required: field.required,
      placeholder: field.placeholder
    }

    switch (field.type) {
      case 'textarea':
        return (
          <textarea
            {...commonProps}
            rows={field.rows || 3}
          />
        )
      
      case 'select':
        return (
          <select {...commonProps}>
            {field.options.map(option => (
              <option key={option.value} value={option.value}>
                {option.label}
              </option>
            ))}
          </select>
        )
      
      case 'number':
        return (
          <input
            {...commonProps}
            type="number"
            min={field.min}
          />
        )
      
      case 'url':
        return (
          <input
            {...commonProps}
            type="url"
          />
        )
      
      default: // text
        return (
          <input
            {...commonProps}
            type="text"
          />
        )
    }
  }

  return (
    <div className="modal-overlay">
      <div className="modal">
        <div className="modal-header">
          <h3>{config.title}</h3>
          <button onClick={onCancel} className="modal-close">&times;</button>
        </div>
        <form onSubmit={onSubmit} className="edit-form">
          {config.fields.map((field) => (
            <div key={field.id} className="form-group">
              <label htmlFor={field.id}>{field.label}</label>
              {renderField(field)}
            </div>
          ))}
          <div className="form-actions">
            <button type="button" onClick={onDelete} className="btn danger">
              Delete
            </button>
            <div className="right-buttons">
              <button type="button" onClick={onCancel} className="btn secondary">
                Cancel
              </button>
              <button type="submit" className="btn primary">
                Save Changes
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  )
}

export default EditModal
