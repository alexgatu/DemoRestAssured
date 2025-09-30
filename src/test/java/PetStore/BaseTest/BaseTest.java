package PetStore.BaseTest;

import PetStore.Utils.ConfigLoader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
//import io.qameta.allure.restassured.AllureRestAssured;

public class BaseTest {


    @BeforeClass
    public static void setup() {
        RestAssured.baseURI= ConfigLoader.getConfig().getProperty("baseUri");
        RestAssured.port = Integer.parseInt(ConfigLoader.getConfig().getProperty("port"));
//        RestAssured.filters(new AllureRestAssured());
    }
}
