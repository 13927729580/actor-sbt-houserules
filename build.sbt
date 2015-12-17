import ReleaseTransformations._

lazy val commonSettings = Seq(
  organization in ThisBuild := "im.actor"
)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    sbtPlugin := true,
    name := "actor-sbt-houserules",
    description := "sbt plugin for enforcing sbt house rules in Actor Team.",
    addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.5.1"),
    addSbtPlugin("me.lessis" % "bintray-sbt" % "0.3.0"),
    licenses := Seq("Apache v2" -> url("https://github.com/actorapp/actor-sbt-houserules/blob/master/LICENSE")),
    scmInfo := Some(ScmInfo(url("https://github.com/actorapp/actor-sbt-houserules"), "git@github.com:actorapp/actor-sbt-houserules.git")),
    publishMavenStyle := false,
    bintrayRepository in bintray := "sbt-plugins",
    bintrayOrganization in bintray := Some("actor"),
    releaseProcess := Seq(
      checkSnapshotDependencies,
      inquireVersions,
      setReleaseVersion,
      commitReleaseVersion,
      tagRelease,
      publishArtifacts,
      setNextVersion,
      commitNextVersion,
      pushChanges
    )
  )
