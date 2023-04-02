import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;

public class TestPeselCharacters {

    /*
    test cases are generated using a equivalence partitions technique
    in this data provider divide id via characters:
    valid - only 11 digits
    invalid - 11 characters include letters
    invalid - 11 characters include special signs
     */
    @DataProvider(name = "peselCharacters")
    public Object[][] characters() {
        return new Object[][] {
                //valid - only 11 digits
                { "93033037299", true},
                //invalid - 11 characters include letters
                { "93d3303K299", false},
                //invalid - 11 characters include special signs
                { "93033$3729*", false}
        };
    }

    @Test(dataProvider = "peselCharacters")
    public void testPeselCharacters (String pesel, boolean isValid){
        int expectedStatusCode = 200;
        Response getResponse = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        Assert.assertEquals(getResponse.statusCode(),expectedStatusCode);
        Assert.assertEquals(getResponse.path("pesel"), pesel);
        if(isValid){
            Assert.assertTrue(getResponse.path("isValid"));
            Assert.assertEquals(getResponse.path("errors").toString(), "[]");
        }
        else {
            Assert.assertFalse(getResponse.path("isValid"));
            Assert.assertEquals(getResponse.path("errors[0].errorMessage"), "Invalid characters. Pesel should be a number.");
        }
    }
}
