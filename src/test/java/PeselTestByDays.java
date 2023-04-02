import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.get;

/*
positive cases covered by years
 */
public class PeselTestByDays {

    /*
    test cases are generated using a equivalence partitions technique and boundary value analyst
    in this data provider divide id via days:
    00 - always invalid
    01 - 28 - always valid
	29 - valid for leap year, invalid for rest (February)
	30 - valid for 30th days months
	31 - valid for 31th days months
	32 - always invalid
     */
    @DataProvider(name = "peselInvalidDays")
    public Object[][] invalidDays() {
        return new Object[][] {
                //day 00
                { "90030092149" },
                //day 29 of February not leap
                { "90022992149" },
                //day 30 of February
                { "92023035392" },
                //day 31 of April
                { "90043192149" },
                //day 32 of March
                { "90033292149" },
        };
    }

    @Test(dataProvider = "peselInvalidDays")
    public void testPeselInvalidDays (String pesel){
        int expectedStatusCode = 200;
        Response getResponse = get("https://peselvalidatorapitest.azurewebsites.net/api/Pesel?pesel=" + pesel);
        Assert.assertEquals(getResponse.statusCode(),expectedStatusCode);
        Assert.assertEquals(getResponse.path("pesel"), pesel);
        Assert.assertFalse(getResponse.path("isValid"));
        Assert.assertEquals(getResponse.path("errors[1].errorMessage"), "Invalid day.");
    }
}
