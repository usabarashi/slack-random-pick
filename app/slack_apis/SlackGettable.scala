package slack_apis

import scala.concurrent.Future
import play.api.libs.ws.{WSClient, WSResponse}

trait SlackGettable {
  val SLACK_API_BASE_URL = "https://slack.com/api/"

  def get(
      method:          String,
      slackApiToken:   String,
      requestParamMap: Map[String, String],
      ws:              WSClient
  ): Future[WSResponse] = {

    val requestParams = (requestParamMap + ("token" -> slackApiToken)).map {
      case (key, value) => s"${key}=${value}"
    }.mkString("&")
    val slackRequestUrl = s"${SLACK_API_BASE_URL}${method}?${requestParams}"

    ws.url(slackRequestUrl).get()
  }

}
