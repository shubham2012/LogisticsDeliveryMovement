package domain;

public class Train {
    private String name;
    private String currentStation;
    private Integer capacity;
    private Integer availableCapacity;
    private boolean moving;

    public Train(String name, String currentStation, Integer capacity) {
        this.name = name;
        this.currentStation = currentStation;
        this.capacity = capacity;
        this.availableCapacity = capacity;
        this.moving = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentStation() {
        return currentStation;
    }

    public void setCurrentStation(String currentStation) {
        this.currentStation = currentStation;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(Integer availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    @Override
    public String toString() {
        return "Train{" +
                "name='" + name + '\'' +
                ", currentStation='" + currentStation + '\'' +
                ", capacity=" + capacity +
                ", availableCapacity=" + availableCapacity +
                ", moving=" + moving +
                '}';
    }
}
