package register.api.entries.jamendo.xml;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import register.api.entries.EntriesController;
import register.domain.entry.commands.jamendo.xml.SaveJamendoXmlEntry;
import register.domain.entry.commands.jamendo.xml.SaveJamendoXmlEntryHandler;
import register.domain.entry.commands.jamendo.xml.SaveJamendoXmlEntryResponse;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class JamendoXmlEntriesController {

    @Autowired
    private SaveJamendoXmlEntryHandler createJamendoXmlEntryHandler;

    @RequestMapping(method = RequestMethod.POST, value = "register/entries/jamendo-xml")
    public ResponseEntity<String> createJamendoXmlEntry(
            @RequestBody @Valid CreateJamendoXmlEntries createJamendoXmlEntries) {
        SaveJamendoXmlEntry command =
                new SaveJamendoXmlEntry(createJamendoXmlEntries.getXmlResponse());

        SaveJamendoXmlEntryResponse result = createJamendoXmlEntryHandler.handle(command);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(EntriesController.class)
                .slash("register")
                .slash("entries")
                .slash(result.getUniqueId()).toUri());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
