package domain;

public class Route {
    private String fromStation;
    private String toStation;
    private Integer cost;

    public Route(String fromStation, String toStation, Integer cost) {
        this.fromStation = fromStation;
        this.toStation = toStation;
        this.cost = cost;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Route{" +
                "fromStation='" + fromStation + '\'' +
                ", toStation='" + toStation + '\'' +
                ", cost=" + cost +
                '}';
    }
}
