drop table if exists kl_house;
create table kl_house
(
    id          serial
        primary key,
    reg_code    varchar,
    area_code   varchar,
    city_code   varchar,
    punkt_code  varchar,
    street_code varchar,
    house_code  varchar,
    name        varchar,
    index       varchar
);

INSERT INTO kl_house (reg_code, area_code, city_code, punkt_code, street_code, house_code, name, index)
SELECT substr(code, 1, 2) as reg_code, substr(code, 3, 3) as area_code,
       substr(code, 6, 3) as city_code, substr(code, 9, 3) as punkt_code,
       substr(code, 12, 4) as street_code, substr(code, 16, 4) as house_code, name, index
FROM kladr_doma
