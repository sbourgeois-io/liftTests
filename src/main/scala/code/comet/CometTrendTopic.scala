package code.comet

import scala.language.postfixOps
import net.liftweb._
import http._
import SHtml._ 
import net.liftweb.common.{Box, Full}
import net.liftweb.util._
import net.liftweb.actor._
import net.liftweb.util.BindPlus._
import net.liftweb.util.Helpers._
import net.liftweb.http.js.JsCmds.{SetHtml}
import net.liftweb.http.js.JE.Str
import _root_.scala.xml.{Text, NodeSeq}
 
 
class CometTrendTopic extends CometActor {
  override def defaultPrefix = Full("trend") 
  
  val timeid = Helpers.nextFuncName
  val hottestid = Helpers.nextFuncName
  
  def render = bind("hottest" -> <span id={hottestid}>No hot trendtopics yet!</span>, "time" -> <span id={timeid}>No updates yet!</span>)
 
  // Schedule an update every 5 seconds
  Schedule.schedule(this, TrendTopic, 5 seconds)
 
         
  override def lowPriority = {
    case TrendTopic => {
      // Update the hottest trend topic
      partialUpdate(SetHtml(hottestid, Text(if (randomInt(2) equals 1) "Functional Programming (Comet)" else "Lift Web Framework(Comet)")))
      // Update the latest time the trend topic was set
      partialUpdate(SetHtml(timeid, Text(now.toString)))
      // Schedule an update every 5 seconds
      Schedule.schedule(this, TrendTopic, 5 seconds)
    }
  }
}
 
case object TrendTopic