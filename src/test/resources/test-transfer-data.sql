INSERT INTO USER (ID, USER_NAME) VALUES(1, 'accounttest');
INSERT INTO USER (ID, USER_NAME) VALUES(2, 'accounttest2');
INSERT INTO ACCOUNT (ID, ACCOUNT_NUMBER, AGENCY, ACCOUNT_DIGIT, AGENCY_DIGIT, BALANCE, ACTIVE, USER_ID) VALUES (1, '99999', '1231', '1', '4', 9000, TRUE, 1);
INSERT INTO ACCOUNT (ID, ACCOUNT_NUMBER, AGENCY, ACCOUNT_DIGIT, AGENCY_DIGIT, BALANCE, ACTIVE, USER_ID) VALUES (2, '11111', '3342', '0', '2', 55000, TRUE, 2);
COMMIT;