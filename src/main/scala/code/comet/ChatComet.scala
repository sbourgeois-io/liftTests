package code.comet

import net.liftweb.http.CometActor
import scala.collection.Seq
import net.liftweb.http.CometListener
import scala.collection.immutable.List
import scala.xml.NodeSeq
import net.liftweb.util.FatLazy
import java.util.Locale
import net.liftweb.actor.LAFuture
import net.liftweb.common.SimpleActor
import scala.collection.mutable.ListBuffer
import net.liftweb.http.js.JsCmd
import net.liftweb.util.ClearClearable

class ChatComet extends CometActor with CometListener {

  private var msgs: Vector[String] = Vector()

  def registerWith() = ChatServer

  override def lowPriority = {
    case v: Vector[String] => msgs = v; reRender()
  }

  def render = "li *" #> msgs & ClearClearable
}