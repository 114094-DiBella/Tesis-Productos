-- Inserts para la tabla categories
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Jeans')
    INSERT INTO categories (name, image_url) VALUES ('Jeans', '');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Remeras')
    INSERT INTO categories (name, image_url) VALUES ('Remeras', '');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Vestidos')
    INSERT INTO categories (name, image_url) VALUES ('Vestidos', '');
IF NOT EXISTS (SELECT 1 FROM categories WHERE name = 'Casual')
    INSERT INTO categories (name, image_url) VALUES ('Casual', '');

-- Inserts para la tabla marcas
IF NOT EXISTS (SELECT 1 FROM marcas WHERE name = 'Nike')
    INSERT INTO marcas (name) VALUES ('Nike');
IF NOT EXISTS (SELECT 1 FROM marcas WHERE name = 'Adidas')
    INSERT INTO marcas (name) VALUES ('Adidas');
IF NOT EXISTS (SELECT 1 FROM marcas WHERE name = 'Puma')
    INSERT INTO marcas (name) VALUES ('Puma');
IF NOT EXISTS (SELECT 1 FROM marcas WHERE name = 'Reebok')
    INSERT INTO marcas (name) VALUES ('Reebok');
IF NOT EXISTS (SELECT 1 FROM marcas WHERE name = 'Under Armour')
    INSERT INTO marcas (name) VALUES ('Under Armour');

-- Inserts para la tabla products (usando UUID generado y sintaxis SQL Server)
IF NOT EXISTS (SELECT 1 FROM products WHERE id = '11111111-1111-1111-1111-111111111111')
    INSERT INTO products (id, name, marca_id, category_id, size, color, price, stock, active, created_at, updated_at)
    VALUES
        ('11111111-1111-1111-1111-111111111111', 'Camiseta Deportiva', 1, 1, 'S', 'Negro', 100.10, 100, 1, GETDATE(), GETDATE());

-- Inserts para la tabla product_images
IF NOT EXISTS (SELECT 1 FROM product_images WHERE product_id = '11111111-1111-1111-1111-111111111111')
    INSERT INTO product_images (image_url, product_id, main, display_order)
    VALUES
        ('https://example.com/images/camiseta1.jpg', '11111111-1111-1111-1111-111111111111', 1, 1);