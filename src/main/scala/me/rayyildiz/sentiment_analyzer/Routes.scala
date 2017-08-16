package me.rayyildiz.sentiment_analyzer

import javax.inject.Inject

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import me.rayyildiz.sentiment_analyzer.models.{ErrorMessage, JsonSerialization}
import me.rayyildiz.sentiment_analyzer.routes.{MiddlewareRoutes, RestRoutes, SwaggerRoutes}

import scala.concurrent.ExecutionContext

class Routes @Inject()(
  private val middlewareRoutes: MiddlewareRoutes,
  private val swaggerRoutes: SwaggerRoutes,
  private val restRoutes: RestRoutes
)(private implicit val materializer: ActorMaterializer, implicit val executionContext: ExecutionContext)
    extends JsonSerialization {

  val exceptionHandler = ExceptionHandler {
    case t: Throwable => {
      extractUri { uri =>
        complete(ErrorMessage(StatusCodes.InternalServerError.intValue, t.getMessage))
      }
    }
  }

  val jsonRejectionHandler = RejectionHandler
    .newBuilder()
    .handleAll[Rejection] { rejections => (context: RequestContext) =>
      {
        val route: StandardRoute = reject(rejections: _*)
        route.apply(context).map { res: RouteResult =>
          res
        }
      }
    }
    .handleNotFound {
      complete(ErrorMessage(StatusCodes.NotFound.intValue, s"Request not found"))
    }
    .result()

  def apply(): Route =
    handleRejections(jsonRejectionHandler) {
      handleExceptions(exceptionHandler) {
        middlewareRoutes() ~ restRoutes() //~swaggerRoutes()
      }
    }
}