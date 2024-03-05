package com.example

import io.dropwizard.testing.ResourceHelpers
import io.dropwizard.testing.junit5.DropwizardAppExtension
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import org.junit.jupiter.api.Test

class ResourceTest {

    companion object {
        private val DROPWIZARD = dropwizard
    }

    @Test
    fun `shall not be able to make auth-required request unauthenticated`() {
        val response = RestAssured.given()
            .get("auth-required")
            .then()

        response.statusCode(401)
    }

    @Test
    fun `shall be able to make auth-required request authenticated`() {
        val response = RestAssured.given()
            .header("X-Auth", "some-value")
            .get("auth-required")
            .then()

        response.statusCode(200)
    }

    @Test
    fun `shall be able to make no-auth request`() {
        val response = RestAssured.given()
            .get("no-auth")
            .then()

        response.statusCode(200)
    }

}


private val dropwizard = DropwizardAppExtension(
    DropwizardApp::class.java,
    ResourceHelpers.resourceFilePath("config.yaml")
).also {
    it.before()
    RestAssured.port = it.localPort
    RestAssured.requestSpecification = RequestSpecBuilder().addHeader("Origin", "http://localhost:9490").build()
}