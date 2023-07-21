-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- ホスト: 127.0.0.1
-- 生成日時: 2023-07-21 08:35:19
-- サーバのバージョン： 10.4.27-MariaDB
-- PHP のバージョン: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- データベース: `student_documentation_software`
--

-- --------------------------------------------------------

--
-- テーブルの構造 `login_logs`
--

CREATE TABLE `login_logs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `login_time` datetime NOT NULL DEFAULT current_timestamp(),
  `ip_address` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `login_logs`
--

INSERT INTO `login_logs` (`id`, `user_id`, `login_time`, `ip_address`) VALUES
(5, 24, '2023-07-21 14:39:15', '0:0:0:0:0:0:0:1'),
(6, 24, '2023-07-21 14:53:07', '0:0:0:0:0:0:0:1');

-- --------------------------------------------------------

--
-- テーブルの構造 `operation_logs`
--

CREATE TABLE `operation_logs` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `operation` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `operation_logs`
--

INSERT INTO `operation_logs` (`id`, `user_id`, `operation`, `created_at`) VALUES
(13, 24, 'Create SecretQuestion & Answer', '2023-07-21 14:39:20'),
(14, 24, 'Create First Setting', '2023-07-21 14:53:30');

-- --------------------------------------------------------

--
-- テーブルの構造 `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `account` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `master_key` varchar(255) NOT NULL,
  `second_master_key` varchar(255) DEFAULT NULL,
  `iv` varchar(255) NOT NULL,
  `secret_question` varchar(255) DEFAULT NULL,
  `secret_answer` varchar(255) DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `student_number` varchar(255) DEFAULT NULL,
  `student_type` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `birth_year` varchar(255) DEFAULT NULL,
  `birth_month` varchar(255) DEFAULT NULL,
  `birth_day` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `users`
--

INSERT INTO `users` (`id`, `account`, `password`, `master_key`, `second_master_key`, `iv`, `secret_question`, `secret_answer`, `class_name`, `student_number`, `student_type`, `last_name`, `first_name`, `birth_year`, `birth_month`, `birth_day`, `created_at`) VALUES
(24, '0ME9RYa7z5n1umV2maso0A==', '$2a$10$r9wSBaBsapGU1F.O1fKn3udOYmR59seKrA9IjX2msixFeAxdGkuru', '5LyDlokG7+S7VOUVOJPOLc4QupXdl+uE0twTAVb9iUI=', 'apWVT4lemzDS1hbR5sWYe7HHoYTRMyJ8JiCrigULdAw=', '0iEaDArMhv+AJOCmEe+ggA==', 'mauF3ayAR2p6hFiOpbTtbLguwJF28igIxOhxLQSZJh4nThqtG9NsJPlepqe5yaZc', '$2a$10$vz.z0JdPYPTK7GFsqkJHu.DxdL3riVkoMtsLON/uzMoMPiDYKKHIS', 'd6ZOEFaCj9m+DdZ7lXVKsQ==', 'PEAuy90iNl5QRzlv7c9nNA==', 'xD4QovEqH/N5kvHbVh0w+g==', 'XM134rjAhwqrLwDqlsR6sQ==', 'oLGYSJ6on/0RNaDNpiKHzehb50WL+X09D/qhMsDR0Cg=', 'Hq9p/gGBLwQ9hu61s//V5w==', 'O65HhytRCWZ6NdZbofmL8Q==', 'O65HhytRCWZ6NdZbofmL8Q==', '2023-07-21 14:38:52'),
(25, 'VxZ37gNsXXdpb2a80RBoLg==', '$2a$10$X2cMjbqduiyuyjsLYl7GMODG3fZMhCBvUNglKZt.FqDSqAvcD3xty', 'iRXUAvMR5LsWaDRTZtSoJAVCJztNP6LQX6URNEZlpww=', NULL, 'xuX+PrdqgxIkSmWrFmPooA==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-21 14:42:52');

--
-- ダンプしたテーブルのインデックス
--

--
-- テーブルのインデックス `login_logs`
--
ALTER TABLE `login_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- テーブルのインデックス `operation_logs`
--
ALTER TABLE `operation_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- テーブルのインデックス `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- ダンプしたテーブルの AUTO_INCREMENT
--

--
-- テーブルの AUTO_INCREMENT `login_logs`
--
ALTER TABLE `login_logs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- テーブルの AUTO_INCREMENT `operation_logs`
--
ALTER TABLE `operation_logs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- テーブルの AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- ダンプしたテーブルの制約
--

--
-- テーブルの制約 `login_logs`
--
ALTER TABLE `login_logs`
  ADD CONSTRAINT `login_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

--
-- テーブルの制約 `operation_logs`
--
ALTER TABLE `operation_logs`
  ADD CONSTRAINT `operation_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
