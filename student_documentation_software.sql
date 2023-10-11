-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- ホスト: 127.0.0.1
-- 生成日時: 2023-10-11 03:33:10
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
  `user_id` varchar(255) NOT NULL,
  `login_time` datetime NOT NULL DEFAULT current_timestamp(),
  `ip_address` varchar(45) DEFAULT NULL,
  `result` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `login_logs`
--

INSERT INTO `login_logs` (`id`, `user_id`, `login_time`, `ip_address`, `result`) VALUES
(1, '1', '2023-08-31 11:53:19', '0:0:0:0:0:0:0:1', ''),
(2, '2', '2023-08-31 12:14:10', '0:0:0:0:0:0:0:1', ''),
(3, '1', '2023-08-31 12:16:44', '0:0:0:0:0:0:0:1', ''),
(4, '3', '2023-08-31 12:20:31', '0:0:0:0:0:0:0:1', ''),
(5, '5', '2023-08-31 13:22:13', '0:0:0:0:0:0:0:1', ''),
(6, '5', '2023-08-31 13:23:59', '0:0:0:0:0:0:0:1', ''),
(7, '1', '2023-08-31 13:27:26', '0:0:0:0:0:0:0:1', ''),
(8, '1', '2023-08-31 13:27:26', '0:0:0:0:0:0:0:1', ''),
(9, '6', '2023-08-31 15:01:29', '0:0:0:0:0:0:0:1', ''),
(10, '2', '2023-09-05 10:04:26', '0:0:0:0:0:0:0:1', ''),
(11, '2', '2023-09-14 14:01:21', '0:0:0:0:0:0:0:1', ''),
(12, '2', '2023-09-20 09:56:48', '0:0:0:0:0:0:0:1', ''),
(13, '2', '2023-09-20 10:55:02', '0:0:0:0:0:0:0:1', ''),
(14, '1', '2023-09-21 14:22:57', '0:0:0:0:0:0:0:1', ''),
(15, '1', '2023-09-22 10:33:27', '0:0:0:0:0:0:0:1', ''),
(16, '2', '2023-09-25 09:21:02', '0:0:0:0:0:0:0:1', ''),
(17, '2', '2023-09-25 09:34:52', '0:0:0:0:0:0:0:1', ''),
(18, '2', '2023-09-25 09:36:00', '0:0:0:0:0:0:0:1', ''),
(19, '7', '2023-09-25 09:43:40', '0:0:0:0:0:0:0:1', ''),
(20, '2', '2023-09-25 09:51:57', '0:0:0:0:0:0:0:1', ''),
(21, '8', '2023-09-25 10:33:49', '0:0:0:0:0:0:0:1', ''),
(22, '5', '2023-09-25 12:11:03', '0:0:0:0:0:0:0:1', ''),
(23, '9', '2023-09-26 13:15:23', '0:0:0:0:0:0:0:1', ''),
(24, '9', '2023-09-26 13:16:51', '0:0:0:0:0:0:0:1', ''),
(25, '9', '2023-09-26 13:22:01', '0:0:0:0:0:0:0:1', ''),
(26, '10', '2023-09-26 13:26:36', '0:0:0:0:0:0:0:1', ''),
(27, '2', '2023-09-26 13:28:36', '0:0:0:0:0:0:0:1', ''),
(28, '2', '2023-09-26 13:31:27', '0:0:0:0:0:0:0:1', ''),
(29, '9', '2023-09-26 13:32:14', '0:0:0:0:0:0:0:1', ''),
(30, '11', '2023-09-26 13:32:38', '0:0:0:0:0:0:0:1', ''),
(31, '12', '2023-09-26 13:37:30', '0:0:0:0:0:0:0:1', ''),
(32, '2', '2023-09-26 13:55:20', '0:0:0:0:0:0:0:1', ''),
(33, '13', '2023-09-26 14:17:03', '0:0:0:0:0:0:0:1', ''),
(34, '13', '2023-09-26 14:21:08', '0:0:0:0:0:0:0:1', ''),
(35, '13', '2023-09-26 14:25:05', '0:0:0:0:0:0:0:1', ''),
(36, '1', '2023-09-26 14:33:44', '0:0:0:0:0:0:0:1', ''),
(37, '1', '2023-09-27 09:09:17', '0:0:0:0:0:0:0:1', ''),
(38, '1', '2023-09-27 09:44:50', '0:0:0:0:0:0:0:1', ''),
(39, '1', '2023-09-27 11:32:22', '0:0:0:0:0:0:0:1', ''),
(40, '1', '2023-09-27 12:15:43', '0:0:0:0:0:0:0:1', ''),
(41, '2', '2023-09-28 10:37:47', '0:0:0:0:0:0:0:1', ''),
(42, '1', '2023-09-28 15:47:32', '0:0:0:0:0:0:0:1', ''),
(43, '14', '2023-09-29 10:32:09', '0:0:0:0:0:0:0:1', ''),
(44, '15', '2023-09-29 11:26:40', '0:0:0:0:0:0:0:1', ''),
(45, '1', '2023-09-29 13:49:57', '0:0:0:0:0:0:0:1', ''),
(46, '16', '2023-09-29 14:38:04', '0:0:0:0:0:0:0:1', ''),
(47, '1', '2023-10-02 09:58:33', '0:0:0:0:0:0:0:1', ''),
(48, '1', '2023-10-02 10:42:19', '0:0:0:0:0:0:0:1', ''),
(49, '1', '2023-10-02 10:57:54', '0:0:0:0:0:0:0:1', ''),
(50, '1', '2023-10-02 11:26:46', '0:0:0:0:0:0:0:1', ''),
(51, '1', '2023-10-02 11:34:57', '0:0:0:0:0:0:0:1', ''),
(52, '1', '2023-10-02 12:08:32', '0:0:0:0:0:0:0:1', ''),
(53, '2', '2023-10-03 10:55:55', '0:0:0:0:0:0:0:1', ''),
(54, '1', '2023-10-03 13:32:54', '0:0:0:0:0:0:0:1', ''),
(55, '1', '2023-10-03 13:34:47', '0:0:0:0:0:0:0:1', ''),
(56, '1', '2023-10-03 14:41:25', '0:0:0:0:0:0:0:1', ''),
(57, '18', '2023-10-04 10:21:28', '0:0:0:0:0:0:0:1', ''),
(58, '18', '2023-10-04 11:51:11', '0:0:0:0:0:0:0:1', ''),
(59, '18', '2023-10-04 12:04:33', '0:0:0:0:0:0:0:1', ''),
(60, '18', '2023-10-04 12:08:17', '0:0:0:0:0:0:0:1', ''),
(61, '18', '2023-10-04 12:21:38', '0:0:0:0:0:0:0:1', ''),
(62, '18', '2023-10-04 12:31:54', '0:0:0:0:0:0:0:1', ''),
(63, '18', '2023-10-04 12:34:50', '0:0:0:0:0:0:0:1', ''),
(64, '18', '2023-10-04 12:46:08', '0:0:0:0:0:0:0:1', ''),
(65, '1', '2023-10-11 10:07:31', '0:0:0:0:0:0:0:1', 'LoginSuccess'),
(66, '19', '2023-10-11 10:08:04', '0:0:0:0:0:0:0:1', 'LoginSuccess'),
(67, '19', '2023-10-11 10:09:01', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(68, '19', '2023-10-11 10:09:57', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(69, '19', '2023-10-11 10:09:59', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(70, '19', '2023-10-11 10:10:01', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(71, '19', '2023-10-11 10:10:11', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(72, '19', '2023-10-11 10:10:14', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(73, '19', '2023-10-11 10:10:15', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(74, '19', '2023-10-11 10:10:18', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(75, 'NoAccount', '2023-10-11 10:10:20', '0:0:0:0:0:0:0:1', 'NoAccount'),
(76, '19', '2023-10-11 10:10:23', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(77, '19', '2023-10-11 10:10:25', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(78, '19', '2023-10-11 10:10:27', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(79, '19', '2023-10-11 10:15:10', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(80, '19', '2023-10-11 10:15:38', '0:0:0:0:0:0:0:1', 'LockTimeAccsess'),
(81, '19', '2023-10-11 10:19:01', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(82, '19', '2023-10-11 10:19:16', '0:0:0:0:0:0:0:1', 'LockTimeAccsess'),
(83, '19', '2023-10-11 10:19:20', '0:0:0:0:0:0:0:1', 'LockTimeAccsess'),
(84, '19', '2023-10-11 10:21:39', '0:0:0:0:0:0:0:1', 'LockTimeAccsess'),
(85, '19', '2023-10-11 10:22:06', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(86, '19', '2023-10-11 10:22:22', '0:0:0:0:0:0:0:1', 'LockTimeAccsess'),
(87, '19', '2023-10-11 10:22:25', '0:0:0:0:0:0:0:1', 'LockTimeAccsess'),
(88, '19', '2023-10-11 10:25:03', '0:0:0:0:0:0:0:1', 'LoginSuccess'),
(89, '19', '2023-10-11 10:26:40', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(90, '19', '2023-10-11 10:26:49', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(91, '19', '2023-10-11 10:26:51', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(92, '19', '2023-10-11 10:26:54', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(93, '19', '2023-10-11 10:26:56', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(94, '19', '2023-10-11 10:26:58', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(95, '19', '2023-10-11 10:27:00', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(96, '19', '2023-10-11 10:27:02', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(97, '19', '2023-10-11 10:27:05', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(98, '19', '2023-10-11 10:27:08', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(99, '19', '2023-10-11 10:27:10', '0:0:0:0:0:0:0:1', 'LockTimeAccsess'),
(100, '19', '2023-10-11 10:28:18', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(101, '19', '2023-10-11 10:29:47', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(102, '19', '2023-10-11 10:30:04', '0:0:0:0:0:0:0:1', 'WrongPassword'),
(103, '19', '2023-10-11 10:30:11', '0:0:0:0:0:0:0:1', 'LoginSuccess');

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
(1, 1, 'Create SecretQuestion & Answer', '2023-08-31 11:53:26'),
(2, 1, 'Create First Setting', '2023-08-31 12:13:36'),
(3, 2, 'Create SecretQuestion & Answer', '2023-08-31 12:14:14'),
(4, 2, 'Create First Setting', '2023-08-31 12:15:00'),
(5, 2, 'Printing Notification Of Change', '2023-08-31 12:15:45'),
(6, 1, 'Printing Student Discount Coupon', '2023-08-31 12:17:04'),
(7, 3, 'Create Vocational Trainee Setting', '2023-08-31 13:14:42'),
(8, 3, 'Create First Setting', '2023-08-31 13:15:51'),
(9, 5, 'Create SecretQuestion & Answer', '2023-08-31 13:22:20'),
(10, 5, 'Create First Setting', '2023-08-31 13:23:05'),
(11, 5, 'Forgot Password Recreate', '2023-08-31 13:23:53'),
(12, 1, 'Chage Vocational Trainee Setting', '2023-08-31 13:35:12'),
(13, 1, 'Change Address & Tel', '2023-08-31 13:38:16'),
(14, 1, 'Change Address & Tel', '2023-08-31 13:38:53'),
(15, 1, 'Change Name & Date of Birth', '2023-08-31 13:42:43'),
(16, 1, 'Change Name & Date of Birth', '2023-08-31 13:43:05'),
(17, 1, 'Change Student Infometion', '2023-08-31 13:46:22'),
(18, 1, 'Chage Vocational Trainee Setting', '2023-08-31 13:49:47'),
(19, 1, 'Printing Notification Of Change', '2023-08-31 14:03:27'),
(20, 1, 'Printing Notification Of Change', '2023-08-31 14:04:35'),
(21, 1, 'Printing Permission Bike', '2023-08-31 14:07:51'),
(22, 1, 'Printing Petition For Deferred-payment', '2023-08-31 14:10:45'),
(23, 1, 'Printing Recommended Delivery', '2023-08-31 14:13:37'),
(24, 1, 'Printing Student Discount Coupon', '2023-08-31 14:18:20'),
(25, 1, 'Printing Student Discount Coupon', '2023-08-31 14:22:23'),
(26, 1, 'Printing Supplementary Lessons', '2023-08-31 14:26:26'),
(27, 1, 'Printing Taking Re Test', '2023-08-31 14:28:27'),
(28, 2, 'Printing Petition For Deferred-payment', '2023-09-05 10:06:18'),
(29, 2, 'Change Name & Date of Birth', '2023-09-20 09:57:22'),
(30, 2, 'Change Student Infometion', '2023-09-20 09:57:43'),
(31, 2, 'Chage Vocational Trainee Setting', '2023-09-20 09:58:26'),
(32, 2, 'Printing Notification Absence Of Ttraining', '2023-09-20 09:59:16'),
(33, 2, 'Printing Certificate Vocational Training', '2023-09-20 10:01:17'),
(34, 2, 'Change Address & Tel', '2023-09-20 11:38:38'),
(35, 2, 'Change Student Infometion', '2023-09-20 12:35:08'),
(36, 8, 'Create SecretQuestion & Answer', '2023-09-25 10:34:15'),
(37, 8, 'Create First Setting', '2023-09-25 10:35:33'),
(38, 5, 'Forgot Password Recreate', '2023-09-25 12:08:19'),
(39, 5, 'Forgot Password Recreate', '2023-09-25 12:09:25'),
(40, 5, 'Forgot Password Recreate', '2023-09-25 12:10:57'),
(41, 9, 'Create SecretQuestion & Answer', '2023-09-26 13:15:29'),
(42, 9, 'Create First Setting', '2023-09-26 13:16:25'),
(43, 9, 'Change Password', '2023-09-26 13:16:44'),
(44, 9, 'Chage Vocational Trainee Setting', '2023-09-26 13:17:02'),
(45, 9, 'Forgot Password Recreate', '2023-09-26 13:21:56'),
(46, 9, 'Forgot Password Recreate', '2023-09-26 13:23:24'),
(47, 9, 'Forgot Password Recreate', '2023-09-26 13:25:32'),
(48, 10, 'Create SecretQuestion & Answer', '2023-09-26 13:26:39'),
(49, 11, 'Create SecretQuestion & Answer', '2023-09-26 13:32:41'),
(50, 11, 'Create First Setting', '2023-09-26 13:33:40'),
(51, 12, 'Create SecretQuestion & Answer', '2023-09-26 13:37:39'),
(52, 12, 'Create First Setting', '2023-09-26 13:39:25'),
(53, 13, 'Create SecretQuestion & Answer', '2023-09-26 14:17:08'),
(54, 13, 'Create First Setting', '2023-09-26 14:17:48'),
(55, 1, 'Change Student Infometion', '2023-09-27 09:09:33'),
(56, 1, 'Create Vocational Trainee Setting', '2023-09-27 09:09:46'),
(57, 1, 'Printing Interview Certificate', '2023-09-27 09:10:11'),
(58, 1, 'Printing Interview Certificate', '2023-09-27 09:44:03'),
(59, 1, 'Printing Attaching Receipts', '2023-09-27 09:44:55'),
(60, 1, 'Printing Interview Certificate', '2023-09-27 10:12:17'),
(61, 1, 'Printing Interview Certificate', '2023-09-27 10:12:30'),
(62, 1, 'Printing Interview Certificate', '2023-09-27 10:13:40'),
(63, 1, 'Printing Interview Certificate', '2023-09-27 10:14:26'),
(64, 1, 'Printing Interview Certificate', '2023-09-27 10:14:43'),
(65, 1, 'Printing Attaching Receipts', '2023-09-27 10:49:18'),
(66, 1, 'Printing Attaching Receipts', '2023-09-27 10:53:54'),
(67, 1, 'Printing Attaching Receipts', '2023-09-27 10:56:18'),
(68, 1, 'Printing Attaching Receipts', '2023-09-27 11:00:14'),
(69, 1, 'Printing Attaching Receipts', '2023-09-27 11:03:08'),
(70, 1, 'Printing Attaching Receipts', '2023-09-27 11:04:08'),
(71, 1, 'Printing Attaching Receipts', '2023-09-27 11:25:47'),
(72, 1, 'Printing Attaching Receipts', '2023-09-27 11:25:55'),
(73, 1, 'Printing Attaching Receipts', '2023-09-27 11:32:00'),
(74, 1, 'Printing Attaching Receipts', '2023-09-27 11:32:30'),
(75, 1, 'Printing Attaching Receipts', '2023-09-27 11:52:58'),
(76, 1, 'Printing Attaching Receipts', '2023-09-27 11:53:42'),
(77, 1, 'Printing Attaching Receipts', '2023-09-27 11:53:52'),
(78, 1, 'Printing Attaching Receipts', '2023-09-27 11:55:52'),
(79, 1, 'Printing Attaching Receipts', '2023-09-27 11:56:12'),
(80, 1, 'Printing Attaching Receipts', '2023-09-27 11:56:46'),
(81, 1, 'Printing Reasons for Non Attendance', '2023-09-27 12:16:12'),
(82, 1, 'Printing Interview Certificate', '2023-09-27 12:17:28'),
(83, 1, 'Printing Interview Certificate', '2023-09-27 12:17:59'),
(84, 1, 'Printing Interview Certificate', '2023-09-27 12:23:12'),
(85, 2, 'Change Student Infometion', '2023-09-28 10:39:19'),
(86, 2, 'Create Vocational Trainee Setting', '2023-09-28 10:39:33'),
(87, 2, 'Chage Vocational Trainee Setting', '2023-09-28 10:41:05'),
(88, 2, 'Printing Notification Absence Of Ttraining', '2023-09-28 10:41:34'),
(89, 2, 'Printing Taking Re Test', '2023-09-28 10:49:14'),
(90, 2, 'Printing Certificate Vocational Training', '2023-09-28 14:40:20'),
(91, 1, 'Printing Certificate Issuance', '2023-09-28 15:48:19'),
(92, 14, 'Create SecretQuestion & Answer', '2023-09-29 10:32:13'),
(93, 15, 'Create SecretQuestion & Answer', '2023-09-29 11:27:01'),
(94, 15, 'Create First Setting', '2023-09-29 11:39:22'),
(95, 15, 'Change Student Infometion', '2023-09-29 11:39:55'),
(96, 16, 'Create SecretQuestion & Answer', '2023-09-29 14:46:19'),
(97, 1, 'Printing Certificate Issuance', '2023-10-02 10:01:15'),
(98, 1, 'Printing Student Discount Coupon', '2023-10-02 10:43:14'),
(99, 1, 'Printing Student Discount Coupon', '2023-10-02 10:44:17'),
(100, 1, 'Printing Supplementary Lessons', '2023-10-02 10:58:25'),
(101, 1, 'Printing Petition For Deferred-payment', '2023-10-02 10:59:20'),
(102, 1, 'Printing Permission Bike', '2023-10-02 11:00:16'),
(103, 1, 'Printing Student Discount Coupon', '2023-10-02 11:31:07'),
(104, 1, 'Printing Notification Absence Of Ttraining', '2023-10-02 11:45:11'),
(105, 1, 'Printing Notification Absence Of Ttraining', '2023-10-02 12:08:46'),
(106, 1, 'Printing Notification Absence Of Ttraining', '2023-10-02 12:09:50'),
(107, 1, 'Change Name & Date of Birth', '2023-10-03 13:35:39'),
(108, 1, 'Change Name & Date of Birth', '2023-10-03 13:36:28'),
(109, 1, 'Change Name & Date of Birth', '2023-10-03 13:37:05'),
(110, 1, 'Change Address & Tel', '2023-10-03 13:38:13'),
(111, 18, 'Create SecretQuestion & Answer', '2023-10-04 10:31:05'),
(112, 18, 'Create First Setting', '2023-10-04 10:41:46'),
(113, 18, 'Create Vocational Trainee Setting', '2023-10-04 10:43:10'),
(114, 18, 'Printing Certificate Issuance', '2023-10-04 10:49:37'),
(115, 18, 'Printing Notification Of Change', '2023-10-04 10:49:56'),
(116, 18, 'Printing Notification Of Change', '2023-10-04 10:50:20'),
(117, 18, 'Printing Student Discount Coupon', '2023-10-04 10:58:52'),
(118, 18, 'Printing Student Discount Coupon', '2023-10-04 11:02:37'),
(119, 18, 'Printing Notification Of Change', '2023-10-04 11:03:05'),
(120, 18, 'Printing Notification Of Change', '2023-10-04 11:07:28'),
(121, 18, 'Printing Notification Of Change', '2023-10-04 11:08:32'),
(122, 18, 'Printing Notification Of Change', '2023-10-04 11:09:48'),
(123, 18, 'Printing Notification Of Change', '2023-10-04 11:12:17'),
(124, 18, 'Printing Attaching Receipts', '2023-10-04 11:14:02'),
(125, 18, 'Forgot Password Recreate', '2023-10-04 11:51:01'),
(126, 18, 'Printing Notification Absence Of Ttraining', '2023-10-04 11:52:11'),
(127, 18, 'Printing Attaching Receipts', '2023-10-04 12:04:39'),
(128, 18, 'Printing Attaching Receipts', '2023-10-04 12:08:22'),
(129, 18, 'Printing Attaching Receipts', '2023-10-04 12:11:26'),
(130, 18, 'Printing Attaching Receipts', '2023-10-04 12:21:46'),
(131, 18, 'Printing Attaching Receipts', '2023-10-04 12:30:33'),
(132, 18, 'Printing Attaching Receipts', '2023-10-04 12:31:18'),
(133, 18, 'Printing Attaching Receipts', '2023-10-04 12:31:58'),
(134, 18, 'Printing Attaching Receipts', '2023-10-04 12:33:38'),
(135, 18, 'Printing Attaching Receipts', '2023-10-04 12:34:54'),
(136, 18, 'Printing Attaching Receipts', '2023-10-04 12:35:25'),
(137, 18, 'Printing Attaching Receipts', '2023-10-04 12:42:57');

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
  `last_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name_ruby` varchar(255) DEFAULT NULL,
  `first_name_ruby` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `post_code` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birth_year` varchar(255) DEFAULT NULL,
  `birth_month` varchar(255) DEFAULT NULL,
  `birth_day` varchar(255) DEFAULT NULL,
  `admission_year` varchar(255) DEFAULT NULL,
  `admission_month` varchar(255) DEFAULT NULL,
  `admission_day` varchar(255) DEFAULT NULL,
  `student_type` varchar(255) DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `student_number` varchar(255) DEFAULT NULL,
  `school_year` varchar(255) DEFAULT NULL,
  `class_number` varchar(255) DEFAULT NULL,
  `name_PESO` varchar(255) DEFAULT NULL,
  `supply_number` varchar(255) DEFAULT NULL,
  `attendance_number` varchar(255) DEFAULT NULL,
  `employment_insurance` varchar(255) DEFAULT NULL,
  `login_failure_count` varchar(255) NOT NULL DEFAULT '0',
  `account_lock_time` datetime DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `users`
--

INSERT INTO `users` (`id`, `account`, `password`, `master_key`, `second_master_key`, `iv`, `secret_question`, `secret_answer`, `last_name`, `first_name`, `last_name_ruby`, `first_name_ruby`, `tel`, `post_code`, `address`, `birth_year`, `birth_month`, `birth_day`, `admission_year`, `admission_month`, `admission_day`, `student_type`, `class_name`, `student_number`, `school_year`, `class_number`, `name_PESO`, `supply_number`, `attendance_number`, `employment_insurance`, `login_failure_count`, `account_lock_time`, `created_at`, `is_deleted`) VALUES
(1, '/McJo72yxr8Ey8pH82bnHQ==', '$2a$10$ORAO43EElVQX/f.MmuO6jOKcf86XNgR3wFNvY8ilrDRRyNzJGR9eK', 'u5T8pkfsV9i/AvPMBmR1BUg+KzKRU7XNRRrHgCfCXo61wT/44ymnoZCyONKueff3', 'XfILB8vLc91anBzfFeFVG02CmRRpfOitENYg8mxu6Jhf6g53YPGlM8QO1o6u07Of', 'iUgsHHsB5ckUMtBCme6Z7Q==', 'luVX+bib3lxkwPP1Horsvvabp/5vJvf5wHQfclGU2u850X4KGk46ZTeYyUEcEm2Q', '$2a$10$FSy8rOKo/PgvUf/X/iaFX.p6BajYPRsWteeZfKlkhj6sZzb5b6bM2', '4xt4CKLOggRo+gu8TQFqa5UBgv2NW4CBLwDrxh0iF5g=', '4xt4CKLOggRo+gu8TQFqa5UBgv2NW4CBLwDrxh0iF5g=', '1qyeY/McALjTxmzXM42yC5Earaaig8VQ5WqO+YJeldg=', 'mQeK8/NmtZ05pTowQXVJYZAVKvsf9Jhv4uj2YieU6Sk=', 'hmNDDmY7+PmJ8/qnODxXfynSdgZjKdki29LdMgPVzYg=', 'U2H8/txeJJq56kwdu9Mfkpr9KtQtERdGbyEscNp32ug=', '8BXV2PtZmg4JnaZBKakMng0immzIkB0hxIljtEsFigvD87+wSrm6CtAisi7airWnD/juLy2LRbAC3eQ9UMlkbq7Vgl4tt+EkT6VG66QkDug=', 'YcTjbsQBlLyoFqDubLFyz+oXyma1m+UdA9Q8Ui5VuNg=', 'CEMzdhj1Y7QRAf6EAxWCutvzstC+iKoLXpk/Pm0grrk=', 'CEMzdhj1Y7QRAf6EAxWCutvzstC+iKoLXpk/Pm0grrk=', 'QBJetn8NNt/MvvSIOY1PdXw/+8S8XJw6rZg5cP3aP1Q=', 'FMjB3f0OInoH7qCQ/0BVNKWWdcDvRcn9wtJBtugsPUc=', 'CEMzdhj1Y7QRAf6EAxWCutvzstC+iKoLXpk/Pm0grrk=', 'BAw+xSJTdwMUWj7OlVkMGeWXG6Uo6XaSpIM2Q6vQS9A=', 'WdCmC/FoTETzEvZf3KILy8GSS/PEF/t50U69XKDYSTLQhCZZvP22gb5aYg/amkaxJKGynVX+apTWQg0telh7HQvxUeHnwd+DgW1RfnCiTXQ=', 'mnxwKWdqyozk5ctgrnjrhkBXzMpzBbwVZJUT4X/JQXw=', 'FMjB3f0OInoH7qCQ/0BVNKWWdcDvRcn9wtJBtugsPUc=', 'CEMzdhj1Y7QRAf6EAxWCutvzstC+iKoLXpk/Pm0grrk=', 'a4SvQ4ZA2KppZXBK3QgCzo1TGP3ZnopQP1p5Klbt2Q8=', 'gFyiD8/EMw8SObqSIK7j4SOk0/dJ4d3XJ9PRJip9NLRgVAW06MkyvzLppnKO/rTm', 'WwCIRlXQF31uPRvoNPvT0GYQLfFFFqVURWlr3QzLIqU=', 'UUQBp6K2jl7WiHpS9GvBMEKU4DGziBINhA7l4iguKJA=', '0', NULL, '2023-08-31 11:53:12', 0),
(2, 'rGROoeLNh5cnXjDBi4rwLA==', '$2a$10$NV.0mxit5AVCbpOj9C4VEua6bFhLcmnS/uLHM/9o5Y9ZjklAMVqSi', 'D6Xud5YetrKBcRD1f7FvlFdTQ5UVYUjsxWsoi3bizjCbWfJPbm5oG86M9SM2N+Ej', '4F+YeYv2ljuUKo+3RJNpkl6KSzLXpMmetSOroVtyrfdbTwrTw+Hp9XyW2UTiaOnR', 'dHjmiJ89CfAFUQGt4DORnA==', 'S/3YAxhHud0N61CDKzRmXqidqOx8htJW6EzfEy31Z7z8vdHs5N6tYdSsAx1gF6G1', '$2a$10$/26aRoc4LxpgD3s31GPbZueeEr0Of5KU8kBjWKa8lToUIiU9Ju6XG', '50iC6kgJ0ju9Zvndy/OaP5W6zTkctom0fzRZfCE0qb4=', 'qbpqbuGNtekCpeTV/JMypDiyShq7q4qyjRZ6zOxa70U=', '3P1B0GYu5CG4IhrG1m/ComLPgqclqmWXZIqUUwStXEg=', '38i4XpKF0yDjVEXRBiqmKEC0VRt20HWmYwLFw1r/TPo=', 'Q8yxfG8wXgsYI8FEW4v1bw0xeTVKk1LDTNs/1drKxDw=', 'hnP5YFHakfDdAUk6fsPO+ywGkoZ6F18JfRt/SaC9iVY=', '/Hrdb9hvkMfnbxf+L/PYIEXuLaDsl05DzYwdptwKkdpesJcLjnosHOaijHYJd2077UQgR0B2jfPfT0zXOT3tJECyRJ5eL6Nj8zZJ46oBkpM=', 'jQdHROwJnlV6KfxbpJhxds74rnOmc+0jUIJSwi2ytRk=', 'bM70KJOMXhIR2NhDq69CasEK8zw/3XHjsMcrRY1Rs7g=', 'bM70KJOMXhIR2NhDq69CasEK8zw/3XHjsMcrRY1Rs7g=', 'OMIxEqUfLxDNqp2ovc+ePRieGe9qVei1XqUcTZMfOTo=', 'xzR5PYQcwvGwpuEZNln04+c1ycYF9Xzg7us9R7eOhPk=', '3DSjxtzlQyd7n/V7bauOy72IB1ZlwUFg5GmdQGwV1s0=', 'hy0SLMdtj6DXZZIH4odHt/NvcRT1pQR7itPZIxjPIKg=', 'O7OM3L9OFZGF3oBked/JhQRwnq53Jz/MPBFqCRq8ssGfIkO3ZxVzN34s3789H2xn', 'JztC4BSO/CYJ8s+0eFyevfxljcBTUW1uOo03n1sVsZE=', 't2N2VLlu7q1TIDsxb8VcLULQG0fgzoVj19oXZUk4B+g=', 'bM70KJOMXhIR2NhDq69CasEK8zw/3XHjsMcrRY1Rs7g=', 'BBnS8vbr2yBhSHySQlfuI4AEwqfB74P+xxbZc3gYKJA=', 'MzelkT7Y1PHAGvIXKBUvUDkZk9qqUXRyLFmdTICmpTY=', 'JKntpthPirQfenc8cNMyqxHLmV9c+avS18MKXBLpbEk=', 'CYULriqc8UO1Zt+DIRaagckzLEHr4qH/aGBry4K614o=', '0', NULL, '2023-08-31 12:14:03', 0),
(3, 'p60BXaCdGvA4nc3mlh8R7p1KLR6dfZS/9OPuhRoR8UU=', '$2a$10$CsWpFdRIyoo2e1VM/Z7W2OVUxr.NUTEzW5kJLM.lbbh1SXRFAjCxG', 'NtPBId3+1GGzAIJbqIR4eGd/Xh599RU0Lw2SIyaI4fQcGoIrBpvovAPRdFOQleZy', NULL, 'PD8X2UHXlMHm5VhimKgAyQ==', NULL, NULL, 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'ocMjNpB7Q53UCem3Guw9Uv2gUg5dCqbSxU1vz7xS+Ew=', 'n8vRfJ98Yk8MJ+74ImHzzNvR5PsNLoBHMLFNxG5YjSusDtCcuOoxBZS7LRTSQyw1', 'n8vRfJ98Yk8MJ+74ImHzzNvR5PsNLoBHMLFNxG5YjSusDtCcuOoxBZS7LRTSQyw1', '1S/CdnkgNvR583orefI3fz9nryHKkwr372ML43voyA0=', 'RorNHjqa87Q1LS84z0c1rDYKrcGJcWruqN6p32SJDYw=', '0', NULL, '2023-08-31 12:20:14', 0),
(4, 'o6s+s6rN69QGzdVFiJZtTxmxYHyLaJQIzYBzp7r2hzA=', '$2a$10$T0QWHpbDcNTccwx14UuOne1QnUR1o.A5B3YX8UrtLV/UygsnBDXIS', 'v/hydRzrlO+tSlaYvf9CV6LC2pyqLyDNFvJiKv3kkggkslLycN7NN1MhjaULSh1M', NULL, 'gmmUPD5JtCAAsdspRA2NjQ==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '2023-08-31 12:22:48', 0),
(5, 'EFvoIj70otm5YBgcTQQoIg==', '$2a$10$ZGI7v8RZZgPdcaAkeaNwHeHszneNVCm03Ueh8T6LhRwJLsPGAgDIW', 'wkUgaT/2zRJqVXlpwlcI3yj+/NGoBgPKirmRRDQP4oSKbs/Y+smvuLW6G69I6+VY', 'tWmtfbtVCEYHD1hmZliz9+DOwYPraG1+WuqoTKEq+A4+XECOMLNImY4LzitJuPlt', 'deSkULPpv4jm6uPr9h5y7A==', 'LcAhqwAaEF56a9SflMlGK8mbhtXaPIa3aIX+VbI502MDxWlG+VN2kH/PIJmReG54', '$2a$10$6h90cTHLfRyW4BDm06q4pOBmj0n7tvI5fJ2AHNCQeANTlCQi3ykAm', 'aXfhcCrS2j40iSUJkMsFNMDSfMpWBoxsyjxzc8VdYdM=', 'aTs8Gdv7n7laQi18sLJXh4RdjiP2Klu5YWrILdCiJ1A=', 'ceh7OFohGpFPhF/VTUzUlJa/wyMS8vUW+MR3kWMwU1o=', '+99jhQcevB98m0tWy7zEfakbcMKMqrK+a27C+22LZOE=', 'ETaGuID5WR1ZUlctziiUMfmhxD8f14W74tLe7nH9upc=', 'zzJqWIXv350WcylTSW2++pjF6EoDgVtYS3T0ilkzbFA=', 'ZVtSElX7S64fph1CsMmGB++pVgtisiD7c6GJeF27BUVq4u3GbxszNTDj5RKC46suM6dzkkX9wf1WJAqfsPBeesFY60MfoLv/rt6xdjLk3WP8Ilg31M/qs9sQGUJ2UjQB', 'RvbNZsm4e9HeUZolQC9fmJG+jylCGFPLLzTWgcerh6U=', '78/I01otaIKKdMLsvhZG8/6GigOhA1rWqNqCCINVmlc=', '78/I01otaIKKdMLsvhZG8/6GigOhA1rWqNqCCINVmlc=', 'LVHfUZD6opwXDteDTcvlnsZXoaxa8zj8QgIhGFBbkRs=', '78/I01otaIKKdMLsvhZG8/6GigOhA1rWqNqCCINVmlc=', '78/I01otaIKKdMLsvhZG8/6GigOhA1rWqNqCCINVmlc=', 'is2rR6uL9VEtfm37lDttts2+qqxUjP5kjcnSQpGhGUI=', 'zqbteSO0heRX3dD7jaqT40sCoxlBhPhb2Pb1BZnDZgIqfFUPql2C4twsV26vHch7', '47681WiHmgwW3qRY9bDUa3t5S83D2oCRhJD1UmCJe6w=', '78/I01otaIKKdMLsvhZG8/6GigOhA1rWqNqCCINVmlc=', '78/I01otaIKKdMLsvhZG8/6GigOhA1rWqNqCCINVmlc=', NULL, NULL, NULL, NULL, '0', NULL, '2023-08-31 13:22:06', 0),
(6, 'uCT4Ko7g4zJKLx6KgaMPhw==', '$2a$10$UNKIRXPWbtdlbI/K8I/sO.EmPnk33qfIVNlkAufRxVeTjsN5EIPAy', 'h5+D0tPxjDIkiToLE1G4Ugu2QVyMVqKwrz/V0ZGMZtf09pMhnFeVPkVELbZnftFU', NULL, '+mkkc+Dzs9f505MxBtFmeA==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '2023-08-31 15:01:22', 0),
(7, 'pkbglc6CEBDM2MS0/37UnA==', '$2a$10$5vSrK7JX1rw26YBR35rvzORPlr5L0cGXD2mKaaYucARM6gAlwTraa', 'dTXAN1uiRN45cz7G8CZnB6Wpg9oFiokDFW+HMouIiA3kafi2owPLwX1S4zyXvwe7', NULL, 'IAatMQhpaA/g6pLm9mE7HA==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '2023-09-25 09:43:26', 0),
(8, 'bzZYJv+WdekCXxrta9wwxA==', '$2a$10$nkeaIYKvY8w76zeU1Rx4jerGcJ4.vR2NSW.il9e/wAK0fhe/9QBBi', 'NhUny902dUg6POE726uQ9I8lohl0Jh3F+TcaO6++pEXkjsCP2d0GmhdPvAr2xsPw', 'KhKezXdnFN+kFdhdfaHmIpOXVnT9rFwilKyLVDA7sVICuf7Q1C/hpoglr/f2mYwE', '0AohC8saxa1YVc27DPCatg==', 'JVwMywgCdeBAL3pk+o8VQI+AELt0gkpmt/1qKzv/sa1/EsmD85jCczN+ZCg/iPZu', '$2a$10$HwxjFvdy30xQfk7EsLWraOxzDNlDQm6Kv.1lGhOOsY1ILZX4rCxNe', '+LvMAg+aV1jseJyxEHVelnMUbOKFSI6lBdOhTrCp96k=', 'T4kyJa6nvYJ/gpIeWy0AxOY1Q/0gQaVcoqRZN+leKFecqNdRtq9JiREd+iKrkbCg', 'N+h2KxqANWS138ALbYX2MBBdsiaQHUFT+bPwT20HycQ=', 'TGUzBGtwg/4gfEAKYwVlugTD3bjn+Ofl7fHiy4zfue4=', 'vdcsvxVJebqXs2zhDsvlhsPJGW8dNdnGK1dfSO1IRvE=', 'qsi6hnsFmhnhHsuER2Dl84TIQ906Vl8lPvKAYyF8t8U=', 'GXgGrORfkWfbRqp02NbzIqTv9kh/P2w4hUHEJgWI0kaVM/c2BpN9xWm5mYLxR6lST740gNYmpouNyz3SKqYB34lnRTT59oPiCyQN0weQUgs=', 'jFrwqQIEhZR6kF18ty3TOc6cDyh4PUhF3rg5fcjl9zA=', 'TpFZnr8CwQS90AwaFOURCmtH56CPIScg64lVYkJGeHI=', 'TpFZnr8CwQS90AwaFOURCmtH56CPIScg64lVYkJGeHI=', 'Hy+Pw3mpezTpz0BqXvjjqppgh1wECkicxaoe7b1KMTk=', 'TpFZnr8CwQS90AwaFOURCmtH56CPIScg64lVYkJGeHI=', 'TpFZnr8CwQS90AwaFOURCmtH56CPIScg64lVYkJGeHI=', '52TAT+7hYUCgVA+++mWqhdClxjE5WN1R0AMWPshwvrw=', 'gMGa/o4bKHBLFfB6cV2wVL+cDw19DXZk+5g6H1Polm/v/yIqszGHDQ7KJBsfCqxosabdrW5teEguOcoKNQ76GU4BfLP/2d8vaD8wD9roV0c=', 'cBusBm6YT4JVMQabhooR+SUW4H0VIOGRu17ILpah8kU=', 'TpFZnr8CwQS90AwaFOURCmtH56CPIScg64lVYkJGeHI=', 'TpFZnr8CwQS90AwaFOURCmtH56CPIScg64lVYkJGeHI=', NULL, NULL, NULL, NULL, '0', NULL, '2023-09-25 10:33:34', 0),
(9, 'Xk1IvtCuw6YWVV3BjGKshA==', '$2a$10$7YxbcBXjEi0eVhdCu1Nuo.Bgd7jVR9aZEPj7hEF/LhBEAyJXCe4OW', 'vW8lKK4AfMGeAsicBn7GonGYBvURS5673KtED3Et6ltgIsOFV7iqacCRiwtytncY', 'Mhlpo9+HL3iLq9Ccw6RqEwkoorV43bi33RJIYOBXg8gZdH/rPu3CD49FAwZMowys', 'DgErtO7//3nsGDG6BoEHhA==', 'AZ22RwkDmmitXCBFJXlA+ZTlm2wjP2AbHGdzIjfEo/i65asp8wIUCj7O79fjdOYfQOeZoFmL/pAWQ3SUsKdufzp6kHp0Zhv3xyr9YBclhYQ=', '$2a$10$WDqUHw6M37gsRRqvcDa9LeMVq.qyiZpy.miC4FmZ6K5Y47Hv/a9TW', '0Hb2LF88xQ+FFAfhJ9eM+sVwRFyciBwXwd7+F9nB/nA=', 'X4UPU/LSGJddHkZOugCqYnoHaKL8XjxK77ZKMiNNfe8fH4+ghwsIfgYQoAfApock', 's1BJoqybkgWtQr7+4mb49cMBOBm9TbhOBGfO44De7Ow=', 'F/T0MQOSBtCYxxS4SEDSsQCc6bITW+Hu5v5Eid8pSWY=', 'vTcUdSmn6hylAkNrsEwlL/rwz8LEtg43kZOyY12ZoPQ=', 'i3xN/NuBaK6Pq6BmnpDu5JqPHSXkTUmkDAreqqOibUM=', 'KirINV3RqgAjHLz15RPj0Cy79dn1JHuCOy4CKHJaVC9JcPI1wpVhPNLa1PaXMJfI236oE0kZrJHH0U0ePRBykh1vV+0z23Y3JZj6avaHXqM=', 'dCMACG0fwwfjwCU5rxBkxUQwltCvFMEwDSXRA3p5a78=', '128s8HxvwPMCHwQPS68U8N2sffAHCEq3cmqTOAplVTc=', '128s8HxvwPMCHwQPS68U8N2sffAHCEq3cmqTOAplVTc=', 'iYoRNf/SSL+Zxm7hVDLgszeW+Ys74clMjtBfX5OW16Y=', '128s8HxvwPMCHwQPS68U8N2sffAHCEq3cmqTOAplVTc=', '128s8HxvwPMCHwQPS68U8N2sffAHCEq3cmqTOAplVTc=', 'c+7b88zOR/tDLWzL9C/xPVMQEdsotrz4RciRakfQzK4=', 'J3/PsJ8RkcN4zoWGQLQ9ZdxVJ/4Jxe4bNAnM6lKJtvpckxqveIrP+3WleQgLUDDKWCE2sjcgCZkfQh4n8LoU4qVKIVEDOt4Inq6HnAtR9Es=', 'j2a7nFmnBrpL+QyyjQ3JnBgDz6Nn/2vbx9+v2ai29cI=', 'XtI+55Tmj24fgrhhA6lYn+QO5IoSivGoejYCAz0jYOA=', '128s8HxvwPMCHwQPS68U8N2sffAHCEq3cmqTOAplVTc=', 'dqGxlLJpK8M/c26jnSyKjrO+uzQmJYSSwnzkC00R4Us=', 'dN+kAmomm4uj0TjOF9Whh+S0ohx7ZiDyFsrjaOayt0sOZGchLw+Yp+u59QzvQGDJ', 'XX8JJ5Lvy1K8s4cuHJHTtwf1CJvW9V3UQ5ihIm+9V5A=', 'HVvpOgNpm22kuWbLrUToy2wIq+fhOg3xRrel2sEYvM4=', '0', NULL, '2023-09-26 13:15:18', 0),
(10, '3icyDxBgqIbJgT5luBiIVA==', '$2a$10$iyNwQZWwJ.A6PR57.344JOppWjkUS10N2CRK55ir5baOABHEsd4E.', 'RiyiW/tXnfgGNI9+T/nMOU70MTR2xmkWodFHl3ZmsXEXw4Idybe5+SYqbmS8kCWt', '4nG9vlU3C0IvGl4WfpFVpatgtxvbXWdrE0qVhE60aPZ0xd178y2143LoxUVf7cSG', 'I2iLTuFNF397y0QhaRPayQ==', '4nYW7ojdpzknDjvPCN7h8AzmI+Y4fAUYMwq2estCVsOPPI72gxP6eMqCD6vssCiE6gEugTgWG5INySFJtok1Mp2zNMq9irOTbQHUgmAylw8=', '$2a$10$N8rQj7mfIEsWeaYqcLHeh.tfVJDkjnEiaEMBz9N6t8aovm8fUsU.G', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '2023-09-26 13:26:31', 0),
(11, 'HlKacOtO+0U4jb47nFt+cA==', '$2a$10$t0MrelK0fcKorfailhgom.BoRvLdnoUgRFK4eD2lt9On3fxESkIvi', 'nYMZLK1Sfr0TxjxvxiFWmhoVgVIpMwbQ5YMw1qc70CQVN+uk95jAo7jU9Cc5Y1uo', '5zmDGcKaQ/iGl2ou1f2k908OA6WOp+hkm4YpcthK8eYHFAVec6bqMenEt5EzZyr4', 'ViSmBr1S6HPNRVNizRwatQ==', 'QemY6U97Fbez7qzYX/yHTFV+cuXa9DHNYXajHgCYlL1mKVJbUj1rpDDoJ3XibFiUC5IatWCD3yqxIClcc0CujmBHGndxhCyXUsiDZ3IByDI=', '$2a$10$W3hA0oPxiBJY1M5vflBcLuQiWuv.G45A/BuwlKOmYgxfRZABEKaq6', 'k34HMaQEojvsOIxDkBuC5F03yNBB69DB3xYi+V5ngJ8=', '35RwEtrOaIiOWjZTpaKBqmB1hDVUUFmyUGxlBrx01Og4iSAl7BiUb3qyCp5UnhLG', 'r8mmhj4EuDqllJvS2lgXA/mi6BPiBCIDJ0yUjBfpnRY=', '4VVzRjRvazwcJbV7HEd/B0hfJa3RBbS3qdRJlGe3rRQ=', 'Qjy4rhoL9HOH1PAy1tbVT7/Iacx1MQvwql0CnEDqH30=', '9Qf2keO+n1sHliFXRRoq8p9VIL3G2ZEBN1PS0Wkc6eU=', 'dUKCSEa+nl9RuwhWBTF5/YTYw8B1dMN3zPjcSQMhHhFdN7L2152ce1UaC8JhCkQQzcZUNThR2LHYnVGOB0daL6YmmEw9/xTR8TjDKSFRprFYCPj0H2NO45naGskfbAs8', 'yHWWjBlNfg0xibgG5RJXAsvrmFSYLFMnV1TJ9IejrSE=', 'tjhHSpbSb7mDhBcBDWk2QxG47WYkKUldgaObjKnF1Iw=', 'tjhHSpbSb7mDhBcBDWk2QxG47WYkKUldgaObjKnF1Iw=', 'SjNgv3++CKVjmgWeGT6fEMvH6q7DJD1aFZpZ/p+QhHQ=', 'tjhHSpbSb7mDhBcBDWk2QxG47WYkKUldgaObjKnF1Iw=', 'tjhHSpbSb7mDhBcBDWk2QxG47WYkKUldgaObjKnF1Iw=', '+JxDMCboYmkMuJisaILXu2Ps3lKMU6OyV+sbEOthRcM=', 'kCqLYLi83FWDVerVLLIcSVqJPEA3nmBxaKa02mRWi7RIAcNYEWFaJ+xa2tgIFCAXRsmLuPIDbi71GdgBhSHhSMQiFYSGmAIfPS48qGmFUfE=', 'qCsYCa0s4MkUZTHMS9U8nJtBRjYbDDQbPTmKMdv9B/w=', 'tjhHSpbSb7mDhBcBDWk2QxG47WYkKUldgaObjKnF1Iw=', 'tjhHSpbSb7mDhBcBDWk2QxG47WYkKUldgaObjKnF1Iw=', NULL, NULL, NULL, NULL, '0', NULL, '2023-09-26 13:32:33', 0),
(12, 'iCSflAaOaSskK+LZj4XWNw==', '$2a$10$zNfSlRXol9RpY3cCgctpSu4UonWo47oURhnVG0toKt/ADMlbMZUw2', 'jzvf3AOX2oQJO4bK4tnv+KJbvBWCIYNIi0oysLc58XaHbHzoK7NUJVGY7h9406qV', '0YMw0wFVnZrYGqSqcfLLptQ/8Bnfrm83P+wvrRQncM4NNAZzfShPDeBaz8rXM45n', 'IySX40iDs/44l5la4T91RQ==', 'hTwiPILSHWtfVnx+vuAJFpCz2Po/soSfmDd/iriqfr48mCxUviFVbgTJjKFhdvXV', '$2a$10$RdstSULATJ0.t9GUiOwrqeGGQKzMGwLLF560DreONK5o806T7HhMG', 'Elapxu2mruDgpgj1jP1J20qH9IIgpaAiYorIw0Ten0w=', 'OQh8/tRjb3Lw2/EZV03+12RUHeNh6t9+FtCxfpj1gCNO+g9zzjfieH+sm5KEByVN', 'NY0fhs1yhZXWlRRZQ/CrogEAVTjwIY6bA9E6MzBADyw=', 'vETOX/Eod3DpL87CdPp+h4dpz9fgyvmlXoMsF8Mr+hc=', 'ROFH1n3IhkHYZZcqwneBt8S0NAw+hzysF4sxQb3WXaE=', 'dMuWaaoWSzx7w0E6AjxaMWPTxuYSVAgr4vHgtb2YSIs=', '9r7y/Y2+gl2zXP3wWGGBm4m1BUyQ6E1BS5dkqwoeTW77+BGZJLiQ7e7b9bt5E3BK/cyXgvET0ETUVILarNLTzAK2dW2h9l2i0vsqj+7svWM=', 'a91bWBviJx+3El+vlHIK3TI0tKzyRBWc2tV7q3htCFs=', '40Zk8wgo5oUQSWsSHHHBsDQ9O8C0aAFeVLz1wc+CXV4=', '40Zk8wgo5oUQSWsSHHHBsDQ9O8C0aAFeVLz1wc+CXV4=', '/DsHxatBZ6qjMIP1j2CcGDvX1Tw4FFLAjEQ7PubEo2A=', '40Zk8wgo5oUQSWsSHHHBsDQ9O8C0aAFeVLz1wc+CXV4=', '40Zk8wgo5oUQSWsSHHHBsDQ9O8C0aAFeVLz1wc+CXV4=', 'wMSD0azvmBmBt3Xtl4j2K+zok9+sMg301yYrPS0o9YI=', 'ioc42KcucTLZezilcMKkh2qNWq3MJiQN+aBdDF8d8QsBmNmxJlO/cWZeVDHfd4VRn1Xvw9zUuG9p8quxG9FvLn9/0BKReDNotcMbbno9LPI=', 'MyyQhjuJC6OT2cAlWs1UtfqI/jOnGkNXFa/EfUcnWzI=', '/jBHuXyiq0Jxts4+Df8t4C5u3TEIbYYBnu0UDAvPN8k=', '/jBHuXyiq0Jxts4+Df8t4C5u3TEIbYYBnu0UDAvPN8k=', NULL, NULL, NULL, NULL, '0', NULL, '2023-09-26 13:37:22', 0),
(13, 'd3xbEPmAps6HpaFcPKlVMw==', '$2a$10$aVKnWqZ8uSolasSkmXj6GeYHPklU0Y7kwrXnC2iPssX0fbilbXUeG', 'TyJoek1tMgmSVDC6sGh/QiKSESlyBn7pkjuB8UwM3AQpXgVjekhtZGm/kOQ5w3QD', 'P/lymM6Q53/C2lvJU7vG2YhaxwadydYPFTkUaTFGlCIMGk4yrzmnN9FtSjbAoKDO', '5ZCLftjw7rPrD09yhINlxA==', 'IlTdmfTDsM+mT0NZQPuHEdFHduyEc2CGJPihu9JWkCQlQ+w0tiKU5E55GYClyNQH', '$2a$10$HtzGsGNQjQl4r8NZXlgxDOvkcXWlhokeHvWMylvyx6i1MvGJgpXzi', 'aws2TtXLSQBV6+eeiKdSz/nKszYD6dry1P79ozz4Jhc=', 'pICvVPsjT6s6JENhOl4sw+XUGA754dbKK215fsz7lp8=', 'FWg48a/KIM5THrWsfEnTUr/JJJFabJ91qpKVpckMcLE=', 'fwhi+vhcI8KHQ+Imn9W4gi2Mulnpw9s3/UIAuYMbV3M=', '06bXo/oE8xGc+YwNvoVXp4qJdzd3OUFVg0WHffOEj+A=', 'c4SHAX8STDlbggcRCayTcLRqFXBy98sFlaXGsFVj2q8=', 'zk7lP8Dtn4FHb2nynnEj5vMHJNdUsBYctnoT5SXn+vwtyU7l8UcA2pJJ9NbUTbQvmZYzy9A0QRkHPjLaJBUTY+M702UHhD5/9k7Pc1a6zkY=', '//Qt3Czv894NJ1yQp5mvT95uOAebS5VbVd66lzRUxiU=', 'K5phAyOL1USbSKCNWwuFKWCvq04Gyqn6eto5kbgQ208=', 'K5phAyOL1USbSKCNWwuFKWCvq04Gyqn6eto5kbgQ208=', 'U0ktZF7A4XC7jik1FmbmoObdzGqW69aX6mFwG+Y7eOM=', 'K5phAyOL1USbSKCNWwuFKWCvq04Gyqn6eto5kbgQ208=', 'K5phAyOL1USbSKCNWwuFKWCvq04Gyqn6eto5kbgQ208=', 'nf2jGrx7H+SiUDO7Jnt+dHGCqJitW9aTI3TBoPFZths=', 'hczMcDfK5g0QY3xwJwfXk7tm0SAMH5duBHmrNZfqaS7Z7hG/p7l7qalzwsDPYkN/UWJaMEf3V3zX8+puJcfJoAN8s3922wQjRajAW/qDwEs=', 'dZWz5w0dUjYCur6U4VA7JigSQjqFM3PhIliF6Jz5XX8=', 'vc7qcLTMvMXx74VjOzz0PyUfd86AFB3GCcJGOvH6ITU=', 'wISrsOXzMH+eaGlQfq12/bcyw4NVnttk6RkDllb5Nvk=', NULL, NULL, NULL, NULL, '0', NULL, '2023-09-26 14:16:48', 0),
(14, 'JFUpaoJARdJ5ZvkbNasf3A==', '$2a$10$yfJunQKfJjcq7tkoHwmp4.yW1.oy4N.OgQHHeVJL/KINO1I3LcgzS', 'IvSk87PU+ItfBytWBE6s1UJpdBvYJkJORa5Saug9jm7Gd76qTbOLmYMCDwkQ719E', 'YQp6ByyyBYjBzgPY9/0y5ZNbXbVaE2KjhBphCfEUuKmqOzTgCtizuJ8CrZP4qMAx', '9m/h/pLILqxJ6pUtXV3rtg==', 'b3JAU4qotg6YShZvWcIT8sliP+8q2DcruOoqBiW5GFM9KleSw9HAXmiBkvRqA6Mm', '$2a$10$wUTNemTp2kkfQZw1S9Stj.srfC5lv9Th4OmwlpBMjI3gDSf2uOZLW', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '2023-09-29 10:32:01', 0),
(15, 'PkdUWIf9EV9NBAfL2UQUWw==', '$2a$10$TUPfwDHPB2zSNSb4CKbeTemK9gsSoYjPe2UFvpuN783uU86GTRT6S', 'V0SBWeHSE5qwhdKBfUvwGLet3yCj1dWSv0K68gZATFvvbE2DKpOC3eFX1eZa/ArR', 'Ai7LdPfvCtQff5SSgJYg3ozaFuIqCqaP9E6UkZZqiikMXkIA8I32VLpeQeMbRZzd', 'm3AnPm0yYN3siPgkuu1eaw==', 'qD2AkjJdkEHDMEdL/F++p2SSZo1osah6bHZJvczqF0335tUGIUEI/76kUBPbnFkd', '$2a$10$AjWg/TiTEj07vHM8SfrAVu3Nkr9PJbzXLkdyGCn6hBqu3AOUzJV7.', 'Gs8hQYbWa0hT/+fhtjEiFFND96xSkr9rCt4BCHO14YU=', 'x6L6MK3Mtm0QmCa6XIo1FbZS6rlPlefYqZuzVz6xG54=', 'Cv9dnW48fb0+U6JS7WIBkzmq1cCDIPOxZaF0Gau24v8=', '2FJLZjX2ZPcJq6Aitm1WAeWIYzBqDvKNiw5rMW/6Hdk=', 'xRQHah7cXROJDPmWhLKDF9RkiD9sY/ce78v8EPhJBCI=', 'UUqad8gKuJ905mSB17YUQJm3DjKO7NCzsfq43ERLO6Q=', 'fVbHT82H5fsTUHCnmzX4/DZXrnAyebR94D5OYrcMABc=', 's+/5iAYw/w3oALlZ0nEtl/hX0tTq8QdudKBHvVEXK6k=', '26dHq6gVRPyuUi5kfQAoYeKP50Ae7WX7YTrJ1vmk5l8=', '26dHq6gVRPyuUi5kfQAoYeKP50Ae7WX7YTrJ1vmk5l8=', 'Ul7dkMP1tcIocqnMtuN/zPJOcopMg0aS4uPVw1idhtA=', 'oTYZk72YxawJqWlVWrIfaDelUALuj23gAoLRRoTz/hw=', 'oLK0f9JyH3I0e9ANqxo3iqsHn0D5Wcku2rEw81BoFWE=', 'nacfP9/YnggUQSWlNgOEUTv20YB8/bWbnIcXQJOIxCM=', 'ghHdMX9kdF1s4BnJTKFQIh0f3wRmUOhkcXqqzzaAiIvSX6ARo2rHde4xpG1sYXclqHvuyO+pJGgX+CCrXqa6Fzl73qfliWKJwR3N8XjKuZU=', 'M2FrFXLzZ35UsX2znzAtIF9ddnidrmWFl094u8HSJ1U=', '26dHq6gVRPyuUi5kfQAoYeKP50Ae7WX7YTrJ1vmk5l8=', '6+X50Uvt0E6GP4MoItsOt5tD6rmgmX0XWnzv4s0Oxmw=', NULL, NULL, NULL, NULL, '0', NULL, '2023-09-29 11:26:32', 0),
(16, 'rv/lR3LM60eGLWDPI6NE2g==', '$2a$10$YwT6G2WaqhE7.U43SQJ7GeGUsO3H7tLTm0GlgeecE0teHlQMgey3O', 'qA9lwYSfQ0jhBznhE7opRYmvoHacfOWxDt37zueTLi0O1eBmA0/P1ookKImBLZv8', 'cCDgenNZMvDxkkVjAz/qcR44uIzgxnkZF5cFc9b7kcuYhzInu67nLT6b9inXYWjA', 'j1l7u0Wdqfs7FBcLlnQADw==', 'BHhh8g+MF/VA5O/y4oTNkfLfEk/Ql+zuXZ1dqgBdueA569AjOFZPkjPwt+FRxjik', '$2a$10$I3OqYZvGEXoMCD5fWCjXeu2oTu8wBcOcmifoNLP3qfrXlcaGFVOi6', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '2023-09-29 14:37:56', 0),
(17, 'TIPSr6hB48TQ9KYqaWEw/Q==', '$2a$10$JPNgmVxyQdmpQN6/IXF/LeQQJtpB.2gJUL9RvFi9iE0SJpw30BHEm', 'MVwu0cmLbBPjZLltUVUL0TiqvOAyVXocM8jzl2tAQUY4Xa7KK+hEtZWNVpgZtcom', NULL, 'lFgBUMTt8SQ+xa2XxVzBgw==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, '2023-10-04 10:12:54', 0),
(18, 'ryao310266w4EKk0Xz3/sQ==', '$2a$10$XVYJRbZBVpYe.AGEzLAvv.YXXvBfWfm7YhmJPyE3hEoOEQgye2EIW', 'QzL5EWxzf+sQs9ZLpEhjjlIB8RMq22Sa7XvscwR60ugHqu4FJN7BkiG8Zsz2oFJS', 'f9M8qi2iu/yEzyJ1J7E0o5IgAIxCHrOqIpxch4bw+TG7Di/ACi7dCLtbvJNxsTcy', 'hHbz6pZEp2XAV/alEKI4zw==', 'amxoH8DIHL2xKKBrJqy2TAEQorAU6ei/9B1yDm1ezpuwwRHv+5leWe3EsRZdXCnS', '$2a$10$X8OORM.8mtGc8gqbwaz97eg/4uRMIzq2AGs2ZoI9hB3tktm8qWMfK', 'juoEI2qTPBrORd3orjyRK+izPURd2fs+KAITJGeW2i4=', 'juoEI2qTPBrORd3orjyRK+izPURd2fs+KAITJGeW2i4=', 'juoEI2qTPBrORd3orjyRK+izPURd2fs+KAITJGeW2i4=', 'juoEI2qTPBrORd3orjyRK+izPURd2fs+KAITJGeW2i4=', 'GqeIKCLbp7gzFmt+KYP3+otVLu+Yrmck6KSjINz0wa4=', 'L81sq8ZaZ9TnxJUdJj8GfL7UagFzjMMFl6ik4OKDrb8=', 'e/Ro4zo6UFySly0IQgQaELtwPLVz4ffSz9JDxKXkAH0=', 'QJkPvIBS83Wy1c7sXYEOzUXDMaYRbXzpY4ZEp2sWIRk=', 'gDR8nGu9LQl2bIax2wAXvV9lVqbLL/6r8bdjwNJ4bIQ=', 'gDR8nGu9LQl2bIax2wAXvV9lVqbLL/6r8bdjwNJ4bIQ=', 'ns0uSspb9XBfYzOCoIL1iXCh/aSvCnT2kGHNgW6EBdY=', 'gDR8nGu9LQl2bIax2wAXvV9lVqbLL/6r8bdjwNJ4bIQ=', 'gDR8nGu9LQl2bIax2wAXvV9lVqbLL/6r8bdjwNJ4bIQ=', 'v/ykt73f4eD8FcUDLqZrHgLchNk0vNkY9uYVKHHfNY0=', 'xrChuUhESv/0s4OvfrNnlufC2WYVqKazl4w414B9MrGZlbw1M1HecL5Pxyq4tq6n', 'ihamurV1fkgwViLDNGeuIWoVGxjVYXhi5a3/+/ajQ7A=', 'gDR8nGu9LQl2bIax2wAXvV9lVqbLL/6r8bdjwNJ4bIQ=', 'gDR8nGu9LQl2bIax2wAXvV9lVqbLL/6r8bdjwNJ4bIQ=', 'e/Ro4zo6UFySly0IQgQaELtwPLVz4ffSz9JDxKXkAH0=', 'e/Ro4zo6UFySly0IQgQaELtwPLVz4ffSz9JDxKXkAH0=', 'gDR8nGu9LQl2bIax2wAXvV9lVqbLL/6r8bdjwNJ4bIQ=', 'niVaxhzpRKCpDVLSGdU8IqNexDrkhEsJfBcQQ+LoqxI=', '0', NULL, '2023-10-04 10:21:21', 0),
(19, 'eYdB3/YRGPA+FGcAXoUFSQ==', '$2a$10$7GZdeQaekfiC9a/ndqEhOeGyUgB7VCeUu4uA1VRufbTK6NpBm7uOS', 'Q0jv0f/GCScKYHqnNQt90wyX5lESYPV0QlTclBk0Ti3Aviwk7sI6LRflosTXVpkF', NULL, '0Jgpn9B+bdY/vgsMHzcJxg==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', '2023-10-11 10:28:18', '2023-10-11 10:07:53', 0);

--
-- ダンプしたテーブルのインデックス
--

--
-- テーブルのインデックス `login_logs`
--
ALTER TABLE `login_logs`
  ADD PRIMARY KEY (`id`);

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
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `account` (`account`);

--
-- ダンプしたテーブルの AUTO_INCREMENT
--

--
-- テーブルの AUTO_INCREMENT `login_logs`
--
ALTER TABLE `login_logs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=104;

--
-- テーブルの AUTO_INCREMENT `operation_logs`
--
ALTER TABLE `operation_logs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=138;

--
-- テーブルの AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- ダンプしたテーブルの制約
--

--
-- テーブルの制約 `operation_logs`
--
ALTER TABLE `operation_logs`
  ADD CONSTRAINT `operation_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
