package es.udc.paproject.backend.rest.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class TicketDeliveryDto {
    private Long id;
    private int transactionNumber;
    private Long purchaseId;

    public TicketDeliveryDto() {}

    public TicketDeliveryDto(Long id, int transactionNumber, Long purchaseId) {
        this.id = id;
        this.transactionNumber = transactionNumber;
        this.purchaseId = purchaseId;
    }

    public Long getId() {return id;}
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
    @Positive
    @NotNull

    public Long getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
    }
}
