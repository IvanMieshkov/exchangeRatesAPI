package com.exchange.currency.dataProviders.impl;

import com.exchange.currency.apiClient.ApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;

class PrivatbankImplTest {
    @Mock
    private ApiClient apiClient;
    @InjectMocks
    private PrivatbankImpl privatbank = new PrivatbankImpl(apiClient);
    private final String api = "privatTestApi";
    private final String provider = "privatbank";
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(privatbank, "api", api);
        ReflectionTestUtils.setField(privatbank, "provider", provider);
    }
    @Test
    public void testLoadData() throws Exception {
        privatbank.loadData();
        verify(apiClient).loadData(api, provider);
    }
}