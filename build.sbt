import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-pipe-operator",
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.4.0",
      scalaTest % Test
    )
  )
