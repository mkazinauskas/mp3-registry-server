package search.scheduled

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import search.domain.entry.commands.CreateEntry
import search.domain.entry.commands.CreateEntryHandler
import search.domain.processed.Processed
import search.domain.processed.ProcessedRepository
import search.domain.processed.commands.CreateProcessedHandler
import search.register.RegisterEntryResponse
import search.register.RegisterResource
import spock.lang.Specification

class ScheduledTasksSpec extends Specification {

    private RegisterResource registerResource = Mock()

    private CreateEntryHandler createEntryHandler = Mock()

    private CreateEntryMapper mapper = Mock()

    private CreateProcessedHandler createProcessedHandler = Mock()

    private ProcessedRepository processedRepository = Mock()

    private ScheduledTasks testTarget = new ScheduledTasks(
            registerResource,
            createEntryHandler,
            mapper,
            createProcessedHandler,
            processedRepository
    )

    def 'should populate data for first time'() {
        given:
            processedRepository.findAll(_ as Pageable) >> new PageImpl<Processed>([])
        and:
            registerResource.first() >> new RegisterEntryResponse(
                    uniqueId: 'uniqueId',
                    value: 'value'
            )
        and:
            mapper.map(_ as RegisterEntryResponse) >> new CreateEntry()
        when:
            testTarget.populateEntries()
        then:
            1 * createProcessedHandler.handle({ it.uniqueId == 'uniqueId' })
            1 * createEntryHandler.handle(_ as CreateEntry)
            0 * registerResource.latest()
    }
}
