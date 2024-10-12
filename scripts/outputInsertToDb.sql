SELECT insert_or_update_employees('[
        {
            "employeeCode": "611207BE",
            "action": "hire",
            "data": {
                "person.id": 6,
                "person.full_name": "Alberto Leonard",
                "person.gender": "M",
                "person.birthdate": null,
                "person.employee_code": "611207BE",
                "person.hire_date": "2092-07-01",
                "person.termination_date": "2092-06-16",
                "salary_component.id": 1,
                "salary_component.person_id": 6,
                "salary_component.amount": 3300,
                "salary_component.currency": "USD",
                "salary_component.start_date": "2022-01-01",
                "salary_component.end_date": "2022-01-31"
            },
            "payComponents": [
                {
                    "amount": 3300,
                    "currency": "USD",
                    "startDate": "2022-01-01",
                    "endDate": "2022-01-31"
                }
            ]
        },
        {
            "employeeCode": "660831F5",
            "action": "change",
            "data": {
                "person.id": 1,
                "person.full_name": "Hazel Webster",
                "person.gender": "F",
                "person.birthdate": null,
                "person.employee_code": "660831F5",
                "person.hire_date": "2098-09-06",
                "person.termination_date": "2092-06-17",
                "salary_component.id": 2,
                "salary_component.person_id": 1,
                "salary_component.amount": 4400,
                "salary_component.currency": "EUR",
                "salary_component.start_date": "2022-01-01",
                "salary_component.end_date": "2022-01-31"
            },
            "payComponents": [
                {
                    "amount": 4400,
                    "currency": "EUR",
                    "startDate": "2022-01-01",
                    "endDate": "2022-01-31"
                },
                {
                    "amount": 500,
                    "currency": "EUR",
                    "startDate": "2021-01-01",
                    "endDate": "2021-12-31"
                }
            ]
        },
        {
            "employeeCode": "6711242C",
            "action": "change",
            "data": {
                "person.id": 2,
                "person.full_name": "Tara Maynard",
                "person.gender": "F",
                "person.birthdate": null,
                "person.employee_code": "6711242C",
                "person.hire_date": null,
                "person.termination_date": null,
                "salary_component.id": 3,
                "salary_component.person_id": 2,
                "salary_component.amount": 4100,
                "salary_component.currency": "EUR",
                "salary_component.start_date": "2022-01-01",
                "salary_component.end_date": "2022-01-31"
            },
            "payComponents": [
                {
                    "amount": 4100,
                    "currency": "EUR",
                    "startDate": "2022-01-01",
                    "endDate": "2022-01-31"
                }
            ]
        },
        {
            "employeeCode": "7805046C",
            "action": "terminate",
            "data": {
                "person.id": 7,
                "person.full_name": null,
                "person.gender": "F",
                "person.birthdate": null,
                "person.employee_code": "7805046C",
                "person.hire_date": "2007-07-18",
                "person.termination_date": "2092-06-20",
                "salary_component.id": 4,
                "salary_component.person_id": 7,
                "salary_component.amount": 1500,
                "salary_component.currency": "EUR",
                "salary_component.start_date": "2022-01-01",
                "salary_component.end_date": "2022-01-31"
            },
            "payComponents": [
                {
                    "amount": 1500,
                    "currency": "EUR",
                    "startDate": "2022-01-01",
                    "endDate": "2022-01-31"
                }
            ]
        },
        {
            "employeeCode": "80072480",
            "action": "hire",
            "data": {
                "person.id": 8,
                "person.full_name": "Brock Salazar",
                "person.gender": "M",
                "person.birthdate": null,
                "person.employee_code": "80072480",
                "person.hire_date": "2013-05-31",
                "person.termination_date": "2092-06-21",
                "salary_component.id": 5,
                "salary_component.person_id": 8,
                "salary_component.amount": 1400,
                "salary_component.currency": "EUR",
                "salary_component.start_date": "2022-01-01",
                "salary_component.end_date": "2022-01-31"
            },
            "payComponents": [
                {
                    "amount": 1400,
                    "currency": "EUR",
                    "startDate": "2022-01-01",
                    "endDate": "2022-01-31"
                },
                {
                    "amount": 100,
                    "currency": "EUR",
                    "startDate": "2022-01-01",
                    "endDate": "2022-01-31"
                }
            ]
        },
        {
            "employeeCode": "90040543",
            "action": "change",
            "data": {
                "person.id": 4,
                "person.full_name": "Cole Sloan",
                "person.gender": "M",
                "person.birthdate": null,
                "person.employee_code": "90040543",
                "person.hire_date": "2015-05-31",
                "person.termination_date": "2092-06-22",
                "salary_component.id": 6,
                "salary_component.person_id": 4,
                "salary_component.amount": 3000,
                "salary_component.currency": "EUR",
                "salary_component.start_date": "2022-01-01",
                "salary_component.end_date": "2022-01-31"
            },
            "payComponents": [
                {
                    "amount": 3000,
                    "currency": "EUR",
                    "startDate": "2022-01-01",
                    "endDate": "2022-01-31"
                },
                {
                    "amount": 50,
                    "currency": "EUR",
                    "startDate": "2022-01-01",
                    "endDate": "2022-07-01"
                }
            ]
        },
        {
            "employeeCode": "2005055D",
            "action": "terminate",
            "data": {
                "person.id": 5,
                "person.full_name": null,
                "person.gender": null,
                "person.birthdate": null,
                "person.employee_code": "2005055D",
                "person.hire_date": null,
                "person.termination_date": "2024-10-13"
            },
            "payComponents": []
        },
        {
            "employeeCode": "020121F8",
            "action": "hire",
            "data": {
                "person.id": 9,
                "person.full_name": "Zaiden Arnold",
                "person.gender": null,
                "person.birthdate": "2021-01-02",
                "person.employee_code": "020121F8",
                "person.hire_date": "2022-03-01",
                "person.termination_date": "2021-01-01"
            },
            "payComponents": []
        },
        {
            "employeeCode": "22010100",
            "action": "hire",
            "data": {
                "person.id": 10,
                "person.full_name": "Norah Church",
                "person.gender": "F",
                "person.birthdate": "2019-03-03",
                "person.employee_code": "22010100",
                "person.hire_date": "2022-01-01",
                "person.termination_date": "2021-12-25"
            },
            "payComponents": []
        }
    ]'
) AS result;

