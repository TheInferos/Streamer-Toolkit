export const gamesAPI = {
  fetchAll: async () => {
    const response = await fetch('http://localhost:8080/api/game/all')
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  add: async (gameData) => {
    const response = await fetch('http://localhost:8080/api/game', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(gameData)
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  update: async (gameData) => {
    const response = await fetch(`http://localhost:8080/api/game/${gameData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(gameData)
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  delete: async (id) => {
    const response = await fetch(`http://localhost:8080/api/game/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return true
  }
}

export const streamsAPI = {
  fetchAll: async () => {
    const response = await fetch('http://localhost:8080/api/stream/all')
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  add: async (streamData) => {
    const response = await fetch('http://localhost:8080/api/stream', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(streamData)
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  update: async (streamData) => {
    const response = await fetch(`http://localhost:8080/api/stream/${streamData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(streamData)
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  delete: async (id) => {
    const response = await fetch(`http://localhost:8080/api/stream/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return true
  }
}

export const viewersAPI = {
  fetchAll: async () => {
    const response = await fetch('http://localhost:8080/api/viewer/all')
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  add: async (viewerData) => {
    const response = await fetch('http://localhost:8080/api/viewer', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(viewerData)
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  update: async (viewerData) => {
    const response = await fetch(`http://localhost:8080/api/viewer/${viewerData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(viewerData)
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return response.json()
  },

  delete: async (id) => {
    const response = await fetch(`http://localhost:8080/api/viewer/${id}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      }
    })
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return true
  }
}

export const punishmentsAPI = {
  fetchAll: async () => {
    try {
      console.log('Fetching punishments from API...')
      const response = await fetch('http://localhost:8080/api/punishment/all')
      console.log('API response status:', response.status)
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      
      const data = await response.json()
      console.log('Punishments API response:', data)
      return data
    } catch (error) {
      console.error('Error fetching punishments:', error)
      throw error
    }
  },

  add: async (punishmentData) => {
    try {
      console.log('Adding punishment:', punishmentData)
      const response = await fetch('http://localhost:8080/api/punishment/add', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(punishmentData)
      })
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      
      const result = await response.json()
      console.log('Punishment added successfully:', result)
      return result
    } catch (error) {
      console.error('Error adding punishment:', error)
      throw error
    }
  },

  update: async (punishmentData) => {
    try {
      console.log('Updating punishment:', punishmentData)
      const response = await fetch(`http://localhost:8080/api/punishment/${punishmentData.id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(punishmentData)
      })
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      
      const result = await response.json()
      console.log('Punishment updated successfully:', result)
      return result
    } catch (error) {
      console.error('Error updating punishment:', error)
      throw error
    }
  },

  delete: async (id) => {
    try {
      console.log('Deleting punishment:', id)
      const response = await fetch(`http://localhost:8080/api/punishment/${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        }
      })
      
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      
      console.log('Punishment deleted successfully')
      return true
    } catch (error) {
      console.error('Error deleting punishment:', error)
      throw error
    }
  }
}
