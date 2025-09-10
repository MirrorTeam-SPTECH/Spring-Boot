-- Criar usuários (senha: password)
INSERT INTO users (id, name, email, password, phone, role, active, created_at) VALUES
                                                                                   (1, 'Admin', 'admin@foodtruck.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', '11999999999', 'ADMIN', true, CURRENT_TIMESTAMP),
                                                                                   (2, 'Funcionário', 'funcionario@foodtruck.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', '11888888888', 'EMPLOYEE', true, CURRENT_TIMESTAMP),
                                                                                   (3, 'Cliente Teste', 'cliente@teste.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymGe07xd00DMxs.AQubh4a', '11777777777', 'CUSTOMER', true, CURRENT_TIMESTAMP);

-- Criar itens do menu
INSERT INTO menu_items (id, name, description, price, category, preparation_time, image_url, active, created_at) VALUES
-- Combos
(1, 'Combo X-Burger', 'Hambúrguer artesanal + Batata + Refrigerante', 35.90, 'COMBOS', '15 min', null, true, CURRENT_TIMESTAMP),
(2, 'Combo Vegano', 'Burger vegano + Salada + Suco natural', 32.90, 'COMBOS', '12 min', null, true, CURRENT_TIMESTAMP),

-- Hambúrgueres
(3, 'Classic Burger', 'Pão, carne 180g, queijo, alface, tomate, molho especial', 22.90, 'HAMBURGUERES', '10 min', null, true, CURRENT_TIMESTAMP),
(4, 'Bacon Burger', 'Pão, carne 180g, queijo, bacon, cebola caramelizada', 26.90, 'HAMBURGUERES', '12 min', null, true, CURRENT_TIMESTAMP),
(5, 'Veggie Burger', 'Pão, hambúrguer de grão de bico, queijo vegano, vegetais', 24.90, 'HAMBURGUERES', '10 min', null, true, CURRENT_TIMESTAMP),

-- Espetinhos
(6, 'Espetinho de Carne', 'Carne bovina temperada', 12.00, 'ESPETINHOS', '8 min', null, true, CURRENT_TIMESTAMP),
(7, 'Espetinho de Frango', 'Frango marinado especial', 10.00, 'ESPETINHOS', '8 min', null, true, CURRENT_TIMESTAMP),

-- Porções
(8, 'Batata Frita P', 'Porção pequena de batata', 8.00, 'PORCOES', '5 min', null, true, CURRENT_TIMESTAMP),
(9, 'Batata Frita G', 'Porção grande de batata', 14.00, 'PORCOES', '5 min', null, true, CURRENT_TIMESTAMP),
(10, 'Onion Rings', 'Anéis de cebola empanados', 12.00, 'PORCOES', '6 min', null, true, CURRENT_TIMESTAMP),

-- Bebidas
(11, 'Refrigerante Lata', 'Coca, Guaraná ou Sprite', 5.00, 'BEBIDAS', '0 min', null, true, CURRENT_TIMESTAMP),
(12, 'Suco Natural', 'Laranja, Limão ou Maracujá', 8.00, 'BEBIDAS', '2 min', null, true, CURRENT_TIMESTAMP),
(13, 'Água Mineral', '500ml', 3.00, 'BEBIDAS', '0 min', null, true, CURRENT_TIMESTAMP),

-- Adicionais
(14, 'Bacon Extra', 'Porção extra de bacon', 4.00, 'ADICIONAIS', '0 min', null, true, CURRENT_TIMESTAMP),
(15, 'Queijo Extra', 'Fatia extra de queijo', 3.00, 'ADICIONAIS', '0 min', null, true, CURRENT_TIMESTAMP);