DELIMITER $$

CREATE OR REPLACE FUNCTION insert_or_update_employees(json_data JSON) RETURNS INT
BEGIN
	DECLARE idx INT DEFAULT 0;
	DECLARE total_count INT DEFAULT JSON_LENGTH(json_data);
	DECLARE current_employee JSON;

	WHILE idx < total_COUNT DO
		SET current_employee = JSON_EXTRACT(json_data, CONCAT('$[', idx, '].data'));

		IF JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.id"')) IS NOT NULL THEN
			INSERT INTO person (id, full_name, gender, birthdate, employee_code, hire_date, termination_date)
			VALUES (
				JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.id"')),
				JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.full_name"')),
				JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.gender"')),
				CASE 
					WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.birthdate"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.birthdate"')) = 'null' THEN NULL 
					ELSE STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.birthdate"')), '%Y-%m-%d') 
				END,
				JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.employee_code"')),
				STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.hire_date"')), '%Y-%m-%d'),
				STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."person.termination_date"')), '%Y-%m-%d')
			)
			ON DUPLICATE KEY UPDATE 
				full_name = VALUES(full_name),
				gender = VALUES(gender),
				birthdate = VALUES(birthdate),
				employee_code = VALUES(employee_code),
				hire_date = VALUES(hire_date),
				termination_date = VALUES(termination_date);
		END IF;

		IF JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."salary_component.id"')) IS NOT NULL THEN
			INSERT INTO salary_component (id, person_id, amount, currency, start_date, end_date)
			VALUES (
					JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."salary_component.id"')),
					JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."salary_component.person_id"')),
					JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."salary_component.amount"')),
					JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."salary_component.currency"')),
					STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."salary_component.start_date"')), '%Y-%m-%d'),
					STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee, '$."salary_component.end_date"')), '%Y-%m-%d')
			)
			ON DUPLICATE KEY UPDATE 
				amount = VALUES(amount),
				person_id = VALUES(person_id),
				amount = VALUES(amount),
				currency = VALUES(currency),
				start_date = VALUES(start_date),
				end_date = VALUES(end_date);
		END IF;
		SET idx = idx + 1;
	END WHILE;
	RETURN 1;
END $$
DELIMITER ;
