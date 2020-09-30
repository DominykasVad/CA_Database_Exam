CREATE TABLE `Users` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `phone_number` varchar(32),
  `email_address` varchar(255),
  `account_iban` varchar(32) NOT NULL
);

CREATE TABLE `Accounts` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `iban` varchar(32) NOT NULL,
  `balance` decimal(19,4) DEFAULT(0.0)
);

CREATE TABLE `Transactions` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `from_acccount_iban` varchar(32) NOT NULL,
  `to_account_iban` varchar(32) NOT NULL,
  `amount` decimal(19,4) NOT NULL
);

ALTER TABLE `Accounts` ADD FOREIGN KEY (`iban`) REFERENCES `Users` (`account_iban`);

ALTER TABLE `Transactions` ADD FOREIGN KEY (`from_acccount_iban`) REFERENCES `Accounts` (`iban`);

ALTER TABLE `Transactions` ADD FOREIGN KEY (`to_account_iban`) REFERENCES `Accounts` (`iban`);
