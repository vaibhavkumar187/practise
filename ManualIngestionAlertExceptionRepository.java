package com.sg.dprame.brg.manualingestionalerts;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "manual_ingestion_alert_exception")
public class ManualIngestionAlertException {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "record_stream_id", nullable = false)
    private UUID recordStreamId;

    @Column(name = "record_id", nullable = false)
    private UUID recordId;

    @Column(name = "due_date", nullable = false)
    private int dueDate;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "reason")
    private String reason;

    @Column(name = "date_of_attestation")
    private String dateOfAttestation;

    // Getters and Setters

    // Builder or constructor can be added here
}


package com.sg.dprame.brg.manualingestionalerts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManualIngestionAlertExceptionRepository extends JpaRepository<ManualIngestionAlertException, UUID> {

    Optional<ManualIngestionAlertException> findByRecordStreamIdAndRecordIdAndDueDate(UUID recordStreamId, UUID recordId, int dueDate);

    List<ManualIngestionAlertException> findByDueDateIn(List<Integer> dueDateRange);
}



package com.sg.dprame.brg.manualingestionalerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ManualIngestionAlertExceptionService {

    @Autowired
    private ManualIngestionAlertExceptionRepository repository;

    public Optional<ManualIngestionAlertException> manualIngestionAlertExceptionFor(UUID recordStreamId, UUID recordId, int dueDate) {
        return repository.findByRecordStreamIdAndRecordIdAndDueDate(recordStreamId, recordId, dueDate);
    }

    public ManualIngestionAlertException save(ManualIngestionAlertException manualIngestionAlertException) {
        return repository.save(manualIngestionAlertException);
    }

    public Optional<List<ManualIngestionAlertException>> attestationReports(List<Integer> dateYYYYMMDDRange) {
        List<ManualIngestionAlertException> results = repository.findByDueDateIn(dateYYYYMMDDRange);
        return results.isEmpty() ? Optional.empty() : Optional.of(results);
    }
}
