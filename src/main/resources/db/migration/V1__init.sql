-- ====== RBAC ======
CREATE TABLE roles (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE users (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(120) NOT NULL,
   email VARCHAR(180) NOT NULL UNIQUE,
   password_hash VARCHAR(255) NOT NULL,
   role_id BIGINT NOT NULL,
   enabled BOOLEAN NOT NULL DEFAULT TRUE,
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id)
);

-- ====== Catalog config ======
CREATE TABLE categories (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(60) NOT NULL UNIQUE,
    display_name VARCHAR(120) NOT NULL
);

CREATE TABLE item_types (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    code VARCHAR(80) NOT NULL UNIQUE,
    display_name VARCHAR(160) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_item_types_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- ====== Items (unidades físicas por serial QR) ======
CREATE TABLE items (
   serial VARCHAR(64) PRIMARY KEY,
   type_id BIGINT NOT NULL,
   state VARCHAR(30) NOT NULL,
   location VARCHAR(30) NOT NULL,
   created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
   updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   CONSTRAINT fk_items_type FOREIGN KEY (type_id) REFERENCES item_types(id)
);

-- Imagen por tipo (como pediste: 1 imagen por tipo)
CREATE TABLE item_type_images (
  type_id BIGINT PRIMARY KEY,
  image_path VARCHAR(500) NOT NULL,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_item_type_images_type FOREIGN KEY (type_id) REFERENCES item_types(id)
);

-- Índices útiles
CREATE INDEX idx_items_type ON items(type_id);
CREATE INDEX idx_items_state ON items(state);
CREATE INDEX idx_items_location ON items(location);
