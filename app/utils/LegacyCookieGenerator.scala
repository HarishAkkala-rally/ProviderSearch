package utils

import java.util.UUID

import play.api.http.HeaderNames.COOKIE
import play.api.libs.crypto.{CookieSigner, CookieSignerProvider}
import play.api.mvc.{Cookie, Cookies, LegacySessionCookieBaker, Session}
import play.api.mvc._

import scala.concurrent.duration._
import play.api.http._

object LegacyCookieGenerator {

  //  val config: SessionConfiguration = HttpConfiguration.fromConfiguration()
  //    val config: SessionConfiguration = SessionConfiguration(${play.http.session.cookieName}, true, maxAge = Some(FiniteDuration(7, DAYS))) //TODO - Pass parameters
  val config: SessionConfiguration = SessionConfiguration("CHOPSHOP_SESSION", true, maxAge = Some(FiniteDuration(7, DAYS))) //TODO - Pass parameters

  /* TODO https://www.playframework.com/documentation/2.5.x/CryptoMigration25
      https://www.playframework.com/documentation/2.5.x/ApplicationSecret
      https://www.playframework.com/documentation/2.8.x/Configuration

   */
  val secret: SecretConfiguration = SecretConfiguration()

  val cookieSigner: CookieSigner = new CookieSignerProvider(secret).get

  def generateCookie =
    new LegacySessionCookieBaker(config, cookieSigner).encodeAsCookie(Session(Map("Name" -> "Harish")))


  def encodeSessionCookie(
    rallyId: String
  ): Map[String, String] = {
    val xsrfCookie: Cookie = newXSRFCookie
    val sessionCookie: Cookie = generateCookie
    Map(
      COOKIE -> new DefaultCookieHeaderEncoding().encodeCookieHeader(Seq(sessionCookie, xsrfCookie)),
      "X-XSRF-TOKEN" -> xsrfCookie.value
    )
  }

  def newXSRFCookie: Cookie =
    Cookie(
      name = "XSRF-TOKEN",
      value = UUID.randomUUID().toString,
      maxAge = Some(7),
      secure = true,
      httpOnly = false
    )
}



