package PetStore.Pet;

import PetStore.BaseTest.BaseTest;
import PetStore.DTO.Pet;
import PetStore.Specs.RequestSpecs;
import PetStore.Specs.ResponseSpecs;
import PetStore.Utils.DataHelper;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.TestRunner.PriorityWeight.dependsOnMethods;

public class PetTests extends BaseTest {

    @DataProvider(name="pets")
    public Object[][] pets() {
        return new Object[][] {
                {910, "bobitza", "sold", 1, "mytag", 1, "dog"},
                {901, "", "sold", 1, "tag", 1, ""},
                {902, "", "", 1, "", 1, ""},
                {-1, "aaa", "aaaa", -1, "aaaa", -1, "aaaa"}
        };
    }

    @DataProvider(name="pets2")
    public Iterator<Pet> pets2() {
        List<String[]> data = DataHelper.readDataFromCsv("testData/pets.csv");
        List<Pet> pets = new ArrayList<>();
        for (String[] line : data) {
            for (Object o : line) {
                System.out.println(o.toString());
            }
            System.out.println("column length: " + Arrays.stream(line).toList().size());
            pets.add(DataHelper.generateCustomPet(
                    DataHelper.extractIntegerFromObject(line[0]),
                    line[1],
                    line[2],
                    DataHelper.extractIntegerFromObject(line[3]),
                    line[4],
                    DataHelper.extractIntegerFromObject(line[5]),
                    line[6]));
        }
        return pets.iterator();
    }

    @Test(dataProvider = "pets", groups = {"smoke", "regression"})
    public void createPet(int petId, String petName, String status, int tagId, String tagName, int catId, String catName) {
        given()
                .contentType(ContentType.JSON)
                .body(DataHelper.generateCustomPet(petId, petName, status, tagId, tagName, catId, catName))
         .when()
                .post("/pet")
         .then()
                .statusCode(200);
    }

    @Test(dataProvider = "pets2")
//    @Ignore
    public void createPet2(Pet pet) {
        given()
                .spec(RequestSpecs.basicSpec)
                .body(pet)
        .when()
                .post("/pet")
        .then()
                .spec(ResponseSpecs.defaultChecker);
    }

    @Test(dependsOnMethods="createPet",  groups = {"regression"})
    public void updatePet() {
        given()
                .contentType(ContentType.JSON)
                .body(DataHelper.generateRandomPet())
        .when()
                .put("/pet")
        .then()
                .statusCode(200);
    }

    @Test(dependsOnMethods="createPet",groups = {"regression"})
    public void getPet() {
        Pet mypet =
            given()
                    .pathParam("petId", 910)
            .when()
                    .get("/pet/{petId}")
            .then()
                    .statusCode(200)
                    .log()
                    .all().extract().as(Pet.class);
        Assert.assertEquals(mypet.getName(), "bobitza");
        Assert.assertEquals(mypet, DataHelper.generateRandomPet());
    }

    @Test(dependsOnMethods="getPet", groups = {"regression"})
    public void deletePet() {
        given()
                .pathParam("petId", 900)
        .when()
                .delete("/pet/{petId}")
        .then()
                .statusCode(200);
    }
}
