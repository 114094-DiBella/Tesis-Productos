-- Inserts para la tabla categories
INSERT INTO categories (name,image_url) VALUES ('Jeans', '');
INSERT INTO categories (name,image_url) VALUES ('Remeras', '');
INSERT INTO categories (name,image_url) VALUES ('Vestidos', '');
INSERT INTO categories (name,image_url) VALUES ('Casual', '');

-- Inserts para la tabla marcas
INSERT INTO marcas (name) VALUES ('Nike');
INSERT INTO marcas (name) VALUES ('Adidas');
INSERT INTO marcas (name) VALUES ('Puma');
INSERT INTO marcas (name) VALUES ('Reebok');
INSERT INTO marcas (name) VALUES ('Under Armour');

-- Inserts para la tabla products (usando UUID generado)
INSERT INTO products (id, name, marca_id, category_id, size, color, price, stock, active, created_at, updated_at)
VALUES
    ('11111111-1111-1111-1111-111111111111', 'Camiseta Deportiva', 1, 1, 'S', 'Negro', 100.10, 100, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- Inserts para la tabla product_images
INSERT INTO product_images (image_url, product_id, main, display_order)
VALUES
    ('https://example.com/images/camiseta1.jpg', '11111111-1111-1111-1111-111111111111', true, 1);