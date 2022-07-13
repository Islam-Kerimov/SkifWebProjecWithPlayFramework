name := """play-java-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(guice, evolutions, javaJdbc)

libraryDependencies += "com.h2database" % "h2" % "2.1.212"

libraryDependencies += "com.typesafe.play" %% "play-mailer" % "8.0.0"
libraryDependencies += "com.typesafe.play" %% "play-mailer-guice" % "8.0.0"