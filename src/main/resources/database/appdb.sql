-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Czas generowania: 31 Sty 2018, 15:35
-- Wersja serwera: 10.1.29-MariaDB
-- Wersja PHP: 7.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Baza danych: `appdb`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `client`
--

CREATE TABLE `client` (
  `client_id` int(10) NOT NULL,
  `name` varchar(200) COLLATE utf8_polish_ci NOT NULL,
  `address` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `postalcode` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `phonenumber` varchar(15) COLLATE utf8_polish_ci NOT NULL,
  `nip` varchar(13) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `client`
--

INSERT INTO `client` (`client_id`, `name`, `address`, `postalcode`, `phonenumber`, `nip`) VALUES
(6, 'Rosenstrauch Arkadiusz', 'Ciechanowice 154/4', '58-410 Marciszów', '123321123', '123-321-11-22'),
(7, 'Sabat Angelika', 'ul. Spółdzielcza 44', '58-410 Marciszów', '555444333', ''),
(8, 'Mirek Michał', 'Ciechanowice 192', '58-410 Marciszów', '111333222', '555-444-33-22'),
(9, 'Grzelak Sebastian', 'Ciechanowice 131', '58-410 Marciszów', '555222111', '666-444-22-11');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `config`
--

CREATE TABLE `config` (
  `config_id` int(10) NOT NULL,
  `hirename` varchar(200) COLLATE utf8_polish_ci NOT NULL,
  `hireadress` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `hirepostalcode` varchar(100) COLLATE utf8_polish_ci NOT NULL,
  `hirephonenumber` varchar(15) COLLATE utf8_polish_ci NOT NULL,
  `hirenip` varchar(13) COLLATE utf8_polish_ci NOT NULL,
  `maxrentaltime` int(3) NOT NULL,
  `dailypenality` int(3) NOT NULL,
  `orderconfirmationfolder` varchar(250) COLLATE utf8_polish_ci NOT NULL,
  `returnconfirmationfolder` varchar(250) COLLATE utf8_polish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `config`
--

INSERT INTO `config` (`config_id`, `hirename`, `hireadress`, `hirepostalcode`, `hirephonenumber`, `hirenip`, `maxrentaltime`, `dailypenality`, `orderconfirmationfolder`, `returnconfirmationfolder`) VALUES
(1, 'Wypożyczalnia Sprzętu Budowlanego \"Wymyślona\"', 'ul. Wymyślona 13a', '58-410 Wymyślno', '444-222-111', '111-222-33-44', 7, 15, 'D:\\\\Dokumenty\\\\Praca Inżynierska\\\\pracadyplomowa\\\\confirmation\\\\rent', 'D:\\\\Dokumenty\\\\Praca Inżynierska\\\\pracadyplomowa\\\\confirmation\\\\return');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `item`
--

CREATE TABLE `item` (
  `item_id` int(10) NOT NULL,
  `name` varchar(200) COLLATE utf8_polish_ci NOT NULL,
  `bail` double(10,2) NOT NULL,
  `rentalprice` double(10,2) NOT NULL,
  `count` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `item`
--

INSERT INTO `item` (`item_id`, `name`, `bail`, `rentalprice`, `count`) VALUES
(1, 'Wiertarka akumulatorowa \"MAKITA\"', 75.00, 12.50, 10),
(2, 'Wiertarka udarowa \"BOSCH\"', 120.14, 22.70, 10),
(3, 'Młot pneumatyczny \"DEWALT\"', 230.00, 55.00, 10),
(4, 'Zagęszczarka do betonu \"EINHELL\"', 300.00, 75.00, 10);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rental`
--

CREATE TABLE `rental` (
  `client_id` int(10) NOT NULL,
  `item_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rentaldetails`
--

CREATE TABLE `rentaldetails` (
  `rental_id` int(10) NOT NULL,
  `item_id` int(10) NOT NULL,
  `rental_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `rental_count` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `rentalinfo`
--

CREATE TABLE `rentalinfo` (
  `client_id` int(10) NOT NULL,
  `rental_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `user`
--

CREATE TABLE `user` (
  `user_id` int(10) NOT NULL,
  `login` varchar(40) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(40) COLLATE utf8_polish_ci NOT NULL,
  `admin` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;

--
-- Zrzut danych tabeli `user`
--

INSERT INTO `user` (`user_id`, `login`, `password`, `admin`) VALUES
(1, 'admin', 'admin', 1),
(2, 'user', 'user', 0);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`client_id`);

--
-- Indexes for table `config`
--
ALTER TABLE `config`
  ADD PRIMARY KEY (`config_id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `rental`
--
ALTER TABLE `rental`
  ADD PRIMARY KEY (`client_id`,`item_id`),
  ADD KEY `fk_client` (`client_id`),
  ADD KEY `fk_item` (`item_id`);

--
-- Indexes for table `rentaldetails`
--
ALTER TABLE `rentaldetails`
  ADD PRIMARY KEY (`rental_id`);

--
-- Indexes for table `rentalinfo`
--
ALTER TABLE `rentalinfo`
  ADD PRIMARY KEY (`client_id`,`rental_id`),
  ADD KEY `fk_rentalinfo` (`client_id`),
  ADD KEY `fk_rental` (`rental_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT dla tabeli `client`
--
ALTER TABLE `client`
  MODIFY `client_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT dla tabeli `config`
--
ALTER TABLE `config`
  MODIFY `config_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT dla tabeli `item`
--
ALTER TABLE `item`
  MODIFY `item_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT dla tabeli `rentaldetails`
--
ALTER TABLE `rentaldetails`
  MODIFY `rental_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT dla tabeli `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Ograniczenia dla zrzutów tabel
--

--
-- Ograniczenia dla tabeli `rental`
--
ALTER TABLE `rental`
  ADD CONSTRAINT `fk_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`),
  ADD CONSTRAINT `fk_item` FOREIGN KEY (`item_id`) REFERENCES `item` (`item_id`);

--
-- Ograniczenia dla tabeli `rentalinfo`
--
ALTER TABLE `rentalinfo`
  ADD CONSTRAINT `fk_rental` FOREIGN KEY (`rental_id`) REFERENCES `rentaldetails` (`rental_id`),
  ADD CONSTRAINT `fk_rentalinfo` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
