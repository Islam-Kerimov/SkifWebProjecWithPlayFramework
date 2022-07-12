name := """play-java-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(guice, evolutions, jdbc, javaJdbc)

libraryDependencies += "com.h2database" % "h2" % "2.1.212"

libraryDependencies += javaJpa

libraryDependencies += "org.hibernate" % "hibernate-core" % "5.5.6"

PlayKeys.externalizeResourcesExcludes += baseDirectory.value / "conf" / "META-INF" / "persistence.xml"