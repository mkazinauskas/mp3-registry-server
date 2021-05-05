package register.domain.entry

import groovy.transform.CompileStatic
import org.apache.commons.codec.digest.DigestUtils
import org.hibernate.validator.constraints.NotBlank

import javax.persistence.*

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric

@Entity
@Table(name = 'entries')
@CompileStatic
class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = 'creation_date', nullable = false, length = 32)
    Date created = new Date()

    @Column(name = 'unique_id', nullable = false, unique = true, length = 32)
    String uniqueId = randomAlphanumeric(32)

    @Column(name = 'source_type', nullable = false, length = 50)
    @Enumerated(value = EnumType.STRING)
    SourceType sourceType

    @NotBlank
    @Column(name = 'checksum', nullable = false, unique = true, length = 32)
    String checksum

    @NotBlank
    @Column(name = 'value', nullable = false, columnDefinition = 'longtext')
    String value

    @PrePersist
    void prePersist() {
        checksum = calculateChecksum(value)
    }

    static String calculateChecksum(String value){
        return DigestUtils.md5Hex(value)
    }

}
