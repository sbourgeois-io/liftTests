package code.view

import scala.xml._
import net.liftweb.http.LiftView

class Myview extends LiftView {               
  override def dispatch = {                         
    case "sample" => render _
  }
  def render: NodeSeq = <body >
													<div id="main" class="lift:surround?with=default;at=content well span6">
														<h1>Test</h1>
													</div>
												</body>
}