/*
 * Text adventure game by Patrik Mäki - 2019-11-26.
 * File: Action.scala - define the actions allowed in the game.
 */
package o1.adventure

class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  def execute(actor: Player) = this.verb match {
    case "go"    => Some(actor.go(this.modifiers))
    case "w" => Some(actor.go("north"))
    case "a" => Some(actor.go("west"))
    case "s" => Some(actor.go("south"))
    case "d" => Some(actor.go("east"))
    case "rest"  => Some(actor.rest())

    case "quit"  => Some(actor.quit())
    case "inventory" => Some(actor.inventory)
    case "get" => Some(actor.get(this.modifiers))
    case "examine" => Some(actor.examine(this.modifiers))
    case "drop" => Some(actor.drop(this.modifiers))
    
    case "do" => Some(actor.dodiddone(this.modifiers))
    case "help" => Some(actor.help)
    case "use" => Some(actor.use(this.modifiers))
    case "disturb" => Some(actor.disturb(this.modifiers))
    case "speak" => Some(actor.speak(this.modifiers))
    case "say" => Some(actor.decide(this.modifiers))
    case other   => None
  }


  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"

}

