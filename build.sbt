//enablePlugins(ScalaJSPlugin)

name := "NTS2 first web site"

version := "0.0.1"

organization := "party.nanotek"

scalaVersion := "2.11.7"

EclipseKeys.withSource := true
EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

resolvers ++= Seq(
				  "Typesafe repository snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
				  "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
				  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
				  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
				  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
				  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
				  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
				  "Twitter Repository"               at "http://maven.twttr.com",
				  Resolver.bintrayRepo("websudos", "oss-releases")
                 )

seq(webSettings :_*)

net.virtualvoid.sbt.graph.Plugin.graphSettings
filterScalaLibrary := true

unmanagedResourceDirectories in Test <+= (baseDirectory) { _ / "src/main/webapp" }

//testFrameworks += new TestFramework("utest.runner.Framework")
//skip in packageJSDependencies := true

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

//jsDependencies += "org.webjars" % "jquery" % "2.1.3" / "2.1.3/jquery.js"

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  val PhantomVersion = "1.12.2"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion           % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion           % "compile",
    "net.liftmodules"   %% "fobo_2.6"           % "1.4"                 % "compile",
    "org.eclipse.jetty" % "jetty-webapp"        % "8.1.17.v20150415"     % "container,test",
    "org.eclipse.jetty" % "jetty-plus"          % "8.1.17.v20150415"     % "container,test", // For Jetty Config
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,test" artifacts Artifact("javax.servlet", "jar", "jar"),
    "ch.qos.logback"    % "logback-classic"     % "1.1.3",
    "org.specs2"        %% "specs2-core"             % "3.6.4"              % "test",
    "com.h2database"    %  "h2"                 % "1.4.187",
	"com.websudos" %% "phantom-dsl" % PhantomVersion,
	"com.websudos" %% "phantom-testkit" % PhantomVersion % "test, provided"
    //,
    //"org.scala-js"      %%% "scalajs-dom"       % "0.8.0",
	//"be.doeraene"       %%% "scalajs-jquery"    % "0.8.0",
	//"com.lihaoyi"       %%% "utest" % "0.3.0"   % "test"
	//,"org.webjars"       %%% "jquery"              % "2.1.3/jquery.js"
  )
}



