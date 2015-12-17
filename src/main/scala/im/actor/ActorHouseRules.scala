package im.actor

import bintray.BintrayPlugin
import bintray.BintrayPlugin.autoImport._
import im.actor.ActorHouseRules.PublishType.PublishToBintray
import sbt.Keys._
import sbt._

import scala.xml.NodeSeq

object ActorHouseRules extends AutoPlugin {

  sealed trait PublishType

  object PublishType {

    object PublishToBintray extends PublishType

    object PublishToSonatype extends PublishType

  }

  override def requires = plugins.JvmPlugin

  override def trigger = allRequirements

  def actorDefaultSettings(
    org: String = "im.actor",
    publishTo: PublishType,
    pomExtra: NodeSeq
  ): Seq[Def.Setting[_]] =
    publishSettings(pomExtra, org, publishTo) ++ actorCompileSettings

  private def publishSettings(
    pomExtraVal: NodeSeq,
    org: String,
    publishType: PublishType
  ): Seq[Def.Setting[_]] =
    (publishType match {
      case PublishType.PublishToBintray => BintrayPlugin.bintrayPublishSettings
      case PublishType.PublishToSonatype =>
        Seq(
          publishTo := {
            val nexus = "https://oss.sonatype.org/"
            if (isSnapshot.value)
              Some("snapshots" at nexus + "content/repositories/snapshots")
            else
              Some("releases" at nexus + "service/local/staging/deploy/maven2")
          }
        )
    }) ++
      Seq(
        organization := org,
        bintrayOrganization in bintray := Some("actor"),
        bintrayRepository in bintray := "maven",
        licenses := Seq("Apache v2" -> url("https://github.com/actorapp/actor-platform/blob/master/LICENSE")),
        pomExtra in Global := pomExtraVal
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