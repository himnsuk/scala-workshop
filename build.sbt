name := "scala-workshop"

version := "0.2"

scalaVersion := "2.12.6"

libraryDependencies += "com.softwaremill.quicklens" %% "quicklens" % "1.4.11"
libraryDependencies += "io.monix" %% "monix" % "2.3.3"
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.2.26"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.5"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"

// https://mvnrepository.com/artifact/org.scalamock/scalamock-scalatest-support
libraryDependencies += "org.scalamock" %% "scalamock-scalatest-support" % "3.6.0" % Test


resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"


