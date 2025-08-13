import React from 'react'
import DataSection from './DataSection'
import { streamsAPI } from '../utils/api'

const Streams = () => {
  return (
    <DataSection
      type="stream"
      title="Streams"
      description="Manage your live streams and recordings"
      fetchFunction={streamsAPI.fetchAll}
      addFunction={streamsAPI.add}
      editFunction={streamsAPI.update}
      deleteFunction={streamsAPI.delete}
      emptyStateMessage="No streams found"
      emptyStateSubmessage="Start streaming to see your content here!"
    />
  )
}

export default Streams
