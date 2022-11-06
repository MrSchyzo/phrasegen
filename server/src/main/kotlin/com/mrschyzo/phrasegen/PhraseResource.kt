package com.mrschyzo.phrasegen

import com.mrschyzo.phrasegen.Phrase.TextOnly
import com.mrschyzo.phrasegen.Phrase.WithAudio
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import java.util.Optional
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.Schema
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import kotlin.random.Random

@Path("/phrase")
class PhraseResource {

    @GET
    @Path("/{${Parameters.ID}}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Summary", description = "Description", operationId = "Test")
    @APIResponse(content = [
        Content(schema = Schema(
            oneOf = [TextOnly::class, WithAudio::class],
            discriminatorProperty = "type"
        ))
    ])
    fun phraseById(@PathParam(Parameters.ID) id: Id): Phrase = if (Random.nextBoolean())
        TextOnly(id = id, text = "Succ")
    else
        WithAudio(id = id, text = "Secc", speechLocation = "https://google.com")

    class Parameters {
        companion object {
            const val ID = "id"
        }
    }
}

typealias Id = Long

sealed class Phrase {
    val type = this.javaClass.simpleName

    data class TextOnly(val id: Id, val text: String): Phrase()
    data class WithAudio(val id: Id, val text: String, val speechLocation: String): Phrase()
}
