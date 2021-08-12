package controllers

import java.time.Clock

import Actions.UserAction
import javax.inject._
import play.api.Configuration
import play.api.http.HttpVerbs
import play.api.libs.json.Json
import play.api.mvc._

import utils.LegacyCookieGenerator



@Singleton
class PlayTestController @Inject()(cc: ControllerComponents, config: Configuration, userAction: UserAction) extends AbstractController(cc) {

  def getPcps = userAction {
    request => {
      Ok(Json.toJson(Models.Provider.allProviders))
    }
  }

//  def getPlayResult(
//                       rallyId: String
//                     ): Option[String] =
//    Rq.url("http://localhost:8018/rest/careteam/v1/primaryCare/" + rallyId)
//      .withHeaders(LegacyCookieGenerator.encodeSessionCookie(rallyId).toSeq: _*)
//      .withMethod(HttpVerbs.GET)
//      .withHandler(OkOrNotFound[String])
//      .withRqClient(careteamRqClient)
//      .execute

//  def getCareteamPcps(
//       rallyId: String
//  ): Option[FamilyPrimaryCare] =
//    Rq.url(config.baseUrl + "/rest/careteam/v1/primaryCare/" + rallyId)
//      .withHeaders(CookieShim.encodeSessionCookie(rallyId).toSeq: _*)
//      .withMethod(HttpVerbs.GET)
//      .withHandler(OkOrNotFound[FamilyPrimaryCare])
//      .withRqClient(careteamRqClient)
//      .execute
}


case class
