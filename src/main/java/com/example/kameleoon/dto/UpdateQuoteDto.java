package com.example.kameleoon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQuoteDto {

    private Long quoteId;
    private String quoteContent;
    private Long voteAmount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UpdateQuoteDto)) return false;

        UpdateQuoteDto that = (UpdateQuoteDto) o;

        return new EqualsBuilder().append(quoteId, that.quoteId).append(quoteContent, that.quoteContent).append(voteAmount, that.voteAmount).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(quoteId).append(quoteContent).append(voteAmount).toHashCode();
    }

    @Override
    public String toString() {
        return "UpdateQuoteDto{" +
                "quoteId=" + quoteId +
                ", quoteContent='" + quoteContent + '\'' +
                ", quoteAmount=" + voteAmount +
                '}';
    }
}
