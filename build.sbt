name := "skynet"

version := "0.0.1"

unmanagedSourceDirectories in Compile <+= baseDirectory(_ / "src")

initialCommands in console := """import ee.moo.skynet.util.Console._"""