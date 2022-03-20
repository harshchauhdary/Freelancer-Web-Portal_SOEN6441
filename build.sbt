import sbt.util.Cache.cache

name := """Assignment1"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"
libraryDependencies ++= Seq(
  guice,
  javaWs,
  ehcache,
  "com.typesafe.play" %% "play-cache" % "2.8.13"
)

