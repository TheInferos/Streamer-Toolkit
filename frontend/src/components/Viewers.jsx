import React from 'react'
import DataSection from './DataSection'
import { viewersAPI } from '../utils/api'

const Viewers = () => {
  return (
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
    />
  )
}

export default Viewers
