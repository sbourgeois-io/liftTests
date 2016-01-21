package code
package lib

import model._
import net.liftweb._
import net.liftweb.http._
import net.liftweb.http.rest._
import net.liftweb.util._
import common._
import http._
import rest._
import json._
import scala.xml._
import util._
import Helpers._
import js.JsCmds._

object GameRest extends RestHelper {
  serve {
    case "restapi" :: "game" :: "list" :: Nil Get _               => anyToJValue(listAllGames)

    case "restapi" :: "game" :: "list" :: AsLong(id) :: Nil Get _ => anyToJValue(listGame(id))
  }

  def listAllGames(): List[GameInformation] = {
    GameInformation.games.map {
      game =>
        GameInformation(
          game.id,
          game.name,
          game.highestScore)
    }
  }

  def listGame(id: Long): Box[GameInformation] = {
    for {
      game <- GameInformation.find(id) ?~ "The requested game doesn't exist"
    } yield GameInformation(
      game.id,
      game.name,
      game.highestScore)
  }
}