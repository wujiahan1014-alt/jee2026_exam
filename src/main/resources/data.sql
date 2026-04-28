DELETE FROM movie;
DELETE FROM user;

INSERT INTO user(id, name) VALUES (1, '张三');
INSERT INTO user(id, name) VALUES (2, '李四');

INSERT INTO movie(watch_time, name, price, review, user_id)
VALUES ('2026-02-17', '流浪地球2', 45.5,
        '宏大科幻场景极具冲击力，剧情饱满立意深刻很震撼', 1);

INSERT INTO movie(watch_time, name, price, review, user_id)
VALUES ('2026-02-18', '阿凡达2', 60,
        '奇幻深海世界观惊艳，视觉拉满，亲情主线细腻又打动人心视觉', 1);

INSERT INTO movie(watch_time, name, price, review, user_id)
VALUES
  ('2026-03-05', '满江红', 38.0, '剧情层层反转悬念十足，台词韵味十足感染力极强', 2),
  ('2026-04-10', '飞驰人生2', 42.0, '笑点与温情并存，赛车场面热血沸腾全程观感舒适', 2);