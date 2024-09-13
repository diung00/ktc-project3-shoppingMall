-- Insert 10 users with ROLE_BUSINESS, each with a unique shop
INSERT INTO user (username, password, nickname, name, age, email, phone, authorities) VALUES
('user1', '1234', 'Nick1', 'User One', 30, 'user1@example.com', '1234567890', 'ROLE_BUSINESS'),
('user2', '1234', 'Nick2', 'User Two', 31, 'user2@example.com', '1234567891', 'ROLE_BUSINESS'),
('user3', '1234', 'Nick3', 'User Three', 32, 'user3@example.com', '1234567892', 'ROLE_BUSINESS'),
('user4', '1234', 'Nick4', 'User Four', 33, 'user4@example.com', '1234567893', 'ROLE_BUSINESS'),
('user5', '1234', 'Nick5', 'User Five', 34, 'user5@example.com', '1234567894', 'ROLE_BUSINESS'),
('user6', '1234', 'Nick6', 'User Six', 35, 'user6@example.com', '1234567895', 'ROLE_BUSINESS'),
('user7', '1234', 'Nick7', 'User Seven', 36, 'user7@example.com', '1234567896', 'ROLE_BUSINESS'),
('user8', '1234', 'Nick8', 'User Eight', 37, 'user8@example.com', '1234567897', 'ROLE_BUSINESS'),
('user9', '1234', 'Nick9', 'User Nine', 38, 'user9@example.com', '1234567898', 'ROLE_BUSINESS'),
('user10', '1234', 'Nick10', 'User Ten', 39, 'user10@example.com', '1234567899', 'ROLE_BUSINESS');

-- Insert 3 users with ROLE_USER
INSERT INTO user (username, password, nickname, name, age, email, phone, authorities) VALUES
('user11', '1234', 'Nick11', 'User Eleven', 40, 'user11@example.com', '1234567800', 'ROLE_USER'),
('user12', '1234', 'Nick12', 'User Twelve', 41, 'user12@example.com', '1234567801', 'ROLE_USER'),
('user13', '1234', 'Nick13', 'User Thirteen', 42, 'user13@example.com', '1234567802', 'ROLE_USER');

INSERT INTO shop (name, description, category, status) VALUES
('Food Shop 1', 'Food shop description 1', 'FOOD', 'OPEN'),
('Food Shop 2', 'Food shop description 2', 'FOOD', 'OPEN'),
('Fashion Shop 1', 'Fashion shop description 1', 'FASHION', 'OPEN'),
('Fashion Shop 2', 'Fashion shop description 2', 'FASHION', 'OPEN'),
('Electronics Shop 1', 'Electronics shop description 1', 'ELECTRONICS', 'OPEN'),
('Electronics Shop 2', 'Electronics shop description 2', 'ELECTRONICS', 'OPEN'),
('Beauty Shop 1', 'Beauty shop description 1', 'BEAUTY', 'OPEN'),
('Beauty Shop 2', 'Beauty shop description 2', 'BEAUTY', 'OPEN'),
('Home Shop 1', 'Home shop description 1', 'HOME', 'OPEN'),
('Home Shop 2', 'Home shop description 2', 'HOME', 'OPEN');


-- Insert 3 items per shop with stock = 100
-- For simplicity, assuming the IDs are sequential
-- Insert items for Shop 1
INSERT INTO item (name, description, price, stock, shop_id) VALUES
('Food Item 1', 'Food item description 1', 10.0, 100, 1),
('Food Item 2', 'Food item description 2', 20.0, 100, 1),
('Food Item 3', 'Food item description 3', 30.0, 100, 1),

('Food Item 4', 'Food item description 4', 40.0, 100, 2),
('Food Item 5', 'Food item description 5', 50.0, 100, 2),
('Food Item 6', 'Food item description 6', 60.0, 100, 2),

('Fashion Item 1', 'Fashion item description 1', 15.0, 100, 3),
('Fashion Item 2', 'Fashion item description 2', 25.0, 100, 3),
('Fashion Item 3', 'Fashion item description 3', 35.0, 100, 3),

('Fashion Item 4', 'Fashion item description 4', 45.0, 100, 4),
('Fashion Item 5', 'Fashion item description 5', 55.0, 100, 4),
('Fashion Item 6', 'Fashion item description 6', 65.0, 100, 4),

('Electronics Item 1', 'Electronics item description 1', 100.0, 100, 5),
('Electronics Item 2', 'Electronics item description 2', 200.0, 100, 5),
('Electronics Item 3', 'Electronics item description 3', 300.0, 100, 5),

('Electronics Item 4', 'Electronics item description 4', 400.0, 100, 6),
('Electronics Item 5', 'Electronics item description 5', 500.0, 100, 6),
('Electronics Item 6', 'Electronics item description 6', 600.0, 100, 6),

('Beauty Item 1', 'Beauty item description 1', 5.0, 100, 7),
('Beauty Item 2', 'Beauty item description 2', 10.0, 100, 7),
('Beauty Item 3', 'Beauty item description 3', 15.0, 100, 7),

('Beauty Item 4', 'Beauty item description 4', 20.0, 100, 8),
('Beauty Item 5', 'Beauty item description 5', 25.0, 100, 8),
('Beauty Item 6', 'Beauty item description 6', 30.0, 100, 8),

('Home Item 1', 'Home item description 1', 50.0, 100, 9),
('Home Item 2', 'Home item description 2', 60.0, 100, 9),
('Home Item 3', 'Home item description 3', 70.0, 100, 9),

('Home Item 4', 'Home item description 4', 80.0, 100, 10),
('Home Item 5', 'Home item description 5', 90.0, 100, 10),
('Home Item 6', 'Home item description 6', 100.0, 100, 10);



INSERT INTO purchase (user_id, quantity, status, total_price) VALUES
(11, 1, 'PENDING', 10.0), -- User 11
(12, 2, 'PENDING', 40.0), -- User 12
(13, 3, 'PENDING', 90.0); -- User 13


INSERT INTO purchase_items (purchase_id, item_id) VALUES
(1, 1), -- Purchase 1 includes Item 1
(2, 2), -- Purchase 2 includes Item 2
(3, 3); -- Purchase 3 includes Item 3

UPDATE user SET shop_id = 1 WHERE id = 3;
UPDATE user SET shop_id = 2 WHERE id = 4;
UPDATE user SET shop_id = 3 WHERE id = 5;
UPDATE user SET shop_id = 4 WHERE id = 6;
UPDATE user SET shop_id = 5 WHERE id = 7;
UPDATE user SET shop_id = 6 WHERE id = 8;
UPDATE user SET shop_id = 7 WHERE id = 9;
UPDATE user SET shop_id = 8 WHERE id = 10;
UPDATE user SET shop_id = 9 WHERE id = 11;
UPDATE user SET shop_id = 10 WHERE id = 12;
