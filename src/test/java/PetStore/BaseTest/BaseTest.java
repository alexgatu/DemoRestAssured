package PetStore.BaseTest;

import PetStore.Utils.ConfigLoader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.annotations.Listeners;

public class BaseTest {


    @BeforeClass(alwaysRun = true)
    public static void setup() {
        RestAssured.baseURI= ConfigLoader.getConfig().getProperty("baseUri");
        RestAssured.port = Integer.parseInt(ConfigLoader.getConfig().getProperty("port"));
        RestAssured.filters(new AllureRestAssured());
    }
}
