package Actions

import java.util.{Locale, UUID}

//import Actions.AuthSessionAction.UserSessionEnforcingActionRefiner
import javax.inject.Inject
import play.api.Configuration
import play.api.mvc.{request, _}
import utils.AuthUtils.SessionId

import scala.concurrent.{ExecutionContext, Future}

class AuthSessionAction @Inject()(val parser: BodyParsers.Default)(implicit val executionContext: ExecutionContext)
  extends ActionBuilder[AuthUserRequest, AnyContent]
    with ActionRefiner[Request, AuthUserRequest]{


  override def refine[A](
    request: Request[A]
  ): Future[Either[Result, AuthUserRequest[A]]] = Future.successful {
    getLegacyUserIdAndSessionId(request.session)
    Some(new AuthUserRequest[A](request)).toRight(Results.Unauthorized)
  }

  def getLegacyUserIdAndSessionId(
   session: Session
  ) = {
    val maybeAuthenticatedUserId =
      session
        .get("id")
    val maybeSessionId = session.get("sid").map(SessionId(_))

    (maybeAuthenticatedUserId, maybeSessionId)
  }

}

//object AuthSessionAction {
//
//  private object UserSessionEnforcingActionRefiner
//    extends ActionBuilder[UserRequest, AnyContent]
//      with ActionRefiner[Request, UserRequest]
//      {
//
//    def getLegacyUserIdAndSessionId(
//      session: Session
//    ) = {
//      val maybeAuthenticatedUserId =
//        session
//          .get("id")
//      val maybeSessionId = session.get("sid").map(SessionId(_))
//
//      (maybeAuthenticatedUserId, maybeSessionId)
//    }
//
//    override def refine[A](
//      request: Request[A]
//    ): Future[Either[Result, UserRequest[A]]] = Future.successful {
//      getLegacyUserIdAndSessionId(request.session)
//      Some(new UserRequest[A](request)).toRight(Results.Unauthorized)
//    }
//  }
//}


//object UserSessionCookieHelper extends CookieHelper[UserSession] {
//
//  override def extractFromCookie(
//    maybeCookieValue: Option[String]
//  ): Option[UserSession] =
//    for {
//      cookieValue <- maybeCookieValue
//      rallyId <- CookieHelper.safeClaimExtraction(cookieValue, ArachneJWTKeys.Subject)
//      maybeSessionId = safeClaimExtraction(cookieValue, ArachneJWTKeys.SessionIdClaim)
//      userId = AuthenticatedUserId.applyOption(rallyId)
//      sessionId = maybeSessionId.map(SessionId(_))
//    } yield UserSession(userId, sessionId)
//}
//
//case class UserSession(
//                        userId: Option[AuthenticatedUserId],
//                        sessionId: Option[SessionId]
//                      )





/**
  * A wrapper for Request which contains the authenticated user id.
  */
//class UserRequest[A](
//  val userId: AuthenticatedUserId,
//  val sessionId: Option[SessionId],
//  val locale: Locale,
//  request: Request[A]
//) extends WrappedRequest[A](request)
//  with PossibleSessionIdentifiers {
//  val sessionIdentifiers: Option[SessionIdentifiers] = Some(SessionIdentifiers(userId, sessionId))
//}

class AuthUserRequest[A](
//  val sessionId: Option[SessionId],
//  val locale: Locale,
  request: Request[A]
) extends WrappedRequest[A](request) {
}

object AuthUserRequest {
//  implicit def localeOf[A](
//    implicit
//    request: UserRequest[A]
//  ): Locale = request.locale
}


//trait PossibleSessionIdentifiers {
//  self: Request[_] =>
//
//  /**
//    * Ids of the user session associated with the request.
//    *
//    * @return User session ids, if a session is present
//    */
//  def sessionIdentifiers: Option[SessionIdentifiers]
//}
//
//object PossibleSessionIdentifiers {
//
//  case class SessionIdentifiers(
//    userId: UserId,
//    sessionId: Option[SessionId]
//  )
//}
