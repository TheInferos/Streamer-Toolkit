import React from 'react'
import DataSection from './DataSection'
import Wheel from './Wheel'
import { punishmentsAPI } from '../utils/api'

const PunishmentDataSection = ({ punishments, onPunishmentsChange }) => {
  return (
    <>
      <Wheel 
        items={punishments}
        type="punishment"
        title="Punishment Wheel"
        getItemName={(item) => item.name}
        getItemWeight={(item) => item.weight}
      />
      
      <DataSection
        type="punishment"
        title="Punishments"
        description="Manage your punishment list for the wheel"
        fetchFunction={punishmentsAPI.fetchAll}
        addFunction={punishmentsAPI.add}
        editFunction={punishmentsAPI.update}
        deleteFunction={punishmentsAPI.delete}
        emptyStateMessage="No punishments yet"
        emptyStateSubmessage="Add your first punishment to get started!"
        onDataChange={onPunishmentsChange}
      />
    </>
  )
}

export default PunishmentDataSection
