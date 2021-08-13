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
  val secret: SecretConfiguration = SecretConfiguration(secret = "MFMj[wFNcEwif;hFjVO2LY=hg_]24i277Tncl`3?eM5NCDHZfdISHmsUpHgr5NYJ")

  val cookieSigner: CookieSigner = new CookieSignerProvider(secret).get

  val randomUuid = UUID.randomUUID().toString

  /*
  [Yesterday 2:13 pm] Harish, Akkala S
    Cookie: CHOPSHOP_SESSION=28f7800f7d18c927910be147c1fb0e7f83bb6dcb-created=2021-08-10T14%3A29%3A44.946Z&heartbeat=2021-08-10T14%3A29%3A44.946Z&id=0414ea4a-9df0-4976-9797-7f9b52deee9e&sid=3716a1b2-4f35-456b-8f41-cdba3dce56d5; XSRF-TOKEN=3716a1b2-4f35-456b-8f41-cdba3dce56d5
	X-XSRF-TOKEN: 3716a1b2-4f35-456b-8f41-cdba3dce56d5
   */

  def generateCookie =
    new LegacySessionCookieBaker(config, cookieSigner).encodeAsCookie(Session(Map("created" -> "", "heartbeat" -> "", "id" -> "0414ea4a-9df0-4976-9797-7f9b52deee9e", "sid" -> randomUuid)))


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
      value = randomUuid,
      maxAge = Some(7),
      secure = true,
      httpOnly = false
    )
}



