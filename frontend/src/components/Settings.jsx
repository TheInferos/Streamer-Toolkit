import React, { useState } from 'react'

const Settings = () => {
  const [settings, setSettings] = useState({
    notifications: true,
    autoSave: true,
    darkMode: false,
    streamQuality: '1080p',
    chatEnabled: true,
    followersOnly: false,
  })

  const handleSettingChange = (key, value) => {
    setSettings(prev => ({
      ...prev,
      [key]: value
    }))
  }

  return (
    <div className="settings">
      <div className="settings-header">
        <h2>Settings</h2>
        <p>Manage your account preferences and stream settings</p>
      </div>

      <div className="settings-content">
        <div className="settings-section">
          <h3>Stream Settings</h3>
          <div className="setting-item">
            <div className="setting-info">
              <h4>Stream Quality</h4>
              <p>Select your preferred streaming resolution</p>
            </div>
            <select 
              value={settings.streamQuality}
              onChange={(e) => handleSettingChange('streamQuality', e.target.value)}
              className="setting-control"
            >
              <option value="720p">720p</option>
              <option value="1080p">1080p</option>
              <option value="1440p">1440p</option>
              <option value="4K">4K</option>
            </select>
          </div>

          <div className="setting-item">
            <div className="setting-info">
              <h4>Enable Chat</h4>
              <p>Allow viewers to send messages during streams</p>
            </div>
            <label className="toggle-switch">
              <input 
                type="checkbox" 
                checked={settings.chatEnabled}
                onChange={(e) => handleSettingChange('chatEnabled', e.target.checked)}
              />
              <span className="toggle-slider"></span>
            </label>
          </div>

          <div className="setting-item">
            <div className="setting-info">
              <h4>Followers Only Chat</h4>
              <p>Restrict chat to followers only</p>
            </div>
            <label className="toggle-switch">
              <input 
                type="checkbox" 
                checked={settings.followersOnly}
                onChange={(e) => handleSettingChange('followersOnly', e.target.checked)}
              />
              <span className="toggle-slider"></span>
            </label>
          </div>
        </div>

        <div className="settings-section">
          <h3>App Preferences</h3>
          <div className="setting-item">
            <div className="setting-info">
              <h4>Push Notifications</h4>
              <p>Receive notifications for new followers and donations</p>
            </div>
            <label className="toggle-switch">
              <input 
                type="checkbox" 
                checked={settings.notifications}
                onChange={(e) => handleSettingChange('notifications', e.target.checked)}
              />
              <span className="toggle-slider"></span>
            </label>
          </div>

          <div className="setting-item">
            <div className="setting-info">
              <h4>Auto Save</h4>
              <p>Automatically save stream settings</p>
            </div>
            <label className="toggle-switch">
              <input 
                type="checkbox" 
                checked={settings.autoSave}
                onChange={(e) => handleSettingChange('autoSave', e.target.checked)}
              />
              <span className="toggle-slider"></span>
            </label>
          </div>

          <div className="setting-item">
            <div className="setting-info">
              <h4>Dark Mode</h4>
              <p>Use dark theme for the application</p>
            </div>
            <label className="toggle-switch">
              <input 
                type="checkbox" 
                checked={settings.darkMode}
                onChange={(e) => handleSettingChange('darkMode', e.target.checked)}
              />
              <span className="toggle-slider"></span>
            </label>
          </div>
        </div>

        <div className="settings-section">
          <h3>Account</h3>
          <div className="account-info">
            <div className="profile-section">
              <img 
                src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=80&h=80&fit=crop&crop=face" 
                alt="Profile" 
                className="profile-image"
              />
              <div className="profile-details">
                <h4>John Doe</h4>
                <p>john.doe@example.com</p>
                <p>Member since January 2024</p>
              </div>
            </div>
            
            <div className="account-actions">
              <button className="btn secondary">Edit Profile</button>
              <button className="btn secondary">Change Password</button>
              <button className="btn danger">Delete Account</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default Settings
