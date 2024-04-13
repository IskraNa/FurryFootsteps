CREATE TABLE activitytype
(
    activityid SERIAL NOT NULL,
    type       VARCHAR(255) NOT NULL,
    PRIMARY KEY (activityid)
);

CREATE TABLE availability
(
    availabilityid SERIAL NOT NULL,
    datetime_to    TIMESTAMP NOT NULL,
    datetime_from  TIMESTAMP NOT NULL,
    post_id        INT4 NOT NULL,
    PRIMARY KEY (availabilityid)
);

CREATE TABLE pettype
(
    pettypeid SERIAL NOT NULL,
    type      VARCHAR(255) NOT NULL,
    PRIMARY KEY (pettypeid)
);

CREATE TABLE post
(
    postid           SERIAL NOT NULL,
    description      VARCHAR(255) NOT NULL,
    pet_size         VARCHAR(255) NOT NULL,
    price            FLOAT8 NOT NULL,
    pet_type_id      INT4 NOT NULL,
    activity_type_id INT4 NOT NULL,
    user_id          INT4 NOT NULL,
    PRIMARY KEY (postid)
);

CREATE TABLE request
(
    requestid SERIAL NOT NULL,
    post_id   INT4 NOT NULL,
    user_id   INT4 NOT NULL,
    PRIMARY KEY (requestid)
);

CREATE TABLE review
(
    reviewid SERIAL NOT NULL,
    comment  VARCHAR(255),
    rating   FLOAT8 NOT NULL,
    user_id  INT4 NOT NULL,
    post_id  INT4 NOT NULL,
    PRIMARY KEY (reviewid)
);

CREATE TABLE users
(
    userid          SERIAL NOT NULL,
    password        VARCHAR(255) NOT NULL,
    pet_description VARCHAR(255),
    email           VARCHAR(255) NOT NULL,
    NAME            VARCHAR(255) NOT NULL,
    surname         VARCHAR(255) NOT NULL,
    phone           VARCHAR(255),
    location        VARCHAR(255),
    bio             VARCHAR(255),
    picture         BYTEA,
    PRIMARY KEY (userid)
);

ALTER TABLE availability
    ADD CONSTRAINT fkavailabili806928 FOREIGN KEY (post_id) REFERENCES post (
                                                                             postid);

ALTER TABLE request
    ADD CONSTRAINT fkrequest25768 FOREIGN KEY (user_id) REFERENCES users (userid);

ALTER TABLE request
    ADD CONSTRAINT fkrequest657634 FOREIGN KEY (post_id) REFERENCES post (postid);

ALTER TABLE post
    ADD CONSTRAINT fkpost272859 FOREIGN KEY (user_id) REFERENCES users (userid);

ALTER TABLE review
    ADD CONSTRAINT fkreview270015 FOREIGN KEY (post_id) REFERENCES post (postid);

ALTER TABLE review
    ADD CONSTRAINT fkreview901881 FOREIGN KEY (user_id) REFERENCES users (userid);

ALTER TABLE post
    ADD CONSTRAINT fkpost410039 FOREIGN KEY (activity_type_id) REFERENCES
        activitytype (activityid);

ALTER TABLE post
    ADD CONSTRAINT fkpost387498 FOREIGN KEY (pet_type_id) REFERENCES pettype (
                                                                              pettypeid);