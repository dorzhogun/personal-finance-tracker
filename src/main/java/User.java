import java.util.HashMap;

public class User {
    private String name;
    private String email;
    private String password;
    private boolean isBlocked;
    private boolean isAdmin;
    private HashMap<String, Transaction> transactions;
    private double budget;
    private double goal;
    private double balance;
    private double totalExpense;
    private double totalIncome;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.isBlocked = false;
        transactions = new HashMap<>();
    }

    public void updateProfile(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public double getBudget() { return budget; }
    public double getGoal() { return goal; }
    public boolean isBlocked() { return isBlocked; }
    public boolean isAdmin() { return isAdmin; }
    public HashMap<String, Transaction> getTransactions() { return transactions; }
    public void setIsBlocked(boolean isBlocked) { this.isBlocked = isBlocked; }
    public void setIsAdmin(Boolean isAdmin) { this.isAdmin = isAdmin; }
    public void setBudget(Double budget) { this.budget = budget;}
    public void setGoal(Double goal) { this.goal = goal; }
}
