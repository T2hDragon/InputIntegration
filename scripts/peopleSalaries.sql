SELECT 
    person.id AS `Employee ID`,
    person.full_name AS `Full Name`,
    person.gender AS `Gender`,
    DATE_FORMAT(person.hire_date, '%Y-%m-%d') AS `Hire Date`,
    IF(salary_component.amount IS NULL, NULL, CAST(salary_component.amount AS CHAR(50))) AS `Current Salary`,
    salary_component.currency AS `Currency`,
    salary_component_expired.end_date AS `Date of salary expiration`
FROM
    person
		LEFT JOIN salary_component ON
			person.id = salary_component.person_id 
		    AND salary_component.start_date <= CURDATE() 
		    AND (salary_component.end_date IS NULL OR salary_component.end_date >= CURDATE())
		LEFT JOIN salary_component AS salary_component_expired ON
			person.id = salary_component_expired.person_id AND
			salary_component.id IS NULL;
