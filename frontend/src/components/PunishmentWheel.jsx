import React, { useState, useEffect } from 'react'
import PunishmentDataSection from './PunishmentDataSection'

const PunishmentWheel = () => {
  const [punishments, setPunishments] = useState([])

  const handlePunishmentsChange = (newPunishments) => {
    console.log('Punishments changed:', newPunishments)
    setPunishments(newPunishments || [])
  }

  // Debug: log current punishments state
  useEffect(() => {
    console.log('Current punishments in PunishmentWheel:', punishments)
  }, [punishments])

  return (
    <div className="page-container">
      <div className="page-header">
        <h2>Punishment Wheel</h2>
        <p>Spin the wheel to randomly select a punishment!</p>
      </div>

      <PunishmentDataSection 
        punishments={punishments}
        onPunishmentsChange={handlePunishmentsChange}
      />
    </div>
  )
}

export default PunishmentWheel
