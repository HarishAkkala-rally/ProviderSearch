// The Play plugin


// See https://wiki.audaxhealth.com/display/ENG/Build+Structure#BuildStructure-Localconfiguration
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

resolvers += Resolver.url("Ivy Plugin Releases", url("https://artifacts.werally.in/artifactory/ivy-plugins-release"))(Resolver.ivyStylePatterns)
resolvers += "Maven Plugin Releases" at "https://artifacts.werally.in/artifactory/plugins-release"
// resolvers += Resolver.url("Rally Plugin Snapshots", url("https://artifacts.werally.in/artifactory/ivy-plugins-snapshot"))(Resolver.ivyStylePatterns)

//addSbtPlugin("com.rallyhealth.sbt" %% "rally-shading-sbt-plugin" % "1.1.1")

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.8.0")