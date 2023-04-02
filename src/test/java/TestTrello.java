import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class TestTrello {
    String url = "https://api.trello.com/1/boards/";
    String name = "ProjektAPI";
    String key = "019520926368c84569933dde94bfe09c";
    String token = "ATTA13b6b353e6252a5fbc6856e826953891aebdef1fc949db068dbad5640b99539bC9D77F90";
    Response createBoard;

    @Test
    public void test01CreateBoard () {
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/json");

        createBoard = request.post(url + "?name=" + name + "&key=" + key + "&token=" + token);
        Assert.assertEquals(createBoard.statusCode(), 200);
        Assert.assertEquals(createBoard.path("name"), name);
    }

    @Test
    public void test02GetBoard() {
        Response getBoard = get(url + createBoard.path("id") + "?key=" + key + "&token=" + token);
        Assert.assertEquals(getBoard.statusCode(), 200);
        Assert.assertEquals(getBoard.path("name"), name);
        Assert.assertEquals((String) getBoard.path("idOrganization"), createBoard.path("idOrganization"));
    }

    @Test
    public void test03DeleteBoard() {
        Response deleteBoard = delete(url + createBoard.path("id") + "?key=" + key + "&token=" + token);
        Assert.assertEquals(deleteBoard.statusCode(), 200);
    }

    @Test
    public void test04BoardNotFound(){
        Response getBoard = get(url + createBoard.path("id") + "?key=" + key + "&token=" + token);
        Assert.assertEquals(getBoard.statusCode(),404);
    }
}
