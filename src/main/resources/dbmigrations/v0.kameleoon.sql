DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS quote;
DROP TABLE IF EXISTS vote;

CREATE TABLE IF NOT EXISTS user
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created   TIMESTAMP WITHOUT TIME ZONE             NOT NULL DEFAULT now(),
    updated   TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    user_name VARCHAR(255),
    email     VARCHAR(255),
    password  VARCHAR(255),
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS quote
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created       TIMESTAMP WITHOUT TIME ZONE             NOT NULL DEFAULT now(),
    updated       TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    quote_content VARCHAR(255),
    vote_amount   BIGINT DEFAULT 0,
    user_id       BIGINT,
    CONSTRAINT pk_quote PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vote
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created    TIMESTAMP WITHOUT TIME ZONE             NOT NULL DEFAULT now(),
    updated    TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    vote_count BIGINT,
    user_id    BIGINT,
    quote_id   BIGINT,
    CONSTRAINT pk_vote PRIMARY KEY (id)
);

ALTER TABLE quote
    ADD CONSTRAINT FK_QUOTE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);


ALTER TABLE vote
    ADD CONSTRAINT FK_VOTE_ON_QUOTE FOREIGN KEY (quote_id) REFERENCES quote (id);

ALTER TABLE vote
    ADD CONSTRAINT FK_VOTE_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);