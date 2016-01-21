package code.model

import net.liftweb._
import util._
import Helpers._
import common._

case class GameInformation(id: Long, name: String, highestScore: Long)

object GameInformation {
  var games = List(
    new GameInformation(1, "Invaders 2013", 7200),
    new GameInformation(2, "Candy Crush", 32500),
    new GameInformation(3, "Angry Birds", 155500))

  def find(id: Long): Box[GameInformation] = synchronized {
    games.find(_.id == id)
  }
}