package code.snippet

import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmd
import net.liftweb.http.js.JE.JsRaw
import net.liftweb.util.Helpers._
import scala.xml.Text
import net.liftweb.util.CssSel
import scala.xml.NodeSeq
import Helper._
import net.liftweb.util.CssSelector
import net.liftweb.http.DispatchSnippet
import net.liftweb.http.StatefulSnippet
import net.liftweb.http.S

object Helper {
  def console(text: String): () => JsCmd = () => JsRaw("{ console.log('" + text + "') }").cmd
  private def simulateLongAction(): Boolean = true
  def longAction() = if (simulateLongAction) Thread.sleep(randomInt(5000))
}

class AjaxEditable extends StatefulSnippet {
  var dispText = "Click me!"

  def dispatch = { case "render" => render }

  def render(in: NodeSeq): NodeSeq =  {
    transform(prepare())
  }

  def prepare() = {
    SHtml.ajaxEditable(Text(dispText),
      SHtml.text(dispText, s => { longAction(); dispText = s; println("Edited with: " + dispText) }),
      console("submitted Ajax editable"))
  }
  def transform = ":button [class+]" #> "btn btn-primary" &
    ":submit [class+]" #> "btn btn-primary"

  //prepare
}

class Anchor {
  def render = SHtml.a(console("anchor submitted"), Text("Clickable Anchor"))
}

class TrendTopic {
  def hottest: CssSel = {
    longAction()
    "#hottest *" #> "Functional programming"
  }
}

class Game {
  def render(in: NodeSeq): NodeSeq = {
    longAction()
    val cssSel = "#gameName *" #> "Invaders" &
      "#highestScore *" #> randomInt(15000)
    cssSel(in)
  }
}

object ShowMemoize {
  def render =
    "div" #> SHtml.idMemoize(
      outer =>
        // redraw the whole div when this button is pressed
        "@refresh_all [onclick]" #> SHtml.ajaxInvoke(outer.setHtml _) &

          // deal with the "one" div
          "@one" #> SHtml.idMemoize(
            one =>
              "span *+" #> now.toString & // display the time
                "button [onclick]" #> SHtml.ajaxInvoke(one.setHtml _)) & // redraw

            // deal with the "two" div
            "@two" #> SHtml.idMemoize(
              two => // the "two" div
                // display a bunch of items
                "ul *" #> (0 to randomInt(6)).map(i => "li *+" #> i) &
                  // update the "two" div on button press
                  "button [onclick]" #> SHtml.ajaxInvoke(two.setHtml _)))
}

class CSStransformer {

  def global = "*" #> <span>Replace All</span>

  var thing = "Replaced text"

  def render = {
    "#thing" #> <p>ID Replaced</p> &
      ".amazing" #> <p>Replaced class</p> &
      "type=text" #> SHtml.text(thing, thing = _) &
      "@somename" #> <p>Replaced name</p> &
      ":button" #> SHtml.button("Hit me", () => println("w00t")) &
      "li *+" #> List("monday", "tuesday", "wednesday") &
      "#apd *+" #> "Timothy" &
      "#prepend_target -*" #> "Timothy" &
      "type=text [class]" #> "textinput" &
      ".foo [class+]" #> "bar"
  }
}

object howdy extends DispatchSnippet {
  def dispatch = {
    case _ => howdy _
  }
  def howdy(xhtml: NodeSeq) = Text("Hello world")
}

class CountIncrement extends StatefulSnippet {
  def dispatch = {
    case _ if count < 5  => renderBelowFive
    case _ if count >= 5 => renderAboveFive
  }

  def renderBelowFive =
    "#count" #> count.toString &
      "#increment" #> SHtml.submit("Increment", () => count += 1)

  def renderAboveFive = (xhtml: NodeSeq) =>
    Text("Count is five or more.")

  private var count = 0
}

class NiceLiftForm extends StatefulSnippet {
  private var animal = ""
  private var legs = 0

  def dispatch = { case "render" => render }

  def render = {

    def process() {
      if (legs < 2) {
        S.error("legs", "Less then 2 legs, are you serious?")
      } else {
        S.notice("animal", "Animal: " + animal)
        S.notice("legs", "Has Legs: " + legs)
        S.redirectTo("/")
      }
    }

    "@animal" #> SHtml.text(animal, animal = _) &
      "@legs" #> SHtml.text(legs.toString, s => asInt(s).foreach(legs = _)) &
      "type=submit" #> SHtml.onSubmitUnit(process)
  }
}

class Messages extends StatefulSnippet {
    private var warningText = ""
    private var errorText = ""
    private var noticeText = ""
    def dispatch = { case "render" => render }
    def process() {
      S.warning("Warning: " + warningText)
      S.error("Error: " + errorText)
      S.notice("Notice: " + noticeText)
    }
    def render = {
      ".warning" #> SHtml.text(warningText, warningText = _) &
      ".error" #> SHtml.text(errorText, errorText = _) &
      ".notice" #> SHtml.text(noticeText, noticeText = _) &
      "type=submit" #> SHtml.onSubmitUnit { process }
    }
}