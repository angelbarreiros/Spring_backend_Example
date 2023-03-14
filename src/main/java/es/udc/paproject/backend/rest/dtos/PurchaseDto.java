package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PurchaseDto {
    private Long id;
    private int transactionNumber;
    private int numberOfTickets;

    public PurchaseDto(){}

    public PurchaseDto(Long id, int transactionNumber, int numberOfTickets, Long sessionId) {
        this.id = id;
        this.transactionNumber = transactionNumber;
        this.numberOfTickets = numberOfTickets;
    }

    public PurchaseDto(Long id, int transactionNumber, int numberOfTickets) {
        this.id = id;
        this.transactionNumber = transactionNumber;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @NotNull
    @Positive
    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }
    @NotNull
    @Min(1)
    @Max(10)
    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }


}
