package im.actor

import sbt._
import Keys._
import bintray.BintrayPlugin
import bintray.BintrayPlugin.autoImport._

object ActorHouseRulesPlugin extends AutoPlugin {
  override def requires = plugins.JvmPlugin
  override def trigger = allRequirements

  lazy val actorPublishSettings = publishSettings("im.actor")

  lazy val actorServerPublishSettings = publishSettings("im.actor.server")

  def actorDefaultSettings(org: String = "im.actor"): Seq[Def.Setting[_]] =
    publishSettings(org) ++ actorCompileSettings

  private def publishSettings(org: String): Seq[Def.Setting[_]] =
    Seq(
      organization := org,
      bintrayOrganization in bintray := Some("actor"),
      bintrayRepository in bintray := "maven",
      licenses := Seq("Apache v2" -> url("https://github.com/actorapp/actor-platform/blob/master/LICENSE"))
    )

  private lazy val actorCompileSettings = Seq(
    scalacOptions in Compile ++= Seq(
      "-target:jvm-1.8",
      "-Ybackend:GenBCode",
      "-Ydelambdafy:method",
      "-Yopt:l:classpath",
      "-encoding", "UTF-8",
      "-deprecation",
      "-unchecked",
      "-feature",
      "-language:higherKinds",
      "-Xfatal-warnings",
      "-Xlint",
      "-Xfuture",
      "-Ywarn-dead-code",
      "-Ywarn-infer-any",
      "-Ywarn-numeric-widen"
    ),
    javaOptions ++= Seq("-Dfile.encoding=UTF-8"),
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation")
  )
}