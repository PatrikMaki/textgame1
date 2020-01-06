/*
 * Text adventure game by Patrik Mäki - 2019-11-26.
 * File: Player.scala - define the player methods for the game.
 */
package o1.adventure

import scala.collection.mutable.Map

/**
 * A `Player` object represents a player character controlled by the real-life user of the program.
 *
 * A player object's state is mutable: the player's location and possessions can change, for instance.
 *
 * @param startingArea  the initial location of the player
 */
class Player(startingArea: Area) {

  private var currentLocation = startingArea // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false // one-way flag
  private var BadEnding = false
  private var boss = false //is bossbattle 
  private var boss1 = false //the dead boss
  private var boss2 = false //God boss
  private var boss3 = false //the devil boss
  private var hell = false
  private var health = 100
  private var playerHealth = 100
  private var hittingPower = 10
  private var interviewDone = false
  def interview = interviewDone = true
  private val items = Map[String, Item]()

  def help() = {
    "old commands:\n go, rest, quit, inventory, get, examine, drop\nnew commands are:\n do, help, use, disturb, speak, say\nand in a boss battle:\n cast, throw, hit, retreat"
  }
  def help2() = {
    "You are in Boss battle\nold commands:\n go, rest, quit, inventory, get, examine, drop\nnew commands are:\n do, help, use, disturb, speak, say\nand in a boss battle:\n cast, throw, hit, retreat"
  }

  private var bankAccount = 10000
  def SecretEnding = this.BadEnding
  def BossHealth = health
  def PlayerHealth = playerHealth
  def money = bankAccount
  def bossBattle = boss 
  def battleEnds = {
    if (health <= 0 ) {
      boss = false
      bankAccount += 2000
      "\nBoss defeated\nYou can now leave the area."
    } else ""
  }

  def BossHit = {
    if(boss){
    this.playerHealth -= hittingPower
    "OOF, the Boss hits you\nyour health: " + playerHealth
    }
  }

  def Smite = {
    if (this.health > 0) {
      this.playerHealth -= 1000
      "God smites you down"
    } else {
      "you've become evil\nYOU HAVE KILLED GOD"
    }
  }

  def decide(decision: String) = {
    if (decision == "no" && !boss3) {
      boss = true
      boss2= true
      health = 4500
      hittingPower = 30 //delete
      "BOSS BATTLE\nYou have to defeat the boss before it kill's you.\n"
    } else if (decision == "yes") {
      hell = true
      "You can now go to hell\nif you return with evil, I can let you to Heaven"
    } else if (decision == "no" && boss3){
      "what are trying to do?"
    }else
      "it's a yes or no question"
  }

  def cast(spell: String) = {
    if (spell == "fire") {
      health -= 10
      "boss takes damage"+"\nBoss health:" + health+"\nyour health: " + playerHealth + "\n" + battleEnds
    } else "You have to cast something, for example fire."
  }

  def thro(item: String) = {
    if (item == "rock") {
      health -= 50
      "boss takes a lot of damage"+"\nBoss health:" + health +"\nyour health: " + playerHealth+ "\n"+battleEnds
    } else if (item == "money") {
      if (boss2) {
        health -= (this.money - 10000)
        if (health <= 0) {
          BadEnding = true
          "God takes a lot of damage"+"\nBoss health:" + health +"\nyour health: " + playerHealth+ battleEnds
        } else {
          "God takes a lot of damage"+"\nBoss health:" + health +"\nyour health: " + playerHealth+ battleEnds
        }
      } else {
        health -= 30
        "Boss takes a lot of damage"+"\nBoss health:" + health +"\nyour health: " + playerHealth+ battleEnds
      }
    } else "You have to throw something, eg. rock, money"
  }

  def hit = {
    health -= 1
    "You punch the boss"+"Boss health:" + health+"\nyour health: " + playerHealth + battleEnds
  }

  def disturb(target: String) = {
    if (target == "the devil" && this.currentLocation.name == "Hell" && !boss3) {
      boss = true
      boss3 = true
      health = 150
      hittingPower = 20
      "BOSS BATTLE - the devil\nYou have to defeat the boss before it kill's you.\n"

    } else if (target == "the dead" && this.currentLocation.name == "graveyard" && !boss1) {
      boss = true
      boss1 = true
      "BOSS BATTLE - the dead\nYou have to defeat the boss before it kill's you."
    } else "wrong target"
  }

  def speak(topic: String) = {
    if (topic == "in tongues" && (this.currentLocation.name == "Church" || this.currentLocation.name == "Lagos")) {
      this.bankAccount += 10
      "yyadayaada baadabaddaaaad lupus slupus sded ava lalalaaa mmadmadamd"
    } else if (topic == "about abortion" && (this.currentLocation.name == "Church" || this.currentLocation.name == "Lagos")) {
      this.bankAccount += 50
      "KILLING BABIES IS WRONG"
    } else if (topic == "about jesus" && (this.currentLocation.name == "Church" || this.currentLocation.name == "Lagos")) {
      this.bankAccount += 60
      "OUR LORD AND SAVIOUR JESUS..."
    } else if (topic == "about donating money" && (this.currentLocation.name == "Church" || this.currentLocation.name == "Lagos")) {
      this.bankAccount += 100
      "You speak about your large amounts of cash, and the you ask for more after saying that you feel immense jealosy in the room"
    } else "you need to speak about something, for example abortion, jesus or just speak in tongues"
  }

  def use(itemName: String) = {
    if (!this.has(itemName)) {
      "You don't have that item"
    } else if (itemName == "oil" && this.currentLocation.name == "Postal office") {
      this.bankAccount += 100
      this.items.remove(itemName)
      "You send the item to an unsuspecting beliver, there's a good chance they will send you the $50\n and oil that has been poured in the letter as you asked"
    } 
    else if (itemName == "dvd" && this.currentLocation.name == "Church") {
      this.bankAccount += 100
      this.items.remove(itemName)
      "You sell a dvd"
    } else if (itemName == "cloth" && this.currentLocation.name == "Postal office") {
      this.bankAccount += 500
      this.items.remove(itemName)
      "You send the item to an unsuspecting beliver,\nthere's a good chance they will send you the $500\nand the mountain shaped piece of cloth"
    } else if (itemName == "footprint" && this.currentLocation.name == "Postal office") {
      this.bankAccount += 800
      this.items.remove(itemName)
      "You send your footprint to VIP member, it's a very special blessed item."
    } else if (itemName == "check" && this.currentLocation.name == "Postal office") {
      this.bankAccount += 1000
      this.items.remove(itemName)
      "You send a check of 10$ to you.\nIt's not much but old people can easily mix those numbers up."
    } else "you can't use that"
  }

  def dodiddone(order: String) = {
    if (order == "a prayer" && this.currentLocation.name == "Church" || this.currentLocation.name == "Lagos") {
      this.bankAccount += 100
      "you pray with your church members and request some money"
    } else if (order == "a broadcast" && this.currentLocation.name == "Church" || this.currentLocation.name == "Lagos") {
      this.bankAccount += 150
      "TV-broadcast where you ask people to send you money."
    } else if (order == "a prayer" || order == "a broadcast") {
      "you are not in church or Lagos"
    } else "do a prayer or a broadcast"
  } //a TV-broadcast

  def get(itemName: String) = {
    if (this.currentLocation.contains(itemName)) {
      val a = this.currentLocation.removeItem(itemName).get
      this.items += a.name -> a
      "You pick up the " + itemName + "."
    } else "There is no " + itemName + " here to pick up."
  }

  def drop(itemName: String) = {
    this.items.remove(itemName) match {
      case None => "You don't have that!"
      case Some(a) => {
        this.currentLocation.addItem(a)
        "You drop the " + itemName + "."
      }
    }
  }

  def has(itemName: String): Boolean = {
    this.items.contains(itemName)
  }

  def inventory = {
    var x = "Your bankAccount has " + this.bankAccount + " Dollars and\nyou are carrying:\n"
    if (this.items.isEmpty) {
      "Your bankAccount has " + this.bankAccount + " Dollars and\nyou are not carrying anything."
    } else {

      for (a <- this.items) {
        x += a._2.name + "\n"
      }
      x
    }
  }

  def examine(itemName: String) = {
    if (has(itemName)) {
      "You look closely at the " + itemName + ".\n" + this.items.get(itemName).get.description
    } else "if you want to examine something, you need to pick it up first"
  }

  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven

  /** Returns the current location of the player. */
  def location = this.currentLocation

  /**
   * Attempts to move the player in the given direction. This is successful if there
   * is an exit from the player's current location towards the direction name. Returns
   * a tuple of boolean of success and additional information in case failure.
   */
  def canGo(destination: Option[Area]): (Boolean, String) = {
    if (destination.isDefined && destination.get.name == "sf") {
      val a = (destination.get.name != "sf" && this.has("Divine blessing"))
      (a, "")
    } else if (destination.isDefined && destination.get.name == "Gates of heaven") {
      if (this.has("bible") && this.bankAccount > 10000 + 999 && this.interviewDone) {

        (true, "")
      } else {
        (false, "You need to get a 1000 dollars and a bible.")
      }
    } else if (destination.isDefined && destination.get.name == "Heaven") {
      (this.has("evil") && boss3, "")
    } else if (destination.isDefined && destination.get.name == "Hell") {
      (hell, "")
    } else if (destination.isDefined && destination.get.name == "Church") {
      if (this.has("key")) (true, "") else {
        (false, "You need a key.")
      }
    } else if (destination.isDefined && destination.get.name == "Lair") {
      (boss3, "")
    } else {
      //println("no")
      (true, "")
    }
  }

  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    val (ok, explanation) = canGo(destination)
    if (ok) {
      this.currentLocation = destination.getOrElse(this.currentLocation)
      if (destination.isDefined) "You go " + direction + "." else "You can't go " + direction + ". " + explanation
    } else "You can't go " + direction + ". " + explanation
  }

  /**
   * Causes the player to rest for a short while (this has no substantial effect in game terms).
   * Returns a description of what happened.
   */
  def rest() = {
    this.playerHealth = 100
    "You rest for a while. Better get a move on, though."
  }

  def restBoss() = {
    this.playerHealth += 10
    "You rest for a while. Better get a move on, though."
  }

  /**
   * Signals that the player wants to quit the game. Returns a description of what happened within
   * the game as a result (which is the empty string, in this case).
   */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }

  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name

}
