package slack_apis

import scala.concurrent.Future
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.json._
import play.api.libs.ws.{WSClient, WSResponse}

trait SlackPostable {
  val SLACK_API_BASE_URL = "https://slack.com/api/"

  def post(method: String, slackApiToken: String, requestParamJson: JsValue, ws: WSClient): Future[WSResponse] = {
    val slackRequestUrl: String = s"${SLACK_API_BASE_URL}${method}"
    ws.url(slackRequestUrl)
      .addHttpHeaders(
        HeaderNames.CONTENT_TYPE -> MimeTypes.JSON,
        HeaderNames.AUTHORIZATION -> s"Bearer ${slackApiToken}"
      )
      .post(requestParamJson)

  }

}
