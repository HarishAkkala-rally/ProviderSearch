package controllers

import Actions.UserAction
import com.rallyhealth.rq.v1.handler.HandledRqRequest
import javax.inject._
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc._
import utils.LegacyCookieGenerator
import com.rallyhealth.rq.v1.handler.Predicate.{OkOrNotFound, PredicateHandler}
import play.api.http.HttpVerbs
import scalaj.http.{Http, HttpOptions}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class PlayTestController @Inject()(cc: ControllerComponents, config: Configuration, userAction: UserAction) extends AbstractController(cc) {

  def getPcps = userAction {
    request => {
      Ok(Json.toJson(getResult().toString))
    }
  }

  private def getResult() = {
    val rallyId = "0414ea4a-9df0-4976-9797-7f9b52deee9e"

    Http("http://localhost:8018/rest/careteam/playResult/" + rallyId)
      .headers(LegacyCookieGenerator.encodeSessionCookie(rallyId))
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(100000)).asString
  }
}

//
//import com.rallyhealth.rq.v1._
//
//class CareteamClient()(implicit
//  ec: ExecutionContext
//) {
//
//  val careteamRequestBuilder: CareteamRequestBuilder = new CareteamRequestBuilder()
//  private implicit val unitPredicateHandler: PredicateHandler[String] = (x: RqResponse) => x.body.toString
//
//  def getPlayResult(
//    rallyId: String
//  ): Future[Option[String]] = {
//    Rq.url("http://localhost:8018/rest/careteam/v1/primaryCare/" + rallyId)
//      .withHeaders(LegacyCookieGenerator.encodeSessionCookie(rallyId).toSeq: _*)
//      .withMethod(HttpVerbs.GET)
//      .withHandler(OkOrNotFound[String])
//      .execute()
////        careteamRequestBuilder.getPlayResult(rallyId).execute()
//  }
//}
//
//
//import com.rallyhealth.rq.v1._
//import com.rallyhealth.rq.v1.handler.ExecutableRqRequest
//import com.rallyhealth.rq.v1.handler.Predicate.OkOrNotFound
//import com.softwaremill.tagging.@@
//import play.api.http.HttpVerbs
//
//class CareteamRequestBuilder(
////  careteamRqClient: RqClient @@ CareteamClient
//) {
//
//  private implicit val unitPredicateHandler: PredicateHandler[String] = (x: RqResponse) => x.body.toString
//
//  def getPlayResult(
//   rallyId: String
//  ): HandledRqRequest[Option[String]] =
//    Rq.url("http://localhost:8018/rest/careteam/v1/primaryCare/" + rallyId)
//      .withHeaders(LegacyCookieGenerator.encodeSessionCookie(rallyId).toSeq: _*)
//      .withMethod(HttpVerbs.GET)
//      .withHandler(OkOrNotFound[String])
////      .withRqClient(careteamRqClient)
//
//}
