package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._
import common._
import http._
import sitemap._
import Loc._
import mapper._
import code.model._
import net.liftmodules.FoBo
import scala.language.postfixOps
import code.lib.GameRest
import code.snippet.howdy

/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    //    if (!DB.jndiJdbcConnAvailable_?) {
	//    sys.props.put("h2.implicitRelativePath", "true")
    //      val vendor = 
    //	new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
    //			     Props.get("db.url") openOr 
    //			     "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
    //			     Props.get("db.user"), Props.get("db.password"))
    //
    //      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)
    //
    //      DB.defineConnectionManager(util.DefaultConnectionIdentifier, vendor)
    //    }
    //
    //    // Use Lift's Mapper ORM to populate the database
    //    // you don't need to use Mapper to use Lift... use
    //    // any ORM you want
    //    Schemifier.schemify(true, Schemifier.infoF _, User)

    // where to search snippet
    LiftRules.addToPackages("code")

  //  def sitemapMutators = User.sitemapMutator
    //The SiteMap is built in the Site object bellow 
//    LiftRules.setSiteMapFunc(() => sitemapMutators(Site.sitemap))
    LiftRules.setSiteMapFunc(() => Site.sitemap)

    //Init the FoBo - Front-End Toolkit module, 
    //see http://liftweb.net/lift_modules for more info
    FoBo.InitParam.JQuery = FoBo.JQuery1102
    FoBo.InitParam.ToolKit = FoBo.Bootstrap320
    //    FoBo.InitParam.ToolKit=FoBo.Pace0415e
    FoBo.init()

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

    // What is the function to test if a user is logged in?
    //    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    // Use HTML5 for rendering
    LiftRules.htmlProperties.default.set((r: Req) =>
      new Html5Properties(r.userAgent))

    LiftRules.noticesAutoFadeOut.default.set((notices: NoticeType.Value) => {
      notices match {
        case NoticeType.Notice => Full((8 seconds, 4 seconds))
        case _                 => Empty
      }
    })

    LiftRules.dispatch.append(GameRest)
    LiftRules.snippetDispatch.append {
      case "howdy" => howdy
    }
    LiftRules.viewDispatch.append {
      case List("viewthing", "example") =>
        Left(() => Full(<div id="main" class="lift:surround?with=default;at=content well span6"><h1>Manual Sample</h1></div>))
    }
    // Make a transaction span the whole HTTP request
    //    S.addAround(DB.buildLoanWrapper)
  }

  object Site {
    import scala.xml._
    val divider1 = Menu("divider1") / "divider1"
    val ddLabel1 = Menu.i("UserDDLabel") / "ddlabel1"
    val home = Menu.i("Home!!!") / "index"
    //val chat = Menu.i("Chat") / "webpages/chat"
    val chat = Menu(Loc("Chat", Link(List("webpages", "chat"), false, "/webpages/chat"), S.loc("Chat", scala.xml.Text("Chat")), LocGroup("lg1")))
    val tests = Menu.i("Tests") / "webpages/tests"
    //val view = Menu.i("View") / "viewthing/example"
    val view = Menu(Loc("View", Link(List("viewthing", "example"), false, "/viewthing/example"), "View", LocGroup("lg2")))
    //val view2 = Menu.i("View2") / "Myview/sample"
    val view2 = Menu(Loc("View2", Link(List("Myview", "sample"), false, "/Myview/sample"), "View2", LocGroup("lg2")))
    //    val userMenu   = User.AddUserMenusHere
    val radio = Menu(Loc("Radio", Link(List("webpages", "radio"), true, "/webpages/radio"), S.loc("Radio", scala.xml.Text("Radio")), LocGroup("lg1")))
    val static = Menu(Loc("Static", Link(List("static"), true, "/static/index"), S.loc("StaticContent", scala.xml.Text("Static Content")), LocGroup("lg2", "topRight")))
    val twbs = Menu(Loc("twbs", ExtLink("http://getbootstrap.com/"),             S.loc("Bootstrap3", Text("Bootstrap3")),                  LocGroup("lg2"), FoBo.TBLocInfo.LinkTargetBlank))

    def sitemap = SiteMap(
      home >> LocGroup("lg1"),
      chat /*>> LocGroup("lg1")*/,
      tests >> LocGroup("lg1"),
      radio,
      static,
      view,
      view2,
      twbs
      //        ddLabel1      >> LocGroup("topRight") >> PlaceHolder submenus (
      //            divider1  >> FoBo.TBLocInfo.Divider >> userMenu
      //            )
      )
  }

}
