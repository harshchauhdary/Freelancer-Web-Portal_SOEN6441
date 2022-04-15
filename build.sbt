import sbt.util.Cache.cache

name := """Assignment1"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)
lazy val akkaVersion = "2.6.18"
scalaVersion := "2.13.6"
libraryDependencies += "org.mockito" % "mockito-inline" % "4.4.0" % Test
libraryDependencies += "com.typesafe.play" %% "play-cache" % "2.8.13"
libraryDependencies ++= Seq(
  javaWs
)
libraryDependencies ++= Seq(
  guice,
  ehcache,
  "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "junit" % "junit" % "4.13.1" % Test,
  "com.novocode" % "junit-interface" % "0.11" % Test
)


