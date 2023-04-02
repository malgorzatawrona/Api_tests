import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;
import io.restassured.response.Response;

public class TestPeselYear {

    /*
    test cases are generated using a equivalence partitions technique
    in this data provider divide id via years:
    1 before 1900
	2 after 1900 before 2000
	3 after 2000 before 2100
	4 after 2100 before 2200
	5 after 2200 before 2299
     */
    @DataProvider(name = "peselYears")
    public Object[][] years() {
        return new Object[][] {
                //before 1900
                { "88832352025" },
                //after 1900 before 2000
                { "12032329055" },
                //after 2000 before 2100
                { "01232357984" },
                //after 2100 before 2200
                { "50432338402" },
                //after 2200 before 2299
                { "98632349619" },
        };
    }

    @Test(dataProvider = "peselYears")
    public void testPeselYear (String pesel){
        int expectedStatusCode = 200;
        Response getResponse = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        Assert.assertEquals(getResponse.statusCode(),expectedStatusCode);
        Assert.assertEquals(getResponse.path("pesel"), pesel);
        Assert.assertTrue(getResponse.path("isValid"));
    }
}
