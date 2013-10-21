name := "sylar"

version := "0.0.1"

unmanagedSourceDirectories in Compile <+= baseDirectory(_ / "src")

unmanagedSourceDirectories in Test <+= baseDirectory(_ / "test")

javacOptions ++= Seq("-encoding", "UTF-8")

initialCommands in console := """import ee.moo.sylar.util.Console._"""

libraryDependencies ++= Seq(
    "junit" % "junit" % "4.11",
    "com.novocode" % "junit-interface" % "0.10-M1" % "test"
)