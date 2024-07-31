drop table TB_ESTATE;
drop table TB_ZONE;
drop table TB_CITY;
drop table TB_PROVINCE;

-- drop all of table


-------------------------------------- TB_PROVINCE
CREATE TABLE TB_PROVINCE
(
    CODE  NUMBER(12) PRIMARY KEY,
    TITLE VARCHAR2(40) NOT NULL
);

-------------------------------------- TB_CITY
CREATE TABLE TB_CITY
(
    CODE          NUMBER(12) PRIMARY KEY,
    TITLE         VARCHAR2(40) NOT NULL,
    PROVINCE_CODE NUMBER(12)
);


ALTER TABLE TB_CITY
    ADD CONSTRAINT fk_city
        FOREIGN KEY (PROVINCE_CODE)
            REFERENCES TB_PROVINCE (CODE);


-------------------------------------- TB_ZONE
CREATE TABLE TB_ZONE
(
    CODE      NUMBER(12) PRIMARY KEY,
    TITLE     VARCHAR2(40) NOT NULL,
    CITY_CODE NUMBER(12)
);

ALTER TABLE TB_ZONE
    ADD CONSTRAINT fk_zone
        FOREIGN KEY (CITY_CODE)
            REFERENCES TB_CITY (CODE);


-------------------------------------- TB_ESTATE
CREATE TABLE TB_ESTATE
(
    ID                  NUMBER(12) PRIMARY KEY,
    PROVINCE_CODE          NUMBER(12) NOT NULL,
    CITY_CODE              NUMBER(12),
    ZONE_CODE              NUMBER(12),
    DATE_YEAR              DATE,
    PRICE_PER_SQUARE_METER NUMERIC(18,2)
);

ALTER TABLE TB_ESTATE
    ADD CONSTRAINT fk_P
        FOREIGN KEY (PROVINCE_CODE)
            REFERENCES TB_PROVINCE (CODE);

ALTER TABLE TB_ESTATE
    ADD CONSTRAINT fk_C
        FOREIGN KEY (CITY_CODE)
            REFERENCES TB_CITY (CODE);


ALTER TABLE TB_ESTATE
    ADD CONSTRAINT fk_Z
        FOREIGN KEY (ZONE_CODE)
            REFERENCES TB_ZONE (CODE);
