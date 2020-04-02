package domain;

public class MovementRequest {

    private String delivery;
    private String from;
    private String to;
    private Integer wight;


    public MovementRequest(String delivery, String from, String to, Integer wight) {
        this.delivery = delivery;
        this.from = from;
        this.to = to;
        this.wight = wight;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Integer getWight() {
        return wight;
    }

    public void setWight(Integer wight) {
        this.wight = wight;
    }

    @Override
    public String toString() {
        return "MovementRequest{" +
                "delivery='" + delivery + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", wight=" + wight +
                '}';
    }
}
