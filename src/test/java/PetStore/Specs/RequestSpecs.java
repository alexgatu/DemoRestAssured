package PetStore.Specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {

    public static RequestSpecification basicSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .build();

}
