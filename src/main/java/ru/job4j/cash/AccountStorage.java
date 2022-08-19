package ru.job4j.cash;

import java.util.HashMap;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            return accounts.putIfAbsent(account.id(), account) != null;
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            accounts.replace(account.id(), account);
            return getById(account.id()).get() == account;
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            return accounts.remove(id) != null;
        }
    }

    public Optional<Account> getById(int id) {
        synchronized (accounts) {
            return Optional.ofNullable(accounts.get(id));
        }
    }

    public boolean transfer(int fromId, int toId, int amount) {
        synchronized (accounts) {
            boolean rsl = false;
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
}

