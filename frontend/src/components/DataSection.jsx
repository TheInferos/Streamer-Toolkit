import React, { useState, useEffect } from 'react'
import DataList from './DataList'
import EditModal from './EditModal'
import AddModal from './AddModal'

const DataSection = ({ 
  type, 
  title, 
  description, 
  fetchFunction, 
  addFunction,
  editFunction,
  deleteFunction,
  emptyStateMessage,
  emptyStateSubmessage,
  onDataChange
}) => {
  const [items, setItems] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [editingItem, setEditingItem] = useState(null)
  const [editForm, setEditForm] = useState({})
  const [isAddModalOpen, setIsAddModalOpen] = useState(false)

  useEffect(() => {
    fetchData()
  }, [])

  // Notify parent when items change
  useEffect(() => {
    console.log(`DataSection ${type} items changed:`, items)
    if (onDataChange) {
      console.log(`DataSection ${type} calling onDataChange with:`, items)
      onDataChange(items)
    }
  }, [items, onDataChange, type])

  const fetchData = async () => {
    try {
      console.log(`DataSection ${type} fetching data...`)
      setLoading(true)
      const data = await fetchFunction()
      console.log(`DataSection ${type} received data:`, data)
      setItems(data)
    } catch (err) {
      console.error(`DataSection ${type} error:`, err)
      setError(err.message)
      console.error(`Error fetching ${type}:`, err)
    } finally {
      setLoading(false)
    }
  }

  const handleEditClick = (item) => {
    setEditingItem(item)
    // Set edit form based on type
    switch (type) {
      case 'game':
        setEditForm({
          name: item.name || '',
          genres: item.genreList ? item.genreList.join(', ') : ''
        })
        break
      case 'stream':
        setEditForm({
          title: item.title || '',
          name: item.name || '',
          description: item.description || '',
          tags: item.tags ? item.tags.join(', ') : '',
          url: item.url || ''
        })
        break
      case 'viewer':
        setEditForm({
          username: item.username || '',
          status: item.status || '',
          watchTime: item.watchTime || '',
          messageCount: item.messageCount || ''
        })
        break
      case 'punishment':
        setEditForm({
          name: item.name || '',
          weight: item.weight || 1
        })
        break
      default:
        setEditForm({})
    }
  }

  const handleEditSubmit = async (e) => {
    e.preventDefault()
    
    try {
      let updatedItem = { ...editingItem, ...editForm }
      
      // Process form data based on type
      if (type === 'game' && editForm.genres) {
        updatedItem.genreList = editForm.genres.split(',').map(genre => genre.trim()).filter(genre => genre)
      }
      
      if (type === 'stream' && editForm.tags) {
        updatedItem.tags = editForm.tags.split(',').map(tag => tag.trim()).filter(tag => tag)
      }

      const result = await editFunction(updatedItem)
      setItems(items.map(item => 
        item.id === editingItem.id ? result : item
      ))
      
      setEditingItem(null)
      setEditForm({})
    } catch (err) {
      console.error(`Error updating ${type}:`, err)
      alert(`Failed to update ${type}: ` + err.message)
    }
  }

  const handleEditCancel = () => {
    setEditingItem(null)
    setEditForm({})
  }

  const handleInputChange = (e) => {
    const { name, value } = e.target
    setEditForm(prev => ({
      ...prev,
      [name]: value
    }))
  }

  const handleAddSubmit = async (formData) => {
    try {
      const newItem = await addFunction(formData)
      setItems([...items, newItem])
    } catch (err) {
      console.error(`Error adding ${type}:`, err)
      alert(`Failed to add ${type}: ` + err.message)
    }
  }

  const handleDelete = async () => {
    if (!editingItem) return
    
    if (window.confirm(`Are you sure you want to delete this ${type}? This action cannot be undone.`)) {
      try {
        await deleteFunction(editingItem.id)
        setItems(items.filter(item => item.id !== editingItem.id))
        setEditingItem(null)
        setEditForm({})
      } catch (err) {
        console.error(`Error deleting ${type}:`, err)
        alert(`Failed to delete ${type}: ` + err.message)
      }
    }
  }

  if (loading) {
    return (
      <div className="page-container">
        <div className="page-header">
          <h2>{title}</h2>
          <p>Loading {type}...</p>
        </div>
        <div className="loading-spinner">🔄</div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="page-container">
        <div className="page-header">
          <h2>{title}</h2>
          <p>Error loading {type}</p>
        </div>
        <div className="error-message">
          <p>❌ Error: {error}</p>
          <button onClick={fetchData} className="btn primary">
            Try Again
          </button>
        </div>
      </div>
    )
  }

  return (
    <div className="page-container">
      <div className="page-header">
        <h2>{title}</h2>
        <p>{description}</p>
      </div>

      <div className="data-section">
        <div className="section-header">
          <h3>All {title} ({items.length})</h3>
          <div className="section-actions">
            <button onClick={() => setIsAddModalOpen(true)} className="btn primary">
              Add {title.slice(0, -1)}
            </button>
            <button onClick={fetchData} className="btn secondary">
              Refresh
            </button>
          </div>
        </div>

        <DataList 
          items={items}
          type={type}
          onEditClick={handleEditClick}
          emptyStateMessage={emptyStateMessage}
          emptyStateSubmessage={emptyStateSubmessage}
        />
      </div>

      <EditModal
        editingItem={editingItem}
        type={type}
        editForm={editForm}
        onInputChange={handleInputChange}
        onSubmit={handleEditSubmit}
        onCancel={handleEditCancel}
        onDelete={handleDelete}
      />

      <AddModal
        isOpen={isAddModalOpen}
        type={type}
        onClose={() => setIsAddModalOpen(false)}
        onSubmit={handleAddSubmit}
      />
    </div>
  )
}

export default DataSection
