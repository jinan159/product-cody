-- BRANDS
INSERT INTO BRANDS (ID, NAME)
VALUES (1, 'test-brand-1'),
       (2, 'test-brand-2'),
       (3, 'test-brand-3')
;

-- CATEGORIES
INSERT INTO CATEGORIES (ID, NAME)
VALUES (1, 'test-category-1'),
       (2, 'test-category-2'),
       (3, 'test-category-3')
;

-- PRODUCTS
INSERT INTO PRODUCTS (ID, BRAND_ID, CATEGORY_ID, AMOUNT, NAME)
VALUES
(1, 1, 1, 900, 'test-brand-1-category-1-product'),
(2, 1, 2, 1000, 'test-brand-1-category-2-product'),
(3, 1, 3, 1100, 'test-brand-1-category-3-product'),
(4, 2, 1, 1900, 'test-brand-2-category-1-product'),
(5, 2, 2, 2000, 'test-brand-2-category-2-product'),
(6, 2, 3, 2100, 'test-brand-2-category-3-product'),
(7, 3, 1, 800, 'test-brand-3-category-1-product'),
(8, 3, 2, 1000, 'test-brand-3-category-2-product'),
(9, 3, 3, 1200, 'test-brand-3-category-3-product')
;