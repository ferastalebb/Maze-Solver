public class Car {
    private String make = "Tesla";
    private String model = "Model X";
    private String color = "grey";
    private int doors = 2 ;
    private boolean convertible = true;

    public void describeCar() {
        System.out.println(doors + "-door "+
                color + " "+
                make + " " +
                model + " " +
                (convertible ? "Convertible" :""));
    }
}
