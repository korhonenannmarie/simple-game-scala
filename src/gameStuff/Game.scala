package gameStuff

import gameStuff.Character
import java.awt.Image
import scala.collection.mutable.Buffer
import scala.io.StdIn.readLine
class Game:

  private var roundCount: Int = 0
  private var currentScore: Int = 0
  private var highScores = Buffer()
  private var roundIsOver: Boolean = false
  private var thisWaveIsOver: Boolean = false

  def currentRound = roundCount
  def waveIsOver = thisWaveIsOver

  val mage: Character = Mage(mageName, mageHealth, mageArmour, mageToHit, mageDamage, mageShield)
  val fighter: Character = Fighter(fighterName, fighterHealth, fighterArmour, fighterToHit, fighterDamage, fighterShield)
  val rogue: Character = Rogue(rogueName, rogueHealth, rogueArmour, rogueToHit, rogueDamage, rogueShield)

  val Characters: Buffer[Character] = Buffer(mage, fighter, rogue)
  val Monsters: Buffer[Monster] = Buffer()

  def playGame() =
    while !this.isOver do
      println(this.welcomeMessage)
      for wave <- 0 until maxWave do
        while !this.waveIsOver do
          val command = readLine()
          val turnReport = this.playTurn(command)
          if turnReport != None then
            println(turnReport)
            this.monstersTurn()
        this.newWave()
    println(this.goodbyeMessage)

  def monstersTurn() = // missing a lot of monster logic
    roundCount += 1


  def setMonsters() = ???

  def playTurn(command: String) =
    
    val commandText = command.trim.toLowerCase
    val strActor       = commandText.takeWhile( _ != ' ' )
    val verb        = commandText.drop(strActor.length).takeWhile( _ != ' ' ).trim
    val strTarget      = commandText.drop(strActor.length + verb.length).trim  
    
    val target =
      strTarget match
        case "mage" => mage
        case "fighter" => fighter
        case "rogue" => rogue
    
    val actor =
      strActor match
        case "mage" => mage
        case "fighter" => fighter
        case "rogue" => rogue
        
    
    def execute(actor: Character) =
      verb match
        case "rest" => Some(actor.rest())
        case "attack" => Some(actor.attack(target))
        case "attackSelf" => Some(actor.attack(actor))
        case other => None
    
    val doingStuff  = execute(actor)   
    
    var outcomeReport = s"$doingStuff \n" + s"${mage.currentStats()} \n${fighter.currentStats()} \n${rogue.currentStats()}"

    if doingStuff.isDefined then
      outcomeReport
    else
      None
      
  end playTurn

  

  def welcomeMessage = "Welcome to the game"
  
  def goodbyeMessage = "Goodbye for now"
  
  def isOver: Boolean = false // to be implemented

  def newWave(): Unit =
    Characters.foreach(_.modifyForNewWave())
    Monsters.foreach(_.modifyForNewWave())

  def str2character(str: String): Character =
    str match
      case "mage" => mage
      case "fighter" => fighter
      case "rogue" => rogue



  // for testing the logic of my program:

  def testTurn(command: String) =
    val input = command.trim.toLowerCase
    input match
      case "yes" => "Whoop"
      case "no" => "alright nope"
      case other => None


  def testPlayGame() =
    while !this.isOver do
      val command = readLine("\nCommand:")
      val turnReport = this.playTurn(command)
      if turnReport != None then
         println(turnReport)

