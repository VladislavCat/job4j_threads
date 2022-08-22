package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) != null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
            boolean rsl;
            Optional<Account> fromIdAcc = getById(fromId);
            Optional<Account> toIdAcc = getById(toId);
            if (fromIdAcc.isEmpty() || toIdAcc.isEmpty()) {
                throw new IllegalArgumentException("Один или оба из указанных id аккаунтов неправильные.");
            } else if (fromIdAcc.get().amount() < amount) {
                throw new IllegalArgumentException("На указанном счете не хватает средств.");
            }
            rsl = update(new Account(fromId, fromIdAcc.get().amount() - amount));
            rsl = update(new Account(toId, toIdAcc.get().amount() + amount));
            return rsl;
    }
}


