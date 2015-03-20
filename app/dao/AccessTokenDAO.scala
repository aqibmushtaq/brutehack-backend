package dao

import play.api.db.slick.DB
import play.api.Play.current
import utils.MyPostgresDriver.simple._
import models.AccessToken
import scala.util.{Try}
import scala.concurrent.{Future, ExecutionContext}
import java.sql.Timestamp

object AccessTokenDAO {

  val accessTokens = TableQuery[models.database.AccessTokens]

  def deleteExistingAndCreate(accessToken: AccessToken, userId: Long)
    (implicit ec: ExecutionContext): Future[Unit] = Future {
    DB.withSession { implicit session =>
      // these two operations should happen inside a transaction
      accessTokens.filter(a => a.userId === userId).delete
      accessTokens.insert(accessToken)
    }
  }

  def findToken(userId: Long)(implicit ex: ExecutionContext): Future[Option[AccessToken]] = Future {
    DB.withSession { implicit session =>
      accessTokens.filter(a => a.userId === userId).firstOption
    }
  }

  def findAccessToken(token: String)(implicit ec: ExecutionContext): Future[Option[AccessToken]] = Future {
    DB.withSession { implicit session =>
      accessTokens.filter(a => a.accessToken === token).firstOption
    }
  }

  def findRefreshToken(refreshToken: String)(implicit ec: ExecutionContext): Future[Option[AccessToken]] = Future {
    DB.withSession{ implicit session =>
      accessTokens.filter(a => a.refreshToken === refreshToken).firstOption
    }
  }

}

