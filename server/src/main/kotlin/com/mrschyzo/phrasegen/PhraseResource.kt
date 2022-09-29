package com.mrschyzo.phrasegen

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.mrschyzo.phrasegen.Phrase.TextOnly
import com.mrschyzo.phrasegen.Phrase.TextWithAudio
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Summary", description = "Description", operationId = "Test")
    @APIResponse(content = [
        Content(schema = Schema(
            oneOf = [TextOnly::class, TextWithAudio::class],
            discriminatorProperty = "type"
        ))
    ])
    fun phraseById(@PathParam(Parameters.ID) id: Id): Phrase = if (Random.nextBoolean())
        TextOnly(id = id, text = "Succ")
    else
        TextWithAudio(id = id, text = "Secc", speechLocation = "https://google.com")

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
    data class TextWithAudio(val id: Id, val text: String, val speechLocation: String): Phrase()
}

data class Some(
    val x: Int,
    val y: Else,
)

data class Else(
    val z: String?,
    val w: Optional<Int>
)
