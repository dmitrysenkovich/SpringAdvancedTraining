package com.epam.spring.core.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_account")
public class UserAccount extends DomainObject {

    private static final long serialVersionUID = -7407630406411936280L;

    private double money;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userAccount")
    private User user;

    public UserAccount() {

    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAccount that = (UserAccount) o;

        if (Double.compare(that.money, money) != 0) return false;
        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(money);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
