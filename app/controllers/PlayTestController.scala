package controllers

import java.time.Clock

import Actions.UserAction
import javax.inject._
import play.api.Configuration
import play.api.http.HttpVerbs
import play.api.libs.json.Json
import play.api.mvc._
import utils.LegacyCookieGenerator
import com.rallyhealth.rq.v1.handler.{ExecutableRqRequest, HandledRqRequest}
import com.rallyhealth.rq.v1.handler.Predicate.{OkOrNotFound, PredicateHandler}
import com.rallyhealth.rq.v1.handler.RqResponseHandlers.ResponseHandler
import com.rallyhealth.rq.v1.handler.Status.{NoContent, Ok}
import com.rallyhealth.rq.v1._

import scala.concurrent.{ExecutionContext, Future}
import com.rallyhealth.rq.v1._
import com.rallyhealth.rq.v1.handler.ExecutableRqRequest
import com.rallyhealth.rq.v1.handler.Predicate.OkOrNotFound
import com.softwaremill.tagging.@@
import play.api.http.HttpVerbs



@Singleton
class PlayTestController @Inject()(cc: ControllerComponents, config: Configuration, userAction: UserAction) extends AbstractController(cc) {

//  private implicit val unitPredicateHandler: PredicateHandler[String] = (x: RqResponse) => x.body.toString


  def getPcps = userAction {
    request => {
      Ok(Json.toJson(new CareteamClient(new CareteamRequestBuilder()
    }
  }

//  def getPlayResult(
//                       rallyId: String
//                     ): Option[String] =
//    Rq.url("http://localhost:8018/rest/careteam/v1/primaryCare/" + rallyId)
//      .withHeaders(LegacyCookieGenerator.encodeSessionCookie(rallyId).toSeq: _*)
//      .withMethod(HttpVerbs.GET)
//      .withHandler(OkOrNotFound[String])
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


import com.rallyhealth.rq.v1._
import com.rallyhealth.rq.v1.handler.ExecutableRqRequest
import com.rallyhealth.rq.v1.handler.Predicate.OkOrNotFound
import com.softwaremill.tagging.@@
import play.api.http.HttpVerbs

class CareteamClient(
  careteamRequestBuilder: CareteamRequestBuilder
)(implicit
  ec: ExecutionContext
) {

  private implicit val unitPredicateHandler: PredicateHandler[String] = (x: RqResponse) => x.body.toString

  def getPlayResult(
               rallyId: String
             ): Future[Option[String]] = {
        careteamRequestBuilder.getPlayResult(rallyId).execute()
  }
}


import com.rallyhealth.rq.v1._
import com.rallyhealth.rq.v1.handler.ExecutableRqRequest
import com.rallyhealth.rq.v1.handler.Predicate.OkOrNotFound
import com.softwaremill.tagging.@@
import play.api.http.HttpVerbs

class CareteamRequestBuilder(
  careteamRqClient: RqClient @@ CareteamClient
) {

  private implicit val unitPredicateHandler: PredicateHandler[String] = (x: RqResponse) => x.body.toString

  def getPlayResult(
   rallyId: String
  ): ExecutableRqRequest[Option[String]] =
    Rq.url("http://localhost:8018/rest/careteam/v1/primaryCare/" + rallyId)
      .withHeaders(LegacyCookieGenerator.encodeSessionCookie(rallyId).toSeq: _*)
      .withMethod(HttpVerbs.GET)
      .withHandler(OkOrNotFound[String])
      .withRqClient(careteamRqClient)

}
