public class Transaction {
    private String type; // income or expense
    private double sum;
    private String category;
    private String description;
    private String date;

    public Transaction(String type, double sum, String category, String description, String date) {
        this.type = type;
        this.sum = sum;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", sum=" + sum +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }

    public void updateTransaction(double sum, String category, String description, String date) {
        this.sum = sum;
        this.category = category;
        this.description = description;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public double getSum() {
        return sum;
    }
}
