package es.udc.paproject.backend.model.exceptions;
@SuppressWarnings("serial")
public class InvalidCardException extends Exception{
    private int cardNumber;

    public InvalidCardException(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }
}
