package register.domain.entry

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param

interface Entries extends PagingAndSortingRepository<Entry, Long> {

    @Query('SELECT e FROM Entry e where e.uniqueId = :uniqueId')
    Optional<Entry> findByUniqueId(@Param('uniqueId') String uniqueId)

    @Query('SELECT e FROM Entry e where e.id > :id')
    List<Entry> findLaterEntry(@Param('id') long id, Pageable pageable)

    Optional<Entry> findByChecksum(String checksum);

}
