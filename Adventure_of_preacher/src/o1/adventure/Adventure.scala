/*
 * Text adventure game by Patrik Mäki - 2019-11-26.
 * File: Adventure.scala - define the adventure logic.
 */
package o1.adventure

class Adventure {

  /** The title of the adventure game. */
  val title = "Adventures of preacher Kenneth"

  private val start = new Area("Start", "start of your game\n")
  private val cadillac = new Area("CADILLAC", "A luxuary SUV, can take you almost anywhere\n")
  private val mansion = new Area("Mansion", "7000 square feet of pure evi.., luxuary\n")
  private val post = new Area("Postal office", "here you can collect seed money\n")
  private val church = new Area("Church", "healing church, only good stuff happens here\nyou can do different things here to collect money\n")
  private val graveyard = new Area("graveyard", "People are buried here\ndo not disturb the dead\n")
  private val airport = new Area("Airport", "a 20 million $ jet awaits you\n")
  private val lagos = new Area("Lagos", "Lagos Nigeria\n")
  private val s = new Area("sf", "??? lots of red??????\n") //sovjiet finland delete comment
  private val sweden = new Area("Sweden", "Hmm something is missing\n")
  private val gates = new Area("Gates of heaven", "so close\n")
  private val eye = new Area("Eye of a needle", "rich man...camel...needle\n")
  private val hell = new Area("Hell", "Demons, full of demons, disturb the devil\n")
  private val lair = new Area("Lair", "The Devil lives here\n")
  private val heaven = new Area("Heaven", "Victory, you have reached heaven\n")
  private val catacombs = new Area("catacoms", "filled with skeletons, nothing interesting here\n")
  //private val destination = home

  start.setNeighbors(Vector("north" -> cadillac)) // "east" -> tangle, "south" -> southForest, "west" -> clearing   ))
  cadillac.setNeighbors(Vector("north" -> church, "east" -> mansion, "south" -> start, "west" -> airport))
  mansion.setNeighbors(Vector("east" -> post, "west" -> cadillac))
  post.setNeighbors(Vector("west" -> mansion))
  church.setNeighbors(Vector("south" -> cadillac, "north" -> graveyard, "up" -> gates, "down" -> catacombs))
  graveyard.setNeighbors(Vector("south" -> church))
  airport.setNeighbors(Vector("north" -> lagos, "east" -> cadillac, "south" -> s))
  lagos.setNeighbors(Vector("south" -> airport))
  s.setNeighbors(Vector("north" -> airport, "west" -> sweden, "up" -> heaven))
  sweden.setNeighbors(Vector("east" -> s))
  gates.setNeighbors(Vector("north" -> eye, "down" -> church))
  eye.setNeighbors(Vector("north" -> heaven, "down" -> hell))
  hell.setNeighbors(Vector("west" -> lair, "up" -> eye))
  lair.setNeighbors(Vector("east" -> hell))
  catacombs.setNeighbors(Vector("up" -> church))
  // : creates these items
  private val bible = new Item("bible", "holy Bible")
  private val key = new Item("key", "Key to your amazing church")
  private val oil = new Item("oil", "holy oil, which you have to pour on your money to make it seed.\nCan be used at the postal office")
  private val cloth = new Item("cloth", " A mountain shaped piece of cloth.\nCan be used at the postal office")
  private val foot = new Item("footprint", "your footprint\n a  very special blessed item.\nCan be used at the postal office")
  private val check = new Item("check", "A check of 10$ to you.\nNot much but old people can easily mix those numbers up.\nCan be used at the postal office")
  private val wine = new Item("wine", "Divine blessing, Holy wine, made of blessed plums.\nCan be used at the postal office\nalso has secret powers") //secret ending
  private val dvd = new Item("dvd", "A dvd filled with preaching.\nCan be sold at the Church")
  private val evil = new Item("evil", "Ashes of evil")
  // places these items
  this.church.addItem(bible)
  this.post.addItem(key)
  this.mansion.addItem(dvd)
  this.start.addItem(oil)
  this.lagos.addItem(wine)
  this.lagos.addItem(cloth)
  this.airport.addItem(foot)
  this.catacombs.addItem(check)
  this.lair.addItem(evil)

  /** The preacher */
  val player = new Player(start)

  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  val timeLimit = 40
  //the bankaccount
  var bankAccountMoney = this.player.money
  //this.player.money
  // var

  /** Determines if the adventure is complete, that is, if the player has won. */
  def isComplete = this.player.location == this.heaven && this.player.has("bible") // && this.player.has("battery")
  def isLost = this.playerHelth <= 0 && this.player.location.name != "Eye of a needle"
  def killedByGod = this.playerHelth <= 0 && this.player.location.name == "Eye of a needle"
  //this.player.location == this.hell && this.player.has("money")
  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit || this.isLost || this.player.SecretEnding || killedByGod
  private var interviewDone = false // validate that interview is only done once
  def isInterview = this.turnCount >= 2 && this.player.location == cadillac && this.player.has("key") && !interviewDone
  def isBossBattle = this.player.bossBattle
  def bossHealth = this.player.BossHealth
  def playerHelth = this.player.PlayerHealth

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  //you are rich preacher, who earns his living by stealing from mentally weak people,\n ypir mission is to get to heaven, but yoou want to be rich
  def welcomeMessage = {
    val a = "You are a rich televangelist Kenneth, who makes his living by scamming poor people and by spreding his faith...\nYour mission is to collect a lot money and to go to heaven"
    val b = "\nYou can do this by multiple ways. "
    a + b
  }

  def bossMessage = {
    if (bossHealth <= 0) {
      println("Boss defeated\nYou can now leave the area.")
    }
  }

  def Interview = {
    if (isInterview) {
      interviewDone = true
      this.player.interview
      """
You see someone running towards your car, *I hope it's not one those journalists trying to destroy my business
maybe I can get in the Cadillac Before she gets here*

Some reporter:
How are you sir, we would like to ask you, why you don't want to fly commercial.
You have previously said that it's like to
get in a looooong tube with a bunch of demons.

Preacher Kenneth:
*Ahhh... No time to think...*
Not the people. The main reason is that I'd have to stop all the time.

Reporter:
ok, but let's get back to the demons. You said yo don't want to get in a long tube with a bunch of demons.
Do you really think people who fly commercial are demons?

Preacher Kenneth:
NO I DO NOT AND DON'T EVER SAY I DID!!!

You point at the reporter and stare at her with a very angry face
and just when she is about say something, you remember the perfecr scripture.

Preacher Kenneth:
We wrestle not with flesh and blood, but with principalities and powers

... And the interview continues....

Reporter:
The bible also says, that it is easier for a camel to go through the eye of a needle
than for a rich man to enter the kingdom of God.
What do you say to that?

Preacher Kenneth:
You didn't read the scripture: But he said, all things are possible with God.

At the end you bless the reporter and get in your car, but you have a single thought of doubt:
Am I reaally going to get to heaven??        
        """
    } else {
      ""
    }
  }
  private var decisionDone = false
  def decision = {
    if (this.player.location.name == "Eye of a needle" && !decisionDone) {
      println("Kenneth Copeland!!!\nI am God. You know, what I think about rich men:\nit is easier for a camel to go through the eye of a needle\n than for a rich man to enter the kingdom of God")
      println("Will you accept your sins and go to hell or face me\nsay yes or no")
      decisionDone = true
    }
  }

  /**
   * Returns a message that is to be displayed to the player at the end of the game. The message
   * will be different depending on whether or not the player has completed their quest.
   */
  def goodbyeMessage = {
    if (this.isComplete)
      "You have redeemed yourself. Now Heaven at last...\nGame over" //good ending
    else if (this.player.SecretEnding)
      "You've become the Devil\nGame over" //secret ending
    else if (this.turnCount == this.timeLimit)
      "Oh no! Time's up." 
    else if (this.killedByGod)
      "You've been killed by God, eternal punishment in hell\nGame over"//bad ending
    else if (this.isLost)
      //"you greedy bastard, iternal punishment in hell\nGame over"
      "YOU DIED"
    else // game over due to player quitting
      "Quit, all progress is lost."
  }

  /**
   * Plays a turn by executing the given in-game command, such as "go west". Returns a textual
   * report of what happened, or an error message if the command was unknown. In the latter
   * case, no turns elapse.
   */
  def playTurn(command: String):String = {
    if (this.isBossBattle) {
      val action2 = new BossAction(command)
      val outcomeReport2 = action2.execute(this.player)
      if (outcomeReport2.isDefined) {
        this.turnCount += 1
        if (this.player.location.name == "Eye of a needle") {
          //this.player.Smite
          return this.player.Smite
        } else {
          this.player.BossHit
          //println(outcomeReport2.get)
          //outcomeReport2.getOrElse("Unknown command: \"" + command + "\".")
        }
      }
      outcomeReport2.getOrElse("Unknown command: \"" + command + "\".")
      
    } else {
      val action = new Action(command)
      val outcomeReport = action.execute(this.player)
      if (outcomeReport.isDefined) {
        this.turnCount += 1
      }
      outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
    }
  }

}

