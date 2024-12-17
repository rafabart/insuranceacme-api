package br.com.insuranceacme.insuranceacme_api.service;

import br.com.insuranceacme.insuranceacme_api.domain.InsuranceQuoteDocumentMock;
import br.com.insuranceacme.insuranceacme_api.domain.InsuranceQuoteEventMock;
import br.com.insuranceacme.insuranceacme_api.domain.InsuranceQuoteRequestMock;
import br.com.insuranceacme.insuranceacme_api.domain.InsuranceQuoteResponseMock;
import br.com.insuranceacme.insuranceacme_api.domain.document.InsuranceQuoteDocument;
import br.com.insuranceacme.insuranceacme_api.domain.event.InsuranceQuoteEvent;
import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;
import br.com.insuranceacme.insuranceacme_api.domain.response.InsuranceQuoteResponse;
import br.com.insuranceacme.insuranceacme_api.exception.ObjectNotFoundException;
import br.com.insuranceacme.insuranceacme_api.mapper.InsuranceQuoteMapper;
import br.com.insuranceacme.insuranceacme_api.repository.InsuranceQuoteRepository;
import br.com.insuranceacme.insuranceacme_api.service.impl.InsuranceQuoteServiceImpl;
import br.com.insuranceacme.insuranceacme_api.stream.InsuranceQuoteProducer;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class InsuranceQuoteServiceImplTest {

    @Mock
    private InsuranceQuoteRepository repository;

    @Mock
    private InsuranceValidationService validationService;

    @Mock
    private InsuranceQuoteProducer producer;

    @Mock
    private InsuranceQuoteMapper mapper;

    @InjectMocks
    private InsuranceQuoteServiceImpl service;


    @Test
    public void createWithSuccess() {

        final InsuranceQuoteRequest request = InsuranceQuoteRequestMock.build();
        final InsuranceQuoteDocument document = InsuranceQuoteDocumentMock.build(request);
        final InsuranceQuoteEvent event = InsuranceQuoteEventMock.build(document);

        when(mapper.to(request)).thenReturn(document);
        when(mapper.toEvent(document)).thenReturn(event);
        when(repository.save(document)).thenReturn(document);
        final Long id = service.create(request);

        verify(mapper, times(1)).to(request);
        verify(mapper, times(1)).toEvent(document);
        verify(validationService, times(1)).validate(request);
        verify(repository, times(1)).save(document);
        verify(producer, times(1)).send(event);

        assertNotNull(id);
        assertEquals(document.getId(), id);
    }

    @Test
    public void findByIdWithSuccess() {

        final Long id = 123456L;
        final InsuranceQuoteDocument document = InsuranceQuoteDocumentMock.build();
        final InsuranceQuoteResponse response = InsuranceQuoteResponseMock.build(document);

        when(mapper.to(document)).thenReturn(response);
        when(repository.findById(id)).thenReturn(Optional.of(document));
        final InsuranceQuoteResponse result = service.findById(id);

        verify(mapper, times(1)).to(document);
        verify(repository, times(1)).findById(id);

        assertEquals(response, result);
    }

    @Test
    public void findByIdWithException() {
        final Long id = 123456L;

        when(repository.findById(id)).thenThrow(new ObjectNotFoundException("Cotação"));
        assertThrows(RuntimeException.class, () -> {
            service.findById(id);
        });

        verify(repository, times(1)).findById(id);
    }
}