import com.azure.core.amqp.AmqpRetryOptions
import com.azure.messaging.servicebus.*
//import com.fasterxml.jackson.annotation.JsonTypeInfo
//import com.fasterxml.jackson.annotation.JsonTypeName
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Duration
import java.time.ZonedDateTime

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
abstract class Event<T>(open val metadata: EventMetadata, open val payload: T)
data class EventMetadata(
        val ebid: String?,
        val loggedInUser: LoggedInUser,
//        val createdAt: ZonedDateTime,
        val businessCaseId: String
)

data class LoggedInUser(
        val partnerId: String?,
        val partnerNr: String?,
        val portalAccountId: String?
)

//@JsonTypeName("PendenzClosedEvent")
data class PendenzClosedEvent(
        override val metadata: EventMetadata,
        override val payload: PendenzClosedEventPayload
) : Event<PendenzClosedEventPayload>(metadata, payload)

data class PendenzClosedEventPayload(val portalKontoId: String, val pendenzId: String)

class ServicebusClient(private  val objectMapper: ObjectMapper?) {

    companion object {
        const val EVENT_TYPE = "eventType"

        const val CONNECTION_STRING: String =
            "todo"
        const val QUEUE_NAME: String = "localdev-pendenzen"

        val SENDER_RETRY_OPTIONS = AmqpRetryOptions().apply {
            tryTimeout = Duration.ofSeconds(10)
            maxRetries = 0
        }
    }

    private lateinit var outTopicSenderClient: ServiceBusSenderClient


    // initializer block
    init {
        val outTopic = "localdev-pendenzen"
        val senderClientBuilder = ServiceBusClientBuilder().connectionString(CONNECTION_STRING)
            .retryOptions(SENDER_RETRY_OPTIONS)
        outTopicSenderClient = senderClientBuilder
            .sender()
            .topicName(outTopic)
            .buildClient()
    }

    fun sendToOutTopic(event: PendenzClosedEvent) {
        val message = ServiceBusMessage(objectMapper?.writeValueAsBytes(event))
        message.applicationProperties[EVENT_TYPE] = event.javaClass.simpleName
        message.sessionId = event.metadata.businessCaseId
        message.correlationId = event.metadata.businessCaseId
        outTopicSenderClient.sendMessage(message)
    }
}
