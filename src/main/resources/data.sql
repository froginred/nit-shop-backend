-- data.sql

-- USERS
INSERT INTO users (first_name, last_name, email, phone, address, username, password, role) VALUES
('Miki', 'Gabay', 'mikigabay@gmail.com', '0585236376', '45 Ben Eliezer St', 'mikigabay', '$2a$10$24P9JHWZJm8yRsJRpP4a.e11OvU9ynMvz6XAKJOrxl8Nhph7mojJ2', 'USER'),
('Amitay', 'Gabay', 'amitaygabay1@gmail.com', '0504380333', '38 Erez St', 'amitaygabay', '$2a$10$K78Qy75RrDNQcAolPojuM.sI.otXpP23xhZYJ7p2fXrIMoI.k2ehO', 'ADMIN'),
('Nit', 'Sal', 'nitsal@gmail.com', '050000000', 'Tel Aviv', 'nitsal', '$2a$10$6UsygcUvOnMEYaQ704HIK.K46RyS9omfSkzY9hfFaz5BjzYtOFs8i', 'ADMIN');

-- ITEMS (10 items)
INSERT INTO items (title, item_category, description, image_url, price, stock) VALUES
('Sunglasses', 'CLOTHES', 'UV400 polarized sunglasses', 'https://example.com/sunglasses.jpg', 19.99, 25),
('Wireless Mouse', 'ELECTRONICS', '2.4G ergonomic mouse', 'https://example.com/mouse.jpg', 14.50, 40),
('Bluetooth Headphones', 'ELECTRONICS', 'Over-ear, noise isolation', 'https://example.com/headphones.jpg', 49.90, 15),
('Cotton T-Shirt', 'CLOTHES', '100% cotton, unisex', 'https://elements-resized.envatousercontent.com/envato-dam-assets-production/EVA/TRX/bd/98/42/3e/1b/v1_E11/E119YQF.jpg?w=1600&cf_fit=scale-down&q=85&format=auto&s=6d1422e9422dba05b9f43e567040589afc95064155db685378031fd36a0a1415', 12.00, 60),
('Coffee Mug', 'GENERAL', 'Ceramic 350ml mug', 'https://elements-resized.envatousercontent.com/envato-dam-assets-production/EVA/TRX/3e/ef/4b/9b/75/v1_E10/E101V2IR.JPG?w=1600&cf_fit=scale-down&q=85&format=auto&s=d24e9549b836704eb7cc87b7747e0c3d5cf6b66f0d2f92c77b45d809d96a1721', 8.75, 80),
('Backpack', 'GENERAL', '20L daypack', 'https://example.com/backpack.jpg', 29.99, 20),
('Laptop Stand', 'ELECTRONICS', 'Aluminum adjustable stand', 'https://example.com/stand.jpg', 23.40, 18),
('Notebook', 'GENERAL', 'A5 dotted notebook', 'https://example.com/notebook.jpg', 5.90, 120),
('Water Bottle', 'GENERAL', '1L BPA-free bottle', 'https://example.com/bottle.jpg', 11.30, 55),
('Hoodie', 'CLOTHES', 'Warm fleece hoodie', 'https://example.com/hoodie.jpg', 34.00, 22);
