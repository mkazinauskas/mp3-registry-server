package register.api.entries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import register.api.entries.jamendo.xml.JamendoXmlEntriesController;
import register.domain.entry.Entries;
import register.domain.entry.Entry;

import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class EntriesController {

    @Autowired
    private Entries allEntries;

    @RequestMapping(method = RequestMethod.GET, value = "register/entries")
    public ResponseEntity<Resource<Page<EntryBean>>> getEntries(Pageable pageable) {
        Page<Entry> entries = this.allEntries.findAll(pageable);

        Resource<Page<EntryBean>> response = new Resource<>(entries.map(EntryBean::new),
                linkTo(methodOn(EntriesController.class).getEntries(null)).withSelfRel(),
                linkTo(methodOn(JamendoXmlEntriesController.class)
                        .createJamendoXmlEntry(null))
                        .withRel("jamendo-xml")
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "register/entries/{uniqueId}")
    public ResponseEntity<Resource<EntryBean>> getEntry(@PathVariable("uniqueId") String uniqueId) {
        Optional<Entry> entry = this.allEntries.findByUniqueId(uniqueId);

        Resource<EntryBean> response = new Resource<>(entry.map(EntryBean::new)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Entry with unique id = `%s` was not found", uniqueId))
                ),
                linkTo(methodOn(EntriesController.class).getEntry("")).withSelfRel()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "register/entries/later")
    public ResponseEntity<Resource<EntryBean>> getLaterEntry(@RequestParam("uniqueId") String uniqueId) {
        Entry existingEntry = this.allEntries.findByUniqueId(uniqueId)
                .orElseThrow(() -> new RuntimeException(String.format("Entry not found `%s`.", uniqueId)));

        Pageable first = new PageRequest(0, 1, Sort.Direction.ASC, "id");

        Entry entry = this.allEntries.findLaterEntry(existingEntry.getId(), first).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Later entry not found"));

        Resource<EntryBean> response = new Resource<>(new EntryBean(entry),
                linkTo(methodOn(EntriesController.class).getEntry("")).withSelfRel()
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
