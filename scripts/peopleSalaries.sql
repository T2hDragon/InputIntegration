
SELECT 
    person.id AS `Employee ID`,
    person.full_name AS `Full Name`,
    person.gender AS `Gender`,
    DATE_FORMAT(person.hire_date, '%Y-%m-%d') AS `Hire Date`,
    IFNULL(salary_component.amount, 0) AS `Current Salary`,
    IFNULL(salary_component.currency, 'USD') AS `Currency` 
FROM 
    person
LEFT JOIN 
    salary_component ON person.id = salary_component.person_id 
    AND salary_component.start_date <= CURDATE() 
    AND (salary_component.end_date IS NULL OR salary_component.end_date >= CURDATE());
