public class Pen extends Product{
    private String color;

    public Pen(int code, int price, String color)
    {
        super(code, price);
        this.color = color;
    }

    @Override
    public String getDescription()
    {
        return "Pen. Color: " + color;
    }
}
