package controllers

import play.api._
import play.api.mvc._
import play.api.libs.json._

object Application extends Controller {

  def options(path: String) = Action { Ok("") }

  def index = Action {
    val hello = Json.obj("hello" -> "Hello World!")
    Ok(hello)
  }

  def api = Action {
    Ok(Json.obj("hello" -> "Welcome to BruteHack!"))
  }

}
