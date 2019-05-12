MERGE INTO PUBLIC.ROLE(ID, NAME) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN');

MERGE INTO PUBLIC.USER(ID, ACTIVATION_CODE, NAME, EMAIL, PASSWORD, USERNAME, ADRESS) VALUES
(1, null, 'Admin', 'admin@admin', '$2a$10$CfiuRDgTnLgJa7zYFyoCuuo60r3P8yPRgr4gCrPCe0Q1DZRP.ls3G', 'admin', 'admin adress is secret'),
(2, null, 'Marchuk Pavel Petrovich', 'pavel@email', '$2a$10$CfiuRDgTnLgJa7zYFyoCuuo60r3P8yPRgr4gCrPCe0Q1DZRP.ls3G', 'pavel', 'Belarus, Vitebsk, Moskovski avenue 70/2, 310');

MERGE INTO PUBLIC.USER_ROLE(ID_USER, ID_ROLE) VALUES
(1, 2), (2, 1);

MERGE INTO PUBLIC.CONTRACT_OF_SALE(ID, CLIENT_ADRESS, CLIENT_FULL_NAME, DATE_OF_CREATION, FILENAME, IS_ACTIVE,
 LAST_CHANGE, OTHER_INFO, TITLE, SALE_OBJECT, SALING_PRICE, WARRANTY_PERIOD, ID_USER) VALUES
(1, 'Belarus, Minsk, Nezavisimosty avenue 34', 'Metraw Petr Centurion', DATE '2018-10-06', NULL, TRUE,
NULL, NULL, 'Sale BMW E36 to Minsk', 'Auto BMW E36', 8500.0, 365, 2),
(2, 'Russia, Moskow, Lenina street 334/14', 'Maserd Balera Petrovich', DATE '2019-03-16', NULL, TRUE,
NULL, NULL, 'Fridge LG to Moskow he-he-he', 'Fridge LG L342', 350.49, 90, 1);

MERGE INTO PUBLIC.COOPERATION_CONTRACT(ID, CLIENT_ADRESS, CLIENT_FULL_NAME, DATE_OF_CREATION, FILENAME, IS_ACTIVE,
LAST_CHANGE, OTHER_INFO, TITLE, CLIENT_RESPONSIBILITY, CREATOR_RESPONSIBILITY, KIND_OF_ACTIVITY, TERM,
TERMINATION_CONDITIONS, ID_USER) VALUES
(1, 'Belarus, Gomel, Nezavisimosty avenue 4', 'Bazuyk Michael Eldarovich', DATE '2019-01-01', NULL, TRUE,
TIMESTAMP '2019-01-08 16:54:19.783', 'This contact may be cool)', 'Bazuyk - Basketball school',
'pays taxes, pledge immediately 40%, supplies raw materials',
'provides workers, engaged in paperwork for the site and construction', 'building', 365, null , 2),
(2, 'Ukraine, Kiev, Nezavisimosty avenue 44', 'Parashlenko Taras Dmitrovich', DATE '2019-04-09', NULL, TRUE,
NULL, NULL, 'My first contract with UA(online education English)', 'pays taxes, pledge immediately 40%',
'provides employees, engaged in paperwork', 'education', 180, null , 2);

MERGE INTO PUBLIC.CREDIT_CONTRACT(ID, CLIENT_ADRESS, CLIENT_FULL_NAME, DATE_OF_CREATION, FILENAME, IS_ACTIVE,
LAST_CHANGE, OTHER_INFO, TITLE, ANNUAL_INTEREST, CREDIT_AMOUNT, TERM, ID_USER) VALUES
(1, 'Belarus, Mogilew, Lenina 43', 'Ivanov Ivan Ivanovich', DATE '2018-11-06', NULL, FALSE,
 TIMESTAMP '2019-01-07 18:21:13.715', 'the loan was early repaid 5 January 2019', 'Loan for auto Ivanov Mogilew', 12.6, 10000.0, 31, 2),
(2, 'Belarus, Minsk, Lenina 443', 'Vasechkin Vasiliy Ivanovich', DATE '2019-03-07', NULL, TRUE,
 TIMESTAMP '2019-05-08 16:51:46.548', null, 'loan22 332#35', 9.5, 1000.0, 90, 2),
(3, 'Russia, Moskow, Viktory avenue 34/4', 'Medvedev Ivan Dmitrievich', DATE '2018-05-08', NULL, FALSE, NULL,
 NULL, 'loan 32%exp RU', 32.0, 1500.0, 30, 2);

MERGE INTO PUBLIC.RENTAL_CONTRACT(ID, CLIENT_ADRESS, CLIENT_FULL_NAME, DATE_OF_CREATION, FILENAME, IS_ACTIVE,
LAST_CHANGE, OTHER_INFO, TITLE, END_RENTAL, RENTAL_OBJECT, RENTAL_PRICE, START_RENTAL, ID_USER) VALUES
(1, 'Belarus, Mogilew, Lenina 43', 'Medvedev Ivan Dmitrievich', DATE '2019-05-06', NULL, FALSE,
TIMESTAMP '2019-05-08 16:49:30.684', null, 'house on Lenina (day)', DATE '2019-05-07', 'house', 235, DATE '2019-05-06', 2),
(2, 'Russia, Moskow, Viktory avenue 34/4', 'Ivanov Ivan Ivanovich', DATE '2019-05-08', NULL, TRUE, NULL, NULL,
'lease 2 boys third flat', DATE '2020-05-14', '3-rooms flat', 50, DATE '2019-05-14', 2);

MERGE INTO PUBLIC.CATALOG_OF_OPERATION_MODE(ID, OPERATION_MODE) VALUES
(1, '1/2'), (2, '2/2'), (3, '3/4'), (4, '4/3'), (5, '5/2'), (6, '6/1'), (7, '7/7'), (8, '15/15'), (9, '30/30');

MERGE INTO PUBLIC.WORK_CONTRACT(ID, CLIENT_ADRESS, CLIENT_FULL_NAME, DATE_OF_CREATION, FILENAME, IS_ACTIVE,
LAST_CHANGE, OTHER_INFO, TITLE, HOLIDAY, POSITION, SALARY, START_WORK, TERM, WORKING_HOURS, ID_USER,
ID_OPERATION_MODE, PLACE_OF_WORK) VALUES
(1, 'Belarus, Mogilew, Lenina 43', 'Ivanov Ivan Ivanovich', DATE '2019-02-16', NULL, TRUE, NULL, NULL, 'programer C# №3234', 25, 'lead developer', 2300, DATE '2019-02-20', 180, 40, 2, 5, 'OOO Innowise Group'),
(2, 'Russia, Moskow, Viktory avenue 34/4', 'Vasechkin Vasiliy Ivanovich', DATE '2019-05-01', NULL, TRUE, NULL, NULL, 'work in silicon quarry', 40, 'loader', 1500, DATE '2019-05-02', 365, 56, 2, 7, 'SilicaMines Inc.');