package com.example

import io.dropwizard.auth.AuthFilter
import io.dropwizard.auth.Authenticator
import io.dropwizard.auth.PrincipalImpl
import jakarta.inject.Inject
import jakarta.ws.rs.WebApplicationException
import jakarta.ws.rs.container.ContainerRequestContext
import java.security.Principal
import java.util.*

class HeaderFilter @Inject constructor(
    authenticatorService: IAuthenticatorService
) : AuthFilter<String, Principal>() {

    private val authenticatorToUse = if (true) authenticatorService else Authenticator {
        if (it != null)
            Optional.of(PrincipalImpl("some-name"))
        else
            Optional.empty()
    }

    init {
        this.authenticator = authenticatorToUse
    }

    override fun filter(requestContext: ContainerRequestContext) {
        println("Running filter")

        val header = requestContext.getHeaderString("X-Auth")

        if (!authenticate(requestContext, header, "X-Auth-Header"))
            throw WebApplicationException(401)
    }

}

interface IAuthenticatorService : Authenticator<String, Principal>

class AuthenticatorService : IAuthenticatorService {

    override fun authenticate(credentials: String?): Optional<Principal> {
        println("Running authentication with credentials: $credentials")
        return if (credentials != null)
            Optional.of(PrincipalImpl("some-name"))
        else
            Optional.empty()
    }
}
