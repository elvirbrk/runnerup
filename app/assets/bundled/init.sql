INSERT INTO health_type (_id, name) VALUES (1, 'Weight');
INSERT INTO health_type (_id, name) VALUES (2, 'Height');
INSERT INTO health_type (_id, name) VALUES (3, 'Blood pressure');
INSERT INTO health_type (_id, name) VALUES (4, 'Heart rate');

INSERT INTO health_value_type (_id, health_type_id, name, order_number) VALUES (1, 1, 'Weight', 1);
INSERT INTO health_value_type (_id, health_type_id, name, order_number) VALUES (2, 2, 'Height', 1);
INSERT INTO health_value_type (_id, health_type_id, name, order_number) VALUES (3, 3, 'Systolic', 1);
INSERT INTO health_value_type (_id, health_type_id, name, order_number) VALUES (4, 3, 'Diastolic', 2);
INSERT INTO health_value_type (_id, health_type_id, name, order_number) VALUES (5, 4, 'Rate', 1);
INSERT INTO health_value_type (_id, health_type_id, name, order_number) VALUES (6, 4, 'Irregular', 2);

INSERT INTO units (_id, unit_group, health_value_type_id, name, min_value, max_value, default_value, decimals) VALUES (1, 1, 1, 'kg', 10, 200, 80, 1);
INSERT INTO units (_id, unit_group, health_value_type_id, name, min_value, max_value, default_value, decimals) VALUES (2, 1, 2, 'cm', 100, 250, 180, 0);
INSERT INTO units (_id, unit_group, health_value_type_id, name, min_value, max_value, default_value, decimals) VALUES (3, 1, 3, 'mmhg', 50, 250, 120, 0);
INSERT INTO units (_id, unit_group, health_value_type_id, name, min_value, max_value, default_value, decimals) VALUES (4, 1, 4, 'mmhg', 30, 150, 80, 0);
INSERT INTO units (_id, unit_group, health_value_type_id, name, min_value, max_value, default_value, decimals) VALUES (5, 1, 5, 'bpm', 30, 250, 60, 0);
INSERT INTO units (_id, unit_group, health_value_type_id, name, min_value, max_value, default_value, decimals) VALUES (6, 1, 6, 'yes(1)/no(0)', 0, 1, 0, 0);
