package com.dnp.bank.scheduled;

import com.dnp.bank.entities.Client;
import com.dnp.bank.entities.SpecificClientsProduct;
import com.dnp.bank.exceptions.InvalidRateException;
import com.dnp.bank.services.ClientService;
import com.dnp.bank.services.SpecificClientsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
public class MidnightEvaluationFee {

    @Autowired
    private SpecificClientsProductService specificClientsProductService;

    @Autowired
    private ClientService clientService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void evaluateFees() {
        List<SpecificClientsProduct> products = specificClientsProductService.getAllSpecificClientsProducts();

        for (SpecificClientsProduct product : products) {
            LocalDate contractDate = product.getClientsContractDate();
            LocalDate currentDate = LocalDate.now();

            boolean isDue = false;

            if ("DAY".equals(product.getPayRate().getUnit())) {
                long daysElapsed = ChronoUnit.DAYS.between(contractDate, currentDate);
                if (daysElapsed % product.getPayRate().getValue() == 0) {
                    isDue = true;
                }
            } else if ("MONTH".equals(product.getPayRate().getUnit())) {
                long monthsElapsed = ChronoUnit.MONTHS.between(contractDate, currentDate);
                if (monthsElapsed % product.getPayRate().getValue() == 0) {
                    isDue = true;
                }
            }
            if (isDue) {
                BigDecimal fee = calculateFee(product);
                Optional<Client> optionalClient = clientService.getClientById(product.getClientsId());
                if (optionalClient.isPresent()) {
                    Client client = optionalClient.get();
                    deductFee(client, fee);
                } else {
                    throw new InvalidRateException("Klient nenalezen!");
                }
            }
        }
    }
    private BigDecimal calculateFee(SpecificClientsProduct product) {
        if ("ACCOUNT".equals(product.getType())) {
            return product.getClientsRate();
        } else if ("LOAN".equals(product.getType())) {
            BigDecimal loanAmount = product.getOriginalAmount();
            BigDecimal fixedPayment = product.getClientsRate();
            BigDecimal interest = loanAmount.multiply(product.getClientsRate()).divide(new BigDecimal(product.getTotalInstallments()), RoundingMode.HALF_UP);
            return fixedPayment.add(interest);
        }
        return BigDecimal.ZERO;
    }
    private void deductFee(Client client, BigDecimal fee) {
        client.deductFromAccount(fee);
        clientService.saveClient(client);
    }
}
