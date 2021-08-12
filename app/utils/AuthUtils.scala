//package utils
//
//import java.util.UUID
//
//import org.joda.time.DateTime
//import play.api.http.HttpVerbs
//import play.api.libs.json.{Format, Json}
//import play.api.mvc.{Cookie, RequestHeader, Session}
//
///**
//  * Exposes utility functions for creating sessions and xsrf cookies.
//  */
//class AuthUtils {
//  import AuthUtils._
//
//  /**
//    * Constructs a new XSRF cookie. The content of the cookie will simply be the provided sessionId.
//    */
//  def newXSRFCookie(
//                     sessionId: SessionId
//                   ): Cookie =
//    Cookie(
//      AuthServiceConfig.XSRFCookie,
//      value = sessionId.value,
//      authServiceConfig.XSRFCookieMaxAge,
//      authServiceConfig.XSRFCookiePath,
//      authServiceConfig.XSRFCookieDomain,
//      authServiceConfig.XSRFCookieSecure,
//      httpOnly = false
//    )
//
//}
//
//object AuthUtils {
//  case class SessionId(
//                        value: String
//                      ) {
//
//    /**
//      * @note [[toString]] should generally not be used to get a serialization of a value. Rather,
//      *        just directly access this case classes [[value]] member. This override only exists
//      *        in order to minimize the impact of user error downstream.
//      */
//    override def toString: String = value
//  }
//
//  object SessionId {
//    implicit val sessionIdFormat: Format[SessionId] = Json.format[SessionId]
//
//    def generateSecureRandomId(): SessionId =
//      SessionId(UUID.randomUUID().toString)
//  }
//
//  /**
//    * Generates the session that can be validated by [[AuthSessionAction]] and [[SessionEnforcingAction]]
//    *
//    * @param idType the key for the id, specifying what type of user the session is for. This should be either
//    *               [[AuthServiceConfig.RallyOrUserIDSessionHeaderName]]
//    *               or [[AuthServiceConfig.GuestSessionIdHeaderName]]
//    * @param idValue a corresponding id for the user or guest user-type
//    */
//  private def newSession(
//                          idType: String,
//                          idValue: String
//                        ): (SessionId, Session) = {
//    val sessionId = SessionId.generateSecureRandomId()
//    val now = DateTime.now.toString
//
//    val sessionMap = Map(
//      AuthServiceConfig.SessionCreated -> now,
//      AuthServiceConfig.LastHeartbeat -> now,
//      idType -> idValue,
//      AuthServiceConfig.SessionId -> sessionId.value
//    )
//    (sessionId, Session(sessionMap))
//  }
//
//  def newUserSession(
//                      userId: AuthenticatedUserId
//                    ): (SessionId, Session) =
//    newSession(AuthServiceConfig.RallyOrUserIDSessionHeaderName, userId.id)
//
//  def newGuestSession(
//                       guestUserId: GuestUserId
//                     ): (SessionId, Session) =
//    newSession(AuthServiceConfig.GuestSessionIdHeaderName, guestUserId.id)
//
//  // http verbs that do not need xsrf protection
//  private val safeHttpVerbs = Set(HttpVerbs.OPTIONS, HttpVerbs.HEAD)
//
//  /**
//    * Checks to see if the Session ID is the same in the session and the XSRF Cookie, and checks to see if that
//    * value was correctly sent along with the [[AuthServiceConfig.XSRFHeader]]
//    *
//    * This is intended to mitigate XSRF (cross-site request forgery) attacks, based off the double-submit cookie
//    * pattern, described here:
//    *   https://www.owasp.org/index.php/Cross-Site_Request_Forgery_(CSRF)_Prevention_Cheat_Sheet#Double_Submit_Cookie
//    */
//  def hasValidXsrfMitigation(
//                              request: RequestHeader
//                            ): Boolean =
//    if (safeHttpVerbs.contains(request.method)) {
//      // These methods don't need XSRF protection. This whitelist could definitely be expanded if this proves too
//      // restrictive (to include GET for instance), but the double submit pattern should work with this
//      // limited whitelist.
//      true
//    } else {
//
//      val maybeXsrfValidationResult = for {
//        sid <- request.session.get(AuthServiceConfig.SessionId)
//        cookie <- request.cookies.get(AuthServiceConfig.XSRFCookie)
//        header <- request.headers.get(AuthServiceConfig.XSRFHeader)
//      } yield {
//        (sid == cookie.value) && (sid == header)
//      }
//
//      maybeXsrfValidationResult.getOrElse(false)
//    }
//
//  /**
//    * Heartbeats a session by updating [[AuthServiceConfig.LastHeartbeat]] in the session to Now,
//    * if the heartbeat exists.
//    *
//    * Note that this will heartbeat a session regardless of if there is a recent heartbeat or if
//    * the session is expired. This should only be called on a session that has been validated by
//    * [[SessionEnforcingAction]]
//    */
//  def heartbeatSession(
//                        session: Session
//                      ): Session =
//    session + (AuthServiceConfig.LastHeartbeat -> DateTime.now.toString)
//
//  /**
//    * Checks to see if a session has a heartbeat within the past 20 mins.
//    */
//  def hasRecentHeartbeat(
//                          session: Session
//                        ): Boolean = {
//    val maybeLastHeartbeat = session.get(AuthServiceConfig.LastHeartbeat).map(DateTime.parse)
//
//    maybeLastHeartbeat.exists(_.plusMinutes(20).isAfterNow)
//  }
//
//  /**
//    * Checks to see creation date exists and is in within the last day.
//    */
//  def creationDateWithinLastDay(
//                                 session: Session
//                               ): Boolean = {
//    val maybeCreationDate = session.get(AuthServiceConfig.SessionCreated).map(DateTime.parse)
//
//    maybeCreationDate.exists(_.plusDays(1).isAfterNow)
//  }
//
//  /**
//    * Gets the SessionId from a session if it exists.
//    */
//  def maybeSessionId(
//                      session: Session
//                    ): Option[SessionId] =
//    session.get(AuthServiceConfig.SessionId).map(SessionId(_))
//}
//
//
//object AuthServiceConfig {
//
//  /**
//    * Note that the following two value names are misleading, as they are not headers. Rather, they are keys for
//    * key/value pairs inside of the Play session cookie.
//    */
//  val GuestSessionIdHeaderName = "X-Rally-Guest-Session"
//  val RallyOrUserIDSessionHeaderName = "id"
//
//  /**
//    * When the session was originally created, i.e. the last time the user fully authenticated via SSO or guest authentication.
//    * We use this to set an upper limit on session duration and force re-authentication periodically.
//    */
//  val SessionCreated = "created"
//
//  /**
//    * When the session was last refreshed, i.e. the last time the user hit the refresh session endpoint.
//    * We use this to expire sessions that have not been renewed within 20 minutes.
//    */
//  val LastHeartbeat = "heartbeat"
//
//  val SessionId = "sid"
//
//  val XSRFCookie = "XSRF-TOKEN"
//  val XSRFHeader = "X-XSRF-TOKEN"
//}