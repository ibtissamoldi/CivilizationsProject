const express = require('express')
const path = require('path')
const hbs = require('hbs')
const mysql = require('mysql2')

const db = mysql.createConnection({
  host: 'localhost',
  port: 3307,
  user: 'civ_user',
  password: 'bichos2.0',
  database: 'civilizations'
})

db.connect((err) => {
  if (err) {
    console.log('DB connection error:', err)
    return
  }
  console.log('Connected to MySQL')
})

const app = express()
const port = 3000

app.use(express.static('public'))
app.use(express.urlencoded({ extended: true }))

app.set('views', path.join(__dirname, '../views'))
app.set('view engine', 'hbs')
app.set('view options', { layout: 'layouts/main' })

hbs.registerPartials(path.join(__dirname, '../views/partials'))



app.listen(port, () => {
  console.log(`http://localhost:${port}`)
})
