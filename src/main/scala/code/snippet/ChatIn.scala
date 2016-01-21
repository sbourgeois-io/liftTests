package code.snippet

import net.liftweb.http.SHtml
import net.liftweb.http.js.JsCmds.SetValById
import code.comet.ChatServer
import net.liftweb.http.js.JsCmds.SetValueAndFocus

class ChatIn {
	def render = SHtml.onSubmit(s => {
	  //Thread.sleep(1000)
	  ChatServer ! s
	  //Thread.sleep(500)
	  SetValueAndFocus("chat_in", "")
  })
}