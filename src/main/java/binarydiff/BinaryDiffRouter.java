package binarydiff;

import binarydiff.decoder.Base64Decoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.*;

public class BinaryDiffRouter {

    private static BinaryDiffService binaryDiffService = new BinaryDiffService();
    private static Base64Decoder decoder = new Base64Decoder();
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        port(8080);

        put("/v1/diff/:id/left", (request, response) -> {
            final String id = request.params(":id");
            final String left = getDecodedMemberFromBody(request);
            binaryDiffService.setLeft(id, left);
            return mapper.writeValueAsString(okResponse(left));
        });

        put("/v1/diff/:id/right", (request, response) -> {
            final String id = request.params(":id");
            final String right = getDecodedMemberFromBody(request);
            binaryDiffService.setRight(id, right);
            return mapper.writeValueAsString(okResponse(right));
        });

        get("/v1/diff/:id", (request, response) -> {
            final String id = request.params(":id");
            final String left = binaryDiffService.left(id);
            final String right = binaryDiffService.right(id);

            RouterResponse routerResponse;
            if (left == null || right == null) {
                routerResponse = new RouterResponse(400, "Left and Right members must be set"); // 400 Bad Request
            } else {
                final String diff = binaryDiffService.diff(id);
                routerResponse = okResponse(diff);
            }
            response.status(routerResponse.status);
            return mapper.writeValueAsString(routerResponse);
        });

        get("/v1/diff/:id/left", (request, response) -> {
            final String id = request.params(":id");
            final String left = binaryDiffService.left(id);
            return getElementIfPresent(left, response);
        });

        get("/v1/diff/:id/right", (request, response) -> {
            final String id = request.params(":id");
            final String right = binaryDiffService.right(id);
            return getElementIfPresent(right, response);
        });
    }

    private static String getDecodedMemberFromBody(Request request) {
        return decoder.decodeBase64(request.body());
    }

    private static RouterResponse okResponse(String value) {
        return new RouterResponse(200, "ok", value);
    }

    private static String getElementIfPresent(String elem, Response response) throws IOException {
        RouterResponse routerResponse;
        if (elem == null) {
            routerResponse = new RouterResponse(404, "Member not set"); // 404 Not Found
        } else {
            routerResponse = okResponse(elem);
        }
        response.status(routerResponse.status);
        return mapper.writeValueAsString(routerResponse);
    }

    private static class RouterResponse {
        public final int status;
        public final String message;
        public final String value;

        RouterResponse(int status, String message) {
            this(status, message, "");
        }

        RouterResponse(int status, String message, String value) {
            this.status = status;
            this.message = message;
            this.value = value;
        }
    }

}
