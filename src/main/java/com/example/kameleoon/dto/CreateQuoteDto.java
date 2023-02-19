package com.example.kameleoon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateQuoteDto {

    private Long userId;
    @NotBlank
    private String quoteContent;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CreateQuoteDto)) return false;

        CreateQuoteDto that = (CreateQuoteDto) o;

        return new EqualsBuilder().append(userId, that.userId).append(quoteContent, that.quoteContent).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userId).append(quoteContent).toHashCode();
    }

    @Override
    public String toString() {
        return "CreateQuoteDto{" +
                "userId=" + userId +
                ", quoteContent='" + quoteContent + '\'' +
                '}';
    }
}
