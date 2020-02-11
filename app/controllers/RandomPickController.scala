package controllers

import javax.inject.Inject
import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import scala.util.Random
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import slack_apis.{SlackChannelMemberGetter, SlackMemberPoster}
import slack_apis.errors._

class RandomPickController @Inject() (cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {

  def execute(slackApiToken: String, channelId: String, pickUpNum: Int): Action[AnyContent] = Action.async { request =>
    if (pickUpNum <= 0) {
      Future.successful(BadRequest(Json.obj("error" -> "pickUpNum must be greater than 0")))
    } else {
      val channelMemberGetter = new SlackChannelMemberGetter(slackApiToken, ws)
      val postedMessage = for {
        memberResults <- channelMemberGetter.getMemberIdsBy(channelId)
        pickedUpMemberIds = Random.shuffle(memberResults.members).take(pickUpNum)
        pickedUpMembers <- Future.sequence(pickedUpMemberIds.map(channelMemberGetter.getProfile))
        memberPoster = new SlackMemberPoster(slackApiToken, ws)
        response <- memberPoster.postMembersToChannel(channelId, pickedUpMembers)
      } yield {
        response
      }
      postedMessage.map { response =>
        Ok(Json.obj("text" -> response.message.text))
      }.recover {
        case InvalidParamException(message) => BadRequest(Json.obj("error" -> message))
        case e: Exception => InternalServerError(Json.obj("error" -> e.getMessage))
      }
    }
  }
}
