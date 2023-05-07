package com.sesc.mystudentportal.service;

import com.sesc.mystudentportal.model.Account;
import com.sesc.mystudentportal.model.Invoice;
import com.sesc.mystudentportal.model.UserDtls;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IntegrationServiceTest {

    private IntegrationService integrationService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        integrationService = new IntegrationService(restTemplate);
    }

    @Test
    public void testNotifyStudentCreated() {
        // Mock the REST template's postForObject() method
        Account account = new Account();
        String financeHost = "http://finance-service/";
        String libraryHost = "http://library-service/";
        String studentCreatedFinanceSubscriber = "/student/create";
        String studentCreatedLibrarySubscriber = "/student/create";

        doNothing().when(restTemplate).postForObject(eq(financeHost + studentCreatedFinanceSubscriber), eq(account), eq(Account.class));
        doNothing().when(restTemplate).postForObject(eq(libraryHost + studentCreatedLibrarySubscriber), eq(account), eq(Account.class));

        // Call the method under test
        integrationService.notifyStudentCreated(account);

        // Verify that the REST template's postForObject() method was called with the correct arguments
        verify(restTemplate, times(1)).postForObject(financeHost + studentCreatedFinanceSubscriber, account, Account.class);
        verify(restTemplate, times(1)).postForObject(libraryHost + studentCreatedLibrarySubscriber, account, Account.class);
    }

    @Test
    public void testCreateCourseFeeInvoice() {
        // Mock the REST template's postForObject() method
        Invoice invoice = new Invoice();
        String financeHost = "http://finance-service/";
        String courseEnrolmentFinanceSubscriber = "/course/enrol";
        Invoice expectedInvoice = new Invoice();

        when(restTemplate.postForObject(eq(financeHost + courseEnrolmentFinanceSubscriber), eq(invoice), eq(Invoice.class))).thenReturn(expectedInvoice);

        // Call the method under test
        Invoice actualInvoice = integrationService.createCourseFeeInvoice(invoice);

        // Verify that the REST template's postForObject() method was called with the correct arguments
        verify(restTemplate, times(1)).postForObject(financeHost + courseEnrolmentFinanceSubscriber, invoice, Invoice.class);
        assertEquals(expectedInvoice, actualInvoice);
    }

    @Test
    public void testGetStudentPaymentStatus() {
        // Mock the REST template's getForObject() method
        UserDtls userDtls = new UserDtls();
        String financeHost = "http://finance-service/";
        String accountStatusPublisher = "/account/status/{cnumber}";
        Account expectedAccount = new Account();

        when(restTemplate.getForObject(eq(financeHost + accountStatusPublisher + userDtls.getCnumber()), eq(Account.class))).thenReturn(expectedAccount);

        // Call the method under test
        Account actualAccount = integrationService.getStudentPaymentStatus(userDtls);

        // Verify that the REST template's getForObject() method was called with the correct arguments
        verify(restTemplate, times(1)).getForObject(financeHost + accountStatusPublisher + userDtls.getCnumber(), Account.class);
        assertEquals(expectedAccount, actualAccount);
    }
}
