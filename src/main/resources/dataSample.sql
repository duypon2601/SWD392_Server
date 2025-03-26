-- 1. Bảng `category`
INSERT INTO category (name) VALUES
                                ('Lẩu'),
                                ('Món khai vị'),
                                ('Món tráng miệng'),
                                ('Đồ uống'),
                                ('Nước chấm'),
                                ('Món ăn kèm');

-- 2. Bảng `food`
INSERT INTO food (description, image_url, is_deleted, name, status, category_id) VALUES
                                                                                     ('Lẩu hải sản cay với tôm, mực, nghêu', 'https://example.com/lauhaisan.jpg', 0, 'Lẩu hải sản', 'AVAILABLE', 1),
                                                                                     ('Lẩu bò với thịt bò Mỹ thượng hạng', 'https://example.com/laubo.jpg', 0, 'Lẩu bò Mỹ', 'AVAILABLE', 1),
                                                                                     ('Gỏi cuốn tôm thịt tươi ngon', 'https://example.com/goicuon.jpg', 0, 'Gỏi cuốn', 'AVAILABLE', 2),
                                                                                     ('Chè ba màu ngọt mát', 'https://example.com/chebamau.jpg', 0, 'Chè ba màu', 'AVAILABLE', 3),
                                                                                     ('Nước ép cam tươi nguyên chất', 'https://example.com/nuoccam.jpg', 0, 'Nước ép cam', 'AVAILABLE', 4),
                                                                                     ('Lẩu thái chua cay với hải sản tươi sống', 'https://example.com/lauthai.jpg', 0, 'Lẩu thái', 'AVAILABLE', 1),
                                                                                     ('Khoai tây chiên giòn rụm', 'https://example.com/khoaitaychien.jpg', 0, 'Khoai tây chiên', 'AVAILABLE', 2),
                                                                                     ('Bánh flan mềm mịn với caramel', 'https://example.com/banhflan.jpg', 0, 'Bánh flan', 'AVAILABLE', 3),
                                                                                     ('Trà sữa trân châu đường đen', 'https://example.com/trasua.jpg', 0, 'Trà sữa trân châu', 'AVAILABLE', 4),
                                                                                     ('Nước mắm chua ngọt đặc biệt', 'https://example.com/nuocmam.jpg', 0, 'Nước mắm chua ngọt', 'AVAILABLE', 5),
                                                                                     ('Rau muống xào tỏi thơm lừng', 'https://example.com/raumongxao.jpg', 0, 'Rau muống xào', 'AVAILABLE', 6),
                                                                                     ('Lẩu gà lá giang chua thanh', 'https://example.com/lauga.jpg', 0, 'Lẩu gà lá giang', 'AVAILABLE', 1),
                                                                                     ('Lẩu thập cẩm đầy đủ topping', 'https://example.com/lauthapcam.jpg', 0, 'Lẩu thập cẩm', 'AVAILABLE', 1),
                                                                                     ('Cánh gà chiên nước mắm', 'https://example.com/canhgachien.jpg', 0, 'Cánh gà chiên', 'AVAILABLE', 2),
                                                                                     ('Salad rau củ trộn sốt mè', 'https://example.com/salad.jpg', 0, 'Salad rau củ', 'AVAILABLE', 2),
                                                                                     ('Kem dâu tươi mát lạnh', 'https://example.com/kemdau.jpg', 0, 'Kem dâu', 'AVAILABLE', 3),
                                                                                     ('Sữa chua mít ngọt dịu', 'https://example.com/suachuamit.jpg', 0, 'Sữa chua mít', 'AVAILABLE', 3),
                                                                                     ('Nước dừa tươi nguyên chất', 'https://example.com/nuocdua.jpg', 0, 'Nước dừa', 'AVAILABLE', 4),
                                                                                     ('Sinh tố xoài thơm ngon', 'https://example.com/sinhtoxoai.jpg', 0, 'Sinh tố xoài', 'AVAILABLE', 4),
                                                                                     ('Nước chấm muối ớt xanh', 'https://example.com/muoiotxanh.jpg', 0, 'Muối ớt xanh', 'AVAILABLE', 5),
                                                                                     ('Tương đen đậm đà', 'https://example.com/tuongden.jpg', 0, 'Tương đen', 'AVAILABLE', 5),
                                                                                     ('Bắp cải luộc chấm trứng', 'https://example.com/bapcailuoc.jpg', 0, 'Bắp cải luộc', 'AVAILABLE', 6),
                                                                                     ('Nấm xào thập cẩm', 'https://example.com/namxao.jpg', 0, 'Nấm xào', 'AVAILABLE', 6),
                                                                                     ('Lẩu cá lóc đồng quê', 'https://example.com/lauca.jpg', 0, 'Lẩu cá lóc', 'AVAILABLE', 1),
                                                                                     ('Chả cá chiên giòn', 'https://example.com/chaca.jpg', 0, 'Chả cá chiên', 'AVAILABLE', 2),
                                                                                     ('Bánh tráng nướng giòn tan', 'https://example.com/banhtrang.jpg', 0, 'Bánh tráng nướng', 'AVAILABLE', 2),
                                                                                     ('Trái cây dầm mát lạnh', 'https://example.com/traicaydam.jpg', 0, 'Trái cây dầm', 'AVAILABLE', 3),
                                                                                     ('Nước chanh dây tươi mát', 'https://example.com/chanhday.jpg', 0, 'Chanh dây', 'AVAILABLE', 4),
                                                                                     ('Nước sâm bổ lượng giải nhiệt', 'https://example.com/samboluong.jpg', 0, 'Sâm bổ lượng', 'AVAILABLE', 4),
                                                                                     ('Tương ớt cay nồng', 'https://example.com/tuongot.jpg', 0, 'Tương ớt', 'AVAILABLE', 5),
                                                                                     ('Khoai lang nướng bùi thơm', 'https://example.com/khoailang.jpg', 0, 'Khoai lang nướng', 'AVAILABLE', 6);

-- 3. Bảng `restaurant`
INSERT INTO restaurant (is_deleted, location, name) VALUES
                                                        (0, '123 Đường Láng, Quận Đống Đa, Hà Nội', 'Lẩu Ngon Hà Nội'),
                                                        (0, '456 Nguyễn Trãi, Quận 5, TP.HCM', 'Lẩu Ngon Sài Gòn'),
                                                        (0, '789 Lê Lợi, Quận 1, TP.HCM', 'Lẩu Ngon Quận 1');

-- 4. Bảng `dining_table`
INSERT INTO dining_table (is_deleted, qr_code, status, restaurant_id) VALUES
                                                                          (0, '2f0c5d1ebf074d80a3ead8487015dd0e', 'OCCUPIED', 1),
                                                                          (0, '6ea371b2172640abaa12343e27d8b152', 'AVAILABLE', 1),
                                                                          (0, '9ea4397ef55940c5ae0ab63494a8dea9', 'AVAILABLE', 2),
                                                                          (0, '011e37deff7d4996a5e9285645dbf25b', 'AVAILABLE', 1),
                                                                          (0, '69ccbb21cdf24056b2f5f4888c33c112', 'OCCUPIED', 2),
                                                                          (0, '74a1961aa74543ba9edff34b8c017e52', 'AVAILABLE', 3),
                                                                          (0, '77c558e877aa45e8b0504737c2ce343f', 'AVAILABLE', 1),
                                                                          (0, 'a80beb25bc044d6f90bfbd9e47816e62', 'AVAILABLE', 1),
                                                                          (0, 'bf4d09a345a34b918ed4d8fe4629bd79', 'AVAILABLE', 2),
                                                                          (0, 'efa504c077654806803ad625552d43c9', 'AVAILABLE', 2);

-- 5. Bảng `orders`
INSERT INTO orders (created_at, status, total_price, updated_at, table_id) VALUES
                                                                               ('2025-03-26 12:00:00', 'PENDING', 500000.00, '2025-03-26 12:05:00', 1),
                                                                               ('2025-03-26 13:00:00', 'COMPLETED', 750000.00, '2025-03-26 13:30:00', 2),
                                                                               ('2025-03-26 14:00:00', 'CONFIRMED', 600000.00, '2025-03-26 14:10:00', 3),
                                                                               ('2025-03-26 15:00:00', 'PENDING', 450000.00, '2025-03-26 15:05:00', 5);

-- 6. Bảng `payment`
INSERT INTO payment (paid_at, payment_method, payment_status, transaction_id, order_id) VALUES
                                                                                            ('2025-03-26 12:10:00', 'CASH', 'PAID', 'TXN001', 1),
                                                                                            ('2025-03-26 13:35:00', 'CREDIT_CARD', 'PAID', 'TXN002', 2),
                                                                                            ('2025-03-26 14:15:00', 'MOBILE_PAYMENT', 'PAID', 'TXN003', 3),
                                                                                            ('2025-03-26 15:10:00', 'CASH', 'PENDING', 'TXN004', 4),
                                                                                            ('2025-03-26 15:30:00', 'CREDIT_CARD', 'PAID', 'TXN005', 1);

-- 7. Bảng `restaurant_menu`
INSERT INTO restaurant_menu (is_active, restaurant_id) VALUES
                                                           (1, 1),
                                                           (1, 2),
                                                           (1, 3);

-- 8. Bảng `restaurant_menu_item`
INSERT INTO restaurant_menu_item (is_available, price, food_id, restaurant_menu_id) VALUES
                                                                                        (1, 350000.00, 1, 1),  -- Lẩu hải sản tại Hà Nội
                                                                                        (1, 400000.00, 2, 1),  -- Lẩu bò Mỹ tại Hà Nội
                                                                                        (1, 50000.00, 3, 1),   -- Gỏi cuốn tại Hà Nội
                                                                                        (1, 350000.00, 1, 2),  -- Lẩu hải sản tại Sài Gòn
                                                                                        (1, 30000.00, 5, 2),   -- Nước ép cam tại Sài Gòn
                                                                                        (1, 380000.00, 6, 1),  -- Lẩu thái tại Hà Nội
                                                                                        (1, 45000.00, 7, 2),   -- Khoai tây chiên tại Sài Gòn
                                                                                        (1, 25000.00, 8, 3),   -- Bánh flan tại Quận 1
                                                                                        (1, 40000.00, 9, 1),   -- Trà sữa trân châu tại Hà Nội
                                                                                        (1, 20000.00, 10, 2),  -- Nước mắm chua ngọt tại Sài Gòn
                                                                                        (1, 35000.00, 11, 3),  -- Rau muống xào tại Quận 1
                                                                                        (1, 320000.00, 12, 1),  -- Lẩu gà lá giang tại Hà Nội
                                                                                        (1, 400000.00, 13, 2),  -- Lẩu thập cẩm tại Sài Gòn
                                                                                        (1, 60000.00, 14, 3),   -- Cánh gà chiên tại Quận 1
                                                                                        (1, 45000.00, 15, 1),   -- Salad rau củ tại Hà Nội
                                                                                        (1, 30000.00, 16, 2),   -- Kem dâu tại Sài Gòn
                                                                                        (1, 25000.00, 17, 3),   -- Sữa chua mít tại Quận 1
                                                                                        (1, 20000.00, 18, 1),   -- Nước dừa tại Hà Nội
                                                                                        (1, 35000.00, 19, 2),   -- Sinh tố xoài tại Sài Gòn
                                                                                        (1, 15000.00, 20, 3),   -- Muối ớt xanh tại Quận 1
                                                                                        (1, 10000.00, 21, 1),   -- Tương đen tại Hà Nội
                                                                                        (1, 20000.00, 22, 2),   -- Bắp cải luộc tại Sài Gòn
                                                                                        (1, 40000.00, 23, 3),   -- Nấm xào tại Quận 1
                                                                                        (1, 350000.00, 24, 1),  -- Lẩu cá lóc tại Hà Nội
                                                                                        (1, 50000.00, 25, 2),   -- Chả cá chiên tại Sài Gòn
                                                                                        (1, 30000.00, 26, 3),   -- Bánh tráng nướng tại Quận 1
                                                                                        (1, 45000.00, 27, 1),   -- Trái cây dầm tại Hà Nội
                                                                                        (1, 25000.00, 28, 2),   -- Chanh dây tại Sài Gòn
                                                                                        (1, 30000.00, 29, 3);   -- Sâm bổ lượng tại Quận 1


-- 9. Bảng `order_item`
INSERT INTO order_item (price, quantity, menu_item_id, order_id) VALUES
                                                                     (350000.00, 1, 1, 1),  -- 1 Lẩu hải sản
                                                                     (50000.00, 2, 3, 1),   -- 2 Gỏi cuốn
                                                                     (400000.00, 1, 2, 2),  -- 1 Lẩu bò Mỹ
                                                                     (380000.00, 1, 6, 3),  -- 1 Lẩu thái
                                                                     (45000.00, 2, 7, 3),   -- 2 Khoai tây chiên
                                                                     (40000.00, 3, 9, 4),   -- 3 Trà sữa trân châu
                                                                     (35000.00, 1, 11, 4);  -- 1 Rau muống xào

-- 10. Bảng `sub_order`
INSERT INTO sub_order (created_at, status, total_price, updated_at, order_id) VALUES
                                                                                  ('2025-03-26 12:02:00', 'CONFIRMED', 400000.00, '2025-03-26 12:03:00', 1),
                                                                                  ('2025-03-26 14:02:00', 'COMPLETED', 425000.00, '2025-03-26 14:05:00', 3),
                                                                                  ('2025-03-26 15:02:00', 'PENDING', 155000.00, '2025-03-26 15:03:00', 4),
                                                                                  ('2025-03-26 15:10:00', 'PENDING', 320000.00, '2025-03-26 15:15:00', 1),
                                                                                  ('2025-03-26 15:20:00', 'CONFIRMED', 400000.00, '2025-03-26 15:25:00', 2);


-- 11. Bảng `sub_order_item`
INSERT INTO sub_order_item (price, quantity, menu_item_id, sub_order_id) VALUES
                                                                             (350000.00, 1, 1, 1),   -- 1 Lẩu hải sản trong sub_order
                                                                             (50000.00, 1, 3, 1),    -- 1 Gỏi cuốn trong sub_order
                                                                             (380000.00, 1, 6, 2),   -- 1 Lẩu thái trong sub_order
                                                                             (45000.00, 1, 7, 2),    -- 1 Khoai tây chiên trong sub_order
                                                                             (40000.00, 2, 9, 3),    -- 2 Trà sữa trân châu trong sub_order
                                                                             (35000.00, 1, 11, 3),   -- 1 Rau muống xào trong sub_order
                                                                             (320000.00, 1, 12, 4),  -- 1 Lẩu gà lá giang
                                                                             (400000.00, 1, 13, 5);  -- 1 Lẩu thập cẩm


-- 12. Bảng `user`
INSERT INTO user (email, is_deleted, name, password, restaurant_name, role, token_device, username, restaurant_id) VALUES

                                                                                                                       ('manager@laungon.com', 0, 'Trần Thị Manager', 'hashed_password_456', 'Lẩu Ngon Sài Gòn', 'MANAGER', 'token456', 'manager1', 2),
                                                                                                                       ('staff@laungon.com', 0, 'Lê Văn Staff', 'hashed_password_789', 'Lẩu Ngon Hà Nội', 'STAFF', 'token789', 'staff1', 1),
                                                                                                                       ('staff2@laungon.com', 0, 'Phạm Thị Staff', 'hashed_password_101', 'Lẩu Ngon Sài Gòn', 'STAFF', 'token101', 'staff2', 2),
                                                                                                                       ('manager2@laungon.com', 0, 'Hoàng Văn Manager', 'hashed_password_102', 'Lẩu Ngon Quận 1', 'MANAGER', 'token102', 'manager2', 3),
                                                                                                                       ('staff3@laungon.com', 0, 'Nguyễn Thị Staff', 'hashed_password_103', 'Lẩu Ngon Hà Nội', 'STAFF', 'token103', 'staff3', 1),
                                                                                                                       ('manager3@laungon.com', 0, 'Trần Văn Manager', 'hashed_password_104', 'Lẩu Ngon Sài Gòn', 'MANAGER', 'token104', 'manager3', 2),
                                                                                                                       ('staff4@laungon.com', 0, 'Phạm Văn Staff', 'hashed_password_106', 'Lẩu Ngon Hà Nội', 'STAFF', 'token106', 'staff4', 1),
                                                                                                                       ('manager4@laungon.com', 0, 'Hoàng Thị Manager', 'hashed_password_107', 'Lẩu Ngon Sài Gòn', 'MANAGER', 'token107', 'manager4', 2),
                                                                                                                       ('staff5@laungon.com', 0, 'Ngô Văn Staff', 'hashed_password_108', 'Lẩu Ngon Quận 1', 'STAFF', 'token108', 'staff5', 3);

-- 13. Bảng `notifications`
INSERT INTO notifications (body, is_deleted, is_sent, sent_at, title, user_id) VALUES
                                                                                   ('Đơn hàng của bạn đã được xác nhận!', 0, 1, '2025-03-26 12:05:00', 'Xác nhận đơn hàng', 1),
                                                                                   ('Nhà hàng đã hết món Lẩu bò Mỹ', 0, 1, '2025-03-26 13:00:00', 'Thông báo hết món', 2),
                                                                                   ('Đơn hàng của bạn đã hoàn thành!', 0, 1, '2025-03-26 14:06:00', 'Hoàn thành đơn hàng', 3),
                                                                                   ('Vui lòng kiểm tra bàn QR005', 0, 0, NULL, 'Thông báo bàn trống', 2);