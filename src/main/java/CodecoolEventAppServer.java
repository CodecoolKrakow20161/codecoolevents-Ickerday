import static spark.Spark.*;

class CodecoolEventAppServer {

  CodecoolEventAppServer() {
    port(8888);

    get("/", (req, res) -> "helo mi!");
  }
}
