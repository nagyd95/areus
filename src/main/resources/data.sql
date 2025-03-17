CREATE TABLE customers(
                          ID            INT PRIMARY KEY AUTO_INCREMENT,
                          USER_NAME     VARCHAR(50),
                          NAME          VARCHAR(50),
                          EMAIL         VARCHAR(100),
                          DATE_OF_BIRTH TIMESTAMP
);

INSERT INTO customers (NAME, USER_NAME, EMAIL, DATE_OF_BIRTH) VALUES ('Test Data', 'test01', 'email@test.hu', '1995-01-01');
INSERT INTO customers (NAME, USER_NAME, EMAIL, DATE_OF_BIRTH) VALUES ('Test Kata', 'test02', 'email@test.hu', '1991-01-01');
INSERT INTO customers (NAME, USER_NAME, EMAIL, DATE_OF_BIRTH) VALUES ('Test Tata', 'test02', 'email@test.hu', '2010-05-01');
INSERT INTO customers (NAME, USER_NAME, EMAIL, DATE_OF_BIRTH) VALUES ('Test Admin', 'admin', 'email@test.hu', '2000-01-01');
