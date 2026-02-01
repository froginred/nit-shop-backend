-- schema.sql (H2)

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS favorites;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    phone      VARCHAR(20),
    address    VARCHAR(255),
    username   VARCHAR(255) PRIMARY KEY,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(255) DEFAULT 'USER'
);

CREATE TABLE items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_category VARCHAR(255) NOT NULL DEFAULT 'GENERAL',
    title VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    image_url VARCHAR(500),
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    CONSTRAINT chk_items_stock_non_negative CHECK (stock >= 0)
);

CREATE TABLE favorites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    item_id BIGINT NOT NULL,
    CONSTRAINT fk_favorites_user FOREIGN KEY (user_id) REFERENCES users(username) ON DELETE CASCADE,
    CONSTRAINT fk_favorites_item FOREIGN KEY (item_id) REFERENCES items(id) ON DELETE CASCADE,
    CONSTRAINT uq_favorites_user_item UNIQUE (user_id, item_id)
);

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    shipping_address VARCHAR(255),
    total_price DECIMAL(10,2) NOT NULL DEFAULT 0,
    status VARCHAR(10) NOT NULL DEFAULT 'TEMP',
    CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES users(username) ON DELETE CASCADE
);

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_order_parent FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    CONSTRAINT fk_order_item_item FOREIGN KEY (item_id) REFERENCES items(id),
    CONSTRAINT uq_order_items_per_order UNIQUE (order_id, item_id),
    CONSTRAINT chk_order_items_qty_positive CHECK (quantity > 0)
);
