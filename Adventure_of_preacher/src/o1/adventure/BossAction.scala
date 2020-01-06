/*
 * Text adventure game by Patrik Mäki - 2019-11-26.
 * File: BossAction.scala - define the boss actions allowed in the game.
 */
package o1.adventure

class BossAction(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim

  def execute(actor: Player) = this.verb match {
    case "rest"  => Some(actor.restBoss())
    case "quit"  => Some(actor.quit())
    case "inventory" => Some(actor.inventory)
    case "drop" => Some(actor.drop(this.modifiers))
    
    case "preye" => Some(actor.dodiddone(this.modifiers))
    case "help" => Some(actor.help2)
    case "use" => Some(actor.use(this.modifiers))
    //bossactions
    case "cast" => Some(actor.cast(this.modifiers))
    case "throw" => Some(actor.thro(this.modifiers))
    case "hit" => Some(actor.hit)
    case "retreat" => Some("Retreat is not allowed")
    case other   => None
  }
  
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"
}