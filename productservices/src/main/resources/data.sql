-- Inserts para la tabla categories
INSERT INTO categories (name,image_url) VALUES ('Ropa', 'https://d1ih8jugeo2m5m.cloudfront.net/2024/08/ideas_para_tienda_de_ropa_infantil.jpg');
INSERT INTO categories (name,image_url) VALUES ('Accesorios', 'https://media.istockphoto.com/id/489814272/es/foto/anillos-de-oro-plata-y-cadenas.jpg?s=612x612&w=0&k=20&c=UXsPvIHf9LEjFN0WOlx8BwpGAT0ZAusMRE2t_UQiBUE=');
INSERT INTO categories (name,image_url) VALUES ('Deportivo', 'https://www.shutterstock.com/image-photo/fitness-woman-athletic-girl-on-260nw-2466932591.jpg');
INSERT INTO categories (name,image_url) VALUES ('Casual', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ31RxAk_L92YOAsaptWZ0sxsY0xByDSF2UXA&s');

-- Inserts para la tabla marcas
INSERT INTO marcas (name) VALUES ('Nike');
INSERT INTO marcas (name) VALUES ('Adidas');
INSERT INTO marcas (name) VALUES ('Puma');
INSERT INTO marcas (name) VALUES ('Reebok');
INSERT INTO marcas (name) VALUES ('Under Armour');

-- Inserts para la tabla products (usando UUID generado)
INSERT INTO products (id, name, marca_id, category_id, size, color, price, stock, active, created_at, updated_at)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Camiseta Deportiva', 1, 1, 'S', 'Negro', 29.99, 100, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('22222222-2222-2222-2222-222222222222', 'Zapatillas Running', 2, 2, 'XS', 'Blanco', 89.99, 50, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('33333333-3333-3333-3333-333333333333', 'Gorra', 3, 3, 'M', 'Rojo', 19.99, 200, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('44444444-4444-4444-4444-444444444444', 'Shorts Deportivos', 4, 4, 'M', 'Azul', 24.99, 75, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    ('55555555-5555-5555-5555-555555555555', 'Sudadera', 5, 4, 'L', 'Gris', 49.99, 30, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Inserts para la tabla product_images
INSERT INTO product_images (image_url, product_id, main, display_order)
VALUES
    ('https://example.com/images/camiseta1.jpg', '11111111-1111-1111-1111-111111111111', true, 1),
    ('https://example.com/images/camiseta2.jpg', '11111111-1111-1111-1111-111111111111', false, 2),
    ('https://example.com/images/zapatillas1.jpg', '22222222-2222-2222-2222-222222222222', true, 1),
    ('https://example.com/images/zapatillas2.jpg', '22222222-2222-2222-2222-222222222222', false, 2),
    ('https://example.com/images/gorra1.jpg', '33333333-3333-3333-3333-333333333333', true, 1),
    ('https://example.com/images/shorts1.jpg', '44444444-4444-4444-4444-444444444444', true, 1),
    ('https://example.com/images/sudadera1.jpg', '55555555-5555-5555-5555-555555555555', true, 1),
    ('https://example.com/images/sudadera2.jpg', '55555555-5555-5555-5555-555555555555', false, 2);