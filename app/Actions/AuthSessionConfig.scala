package Actions

class AuthSessionConfig() {

  lazy val isEnabled: Boolean = true

  lazy val xsrfProtectionEnabled: Boolean = true
}

object AuthSessionConfig {

  def apply(): AuthSessionConfig =
    new AuthSessionConfig()
}
