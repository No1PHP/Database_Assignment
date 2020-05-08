USE `restaurant`;

INSERT INTO `material`(`name`, `type`, `unit_price`, `availableTime`) VALUES ('flour', 0, 1.1, 1),
                                                                             ('rice', 0, 2.2, 2),
                                                                             ('noodle', 0, 3.3, 3),
                                                                             ('cooking oil', 0, 4.4, 4),
                                                                             ('pork', 2, 5.5, 5),
                                                                             ('mutton', 2, 6.6, 6),
                                                                             ('cabbage', 1, 7.7, 7),
                                                                             ('rape', 1, 8.8, 8);
INSERT INTO `recipe`(`recipeName`, `relevantIngredient`, `price`) VALUES ('小笼包', JSON_ARRAY('flour', 'pork'), 5.0),
                                                                         ('羊肉烧饼', JSON_ARRAY('mutton', 'flour', 'cooking oil'), 7.5);
INSERT INTO `stall`(`stall_name`, `stall_location`, `stall_rent`, `availableRecipe`) VALUES ('广东风味1', 6, 5000, JSON_ARRAY('小笼包')),
                                                                                            ('广东风味2', 10, 5000, JSON_ARRAY('小笼包', '羊肉烧饼'));
INSERT INTO `staff`(`staff_name`, `staff_category`, `Effe_work_time_starts`, `Effe_work_time_end`) VALUES ('admin', 0, '00:00:00', '00:00:00'),
                                                                                                          ('张三', 0, '09:00:00', '18:00:00'),
                                                                                                          ('李四', 0, '12:00:00', '21:00:00'),
                                                                                                          ('王五', 0, '16:00:00', '23:59:59');
INSERT INTO `accessinfo`(`position`, `AccessToOrder`, `AccessToStaff`, `AccessToStock`) VALUES ('admin', true, 1, 1),
                                                                                               ('leader', true, 1, 1),
                                                                                               ('buyer', true, 0, 1);
INSERT INTO `account`(`staffId`, `position`, `accountName`, `password`) VALUES (1, 'admin', 'admin', 'admin'),
                                                                               (2, 'leader', 'zhang3', '333333'),
                                                                               (3, 'buyer', 'li4', '444444'),
                                                                               (4, 'buyer', 'wang5', '555555');