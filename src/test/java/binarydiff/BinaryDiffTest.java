package binarydiff;

import binarydiff.BinaryDiffRouter.RouterResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Base64;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class BinaryDiffTest {

    private final String id = "id";
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeClass
    public static void startServer() {
        String[] args = {};
        BinaryDiffRouter.main(args);
    }

    @Test
    public void testSetLeftMember() throws IOException {
        final String setPath = MessageFormat.format("/v1/diff/{0}/left", id);
        final String value = "Value to set";
        assertCorrectSetResponse(setPath, value);
    }

    @Test
    public void testSetRightMember() throws IOException {
        final String setPath = MessageFormat.format("/v1/diff/{0}/right", id);
        final String value = "Value to set";
        assertCorrectSetResponse(setPath, value);
    }

    private void assertCorrectSetResponse(String path, String value) throws IOException {
        final RouterResponse expected = new RouterResponse(200, "ok", value);

        given()
                .accept(ContentType.JSON)
                .contentType("application/json")
                .body(Base64.getEncoder().encodeToString(value.getBytes()))
                .when()
                .put(path)
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo(mapper.writeValueAsString(expected)));
    }

    @Test
    public void testNotFoundMember() {
        final String newId = "newId";
        String path = MessageFormat.format("/v1/diff/{0}/left", newId);
        get(path).then()
                .assertThat()
                .statusCode(404);

        path = MessageFormat.format("/v1/diff/{0}/right", newId);
        get(path).then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    public void testGetLeftMember() throws IOException {
        final String path = MessageFormat.format("/v1/diff/{0}/left", id);
        final String value = "Value to set and get";
        assertCorrectSetResponse(path, value);
        assertCorrectGetResponse(path, value);
    }

    @Test
    public void testGetRightMember() throws IOException {
        final String path = MessageFormat.format("/v1/diff/{0}/right", id);
        final String value = "Value to set and get";
        assertCorrectSetResponse(path, value);
        assertCorrectGetResponse(path, value);
    }

    private void assertCorrectGetResponse(String path, String value) throws IOException {
        final RouterResponse expected = new RouterResponse(200, "ok", value);

        given()
                .accept(ContentType.JSON)
                .contentType("application/json")
                .when()
                .get(path)
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo(mapper.writeValueAsString(expected)));
    }

    @Test
    public void testEqualMembersDiff() throws IOException {
        final String leftPath = MessageFormat.format("/v1/diff/{0}/left", id);
        assertCorrectSetResponse(leftPath, "Equal members");

        final String rightPath = MessageFormat.format("/v1/diff/{0}/right", id);
        assertCorrectSetResponse(rightPath, "Equal members");

        final String diffPath = MessageFormat.format("/v1/diff/{0}", id);
        assertDiffResponse(diffPath, "Values are equal!");
    }

    @Test
    public void testDifferentSizesDiff() throws IOException {
        final String leftPath = MessageFormat.format("/v1/diff/{0}/left", id);
        assertCorrectSetResponse(leftPath, "Members have different size");

        final String rightPath = MessageFormat.format("/v1/diff/{0}/right", id);
        assertCorrectSetResponse(rightPath, "abc");

        final String diffPath = MessageFormat.format("/v1/diff/{0}", id);
        assertDiffResponse(diffPath, "Values have different sizes");
    }

    @Test
    public void testDiff() throws IOException {
        final String leftPath = MessageFormat.format("/v1/diff/{0}/left", id);
        assertCorrectSetResponse(leftPath, "Member number one");

        final String rightPath = MessageFormat.format("/v1/diff/{0}/right", id);
        assertCorrectSetResponse(rightPath, "Member number two");

        final String diffPath = MessageFormat.format("/v1/diff/{0}", id);
        assertDiffResponse(diffPath, "Values differ in 3 chars at position: 14");
    }

    @Test
    public void testInvalidDiff() {
        final String newId = "newId";
        final String path = MessageFormat.format("/v1/diff/{0}", newId);
        get(path).then()
                .assertThat()
                .statusCode(400);
    }

    private void assertDiffResponse(String path, String value) throws IOException {
        final RouterResponse expected = new RouterResponse(200, "ok", value);

        given()
                .accept(ContentType.JSON)
                .contentType("application/json")
                .when()
                .get(path)
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo(mapper.writeValueAsString(expected)));
    }

}
