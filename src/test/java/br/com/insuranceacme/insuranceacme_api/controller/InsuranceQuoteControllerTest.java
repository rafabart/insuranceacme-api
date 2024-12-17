package br.com.insuranceacme.insuranceacme_api.controller;

import br.com.insuranceacme.insuranceacme_api.domain.InsuranceQuoteRequestMock;
import br.com.insuranceacme.insuranceacme_api.domain.InsuranceQuoteResponseMock;
import br.com.insuranceacme.insuranceacme_api.domain.request.InsuranceQuoteRequest;
import br.com.insuranceacme.insuranceacme_api.domain.response.InsuranceQuoteResponse;
import br.com.insuranceacme.insuranceacme_api.service.InsuranceQuoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(InsuranceQuoteController.class)
public class InsuranceQuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InsuranceQuoteService service;


    @Test
    public void createWithSuccess() throws Exception {
        final InsuranceQuoteRequest request = InsuranceQuoteRequestMock.build();
        final Long id = 123456L;

        final ObjectMapper objectMapper = new ObjectMapper();
        final String requestJson = objectMapper.writeValueAsString(request);

        when(service.create(request)).thenReturn(id);

        mockMvc.perform(post("/Insurancequotes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(header().string("Location", "http://localhost/Insurancequotes/123456"))
                .andExpect(status().isCreated());
    }

    @Test
    public void findByIdWithSuccess() throws Exception {
        final InsuranceQuoteResponse response = InsuranceQuoteResponseMock.build();
        final Long id = response.getId();

        when(service.findById(id)).thenReturn(response);

        mockMvc.perform(get("/Insurancequotes/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_id").value("1b2da7cc-b367-4196-8a78-9cfeec21f587"))
                .andExpect(jsonPath("$.offer_id").value("adc56d77-348c-4bf0-908f-22d402ee715c"));
    }

}
