package search.domain.processed.commands

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import search.domain.processed.Processed
import search.domain.processed.ProcessedRepository

@Component
class CreateProcessedHandler {
    @Autowired
    private ProcessedRepository repository

    @Transactional
    String handle(CreateProcessed createProcessed) {
        Processed savedEntry = repository.save(new Processed(
                uniqueId: createProcessed.uniqueId
        ))
        return savedEntry.uniqueId
    }
}
