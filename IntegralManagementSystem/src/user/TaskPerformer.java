package user;

import transaction.Account;

public class TaskPerformer {
    String name;
    Account account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object obj) {
        TaskPerformer taskPerformer = (TaskPerformer)obj;
        return taskPerformer.getName().equals(this.name);
    }
}
