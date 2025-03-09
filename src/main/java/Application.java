import java.time.LocalDate;
import java.util.*;

public class Application {
    private HashMap<String, User> users = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);
    private User currentUser;

    public void start() {
        User admin = new User("admin", "admin@email", "111");
        admin.setIsAdmin(true);
        users.put("admin@email", admin);
        while (true) {
            System.out.println("Выберите действие: 1. Вход 2. Регистрация 3. Выход");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    login();
                case 2:
                    register();
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Неверная команда. Выберите 1, 2 или 3");
            }
        }
    }

        // Метод входа в систему по email и password
        private void login() {
            System.out.print("Введите email: ");
            String email = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();

            User user = users.get(email);
            if (user != null && user.getPassword().equals(password)) {
                currentUser = user;
                if (currentUser.isAdmin()) {
                    System.out.println("Welcome admin " + currentUser.getName() + " !");
                    adminMenu();
                } else {
                    System.out.println("Welcome user " + currentUser.getName() + " !");
                    userMenu();
                }
            } else {
                System.out.println("Неверный email или пароль.");
            }
        }

        // Метод регистрации и проверки существования в БД пользователя по email и password
        private void register() {
            System.out.print("Введите email: ");
            String email = scanner.nextLine();
            System.out.print("Введите имя: ");
            String name = scanner.nextLine();
            System.out.print("Введите пароль: ");
            String password = scanner.nextLine();

            users.put(email, new User(name, email, password));
            System.out.println("Регистрация успешна.");
        }

    // Меню команд пользователя
    private void userMenu() {
        while (true) {
            System.out.println("Выберите действие: 1. Обновить профиль 2. Удалить аккаунт 3. Управление финансами 4. Выход из меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    updateProfile();
                    break;
                case 2:
                    deleteMyAccount();
                    return;
                case 3:
                    financeManagement();
                    break;
                case 4:
                    System.out.println("Вы покинули меню пользователя");
                    return;
                default:
                    System.out.println("Такого действия нет в перечне");
            }
        }
    }

    // Меню команд администратора
    private void adminMenu() {
        while (true) {
            System.out.println("Выберите действие: 1. Посмотреть всех пользователей и их транзакции 2. Удалить пользователя " +
                    "3. Блокировка пользователя по email 4. Выход из меню");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    deleteUser();
                    break;
                case 3:
                    blockUser();
                    break;
                case 4:
                    System.out.println("Вы покинули меню администратора!");
                    return;
                default:
                    System.out.println("Такого действия нет в перечне!");
            }
        }
    }

    // метод обновления профиля для пользователя
    private void updateProfile() {
        System.out.print("Введите новое имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите новый email: ");
        String email = scanner.nextLine();
        System.out.print("Введите новый пароль: ");
        String password = scanner.nextLine();
        currentUser.updateProfile(name, email, password);
        users.put(email, currentUser);
        System.out.println("Профиль обновлён.");
    }

    // Меню управления финансами
    private void financeManagement() {
        while (true) {
            System.out.println("Выберите действие: 1. Установка бюджета 2. Добавить транзакцию 3. Редактирование транзакции " +
                    "4. Удаление транзакции 5. Просмотр транзакций 6. Меню управления целями 7. Статистика и аналитика " +
                    "8. Выход из управления финансами");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    setBudget();
                    break;
                case 2:
                    if (!currentUser.isBlocked()) {
                        addTransaction();
                        break;
                    }
                    System.out.println("Your account is blocked! You can't make transaction!");
                    break;
                case 3:
                    updateTransaction();
                    break;
                case 4:
                    deleteTransaction();
                    break;
                case 5:
                    viewTransactions();
                    break;
                case 6:
                    goalManagement();
                    break;
                case 7:
                    analyticMenu();
                    break;
                case 8:
                    System.out.println("Вы покинули управление транзакциями");
                    return;
                default:
                    System.out.println("Такого действия нет в перечне!");
            }
        }
    }

    // метод добавления новой транзакции
    private void addTransaction() {
        System.out.print("Введите тип (income/expense): ");
        String type = scanner.nextLine();
        System.out.println("Введите сумму транзакции:");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Введите категорию: ");
        String category = scanner.nextLine();
        System.out.print("Введите описание: ");
        String description = scanner.nextLine();
        HashMap<String, Transaction> currentUserTransactions = currentUser.getTransactions();
        String transactionNumber = UUID.randomUUID().toString();
        if (!currentUser.isBlocked() && type.equals("income")) {
            currentUserTransactions.put(transactionNumber, new Transaction(type, amount, category, description, LocalDate.now().toString()));
            System.out.println("Транзакция добавлена.");
            currentUser.setBalance(currentUser.getBalance() + amount);
            if (currentUser.getBalance() >= currentUser.getGoal()) {
                System.out.println("Цель накопления достигнута!");
            }
            currentUser.setTotalIncome(currentUser.getTotalIncome() + amount);
        } else if (!currentUser.isBlocked() && type.equals("expense") && (currentUser.getBalance() - amount) >= 0) {
            currentUserTransactions.put(transactionNumber, new Transaction(type, amount, category, description, LocalDate.now().toString()));
            System.out.println("Транзакция добавлена.");
            currentUser.setBalance(currentUser.getBalance() - amount);
            currentUser.setTotalExpense(currentUser.getTotalExpense() + amount);
        } else {
            System.out.println("К сожалению, данная транзакция недопустима, т.к. на балансе недостаточно средств или неверно указан тип транзакции");
        }

        // уведомление в случае превышения бюджета
        if ((type.equals("expense") && (currentUser.getBudget() - amount) < 0)) {
            System.out.println("Обращаем Ваше внимание на превышение установленного Вами бюджета на " +
                    (currentUser.getBudget() - amount));
        }
    }

    // метод установки бюджета
    private void setBudget() {
        System.out.print("Введите сумму: ");
        double amount = scanner.nextDouble();
        currentUser.setBudget(amount);
        System.out.println("Бюджет установлен в пределах: " + amount);
    }

    // меню управления целями
    private void goalManagement() {
        while (true) {
            System.out.println("Выберите действие: 1. Установка цели (накопления) 2. Отследить прогресс к цели 3. Выход из меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    setGoal();
                    break;
                case 2:
                    goalProgress();
                    break;
                case 3:
                    System.out.println("Вы покинули меню цели");
                    return;
                default:
                    System.out.println("Такого действия нет в перечне");
            }
        }
    }

    // метод установки цели
    private void setGoal() {
        System.out.print("Введите сумму: ");
        double amount = scanner.nextDouble();
        currentUser.setGoal(amount);
        System.out.println("Цель накопления установлена: " + amount);
    }

    // метод отслеживания прогресса по цели
    private void goalProgress() {
        double amount = currentUser.getGoal() - currentUser.getBalance();
        if (amount > 0) {
            System.out.println("До цели осталось накопить: " + amount);
        } else if (amount <= 0) {
            System.out.println("Поздравляем! Ваша цель достигнута!");
        }
    }

    // меню аналитики из меню пользователя
    private void analyticMenu() {
        while (true) {
            System.out.println("Выберите действие: 1. Текущий баланс 2. Общий расход за период 3. Общий доход за период 4. Выход из меню");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    totalExpense();
                    break;
                case 3:
                    totalIncome();
                    break;
                case 4:
                    System.out.println("Вы покинули меню аналитики");
                    return;
                default:
                    System.out.println("Такого действия нет в перечне");
            }
        }
    }

    private void checkBalance() {
        System.out.println("Ваш баланс равен: " + currentUser.getBalance());
    }

    private void totalExpense() {
        System.out.println("Общий расход за период составляет: " + currentUser.getTotalExpense());
    }

    private void totalIncome() {
        System.out.println("Общий доход за период составляет: " + currentUser.getTotalIncome());
    }

    // метод редактирования транзакции из меню пользователя
    private void updateTransaction() {
        System.out.print("Введите номер транзакции: ");
        String transactionNumber = scanner.nextLine();
        System.out.print("Введите сумму: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Введите категорию: ");
        String category = scanner.nextLine();
        System.out.print("Введите описание: ");
        String description = scanner.nextLine();

        HashMap<String, Transaction> currentUserTransactions = currentUser.getTransactions();
        Transaction transaction = currentUserTransactions.get(transactionNumber);
        if (transaction != null) {
            double newBalance = currentUser.getBalance() + (transaction.getType().equals("income")
                    ? -transaction.getSum() + amount : transaction.getSum() - amount);
            currentUser.setBalance(newBalance);

            if (transaction.getType().equals("income")) {
                currentUser.setTotalIncome(currentUser.getTotalIncome() - transaction.getSum() + amount);
            } else {
                currentUser.setTotalExpense(currentUser.getTotalExpense() - transaction.getSum() + amount);
            }

            transaction.updateTransaction(amount, category, description, LocalDate.now().toString());
            System.out.println("Транзакция с номером " + transactionNumber + " обновлена.");

            if (currentUser.getBalance() >= currentUser.getGoal()) {
                System.out.println("Поздравляем! Вы достигли поставленной цели в " + currentUser.getGoal() + " !");
            }
        } else {
            System.out.println("Транзакция с номером " + transactionNumber + " не найдена! " +
                    "Проверьте номер транзакции получив список всех транзакций");
        }
    }

    // меню просмотра транзакций из меню пользователя
    private void viewTransactions() {
        while (true) {
            System.out.println("Выберите действие: 1. Фильтр по типу транзакций 2. Фильтр по категории транзакций " +
                    "3. Фильтр по дате транзакций 4. Показать все транзакции 5. Выход из просмотра транзакций");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    viewByType();
                    break;
                case 2:
                    viewByCategory();
                    break;
                case 3:
                    viewByDate();
                    break;
                case 4:
                    viewAll();
                    break;
                case 5:
                    System.out.println("Вы покинули меню просмотра транзакций");
                    return;
                default:
                    System.out.println("Такой опции нет - выберите действие из перечня");
            }
        }
    }

    // метод фильтрации по типу и вывод результата из меню пользователя
    private void viewByType() {
        System.out.print("Введите тип транзакций: ");
        String type = scanner.nextLine();
        HashMap<String, Transaction> currentUserTransactions = currentUser.getTransactions();
        HashMap<String, Transaction> searchMap = new HashMap<>();
        for (Map.Entry<String, Transaction> entry : currentUserTransactions.entrySet()) {
            if (entry.getValue().getType().equals(type)) {
                searchMap.put(entry.getKey(), entry.getValue());
            }
        }
        System.out.println(type + " transactions:");
        for (Map.Entry<String, Transaction> entry : searchMap.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Transaction info: " + entry.getValue().toString());
        }
    }

    // метод фильтрации по категории и вывод результата из меню пользователя
    private void viewByCategory() {
        System.out.print("Введите категорию транзакций: ");
        String category = scanner.nextLine();
        HashMap<String, Transaction> currentUserTransactions = currentUser.getTransactions();
        HashMap<String, Transaction> searchMap = new HashMap<>();
        for (Map.Entry<String, Transaction> entry : currentUserTransactions.entrySet()) {
            if (entry.getValue().getCategory().equals(category)) {
                searchMap.put(entry.getKey(), entry.getValue());
            }
        }
        System.out.println("Transactions with " + category + " :");
        for (Map.Entry<String, Transaction> entry : searchMap.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Transaction info: " + entry.getValue().toString());
        }
    }

    // метод фильтрации по дате и вывод результата из меню пользователя
    private void viewByDate() {
        System.out.print("Введите дату: ");
        String date = scanner.nextLine();

        HashMap<String, Transaction> currentUserTransactions = currentUser.getTransactions();
        HashMap<String, Transaction> searchMap = new HashMap<>();
        for (Map.Entry<String, Transaction> entry : currentUserTransactions.entrySet()) {
            if (entry.getValue().getDate().equals(date)) {
                searchMap.put(entry.getKey(), entry.getValue());
            }
        }
        System.out.println("Transactions at " + date + " :");
        for (Map.Entry<String, Transaction> entry : searchMap.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Transaction info: " + entry.getValue().toString());
        }
    }

    // метод просмотра всех пользователей и их транзакций из меню администратора
    private void viewAll() {
        HashMap<String, Transaction> currentUserTransactions = currentUser.getTransactions();
        System.out.println("All " + currentUser.getName() + " transactions: ");
        for (Map.Entry<String, Transaction> entry : currentUserTransactions.entrySet()) {
            System.out.println("ID: " + entry.getKey() + ", Transaction info: " + entry.getValue().toString());
        }
    }

    // метод удаления транзакции из меню пользователя
    private void deleteTransaction() {
        System.out.print("Введите номер транзакции для удаления: ");
        String transactionNumber = scanner.nextLine();
        HashMap<String, Transaction> currentUserTransactions = currentUser.getTransactions();
        Transaction transaction = currentUserTransactions.get(transactionNumber);
        if (transaction != null) {
            currentUser.setBalance((currentUser.getBalance()) +
                    (transaction.getType().equals("income") ? -transaction.getSum() : transaction.getSum()));
            currentUserTransactions.remove(transactionNumber);
            if (transaction.getType().equals("income")) {
                currentUser.setTotalIncome(currentUser.getTotalIncome() - transaction.getSum());
            } else {
                currentUser.setTotalExpense(currentUser.getTotalExpense() - transaction.getSum());
            }
            System.out.println("Транзакция с номером " + transactionNumber + " удалена.");
            if (currentUser.getBalance() >= currentUser.getGoal()) {
                System.out.println("Поздравляем! Вы достигли посталенной цели в " + currentUser.getGoal() + " !");
            }
        } else {
            System.out.println("Транзакция с номером " + transactionNumber + " не найдена! " +
                    "Проверьте номер транзакции получив список всех транзакций");
        }
    }

    // метод для просмотра всех пользователей из меню администратора
    private void viewUsers() {
        for (String key: users.keySet()) {
            User user = users.get(key);
            if (user == null) {
                System.out.println("User with email " + key + " was not found!");
            } else {
                for (Map.Entry<String, Transaction> entry : user.getTransactions().entrySet()) {
                    System.out.println(entry.getKey() + entry.getValue().toString());
                }
            }
        }
    }

    // метод блокировки пользователя из меню администратора
    private void blockUser() {
        System.out.print("Введите email пользователя для блокировки: ");
        String email = scanner.nextLine();
        User user = users.get(email);
        if (user == null) {
            System.out.println("User with email " + email + " was not found!");
        } else if (user.isBlocked() || user.isAdmin()) {
            System.out.println("User is admin or blocked already!");
        } else {
            user.setIsBlocked(true);
            System.out.println("Пользователь с email " + email + " заблокирован!");
        }

    }

    // метод удаления своего аккаунта самим пользователем
    private void deleteMyAccount() {
        users.remove(currentUser.getEmail());
        System.out.println("Аккаунт " + currentUser.getEmail() + " удален!");
    }

    // метод удаления пользователя администратором
    private void deleteUser() {
        System.out.print("Введите email пользователя для удаления: ");
        String email = scanner.nextLine();
        User user = users.get(email);
        if (user == null) {
            System.out.println("User with email " + email + " was not found!");
        } else if (user.isAdmin()) {
            System.out.println("Нельзя удалить аккаунт администратора!");
        } else {
        users.remove(email);
        System.out.println("Пользователь удалён.");
        }
    }
}
