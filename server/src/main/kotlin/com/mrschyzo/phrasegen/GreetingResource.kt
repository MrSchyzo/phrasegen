package com.mrschyzo.phrasegen

import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import java.util.Optional
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("/hello")
/**
 * This is a microservice controller
 */
class GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Summary", description = "Description", operationId = "Test")
    @APIResponse(description = "Something", responseCode = "500",)
    /**
     * Let me try something
     */
    fun hello() = Some(
        x = 5,
        y = Else(
            z = "some",
            w = Optional.empty()
        )
    )
}

data class Some(
    val x: Int,
    val y: Else,
)

data class Else(
    val z: String?,
    val w: Optional<Int>
)
