import de.heikoseeberger.sbtheader.HeaderPattern
import de.heikoseeberger.sbtheader.license.BSD3Clause

lazy val root = (project in file(".")).enablePlugins(PlayScala, ForcePlugin, AutomateHeaderPlugin)

name := "salesforce-cla"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers ++= Seq(Resolver.mavenLocal, Resolver.jcenterRepo)

libraryDependencies ++= Seq(
  ws,
  filters,

  "com.pauldijou"          %% "jwt-play-json"                      % "0.9.2",

  "org.postgresql"         %  "postgresql"                         % "9.4-1203-jdbc42",
  "org.flywaydb"           %% "flyway-play"                        % "3.0.0",

  "com.github.mauricio"    %% "postgresql-async"                   % "0.2.21",
  "com.kyleu"              %% "jdub-async"                         % "1.0",

  "org.webjars"            %% "webjars-play"                       % "2.5.0-3",
  "org.webjars"            %  "salesforce-lightning-design-system" % "0.10.1",
  "org.webjars"            %  "octicons"                           % "3.1.0",
  "org.webjars.bower"      %  "signature_pad"                      % "1.5.1",

  "org.scalatestplus.play" %% "scalatestplus-play"                 % "1.5.1" % "test"
)

pipelineStages := Seq(digest, gzip)


// The sbt-force plugin can be used to fetch and deploy metadata

username in Force := sys.env.getOrElse("SALESFORCE_USERNAME", "")

password in Force := sys.env.getOrElse("SALESFORCE_PASSWORD", "")

packagedComponents in Force := Seq("sf_cla")

headers := Map(
  "scala" -> BSD3Clause("2017", "salesforce.com, inc."),
  "conf" -> BSD3Clause("2017", "salesforce.com, inc.", "#"),
  "html" -> BSD3Clause("2017", "salesforce.com, inc.", "@*")
)

excludes := Seq(
  "conf/clas/**"
)

unmanagedSources.in(Compile, createHeaders) ++= sources.in(Compile, TwirlKeys.compileTemplates).value
