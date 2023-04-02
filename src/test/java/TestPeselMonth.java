import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;

/*
positive cases covered by years
 */
public class TestPeselMonth {

    /*
    test cases are generated using a equivalence partitions technique and boundary value analyst
    in this data provider divide id via month:
    1 before 1900 - months number between 81-92 - chosen cases 80-93
	2 after 1900 before 2000 - months number between 01-12 chosen cases 00-13
	3 after 2000 before 2100 - months number between 21-32 chosen cases 20-33
	4 after 2100 before 2200 - months number between 41-52 chosen cases 40-53
	5 after 2200 before 2299 - months number between 61-72 chosen cases 60-73
     */
    @DataProvider(name = "peselMonths")
    public Object[][] months() {
        return new Object[][] {
                //month 80
                { "88802352025" },
                //month 93
                { "88932352025" },
                //month 00
                { "12002329055" },
                //month 13
                { "12132329055" },
                //month 20
                { "01202357984" },
                //month 33
                { "01332357984" },
                //month 40
                { "50402338402" },
                //month 53
                { "50532338402" },
                //month 60
                { "98602349619" },
                //month 73
                { "98732349619" },
        };
    }

    @Test(dataProvider = "peselMonths")
    public void testPeselMonths (String pesel){
        int expectedStatusCode = 200;
        Response getResponse = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        Assert.assertEquals(getResponse.statusCode(),expectedStatusCode);
        Assert.assertEquals(getResponse.path("pesel"), pesel);
        Assert.assertFalse(getResponse.path("isValid"));
        Assert.assertEquals(getResponse.path("errors[2].errorMessage"), "Invalid month.");
    }
}
