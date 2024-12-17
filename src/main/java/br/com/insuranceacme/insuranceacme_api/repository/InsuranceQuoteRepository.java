package br.com.insuranceacme.insuranceacme_api.repository;

import br.com.insuranceacme.insuranceacme_api.domain.document.InsuranceQuoteDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceQuoteRepository extends MongoRepository<InsuranceQuoteDocument, Long> {
}
