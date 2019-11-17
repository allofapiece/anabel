require('jsdom-global')()
const chai = require('chai')
const spies = require('chai-spies')

global.expect = chai.expect

global.requestAnimationFrame = cb => cb()
global.cancelAnimationFrame = cb => cb()
window.requestAnimationFrame = cb => cb()
window.cancelAnimationFrame = cb => cb()

chai.use(spies)

window.Date = Date
