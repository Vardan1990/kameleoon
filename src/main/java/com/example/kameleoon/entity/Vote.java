package com.example.kameleoon.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "vote")
@NoArgsConstructor
@AllArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "vote_count")
    private Long voteCount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "quote_id")
    private Quote quote;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Vote)) return false;

        Vote vote = (Vote) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(voteCount, vote.voteCount).append(user, vote.user).append(quote, vote.quote).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(voteCount).append(user).append(quote).toHashCode();
    }

    @Override
    public String toString() {
        return "Vote{" +
                "voteCount=" + voteCount +
                ", user=" + user +
                ", quote=" + quote +
                "} " + super.toString();
    }
}
