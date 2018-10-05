package com.prisma.api

import com.prisma.shared.schema_dsl.SchemaDsl
import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json

class HugoWoodFirstTest extends FlatSpec with Matchers with ApiSpecBase {
  "PostgreSQL connector" should "just work" in {
    val project = SchemaDsl.fromString() {
      """
        | type MyRequiredJson {
        |   id: ID! @unique
        |   json: Json!
        | }
      """
    }

    database.setup(project)

    val queryString =
      """mutation createMyRequiredJson($json: Json!) {
        | createMyRequiredJson(data: {json: $json}) {
        |  id
        |  json
        | }
        |}"""

    val variables = Json.parse("""{"json": {"test": 1}}""")

    server.query(queryString, project, variables = variables)
  }
}
