package search.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import search.domain.entry.commands.CreateEntryHandler;
import search.domain.processed.Processed;
import search.domain.processed.ProcessedRepository;
import search.domain.processed.commands.CreateProcessed;
import search.domain.processed.commands.CreateProcessedHandler;
import search.register.RegisterEntryResponse;
import search.register.RegisterResource;

@Component
public class ScheduledTasks {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledTasks.class);

    private RegisterResource registerResource;

    private CreateEntryHandler createEntryHandler;

    private CreateEntryMapper mapper;

    private CreateProcessedHandler createProcessedHandler;

    private ProcessedRepository processedRepository;

    @Autowired
    public ScheduledTasks(RegisterResource registerResource,
                          CreateEntryHandler createEntryHandler,
                          CreateEntryMapper mapper,
                          CreateProcessedHandler createProcessedHandler,
                          ProcessedRepository processedRepository) {
        this.registerResource = registerResource;
        this.createEntryHandler = createEntryHandler;
        this.mapper = mapper;
        this.createProcessedHandler = createProcessedHandler;
        this.processedRepository = processedRepository;
    }

    @Scheduled(fixedDelay = 1000)
    public void populateEntries() {
        Pageable topTen = new PageRequest(0, 1, Sort.Direction.DESC, "created");

        Page<Processed> latest = processedRepository.findAll(topTen);
        if (latest.hasContent()) {
            latest.getContent()
                    .stream()
                    .map(Processed::getUniqueId)
                    .findFirst().ifPresent(lastEntryUniqueId -> {
                        LOG.info("Last entry unique id: {}", lastEntryUniqueId);
                        try {
                            RegisterEntryResponse registerEntryResponse = registerResource.latest(lastEntryUniqueId);
                            if (registerEntryResponse.getValue() == null) {
                                throw new IllegalArgumentException("Register entry value is null for id =" + registerEntryResponse.getUniqueId());
                            }
                            createProcessedHandler.handle(new CreateProcessed(registerEntryResponse.getUniqueId()));
                            LOG.info("New entry unique id: {}", registerEntryResponse.getUniqueId());
                            createEntryHandler.handle(mapper.map(registerEntryResponse));
                        } catch (HttpClientErrorException ex) {
                            handleException(ex);
                        }
                    }
            );
        } else {
            try {
                RegisterEntryResponse registerEntryResponse = registerResource.first();
                if (registerEntryResponse.getValue() == null) {
                    throw new IllegalArgumentException("Register entry value is null for id =" + registerEntryResponse.getUniqueId());
                }
                LOG.info("New item: {}", registerEntryResponse.getUniqueId());

                createProcessedHandler.handle(new CreateProcessed(registerEntryResponse.getUniqueId()));

                createEntryHandler.handle(mapper.map(registerEntryResponse));
            } catch (HttpClientErrorException ex) {
                handleException(ex);
            }
        }

    }

    private void handleException(HttpClientErrorException ex) {
        if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
            LOG.warn("No new items to fetch");
        } else {
            throw ex;
        }
    }
}
