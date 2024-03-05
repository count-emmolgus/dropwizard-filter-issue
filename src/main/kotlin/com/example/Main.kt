package com.example

import io.dropwizard.auth.AuthDynamicFeature
import io.dropwizard.auth.AuthValueFactoryProvider
import io.dropwizard.auth.PrincipalImpl
import io.dropwizard.core.Application
import io.dropwizard.core.Configuration
import io.dropwizard.core.setup.Environment
import jakarta.inject.Singleton
import org.glassfish.hk2.utilities.binding.AbstractBinder
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        DropwizardApp().run(*args)
    }
}

class DropwizardApp : Application<Configuration>() {

    override fun run(configuration: Configuration, environment: Environment) {
        val helloWorldResource = Resource::class.java
        val headerFilter = HeaderFilter::class.java

        with(environment.jersey()) {

            register(helloWorldResource)

            register(AuthDynamicFeature(headerFilter))
            register(AuthValueFactoryProvider.Binder(PrincipalImpl::class.java))

            register(RolesAllowedDynamicFeature::class.java)

            val binds = object : AbstractBinder() {
                override fun configure() {
                    this.bind(AuthenticatorService::class.java).to(IAuthenticatorService::class.java)
                        .`in`(Singleton::class.java)
                }
            }

            register(binds)
        }
    }

}