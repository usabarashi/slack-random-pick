package slack_apis

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import play.api.libs.json._
import play.api.libs.ws.WSClient

class SlackMemberPoster(val slackApiToken: String, val ws: WSClient)
    extends SlackPostable
    with SlackResponseJsonParsable {

  def postMembersToChannel(channelId: String, members: Seq[MemberProfileResponse]): Future[PostedMessageResponse] = {
    val text             = members.map(_.toString).mkString("\n\n")
    val requestParamJson = Json.toJson(PostTextRequest(channelId, text))
    post("chat.postMessage", slackApiToken, requestParamJson, ws).flatMap { response =>
      Future.fromTry(parse[PostedMessageResponse](response.json))
    }
  }

}
