import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;

public class TestPeselCheckSum {

    /*
    test cases are generated using a equivalence partitions technique
    in this data provider divide id via check sum:
    valid - check sum correct
    invalid - check sum incorrect
     */
    @DataProvider(name = "peselCheckSum")
    public Object[][] checkSum() {
        return new Object[][] {
                //valid
                { "92021489267", true},
                //invalid
                { "92021489287", false},
        };
    }

    @Test(dataProvider = "peselCheckSum")
    public void testPeselCheckSum (String pesel, boolean isValid){
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
            Assert.assertEquals(getResponse.path("errors[0].errorMessage"), "Check sum is invalid. Check last digit.");
        }
    }
}
