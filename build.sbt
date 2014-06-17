
organization := "io.github.chrissom"

name := "gravatar"

version := "1.1.0"

scalaVersion := "2.11.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.0" % "test"

startYear := Some(2014)

homepage := Some(new URL("http://github.com/chrissom/gravatar"))

licenses := Seq(("Apache 2", new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")))

pomExtra := {
  <scm>
    <url>http://github.com/chrissom/gravatar</url>
  	<connection>scm:git:github.com/chrissom/gravatar.git</connection>
  	<developerConnection>scm:git:git@github.com:chrissom/gravatar.git</developerConnection>
  </scm>
  <developers>
    <developer>
      <id>chrissom</id>
  	  <name>Christophe Ribeiro</name>
  	  <url>http://chrissom.github.io</url>
  	</developer>
  </developers>
}

sonatypeSettings

publishTo <<= version { version: String =>
    if (version.trim.endsWith("SNAPSHOT"))
      Some("Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
    else
      Some("Sonatype Nexus Staging" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}
