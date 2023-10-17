CREATE TABLE CUSTOMER
(
    ID       BIGINT GENERATED BY DEFAULT AS IDENTITY,
    NAME     VARCHAR(50) NOT NULL,
    PHONE_NUMBER  VARCHAR(10) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    USER_ID BIGINT,
    PRIMARY KEY (ID),
    CONSTRAINT FK_USER_CUSTOMER
    FOREIGN KEY(USER_ID) REFERENCES USERTABLE(ID) ON DELETE CASCADE
);