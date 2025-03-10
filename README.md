# Консольное приложение "Личный финансовый трекер".

***Основные функции:***

1. Регистрация и авторизация пользователей

- Возможность регистрации новых пользователей с уникальным email и паролем.

- Вход в систему с проверкой email и пароля.

2. Управление пользователями

- Возможность редактирования профиля пользователя (имя, email, пароль).

- Возможность удаления аккаунта.

3. Управление финансами (CRUD-операции)

- Создание транзакции: Пользователь может добавить доход или расход, указав сумму, категорию, дату и описание.

- Редактирование транзакции: Возможность изменения суммы, категории и описания.

- Удаление транзакции: Удаление конкретной записи из списка.

- Просмотр транзакций: Возвращение списка всех транзакций пользователя с возможностью фильтрации по дате, категории или типу (доход/расход).

4. Управление бюджетом

- Установка месячного бюджета.

- Отслеживание превышения бюджета и уведомление об этом.

5. Управление целями

- Установка цели (накопления)

- отслеживание прогресса по цели

5. Статистика и аналитика

- Подсчёт текущего баланса.

- Расчёт суммарного дохода и расхода за определённый период.

- Анализ расходов по категориям.

- Формирование отчёта для пользователя по финансовому состоянию.

6. Уведомления

- API для напоминаний о достижении лимитов расходов.

- Возможность интеграции с email-уведомлениями.

7. Администрирование

- Администраторы могут просматривать список пользователей и их транзакций.

- Возможность блокировки или удаления пользователей.

**Стэк:**
- Написано на чистой Java без сторонних библиотек (без Spring и т. д.).

- Данные храняться в коллекциях (в памяти). Поэтому при завершении работы приложения (выходе в стартовом меню) - все данные стираются.

- Реализованы CRUD (Create, Read, Update, Delete) операции для управления пользователями и транзакциями.

- Реализована авторизация и аутентификация пользователей.

  Запуск приложения: с точки входа в Main. Далее выбирайте действия согласно меню команд.

