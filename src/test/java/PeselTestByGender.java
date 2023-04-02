import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class PeselTestByGender {

    /*
    test cases are generated using a equivalence partitions technique
    in this data provider divide id via days:
    even - Female
    odd - Male
     */
    @DataProvider(name = "peselValidGender")
    public Object[][] validGender() {
        return new Object[][] {
                //Female
                { "92021457129", "Female"},
                //Male
                { "92021464679", "Male"},
        };
    }

    @Test(dataProvider = "peselValidGender")
    public void testPeselValidGender (String pesel, String gender){
        int expectedStatusCode = 200;
        Response getResponse = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        Assert.assertEquals(getResponse.statusCode(),expectedStatusCode);
        Assert.assertEquals(getResponse.path("pesel"), pesel);
        Assert.assertTrue(getResponse.path("isValid"));
        Assert.assertEquals(getResponse.path("gender"), gender);
        Assert.assertEquals(getResponse.path("errors").toString(), "[]");
    }
}
