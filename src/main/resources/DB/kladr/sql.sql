delete FROM kladr_all;
INSERT INTO kladr_all (reg_code, area_code, city_code, punkt_code, name)
SELECT kc.reg_code, kc.area_code, kc.city_code, 0 as punkt_code,
       concat(kl_region.socr, ' ', kl_region.name, ', ', kc.socr, ' ', kc.name)
FROM kl_region JOIN kl_city kc on kl_region.reg_code = kc.reg_code
WHERE kc.area_code = 0
UNION

SELECT kl_punkt.reg_code, kl_punkt.area_code, kl_punkt.city_code, kl_punkt.punkt_code,
       concat(kr.socr, ' ', kr.name, ', ', kl_punkt.socr, ' ', kl_punkt.name)
FROM kl_punkt JOIN kl_region kr on kl_punkt.reg_code = kr.reg_code
    AND kl_punkt.area_code = 0 AND kl_punkt.city_code = 0
UNION

SELECT k.reg_code, k.area_code, k.city_code, 0,
       concat(kl_region.socr, ' ', kl_region.name, ', ', ka.socr, ' ', ka.name, ', ', k.socr, ' ', k.name)
FROM kl_region
         JOIN kl_area ka on kl_region.reg_code = ka.reg_code
         JOIN kl_city k on ka.area_code = k.area_code
WHERE kl_region.reg_code = k.reg_code AND ka.area_code = k.area_code
UNION

SELECT kp.reg_code, kp.area_code, kp.city_code, kp.punkt_code,
       concat(kl_region.socr, ' ', kl_region.name, ', ', a.socr, ' ', a.name, ', ',
              kp.socr, ' ', kp.name)
FROM kl_region
         JOIN kl_area a on kl_region.reg_code = a.reg_code
         JOIN kl_punkt kp on a.area_code = kp.area_code
WHERE kp.reg_code = kl_region.reg_code AND kp.area_code = a.area_code AND kp.city_code = 0
UNION

SELECT p.reg_code, p.area_code, p.city_code, p.punkt_code,
       concat(kl_region.socr, ' ', kl_region.name, ', ',
              c.socr, ' ', c.name, ', ', p.socr, ' ', p.name)
FROM kl_region
         JOIN kl_city c on kl_region.reg_code = c.reg_code
         JOIN kl_punkt p on c.city_code = p.city_code
WHERE p.reg_code = kl_region.reg_code AND p.city_code = c.city_code AND p.area_code = 0
ORDER BY 1,2,3,4