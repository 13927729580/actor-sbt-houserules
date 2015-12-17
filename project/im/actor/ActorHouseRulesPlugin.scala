package im.actor

import sbt._
import Keys._
import bintray.Keys._

object ActorHouseRulesPlugin extends AutoPlugin {
  object autoImport {
    lazy val actorPublishSettings = publishSettings("im.actor")

    lazy val actorServerPublishSettings = publishSettings("im.actor.server")

    private def publishSettings(organization: String) = Seq(
      organization := "im.actor",
      bintrayOrganization in bintray := Some("actor"),
      repository in bintray := "maven",
      licenses := Seq("Apache v2" -> url("https://github.com/actorapp/actor-platform/blob/master/LICENSE"))
    )
  }
}