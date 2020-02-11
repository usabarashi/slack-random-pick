package slack_apis

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future
import play.api.libs.ws.WSClient

class SlackChannelMemberGetter(val slackApiToken: String, val ws: WSClient)
    extends SlackGettable
    with SlackResponseJsonParsable {

  def getMemberIdsBy(channelId: String): Future[MemberResponse] = {
    val requestParamMap = Map("channel" -> channelId)
    get("conversation.members", slackApiToken, requestParamMap, ws).flatMap { response =>
      Future.fromTry(parse[MemberResponse](response.json))
    }
  }

  def getProfile(memberId: String): Future[MemberProfileResponse] = {
    val requestParamMap = Map("user" -> memberId)
    get("users.profile.get", slackApiToken, requestParamMap, ws).flatMap { response =>
      Future.fromTry(parse[MemberProfileResponse](response.json))
    }
  }

}
