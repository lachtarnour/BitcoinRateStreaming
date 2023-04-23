import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.json4s._
import org.json4s.native.JsonMethods._

import java.io.{BufferedReader, InputStreamReader}
import java.net.{HttpURLConnection, URL}
import scala.util.{Failure, Success, Try}

class BinanceAPI extends SourceFunction[Double] {
  override def run(ctx: SourceFunction.SourceContext[Double]): Unit = {
    val apiKey = "5453669665msh5f8df7e2b65b9c7p10c615jsnbd4dba168490"
    val symbol = "ETHBTC"
    val url = s"https://binance43.p.rapidapi.com/avgPrice?symbol=$symbol"

    while (true) {
      Try {
        val connection = new URL(url).openConnection.asInstanceOf[HttpURLConnection]
        connection.setRequestMethod("GET")
        connection.setRequestProperty("X-RapidAPI-Key", apiKey)
        connection.setRequestProperty("X-RapidAPI-Host", "binance43.p.rapidapi.com")

        val responseCode = connection.getResponseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
          val in = new BufferedReader(new InputStreamReader(connection.getInputStream))
          val response = new StringBuilder
          var inputLine: String = null
          while ({ inputLine = in.readLine; inputLine != null }) {
            response.append(inputLine)
          }
          in.close()

          implicit val formats: DefaultFormats.type = DefaultFormats
          val json = parse(response.toString)
          val price = (json \ "price").extract[String].toDouble
          println(s"Price: $price")
        } else if (responseCode == 429) {
          // If we exceed the API limit, wait for a minute and try again
          Thread.sleep(60000)
        }else {
          throw new RuntimeException(s"HTTP GET request failed with error code: $responseCode")
        }
      } match {
        case Success(_) =>
        case Failure(ex) =>
          ex.printStackTrace()
      }

      Thread.sleep(15000) // Stream the data every 15 seconds
    }
  }

  override def cancel(): Unit = {}
}
