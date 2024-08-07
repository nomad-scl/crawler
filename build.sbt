ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "crawler",
    libraryDependencies := Seq(
      "org.scalactic" %% "scalactic" % "3.2.19",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test",
      "org.apache.nutch" % "nutch" % "2.4",
      "org.jsoup" % "jsoup" % "1.18.1"
    )

  )
