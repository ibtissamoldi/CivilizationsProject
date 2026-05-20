const express = require('express')
const hbs = require('hbs')
const path = require('path')
const MySQL = require('./utilsMySQL')

const app = express()
const port = 3000

const isProxmox = !!process.env.PM2_HOME

const db = new MySQL()
if (!isProxmox) {
  db.init({
    host: '127.0.0.1',
    port: 3306,
    user: 'root',
    password: '12345',
    database: 'civilizations'
  })
} else {
  db.init({
    host: '127.0.0.1',
    port: 3306,
    user: 'civ_user',
    password: 'bichos2.0',
    database: 'civilizations'
  })
}

app.use(express.static('public'))
app.use(express.urlencoded({ extended: true }))

app.use((req, res, next) => {
  res.setHeader('Cache-Control', 'no-store, no-cache, must-revalidate, proxy-revalidate')
  res.setHeader('Pragma', 'no-cache')
  res.setHeader('Expires', '0')
  res.setHeader('Surrogate-Control', 'no-store')
  next()
})

app.set('views', path.join(__dirname, 'views'))
app.set('view engine', 'hbs')
hbs.registerPartials(path.join(__dirname, 'views', 'partials'))
app.set('view options', { layout: 'layouts/main' })

hbs.registerHelper('eq', (a, b) => a === b)
hbs.registerHelper('resta', (a, b) => Number(a || 0) - Number(b || 0))
hbs.registerHelper('sum', (a, b) => Number(a || 0) + Number(b || 0))

const PER_PAGE = 10

async function getCurrentCivilization(civilizationId) {
  let rows

  if (civilizationId) {
    rows = await db.query(`
      SELECT *
      FROM civilization_stats
      WHERE civilization_id = ${civilizationId}
      LIMIT 1
    `)
  } else {
    rows = await db.query(`
      SELECT *
      FROM civilization_stats
      ORDER BY civilization_id ASC
      LIMIT 1
    `)
  }

  const civilizations = db.table_to_json(rows, {
    civilization_id: 'number',
    name: 'string',

    wood_amount: 'number',
    iron_amount: 'number',
    food_amount: 'number',
    mana_amount: 'number',

    magicTower_counter: 'number',
    church_counter: 'number',
    farm_counter: 'number',
    smithy_counter: 'number',
    carpentry_counter: 'number',

    technology_defense_level: 'number',
    technology_attack_level: 'number',

    battles_counter: 'number'
  })

  return civilizations[0]
}

function unitSchema() {
  return {
    type: 'string',
    total: 'number',
    avg_armor: 'number',
    avg_damage: 'number',
    avg_experience: 'number'
  }
}

function battleUnitSchema() {
  return {
    type: 'string',
    initial: 'number',
    drops: 'number',
    survivors: 'number'
  }
}

app.get('/', async (req, res) => {
  try {
    const civilization = await getCurrentCivilization(parseInt(req.query.civ, 10) || null)
    if (!civilization) return res.render('principal', { noData: true })

    const latestRows = await db.query(`
      SELECT
        num_battle,
        wood_acquired,
        iron_acquired,
        winner
      FROM battle_stats
      WHERE civilization_id = ${civilization.civilization_id}
      ORDER BY num_battle DESC
      LIMIT 2
    `)

    const latestBattles = db.table_to_json(latestRows, {
      num_battle: 'number',
      wood_acquired: 'number',
      iron_acquired: 'number',
      winner: 'string'
    })

    res.render('principal', { civilization, latestBattles })
  } catch (e) {
    console.error(e)
    res.status(500).send('Error consultant la pàgina principal')
  }
})

app.get('/batalles', async (req, res) => {
  try {
    const pagina = parseInt(req.query.pagina, 10) || 0
    const offset = pagina * PER_PAGE
    const civilization = await getCurrentCivilization(parseInt(req.query.civ, 10) || null)
    if (!civilization) return res.render('batalles', { noData: true })

    const battleRows = await db.query(`
      SELECT
        num_battle,
        wood_acquired,
        iron_acquired,
        winner
      FROM battle_stats
      WHERE civilization_id = ${civilization.civilization_id}
      ORDER BY num_battle DESC
      LIMIT ${PER_PAGE} OFFSET ${offset}
    `)

    const totalRows = await db.query(`
      SELECT COUNT(*) AS total
      FROM battle_stats
      WHERE civilization_id = ${civilization.civilization_id}
    `)

    const battles = db.table_to_json(battleRows, {
      num_battle: 'number',
      wood_acquired: 'number',
      iron_acquired: 'number',
      winner: 'string'
    })
    const total = db.table_to_json(totalRows, { total: 'number' })[0].total

    res.render('batalles', {
      civilization,
      battles,
      totalBattles: total,
      pagina,
      hiHaPrev: pagina > 0,
      hiHaNext: offset + PER_PAGE < total,
      paginaSeg: pagina + 1,
      paginaAnt: pagina - 1
    })
  } catch (e) {
    console.error(e)
    res.status(500).send('Error consultant les batalles')
  }
})
function getBattleUnit(rows, type) {
  const unit = rows.find(row => row.type === type)

  if (!unit) {
    return {
      initial: 0,
      drops: 0,
      survivors: 0
    }
  }

  return {
    initial: unit.initial,
    drops: unit.drops,
    survivors: unit.survivors
  }
}

function battleUnitSchema() {
  return {
    type: 'string',
    initial: 'number',
    drops: 'number',
    survivors: 'number'
  }
}

app.get('/informe', async (req, res) => {
  try {
    const numBattle = parseInt(req.query.informe, 10)

    if (Number.isNaN(numBattle)) {
      return res.status(400).send('Falta el paràmetre ?informe=id_batalla')
    }

    const civilization = await getCurrentCivilization(parseInt(req.query.civ, 10) || null)

    if (!civilization) {
      return res.render('informeBatalla', {
        noData: true
      })
    }

    const battleRows = await db.query(`
      SELECT
        civilization_id,
        num_battle,
        wood_acquired,
        iron_acquired,
        winner,

        civ_food_cost,
        civ_wood_cost,
        civ_iron_cost,

        enemy_food_cost,
        enemy_wood_cost,
        enemy_iron_cost,

        civ_food_losses,
        civ_wood_losses,
        civ_iron_losses,

        enemy_food_losses,
        enemy_wood_losses,
        enemy_iron_losses,

        rubble_wood,
        rubble_iron,
        battle_date
      FROM battle_stats
      WHERE civilization_id = ${civilization.civilization_id}
        AND num_battle = ${numBattle}
      LIMIT 1
    `)

    if (!battleRows.length) {
      return res.status(404).send('Informe de batalla no trobat')
    }

    const battle = db.table_to_json(battleRows, {
      civilization_id: 'number',
      num_battle: 'number',
      wood_acquired: 'number',
      iron_acquired: 'number',
      winner: 'string',

      civ_food_cost: 'number',
      civ_wood_cost: 'number',
      civ_iron_cost: 'number',

      enemy_food_cost: 'number',
      enemy_wood_cost: 'number',
      enemy_iron_cost: 'number',

      civ_food_losses: 'number',
      civ_wood_losses: 'number',
      civ_iron_losses: 'number',

      enemy_food_losses: 'number',
      enemy_wood_losses: 'number',
      enemy_iron_losses: 'number',

      rubble_wood: 'number',
      rubble_iron: 'number',
      battle_date: 'string'
    })[0]

    const LOGS_PER_PAGE = 5
const paginaLog = parseInt(req.query.paginaLog, 10) || 0
const offsetLog = paginaLog * LOGS_PER_PAGE

const logRows = await db.query(`
  SELECT num_line, log_entry
  FROM battle_log
  WHERE civilization_id = ${civilization.civilization_id}
    AND num_battle = ${numBattle}
  ORDER BY num_line ASC
  LIMIT ${LOGS_PER_PAGE} OFFSET ${offsetLog}
`)

const totalLogRows = await db.query(`
  SELECT COUNT(*) AS total
  FROM battle_log
  WHERE civilization_id = ${civilization.civilization_id}
    AND num_battle = ${numBattle}
`)

const totalLogs = db.table_to_json(totalLogRows, {
  total: 'number'
})[0].total

    const unitRows = await db.query(`
      SELECT
        side,
        unit_category,
        type,
        initial_units AS initial,
        dropped_units AS drops,
        initial_units - dropped_units AS survivors
      FROM battle_units_stats
      WHERE civilization_id = ${civilization.civilization_id}
        AND num_battle = ${numBattle}
      ORDER BY side ASC, unit_category ASC, type ASC
    `)

    const log = db.table_to_json(logRows, {
      num_line: 'number',
      log_entry: 'string'
    })

    const battleUnits = db.table_to_json(unitRows, {
      side: 'string',
      unit_category: 'string',
      type: 'string',
      initial: 'number',
      drops: 'number',
      survivors: 'number'
    })

    const civAttack = battleUnits.filter(unit =>
      unit.side === 'Player' && unit.unit_category === 'Attack'
    )

    const civDefense = battleUnits.filter(unit =>
      unit.side === 'Player' && unit.unit_category === 'Defense'
    )

    const civSpecial = battleUnits.filter(unit =>
      unit.side === 'Player' && unit.unit_category === 'Special'
    )

    const enemyAttack = battleUnits.filter(unit =>
      unit.side === 'Enemy' && unit.unit_category === 'Attack'
    )

    const enemyDefense = battleUnits.filter(unit =>
      unit.side === 'Enemy' && unit.unit_category === 'Defense'
    )

    const enemySpecial = battleUnits.filter(unit =>
      unit.side === 'Enemy' && unit.unit_category === 'Special'
    )

    const report = {
      swordsman: getBattleUnit(civAttack, 'Swordsman'),
      spearman: getBattleUnit(civAttack, 'Spearman'),
      crossbow: getBattleUnit(civAttack, 'Crossbow'),
      cannon: getBattleUnit(civAttack, 'Cannon'),

      arrowTower: getBattleUnit(civDefense, 'ArrowTower'),
      catapult: getBattleUnit(civDefense, 'Catapult'),
      rocketLauncherTower: getBattleUnit(civDefense, 'RocketLauncherTower'),

      magician: getBattleUnit(civSpecial, 'Magician'),
      priest: getBattleUnit(civSpecial, 'Priest'),

      enemySwordsman: getBattleUnit(enemyAttack, 'Swordsman'),
      enemySpearman: getBattleUnit(enemyAttack, 'Spearman'),
      enemyCrossbow: getBattleUnit(enemyAttack, 'Crossbow'),
      enemyCannon: getBattleUnit(enemyAttack, 'Cannon'),

      enemyArrowTower: getBattleUnit(enemyDefense, 'ArrowTower'),
      enemyCatapult: getBattleUnit(enemyDefense, 'Catapult'),
      enemyRocketLauncherTower: getBattleUnit(enemyDefense, 'RocketLauncherTower'),

      enemyMagician: getBattleUnit(enemySpecial, 'Magician'),
      enemyPriest: getBattleUnit(enemySpecial, 'Priest')
    }

    res.render('informeBatalla', {
  civilization,
  battle,
  log,
  report,

  paginaLog,
  paginaLogVisual: paginaLog + 1,
  hiHaPrevLog: paginaLog > 0,
  hiHaNextLog: offsetLog + LOGS_PER_PAGE < totalLogs,
  paginaLogAnt: paginaLog - 1,
  paginaLogSeg: paginaLog + 1,
  primerLog: offsetLog + 1
  
})

  } catch (e) {
    console.error(e)
    res.status(500).send('Error consultant l\'informe de batalla')
  }
})
app.get('/civilitzacio', async (req, res) => {
  try {
    const civilization = await getCurrentCivilization(parseInt(req.query.civ, 10) || null)

    if (!civilization) {
      return res.render('civilitzacio', { noData: true })
    }

    const attackRows = await db.query(`
      SELECT
        type,
        COUNT(*) AS total,
        ROUND(AVG(armor), 2) AS avg_armor,
        ROUND(AVG(base_damage), 2) AS avg_damage,
        ROUND(AVG(experience), 2) AS avg_experience
      FROM attack_units_stats
      WHERE civilization_id = ${civilization.civilization_id}
      GROUP BY type
      ORDER BY type ASC
    `)

    const defenseRows = await db.query(`
      SELECT
        type,
        COUNT(*) AS total,
        ROUND(AVG(armor), 2) AS avg_armor,
        ROUND(AVG(base_damage), 2) AS avg_damage,
        ROUND(AVG(experience), 2) AS avg_experience
      FROM defense_units_stats
      WHERE civilization_id = ${civilization.civilization_id}
      GROUP BY type
      ORDER BY type ASC
    `)

    const specialRows = await db.query(`
      SELECT
        type,
        COUNT(*) AS total,
        ROUND(AVG(armor), 2) AS avg_armor,
        ROUND(AVG(base_damage), 2) AS avg_damage,
        ROUND(AVG(experience), 2) AS avg_experience
      FROM special_units_stats
      WHERE civilization_id = ${civilization.civilization_id}
      GROUP BY type
      ORDER BY type ASC
    `)

    const attackUnits = db.table_to_json(attackRows, unitSchema())
    const defenseUnits = db.table_to_json(defenseRows, unitSchema())
    const specialUnits = db.table_to_json(specialRows, unitSchema())

    function getTotal(rows, type) {
      const unit = rows.find(row => row.type === type)
      return unit ? unit.total : 0
    }

    const units = {
      swordsman: getTotal(attackUnits, 'Swordsman'),
      spearman: getTotal(attackUnits, 'Spearman'),
      crossbow: getTotal(attackUnits, 'Crossbow'),
      cannon: getTotal(attackUnits, 'Cannon'),

      arrowTower: getTotal(defenseUnits, 'ArrowTower'),
      catapult: getTotal(defenseUnits, 'Catapult'),
      rocketLauncherTower: getTotal(defenseUnits, 'RocketLauncherTower'),

      magician: getTotal(specialUnits, 'Magician'),
      priest: getTotal(specialUnits, 'Priest')
    }

    res.render('civilitzacio', {
      civilization,
      attackUnits,
      defenseUnits,
      specialUnits,
      units
    })

  } catch (e) {
    console.error(e)
    res.status(500).send('Error consultant la civilització')
  }
})

app.get('/programadors', (req, res) => {
  res.render('programadors')
})

const { exec } = require('child_process');

app.get('/launch-game', (req, res) => {
    const command = 'java -cp ".;C:\\Users\\ibtis\\Downloads\\mysql-connector-java-8.0.15(2).jar" M3.GUIgame.MainFrame';
    const workingDir = 'C:\\Github_\\New folder\\CivilizationsProject';
    
    exec(command, { cwd: workingDir }, (error, stdout, stderr) => {
        if (error) {
            console.error(`Error launching game: ${error.message}`);
            return;
        }
    });

    res.redirect('/');
});

app.listen(port, () => {
  console.log(`Servidor actiu a http://localhost:${port}`)
})
