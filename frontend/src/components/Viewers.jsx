import React, { useState, useEffect } from 'react'
import DataSection from './DataSection'
import Wheel from './Wheel'
import { viewersAPI } from '../utils/api'

const Viewers = () => {
  const [viewers, setViewers] = useState([])

  // Fetch viewers when component mounts
  useEffect(() => {
    const fetchViewers = async () => {
      try {
        const viewersData = await viewersAPI.fetchAll()
        setViewers(viewersData)
      } catch (error) {
        console.error('Error fetching viewers:', error)
      }
    }
    fetchViewers()
  }, [])

  const handleViewersChange = (newViewers) => {
    setViewers(newViewers || [])
  }

  return (
    <>
      <Wheel 
        items={viewers}
        type="viewer"
        title="Viewer Selection Wheel"
        getItemName={(item) => item.twitchHandle}
        onItemSelected={(viewer) => console.log('Selected viewer:', viewer)}
      />
      
      <DataSection
        type="viewer"
        title="Viewers"
        description="Manage your audience and viewer analytics"
        fetchFunction={viewersAPI.fetchAll}
        addFunction={viewersAPI.add}
        editFunction={viewersAPI.update}
        deleteFunction={viewersAPI.delete}
        emptyStateMessage="No viewers found"
        emptyStateSubmessage="Start streaming to see your viewers here!"
        onDataChange={handleViewersChange}
      />
    </>
  )
}

export default Viewers
