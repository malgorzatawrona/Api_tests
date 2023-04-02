import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.get;

public class peselTestByUsedTypeOfSeparator {

    /*
    test cases are generated using a equivalence partitions technique
    in this data provider divide id via used type of separators:
    valid - without separator or only space
    invalid - separator like -
    invalid - separator like /
     */
    @DataProvider(name = "peselValidUsedTypeOfSeparator")
    public Object[][] validUsedTypeOfSeparator() {
        return new Object[][] {
                //valid - without  separator
                { "93033055743", true},
                //valid - type of separator - space
                { "93 03 30 65 050", true},
                //invalid - separator like /
                { "93/05/30/46/361", false},
                //invalid - separator like -
                { "93.05.30.01.289", false}
        };
    }

    @Test(dataProvider = "peselValidUsedTypeOfSeparator")
    public void testPeselInvalidUsedTypeOfSeparator(String pesel, boolean isValid){
        int expectedStatusCode = 200;
        Response getResponse = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        Assert.assertEquals(getResponse.statusCode(),expectedStatusCode);
        Assert.assertEquals(getResponse.path("pesel"), pesel.replaceAll(" ",""));
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
