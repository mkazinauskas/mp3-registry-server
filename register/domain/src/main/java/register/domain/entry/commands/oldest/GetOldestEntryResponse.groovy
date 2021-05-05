package register.domain.entry.commands.oldest

import register.domain.entry.Entry

class GetOldestEntryResponse {
    final Optional<Entry> entry

    GetOldestEntryResponse(Optional<Entry> entry) {
        this.entry = entry
    }
}
