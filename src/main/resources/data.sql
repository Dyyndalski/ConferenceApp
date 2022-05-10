INSERT INTO topic(topic_name) VALUES('topic1'),
                                    ('topic2'),
                                    ('topic3');

INSERT into lecture(name, start_time, end_time, topic_id)
VALUES('nazwa1.1', '10:00:00', '11:45:00', 1),
      ('nazwa1.2', '12:00:00', '13:45:00', 2),
      ('nazwa1.3', '14:00:00', '15:45:00', 3),
      ('nazwa2.1', '10:00:00', '11:45:00', 1),
      ('nazwa2.2', '12:00:00', '13:45:00', 2),
      ('nazwa2.3', '14:00:00', '15:45:00', 3),
      ('nazwa3.1', '10:00:00', '11:45:00', 1),
      ('nazwa3.2', '12:00:00', '13:45:00', 2),
      ('nazwa3.3', '13:00:00', '16:10:00', 3);