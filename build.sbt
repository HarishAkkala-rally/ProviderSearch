import sbt.{Credentials, Path, Resolver}

name := """provider-search"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

//scalaVersion := "2.12.8"
scalaVersion := "2.13.1"


credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers += Resolver.sonatypeRepo("snapshots")

resolvers += Resolver.url("Ivy Plugin Releases", url("https://artifacts.werally.in/artifactory/ivy-plugins-release"))(Resolver.ivyStylePatterns)
resolvers += Resolver.url("Ivy Lib Releases", url("https://artifacts.werally.in/artifactory/ivy-libs-release"))(Resolver.ivyStylePatterns)
resolvers += "Maven Plugin Releases" at "https://artifacts.werally.in/artifactory/plugins-release"

libraryDependencies += guice
//libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.199"
libraryDependencies += "com.pauldijou" %% "jwt-play-json" % "4.2.0"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

libraryDependencies ++= Seq(
//  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
//  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalatest" %% "scalatest" % "3.2.9",
  "com.pingfederate" % "opentoken-agent" % "2.5.2",
  "commons-collections" % "commons-collections" % "3.2.2",
  "commons-logging" % "commons-logging" % "1.1.3",
  "com.rallyhealth" %% "weepickle-v1" % "1.4.0",
  "com.typesafe.play" %% "play-json" % "2.8.1",
//  "com.typesafe.play" % "play-json_2.11" % "2.5.1",
  "com.rallyhealth" %% s"weejson-play28-v1" % "1.0.0",
//  "com.rallyhealth.chopshop" % "careteam-client-play25_2.11" % "3.53.0"
//  "com.rallyhealth.chopshop" %% s"lib-chopshop-common-$component" % libChopshopCommonVersion shaded,
//  "com.rallyhealth.chopshop" %% s"lib-chopshop-server-$component" % libChopshopServerVersion,
//  "com.rallyhealth.rq" %% s"lib-rq-client-$component" % "1.7.0" shaded,
//  "com.rallyhealth.interceptor" %% s"lib-http-interceptor-$component" % libHttpInterceptorVersion
)


