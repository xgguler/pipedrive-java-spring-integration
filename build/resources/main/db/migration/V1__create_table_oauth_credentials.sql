CREATE TABLE IF NOT EXISTS OAUTH_CREDENTIALS (
    ID long NOT NULL AUTO_INCREMENT ,
    USER_ID int NOT NULL ,
    TOKEN varchar NOT NULL ,
    REFRESH_TOKEN varchar(100) NOT NULL ,
    EXPIRES_IN datetime NOT NULL ,
    CREATED_DATE datetime NOT NULL ,

    PRIMARY KEY (ID)
)