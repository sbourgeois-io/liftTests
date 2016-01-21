package code
package snippet

import scala.xml.{NodeSeq, Text}
import scala.language.implicitConversions
import net.liftweb._
import net.liftweb.util._
import net.liftweb.common._
import code.lib._
import http._
import SHtml._
import Helpers._
import util._
import js._
import js.JsCmds._
import js.jquery._
 
class RectangleAreaWiring {
    private object RectangleInformation {
        val length = ValueCell(0d)
        val width = ValueCell(0d)
        val area = length.lift(width) {_ * _}
    }
     
    def length = ajaxText(RectangleInformation.length.get.toString,
                     doubleToJsCmd(RectangleInformation.length.set))
                          
    def width = ajaxText(RectangleInformation.width.get.toString,
                     doubleToJsCmd(RectangleInformation.width.set))
                          
    def area = WiringUI.toNode(RectangleInformation.area, JqWiringSupport.fade)(doubleDraw)
 
    private def doubleDraw: (Double, NodeSeq) => NodeSeq = 
        (d, ns) => Text(java.text.NumberFormat.getNumberInstance.format(d))
         
    private implicit def doubleToJsCmd(in: Double => Any): String => JsCmd =
        str => { asDouble(str).foreach(in) }
}