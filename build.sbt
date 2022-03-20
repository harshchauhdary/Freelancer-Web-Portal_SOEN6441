import sbt.util.Cache.cache

name := """Assignment1"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"
libraryDependencies += "org.mockito" % "mockito-inline" % "4.4.0" % Test
libraryDependencies += "com.typesafe.play" %% "play-cache" % "2.8.13"
libraryDependencies ++= Seq(
  javaWs
)
libraryDependencies ++= Seq(
  guice,
  ehcache,
)


