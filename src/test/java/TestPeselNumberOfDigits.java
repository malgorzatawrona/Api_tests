import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;

public class TestPeselNumberOfDigits {

    /*
    test cases are generated using a equivalence partitions technique
    in this data provider divide id via number of digits:
    valid - 11 digits
    invalid - less than 11 digits
    invalid - more than 11 digits
     */
    @DataProvider(name = "peselNumberOfDigits")
    public Object[][] numberOfDigits() {
        return new Object[][] {
                //valid - 11 digits
                { "93033037299", true},
                //invalid - less than 11 digits
                { "93033037", false},
                //invalid - more than 11 digits
                { "930330372996", false}
        };
    }

    @Test(dataProvider = "peselNumberOfDigits")
    public void testPeselNumberOfDigits (String pesel, boolean isValid){
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
            Assert.assertEquals(getResponse.path("errors[0].errorMessage"), "Invalid length. Pesel should have exactly 11 digits.");
        }
    }
}
