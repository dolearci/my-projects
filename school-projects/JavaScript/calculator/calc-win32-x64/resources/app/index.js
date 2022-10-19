/**
 * index module
 * @module index
 */

/**
 * @type {Window}
 */

const { app, BrowserWindow, Menu} = require('electron')
let menuTemplate = [
  {
    label: "Settings",
    submenu: [
      { 
        label: "Advanced",
        click(menuItem, browserWindow, event) {
          createAdvancedWindow();
          browserWindow.close();
        }
      },
      { 
        label: "Basic",
        click(menuItem, browserWindow, event) {
          createWindow();
          browserWindow.close();
        }
      }
    ]
  },
  {
    label: "View",
      submenu: [
        { 
          label: "Reload",
          click(menuItem, browserWindow, event) {
            browserWindow.reload();
          }
        }
      ]
  }
]

/**
 * create window
 * @description Create Basic window for our app
 * @returns {void}
 */

function createWindow () {
  // Create the browser window.
  let win = new BrowserWindow({
    width: 480,
    height: 760,
    webPreferences: {
      nodeIntegration: true
    },
    resizable: false,
    fullscreenable: false
  })

  // and load the index.html of the app.
  win.loadFile('index.html')
    let menu = Menu.buildFromTemplate(menuTemplate)
    Menu.setApplicationMenu(menu)
}

/**
 * Create advanced window
 * @description Create advanced window for our app
 * @returns {void}
 */

function createAdvancedWindow () {
  // Create the browser window.
  let win = new BrowserWindow({
    width: 776,
    height: 760,
    webPreferences: {
      nodeIntegration: true
    },
    resizable: false,
    fullscreenable: false
  })

  // and load the index.html of the app.
  win.loadFile('advanced.html')
    let menu = Menu.buildFromTemplate(menuTemplate)
    Menu.setApplicationMenu(menu)
}

/**
 * delete window
 * @description Delete window of our app
 * @param {class} win
 * @returns {void} 
 */

function deleteWindow(win){
  win = null;
}

app.whenReady().then(createWindow)

