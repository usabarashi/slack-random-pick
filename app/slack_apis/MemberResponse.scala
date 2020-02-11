package slack_apis

import play.api.libs.json.{Json, Reads}

case class MemberResponse(members: Seq[String])
object MemberResponse {
  implicit val reads: Reads[MemberResponse] = Json.reads[MemberResponse]
}
