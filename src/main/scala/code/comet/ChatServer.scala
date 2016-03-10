package code.comet

import net.liftweb.http.ListenerManager
import scala.collection.immutable.List
import net.liftweb.actor.LiftActor

object ChatServer extends LiftActor with ListenerManager {

  private var msgs = Vector("Welcome!!!")

  def createUpdate = msgs

  override def lowPriority = {
    case s: String => {msgs :+= s
    		limitSize(20)
    		updateListeners()
    	}
  }
  
  def limitSize(limit : Int) = {
    if(msgs.length > limit){
      msgs = msgs.takeRight(limit)
    }
  }

}