package com.sesc.mystudentportal.service;

import com.sesc.mystudentportal.YamlSourceFactory;
import com.sesc.mystudentportal.model.Account;
import com.sesc.mystudentportal.model.Invoice;
import com.sesc.mystudentportal.model.UserDtls;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@PropertySource(value = "classpath:integrations.yml", factory = YamlSourceFactory.class)
public class IntegrationService {


    private final RestTemplate restTemplate;
    @Value("${finance.host}")
    private String financeHost;
    @Value("${finance.student.create}")
    private String studentCreatedFinanceSubscriber;
    @Value("${library.host}")
    private String libraryHost;
    @Value("${library.student.create}")
    private String studentCreatedLibrarySubscriber;
    @Value("${finance.course.enrol}")
    private String courseEnrolmentFinanceSubscriber;
    @Value("${finance.account.status}")
    private String accountStatusPublisher;

    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void notifyStudentCreated(Account account) {
        restTemplate.postForObject(financeHost + studentCreatedFinanceSubscriber, account, Account.class);
        restTemplate.postForObject(libraryHost + studentCreatedLibrarySubscriber, account, Account.class);
    }

    public Invoice createCourseFeeInvoice(Invoice invoice) {
        return restTemplate.postForObject(financeHost + courseEnrolmentFinanceSubscriber, invoice, Invoice.class);
    }

    public Account getStudentPaymentStatus( UserDtls userDtls) {
        return restTemplate.getForObject(financeHost + accountStatusPublisher + userDtls.getCnumber(), Account.class);
    }
}
