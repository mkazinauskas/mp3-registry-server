package search.api.entries

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.AsyncResult
import org.springframework.stereotype.Component
import search.domain.searches.commands.CreateSearchQuery
import search.domain.searches.commands.CreateSearchQueryHandler

import java.util.concurrent.Future

@Component
class AsyncSearchQueryHandler {

    @Autowired
    private CreateSearchQueryHandler searchQueriesHandler;

    @Async
    Future<String> save(String query) {
        return new AsyncResult<String>(searchQueriesHandler.handle(new CreateSearchQuery(query)))
    }
}
