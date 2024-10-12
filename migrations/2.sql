DELIMITER $$

CREATE OR REPLACE FUNCTION insert_or_update_employees(json_data JSON) RETURNS VARCHAR(11)
BEGIN
	DECLARE idx INT DEFAULT 0;
	DECLARE total_count INT DEFAULT JSON_LENGTH(json_data);
	DECLARE current_employee_action VARCHAR(11);
	DECLARE current_employee_data JSON;

	WHILE idx < total_COUNT DO
		SET current_employee_data = JSON_EXTRACT(json_data, CONCAT('$[', idx, '].data'));
		SET current_employee_action = JSON_EXTRACT(json_data, CONCAT('$[', idx, '].action'));
		IF current_employee_data IS NOT NULL THEN
			IF JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.id"')) IS NOT NULL THEN
				IF current_employee_action = '"terminate"' THEN
					UPDATE person
					SET
						termination_date = COALESCE(STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.termination_date"')), '%Y-%m-%d'), termination_date)
					WHERE
						id = JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.id"'));
				ELSEIF current_employee_action = '"hire"' THEN
					INSERT INTO person (id, full_name, gender, birthdate, employee_code, hire_date, termination_date)
					VALUES (
						JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.id"')),
						JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.full_name"')),
						CASE 
							WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.gender"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.gender"')) = 'null' THEN NULL 
							ELSE JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.gender"'))
						END,
						CASE 
							WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.birthdate"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.birthdate"')) = 'null' THEN NULL 
							ELSE STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.birthdate"')), '%Y-%m-%d') 
						END,
						JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.employee_code"')),
						STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.hire_date"')), '%Y-%m-%d'),
						CASE 
							WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.termination_date"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.termination_date"')) = 'null' THEN NULL 
							ELSE STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.termination_date"')), '%Y-%m-%d') 
						END
					);
				ELSEIF current_employee_action = '"change"' THEN
					UPDATE person
					SET
						full_name = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.full_name"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.full_name"')) = 'null' THEN NULL 
								ELSE JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.full_name"'))
							END, full_name),
						gender = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.gender"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.gender"')) = 'null' THEN NULL 
								ELSE JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.gender"'))
							END, gender),
						birthdate = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.birthdate"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.birthdate"')) = 'null' THEN NULL 
								ELSE STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.birthdate"')), '%Y-%m-%d') 
							END, birthdate),
						hire_date = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.hire_date"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.hire_date"')) = 'null' THEN NULL 
								ELSE STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.hire_date"')), '%Y-%m-%d') 
							END, hire_date),
						termination_date = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.termination_date"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.termination_date"')) = 'null' THEN NULL 
								ELSE STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.termination_date"')), '%Y-%m-%d') 
							END, termination_date)
					WHERE
						id = JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.id"'));
				ELSE
					return current_employee_action;
				END IF;
			END IF;

			IF JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."salary_component.id"')) IS NOT NULL THEN
				IF current_employee_action = '"hire"' THEN
					INSERT INTO salary_component (id, person_id, amount, currency, start_date, end_date)
					VALUES (
							JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."salary_component.id"')),
							JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."salary_component.person_id"')),
							JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."salary_component.amount"')),
							JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."salary_component.currency"')),
							STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."salary_component.start_date"')), '%Y-%m-%d'),
							STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."salary_component.end_date"')), '%Y-%m-%d')
					);
				ELSEIF current_employee_action = '"change"' THEN
					UPDATE salary_component
					SET
						currency = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.amount"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.amount"')) = 'null' THEN NULL 
								ELSE JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.amount"'))
							END, amount),
						currency = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.currency"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.currency"')) = 'null' THEN NULL 
								ELSE JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.currency"'))
							END, currency),
						start_date = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.start_date"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.start_date"')) = 'null' THEN NULL 
								ELSE STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.start_date"')), '%Y-%m-%d') 
							END, start_date),
						end_date = COALESCE(
							CASE 
								WHEN JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.end_date"')) IS NULL OR JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.end_date"')) = 'null' THEN NULL 
								ELSE STR_TO_DATE(JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."person.end_date"')), '%Y-%m-%d') 
							END, end_date)
					WHERE
						id = JSON_UNQUOTE(JSON_EXTRACT(current_employee_data, '$."salary_component.id"'));
				END IF;
			END IF;
		END IF;
		SET idx = idx + 1;
	END WHILE;
	RETURN '1';
END $$
DELIMITER ;
