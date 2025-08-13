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
