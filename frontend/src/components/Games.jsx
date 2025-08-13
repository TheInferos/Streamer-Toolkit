import React from 'react'
import DataSection from './DataSection'
import { gamesAPI } from '../utils/api'

const Games = () => {
  return (
    <DataSection
      type="game"
      title="Games"
      description="Game List"
      fetchFunction={gamesAPI.fetchAll}
      addFunction={gamesAPI.add}
      editFunction={gamesAPI.update}
      deleteFunction={gamesAPI.delete}
      emptyStateMessage="No games found"
      emptyStateSubmessage="Add some games to get started!"
    />
  )
}

export default Games
