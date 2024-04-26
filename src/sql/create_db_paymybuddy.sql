CREATE SCHEMA `paymybuddy`;

DROP TABLE IF EXISTS `paymybuddy`.`account`;
CREATE TABLE `paymybuddy`.`account` (
                             `account_id` INT NOT NULL AUTO_INCREMENT,
                             `creation_date` DATE NOT NULL,
                             `balance` FLOAT NOT NULL DEFAULT '0.00',
                             `iban` VARCHAR(50) NOT NULL,
                             `email` VARCHAR(225) NOT NULL,
                             `password` VARCHAR(225) NOT NULL,
                             PRIMARY KEY (`account_id`),
                             UNIQUE INDEX `account_id_UNIQUE` (`account_id` ASC) VISIBLE,
                             UNIQUE INDEX `iban_UNIQUE` (`iban` ASC) VISIBLE,
                             UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `paymybuddy`.`user`;
CREATE TABLE `paymybuddy`.`user` (
                             `user_id` INT NOT NULL AUTO_INCREMENT,
                             `first_name` VARCHAR(50) NOT NULL,
                             `last_name` VARCHAR(50) NOT NULL,
                             `address` VARCHAR(225) NOT NULL,
                             `birthday` DATE NOT NULL,
                             `account_id` INT NOT NULL,
                             PRIMARY KEY (`user_id`),
                             UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
                             UNIQUE INDEX `account_id_UNIQUE` (`account_id` ASC) VISIBLE,
                             CONSTRAINT `account_id`
                                 FOREIGN KEY (`account_id`)
                                 REFERENCES `paymybuddy`.`account` (`account_id`)
                                 ON DELETE CASCADE
                                 ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `paymybuddy`.`transfer`;
CREATE TABLE `paymybuddy`.`transfer` (
                             `transfer_id` INT NOT NULL AUTO_INCREMENT,
                             `amount` FLOAT NOT NULL,
                             `transfer_date` DATE NOT NULL,
                             `sender_account_id` INT NOT NULL,
                             `recipient_account_id` INT NOT NULL,
                             PRIMARY KEY (`transfer_id`),
                             UNIQUE INDEX `transfer_id_UNIQUE` (`transfer_id` ASC) VISIBLE,
                             CONSTRAINT `sender_account_id`
                                     FOREIGN KEY (`sender_account_id`)
                                     REFERENCES `paymybuddy`.`account` (`account_id`)
                                     ON DELETE CASCADE
                                     ON UPDATE CASCADE,
                             CONSTRAINT `receiver_account_id`
                                 FOREIGN KEY (`recipient_account_id`)
                                     REFERENCES `paymybuddy`.`account` (`account_id`)
                                     ON DELETE CASCADE
                                     ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

USE paymybuddy;

INSERT INTO account VALUES (000011, '2024-03-11', 158.69, 'US15JDIM25968475125698', 'martin.jacob@yopmail.com', 'ThisIsAPassword123');
INSERT INTO account VALUES (000022, '2024-03-11', 589.65, 'US98IDUR15878845135248', 'suzanne.doe@yopmail.com', 'badpassword1');
INSERT INTO account VALUES (000033, '2024-03-11', 52.80, 'US12PDJT65841516847685', 'james.dean@yopmail.com', 'MotDePasse!54968');
INSERT INTO account VALUES (000044, '2024-03-11', 0.00, 'US75DTFN65484515467445', 'mary.darling@yopmail.com', 'bliblablou8975@');

INSERT INTO user VALUES (000001, 'Martin', 'Jacob', '568 Main Street, Cincinnati, Ohio, USA', '1999-09-09', 000011);
INSERT INTO user VALUES (000002, 'Suzanne', 'Doe', '98 Sunset Boulevard, Los Angeles, California, USA', '1980-10-10', 000022);
INSERT INTO user VALUES (000003, 'James', 'Dean', '366  Washington Place, Washington D.C., USA','1973-12-12', 000033);
INSERT INTO user VALUES (000004, 'Mary', 'Darling', '8796 Lee Way, Miami, Florida, USA', '1966-04-04', 000044);

INSERT INTO transfer VALUES (000111, 15.50, '2024-03-11', 000011, 000022);
INSERT INTO transfer VALUES (000222, 20.00, '2024-03-11', 000022, 000033);
INSERT INTO transfer VALUES (000333, 135.95, '2024-03-11', 000033, 000044);
INSERT INTO transfer VALUES (000444, 27.88, '2024-03-11', 000044, 000011);