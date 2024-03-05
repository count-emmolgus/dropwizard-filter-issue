package com.example

import jakarta.annotation.security.PermitAll
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType

@Path("")
@Produces(MediaType.TEXT_PLAIN)
class Resource {

    @PermitAll
    @GET
    @Path("auth-required")
    fun helloWorld(): String = "Hello, world!"

    @GET
    @Path("no-auth")
    fun helloWorld2(): String = "Hello, world2!"
}