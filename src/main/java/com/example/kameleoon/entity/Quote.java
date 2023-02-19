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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "quote")
@NoArgsConstructor
@AllArgsConstructor
public class Quote extends BaseEntity {

    @Column(name = "quote_content")
    private String quoteContent;
    @Column(name = "vote_amount")
    private Long voteAmount;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Quote)) return false;

        Quote quote = (Quote) o;

        return new EqualsBuilder().appendSuper(super.equals(o)).append(quoteContent, quote.quoteContent).append(voteAmount, quote.voteAmount).append(user, quote.user).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).appendSuper(super.hashCode()).append(quoteContent).append(voteAmount).append(user).toHashCode();
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteContent='" + quoteContent + '\'' +
                ", voteAmount=" + voteAmount +
                ", user=" + user +
                "} " + super.toString();
    }
}
