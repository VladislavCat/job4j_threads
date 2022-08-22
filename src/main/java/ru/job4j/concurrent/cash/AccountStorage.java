package ru.job4j.concurrent.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
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
            Optional<Account> fromIdAcc = getById(fromId);
            Optional<Account> toIdAcc = getById(toId);
            if ((fromIdAcc.isEmpty() || toIdAcc.isEmpty()) || fromIdAcc.get().amount() < amount) {
                return false;
            }
            return update(new Account(fromId, fromIdAcc.get().amount() - amount))
                    && update(new Account(toId, toIdAcc.get().amount() + amount));
    }
}


