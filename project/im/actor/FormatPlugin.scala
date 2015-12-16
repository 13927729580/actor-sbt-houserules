package im.actor

import sbt._
import Keys._
import com.typesafe.sbt.SbtScalariform
import SbtScalariform.{ ScalariformKeys => sr, _ }

object FormatPlugin extends AutoPlugin {
  override def requires = plugins.JvmPlugin && SbtScalariform
  override def trigger = allRequirements

  override def projectSettings: Seq[Def.Setting[_]] = baseSettings

  lazy val baseSettings: Seq[Setting[_]] = Seq(
    sr.preferences := formatSettings ++ docFormatSettings
  )

  lazy val formatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile  := formattingPreferences,
    ScalariformKeys.preferences in Test     := formattingPreferences
    //ScalariformKeys.preferences in MultiJvm := formattingPreferences
  )

  lazy val docFormatSettings = SbtScalariform.scalariformSettings ++ Seq(
    ScalariformKeys.preferences in Compile  := docFormattingPreferences,
    ScalariformKeys.preferences in Test     := docFormattingPreferences
    //ScalariformKeys.preferences in MultiJvm := docFormattingPreferences
  )

  private def formattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences()
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(SpacesAroundMultiImports, true)
  }

  private def docFormattingPreferences = {
    import scalariform.formatter.preferences._
    FormattingPreferences()
      .setPreference(RewriteArrowSymbols, false)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
  }
}