-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- ホスト: 127.0.0.1
-- 生成日時: 2023-07-28 21:09:27
-- サーバのバージョン： 10.4.28-MariaDB
-- PHP のバージョン: 8.1.17

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
(21, 31, 'Create SecretQuestion & Answer', '2023-07-22 20:50:06'),
(22, 31, 'Create First Setting', '2023-07-22 20:50:34'),
(23, 32, 'Create SecretQuestion & Answer', '2023-07-22 21:36:08'),
(24, 33, 'Create SecretQuestion & Answer', '2023-07-22 23:03:16'),
(25, 33, 'Create First Setting', '2023-07-22 23:03:51'),
(26, 34, 'Create SecretQuestion & Answer', '2023-07-22 23:09:31'),
(27, 34, 'Create First Setting', '2023-07-22 23:09:59'),
(28, 35, 'Create SecretQuestion & Answer', '2023-07-23 12:00:02'),
(29, 42, 'Create SecretQuestion & Answer', '2023-07-24 23:51:51'),
(30, 42, 'Create First Setting', '2023-07-24 23:52:18'),
(31, 43, 'Create SecretQuestion & Answer', '2023-07-25 12:42:23'),
(32, 43, 'Create First Setting', '2023-07-25 12:44:33'),
(33, 44, 'Create SecretQuestion & Answer', '2023-07-25 12:48:00'),
(34, 44, 'Create First Setting', '2023-07-25 12:48:17'),
(35, 31, 'Change Password', '2023-07-26 13:37:30'),
(36, 31, 'Forgot Password Recreate', '2023-07-26 13:38:09'),
(37, 37, 'Create SecretQuestion & Answer', '2023-07-26 13:46:07'),
(38, 45, 'Create SecretQuestion & Answer', '2023-07-26 14:16:04'),
(39, 45, 'Create First Setting', '2023-07-26 14:16:53'),
(40, 45, 'Change SecretQuestion & Answer', '2023-07-26 14:20:42'),
(41, 46, 'Create SecretQuestion & Answer', '2023-07-26 22:27:08'),
(42, 47, 'Create SecretQuestion & Answer', '2023-07-27 16:53:06'),
(43, 47, 'Create First Setting', '2023-07-27 16:56:38'),
(44, 47, 'Change Password', '2023-07-27 16:56:49'),
(45, 47, 'Change SecretQuestion & Answer', '2023-07-27 16:57:00'),
(46, 47, 'Change Password', '2023-07-27 16:57:30'),
(47, 47, 'Change Password', '2023-07-27 16:57:54'),
(48, 47, 'Change SecretQuestion & Answer', '2023-07-27 16:59:39'),
(49, 48, 'Create SecretQuestion & Answer', '2023-07-27 21:55:48'),
(50, 48, 'Create First Setting', '2023-07-27 22:37:21'),
(51, 49, 'Create SecretQuestion & Answer', '2023-07-27 23:11:04'),
(52, 49, 'Create First Setting', '2023-07-27 23:11:36'),
(53, 49, 'Change Password', '2023-07-27 23:11:44'),
(54, 49, 'Change SecretQuestion & Answer', '2023-07-27 23:11:57'),
(55, 50, 'Create SecretQuestion & Answer', '2023-07-28 00:26:29'),
(56, 50, 'Create First Setting', '2023-07-28 00:26:54'),
(57, 52, 'Create SecretQuestion & Answer', '2023-07-28 00:38:42'),
(58, 52, 'Create First Setting', '2023-07-28 00:39:04'),
(59, 53, 'Create SecretQuestion & Answer', '2023-07-28 00:44:01'),
(60, 53, 'Create First Setting', '2023-07-28 00:44:21'),
(61, 54, 'Create SecretQuestion & Answer', '2023-07-28 00:49:09'),
(62, 54, 'Create First Setting', '2023-07-28 00:49:48'),
(63, 55, 'Create SecretQuestion & Answer', '2023-07-28 01:10:02'),
(64, 55, 'Create First Setting', '2023-07-28 01:10:25'),
(65, 56, 'Create SecretQuestion & Answer', '2023-07-28 01:28:32'),
(66, 56, 'Create First Setting', '2023-07-28 01:28:55'),
(67, 57, 'Create SecretQuestion & Answer', '2023-07-28 01:31:40'),
(68, 57, 'Create First Setting', '2023-07-28 01:32:01'),
(69, 58, 'Create SecretQuestion & Answer', '2023-07-28 01:35:18'),
(70, 58, 'Create First Setting', '2023-07-28 01:35:38'),
(71, 59, 'Create SecretQuestion & Answer', '2023-07-28 01:44:00'),
(72, 59, 'Create First Setting', '2023-07-28 01:44:19'),
(73, 60, 'Create SecretQuestion & Answer', '2023-07-28 08:51:32'),
(74, 60, 'Create First Setting', '2023-07-28 08:51:54'),
(75, 61, 'Create SecretQuestion & Answer', '2023-07-28 16:13:25'),
(76, 61, 'Create First Setting', '2023-07-28 16:15:11'),
(77, 62, 'Create SecretQuestion & Answer', '2023-07-28 17:52:59'),
(78, 62, 'Create First Setting', '2023-07-28 17:53:42'),
(79, 62, 'Create First Setting', '2023-07-28 17:55:28'),
(80, 62, 'Create Vocational Trainee Setting', '2023-07-28 17:55:46'),
(81, 63, 'Create SecretQuestion & Answer', '2023-07-28 17:57:40'),
(82, 63, 'Create First Setting', '2023-07-28 17:58:40'),
(83, 63, 'Create Vocational Trainee Setting', '2023-07-28 18:43:01'),
(84, 63, 'Create Vocational Trainee Setting', '2023-07-28 18:43:25'),
(85, 31, 'Delete Account', '2023-07-28 20:40:47');

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
  `tel` varchar(255) DEFAULT NULL,
  `post_code` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birth_year` varchar(255) DEFAULT NULL,
  `birth_month` varchar(255) DEFAULT NULL,
  `birth_day` varchar(255) DEFAULT NULL,
  `student_type` varchar(255) DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  `student_number` varchar(255) DEFAULT NULL,
  `school_year` varchar(255) DEFAULT NULL,
  `class_number` varchar(255) DEFAULT NULL,
  `name_PESO` varchar(255) DEFAULT NULL,
  `supply_number` varchar(255) DEFAULT NULL,
  `attendance_number` varchar(255) DEFAULT NULL,
  `employment_insurance` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- テーブルのデータのダンプ `users`
--

INSERT INTO `users` (`id`, `account`, `password`, `master_key`, `second_master_key`, `iv`, `secret_question`, `secret_answer`, `last_name`, `first_name`, `tel`, `post_code`, `address`, `birth_year`, `birth_month`, `birth_day`, `student_type`, `class_name`, `student_number`, `school_year`, `class_number`, `name_PESO`, `supply_number`, `attendance_number`, `employment_insurance`, `created_at`, `is_deleted`) VALUES
(31, '0ME9RYa7z5n1umV2maso0A==', '$2a$10$1CBFMxlneTjyD76Gi97SjO1QG0alfHPj1e5ZHEvhQC1UvI7nM.L0y', 'naQhXlmcKybusZcRw6Z+vQ4sUA7Xnplkv1P/KqIc2DD5Ltjqa1pv0tAHJgdq8H7t', 'pRM1nteCpaUXAdOauD0YqqN1euBo79Dkp9Qqte2qxd+RKhqdNDe3Zfij7R5YRuch', 'pDeR32sXi+wHcAM9tP0qhA==', 'RfVdvXhmcUtmtmSEmP1/ag2LhKFhbUmnLYjwm6QgpDLPViLKxyVOBX83BG25F9MT', '$2a$10$oXMY3zKowOtTftsp8GOI0uocqNKlRugzHWNJFFQVdtk88kPp6XWNS', 'BxOZWKOwdlhW9BumDF/sLrtkUKmNphrw9ySlSjAFjgg=', 'izRjVqyxOzeH66eXfONTcLw9/kh+JPyp3ey1UlJffgk=', NULL, NULL, NULL, '0LuIpf6TnfjDk6JxnFE0SokYMscDms9RtLi/sYKcIJ4=', 'i7F3eld4Eke3o0JeaE6UFW2bBDOaWVyCMPXK1JrFLhM=', 'i7F3eld4Eke3o0JeaE6UFW2bBDOaWVyCMPXK1JrFLhM=', 'FDA9FLVnI5KdGC+RfA6MXY7YrV+84WIyMW/YamaZcZA=', 'oqImun/Utl/oUNKMx2NFMMuBZYRt+/W0821sEnTVu2w=', 'MH/qLCYZm1BOMlsqRutdbmoK8zmSugcZf38ckOjWUc4=', NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-22 20:49:18', 1),
(32, 'B2jVBbSp2+BK4Oy4u6OArg==', '$2a$10$H206HXRKKeTcT/ZpkEOtG.seWfFbz99OYx.n6x8Ahe1OKXm90XqRS', 'IcmWtPe3xeObxRTiBgIAX2BadPbhX7oXtMyw7xaMnb0+JvsbA02k30OCV6Qns0E+', '8OuWUujj5I7nyeeFEX/jnzRrgyNnLlLfX+fw/lS1BOQbEc87GP3Dr0ApvdVHb0RC', 'eNWZ5T96MV0Unp4hf51x1A==', 'NLvlOHG0Ma7n9UJfhq1XCgN9vQRi6FlssahpsxAEvH40/tBQiUtSmAVrxJZPqQH9', '$2a$10$qehCQo2iK7v69AnSuiyCr.AIKKGC6MoS0UtR.UpuzqxtwzC/Uc7CK', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-22 20:49:35', 0),
(33, 'eYdB3/YRGPA+FGcAXoUFSQ==', '$2a$10$p/sHYkP0.HR.WE0dYDk2HOWdA88I6rFSxNyC.oC1SG4xvY2uhljIq', 'ss1Q0O/5koopJmZegsXz8qnZ1s7623QdKBUVLR8zDp6yJ51GFpNzW426THjDw9PU', 'ASYVn6LnSPD+KU6a+V0KE65jLKICvn2ijwTKhSm6h9le6Rj7fyw37NRT8+zY7IKi', '7qTFlbevO1NyTwtrrAaRJw==', 'xSiO4vF/4RV5hcgaQKmLJIT/F4qNTYjmAtmLOXlDp0e9xsd5Ha1QpWu3ObbdYCfw', '$2a$10$5m0cQZpsW/0gu8b3F65k4.aYum3lJX0E47Nx1jju8ufkvZiwUbPeu', 'R54BFqQPOPiQHsyitflTo++7lroqRblBUjmHPxJRBuk=', 'JXJzPEuumlk5I79RwGbzAM61XBuQjYG/Eb4Vyi2x5l0=', NULL, NULL, NULL, 'HUwPtoAuN1nzPHGmvuMvNPz/eArNivzYNY894JBMLzY=', 'vgn79BxdkcpXFgeIuO6RzZ4woVwrg3DZhMdbSSmYNXg=', 'vgn79BxdkcpXFgeIuO6RzZ4woVwrg3DZhMdbSSmYNXg=', 'hsR6xuxEbAjwuRYaTMn11l3nBtgdqT7uigadrJD6hbE=', '/fgvq9ie4YSc7CEm2PIhwtL9YX0uL+M5wHA3Yyfp/9BZohoPB68UgwK/vo3OFNWQ', 'GnOirz6bVTMd88+Hmd+/lEZL2sqmnk5jv6wQUMwTSxE=', NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-22 23:02:55', 0),
(34, 'BviIlLA2blZYHTJ4/UGBPg==', '$2a$10$8beQzG.wgXWqCUHN9wty5ODIQdIXm6HLx6EfyT1.7gKm3UCZqh9Ka', '5XJehkODbTPqY7IUMGmuj3VqVh2KmMUFF8JqNgpqUMYv7bkJCO6rnvqp4RabVkKb', 'Jge0Ety93iyeG/cy8qtFdgFbQIr4VYbwi9wdgPVaUkc8YpusuAniywQqzWa8j6fV', '4rkfUUDaynLs4MlT9JyZkw==', 'BPT7GUP1lVbfDmZ65KoFZpovP6bYLXrd3PyirVWzyvjTgRikWVniaoBzmPnKis+U', '$2a$10$anavz0GZ1rl80kq22i9l0.HkinNNIvOVubUGFgNaZGuhOyg.m0CL.', '9eb13SyIx/KBqoGuRuffzZbvrR110vQU0SptUflXaMI=', 'Dgc+4TA7pZnSU9r+2uNWpKX2m4jeScIRNDwb1kOUUEU=', NULL, NULL, NULL, 'TshplsYoSzalHM2INcJyDR+1Re4+Rt/+T6T2ru+YOLk=', 'Hts+wgx/u9lM8RQ2bJ0eqB2Hdo4GDiAxRvHeXgbuaE4=', 'Hts+wgx/u9lM8RQ2bJ0eqB2Hdo4GDiAxRvHeXgbuaE4=', 'QOtpq/vjtoRmwYTbxhfnItuEbapvFmtTLYIEobr19/k=', 'K6k4gLWh1/QJIM2mjHN6nTYv1LAJ79GdDsD3o4U/7+U=', 'rdyr9VWpiydTDvzfL+4O2HQbgawVK5CA3i728heNr7w=', 'Hts+wgx/u9lM8RQ2bJ0eqB2Hdo4GDiAxRvHeXgbuaE4=', 'Hts+wgx/u9lM8RQ2bJ0eqB2Hdo4GDiAxRvHeXgbuaE4=', NULL, NULL, NULL, NULL, '2023-07-22 23:08:37', 0),
(35, 'TIPSr6hB48TQ9KYqaWEw/Q==', '$2a$10$PxIom2h0jVgvwQn/vxWfuel3kUZq32Mnvckgi2uVYj.bnTdp.j1e.', 'njD3I4mq0SpbLFngyW2f3/7w2KAr1U+AkCJ7ZHNwBQPHOmXw2hVUSdlaeVAGR7ZQ', 'iRXvMF66VPU5CGzEBFjQcmxNKeECplEAy71JJndhkhRhiXyXA3WWQN9ucT0KQJxk', 'PXQEso1VAJleYLu4Q/VDmg==', 'ZFCdT9squR7JMEy2V1PYD1KYU+c1xI1rUZmn42ffcm2GRXy06NS50kaKW9YlFsTz', '$2a$10$eSRToSe02RCowuZ5DzuERuv5TwLtzTVtArjxkSJ7QocJBun9YR4Wi', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-23 11:59:45', 0),
(36, 'd3xbEPmAps6HpaFcPKlVMw==', '$2a$10$EusdIJXhvaP7j2UDCtvnIunZFjW..nKVkNR4VhCEhWXEZoKC9ltaW', 'DFXb61wjFcxH0EclG1fua5lDnSDQrudfBaSs7PEzNVZBbc/yVUfHGVxBMNhVs5oq', NULL, 'VjDY52YVeOpjddlNF5WOIw==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-23 22:38:34', 0),
(37, 'U2MZyGFRf0XhzJYvY07DBA==', '$2a$10$CB6pzKXMBWeIlGshne0AOuHsw6UXsDJ7/wiBhND2gS4HQ4B4PCS.G', 'Yu/nxDI4voh2dYCmviy4up1ykSsw9byiVBERkW+zzJFNlQOeo591+aEPIwEBEUJo', 'BfMoiezW0rH5iYPvEH6ZATYGopFrxm6vNLlJ/u3JzsLCrj8fTE+MsSxWmg1SsG22', 'BihzvtTAESlDD43VfnFWGg==', '9W3ybTa+IS3rR3xk/M4q6xyLKRKSmX33d3XBexswu7diGW3Xn2C3TWyLiSVhkJIO', '$2a$10$ktahwxuJaWaw5ZvT11P.YelpHoeEYCdIkCkkeCWfHAYFAaEGprORy', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-23 22:40:37', 0),
(38, 'B4c7TOT2tLQcPsFIHKmNdQ==', '$2a$10$Gweun1sn9/QkTs0wnka.qubEUlKJv1ITRD8cAlH0QAVq8ruxeUhT6', 'Ukvj4pWaC6+7WnW/x8mdPQA4l7rsHBsABYZVKhQXOhVZaaavigS1eFLvxgiagkex', NULL, '39BQxiJ2bn7cGIhmtaTH7w==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-23 22:41:07', 0),
(39, '3JJDcgtYXBkhuttDw6o8uw==', '$2a$10$5iFqcwzD0rjhkF9GdjXcRucYfDMC3eSJtDn4fHS1SzDM.5Ir9VfCK', 'nQzKS+krzZKNvLVegrF3+ilnBOtLKLnkcJ1JLxCufPx/c9JUweQ0AHTLN+S3MbNx', NULL, 'i+Dj9Jo7+8n+hhD57y6DuQ==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-23 22:41:35', 0),
(40, 'tQcf/Dpk1pqVUVNRum/Ngg==', '$2a$10$kqgoutcWnLe/2UeRTempqOFEL0GjFGJp/fpZBZVxBEQrsuLUPYI/i', 'FmtGhF4ggzsrV8Q0lCKJv+BtQhru9oed5OJ4mI9Wj9IcVlyqN+0HAZ6wouovx+Xh', NULL, 'IhEI6JpK/JZagZEHwtuc+Q==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-23 23:34:34', 0),
(41, 'GPHB89g/EAgkT7bB+DopVQ==', '$2a$10$stROJg.gweU6wlnz1yDkWOLk6YxKdIcp1Rvq0hSPePFcjKas9IUoC', 'fjlgAzBtMljm9etbjF9ZlhYFmz1rWOmzJ4EAESdEL6n2Xt4rg368707rjIwVVu9h', NULL, 'o78JDXMm5Oq2VTjvydmuYQ==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-23 23:35:00', 0),
(42, 'lwtfDwuGbsWdJQLveEmDIw==', '$2a$10$bPDNZDlg94Dtij83voq9A.ayt5vukFo1/eXYHlHmfd.TwuGvxXI32', 'XGuq8RMyzno7hcJaAf7hR/pSbyN61NAaoBx8kuIVGutP8U9Y5Tevx9mqs6MPqBYL', 'RghZZ/nkJzEpXpxGtP3Czfd/EMToHTKoyk+K6KCi8H/3CHex4E8GtNXZD3wQvoq2', 'Z21/N/Ndz57e1jwBVp2AiQ==', '22t6lQUvGI6aBgfVGIRzPRqUuyOE9iYHK0F8u+rbmUMKXq3oFSkdj2LLE64e97US', '$2a$10$VtcN5zFTm48m7A8vKVPCWeBXGg/xB3146LCcNYgou3Xg/xT0sNci6', 'pvcwSJBsRLu5xP3qoloSvEdSffZRefuR3Az16oUbqaM=', 'pvcwSJBsRLu5xP3qoloSvEdSffZRefuR3Az16oUbqaM=', NULL, NULL, NULL, 'UrU8DtA3kW/pqZyeypLAtaACVcCFi4ioWB5mzg6AAbo=', 'EpF6cQkHVvmXSLcHoiKYJTF57V/THYE/DKYMkC78idI=', 'EpF6cQkHVvmXSLcHoiKYJTF57V/THYE/DKYMkC78idI=', 'mOMsP+YpxLV3o8OePz4mYVAPghwPoUnifly6b2Faudk=', '5HUZTRVrnGRG9J66VTWHZNVCvOgi9bUDyc+uZPDK0aQ=', 'i9/fcgUDMBRD4sSGnmmGokGFFjj88wbAKFoW11mgVVo=', 'EpF6cQkHVvmXSLcHoiKYJTF57V/THYE/DKYMkC78idI=', 'EpF6cQkHVvmXSLcHoiKYJTF57V/THYE/DKYMkC78idI=', NULL, NULL, NULL, NULL, '2023-07-24 23:51:33', 0),
(43, 'dJuXnP0J9Etzb7sdzPNa9bZjekM8tMLnvO4GIp/tOj2QjS1bko3oiLzOUKLHmeZG', '$2a$10$5AHqFtxk.NYZ5tYXXxvo6e7uO9bswrx4lXDELQBuDaQ2llsoKFydC', 'xdPA3rPXwH7iVN4+kTzM+MlA/KcTL5zHdD8WniKyJ0AbIl/Qj+eNCDezNmcQzXnC', 'xdPA3rPXwH7iVN4+kTzM+MlA/KcTL5zHdD8WniKyJ0AbIl/Qj+eNCDezNmcQzXnC', '5zLt3t7VTFhdokphvLZCDw==', 'K5RJjOAhrqkWNa1rTvDyh8oXpO8xK0pGlSEYWhXUIeWgO2sqKIzw020AfVPAEhxp', '$2a$10$jx1NhMBoaKyg7uw1D1C19./lXo3rtgYAn5tS8OIwNnoccIkEo5lLe', 'WfJmZyN+6NCgqfMAS41FGe3cKrYM8clYJ5voguzy2t+tOSyuH7k+MsomK78t8OQDgJQoRN8KLuqaL/0mlNlhr+1p0im9uJdB0eunBMevKxM=', 'WfJmZyN+6NCgqfMAS41FGe3cKrYM8clYJ5voguzy2t+tOSyuH7k+MsomK78t8OQDgJQoRN8KLuqaL/0mlNlhr+1p0im9uJdB0eunBMevKxM=', NULL, NULL, NULL, '51q4PzfxkUucLsuIk/QsLOLe5zmMx1O63CKPQaWnJDI=', '6PwsRtPvf/QA4mgXsdtfWpBkVUTY0zm7ClRsmXbiE2k=', '0OoUJRn+IoS+2kE5fK3vRSUYN2NJRzSAYhdHzYL/DTI=', 'mPZCm9w+V5vm0Zc69U2SxizgqoIDdeidIDMwdsyeHzU=', 'WfJmZyN+6NCgqfMAS41FGe3cKrYM8clYJ5voguzy2t+tOSyuH7k+MsomK78t8OQDgJQoRN8KLuqaL/0mlNlhr+1p0im9uJdB0eunBMevKxM=', 'F1SgLJtuACKXLQUjCoih6DrTxnoCiraXHCDERQth95A=', '6PwsRtPvf/QA4mgXsdtfWpBkVUTY0zm7ClRsmXbiE2k=', 'Pb8Eu+AekL+OHs5OXvzif4AzaK+xiWaQEeYSc9hU+UI=', NULL, NULL, NULL, NULL, '2023-07-25 12:41:37', 0),
(44, 'dJuXnP0J9Etzb7sdzPNa9Z/Kk5ZATXFyVHQqNVzRx4mFNn2iIwQaSYoSDlqtTL0/', '$2a$10$XTzaOltisn91MV7OYkwVEOPh12dGgjjIysc.nFm1dTm4O5/sx8VV2', 'KhYUP6a8ooT52H+JjxDCFjVQ02OalxnsTACGs8wNRc2Vjg82b6LAFNY6KAtP8nXC', 'KhYUP6a8ooT52H+JjxDCFjVQ02OalxnsTACGs8wNRc2Vjg82b6LAFNY6KAtP8nXC', 'g3JTJ7Nr2AiTYbuBCNfgmw==', 'eIdppw8CEF4vB4Cd/IepANOnTw2BrMKBh6Rubw4f+kmOSL7PIYqV0h62zPnv1cp0', '$2a$10$vC5yFNa1uHvXfyUmpRf9XOSBR5/WgTZACIs2rcH604rWaaCiozPE6', 'TqxfgNbc5ouwi24QfLwQGx8QUe0duyY8n/ZmrsI1nNaTD5puQO6ZZnnzYfojQ8e+mjo0vgEgLQ6hOdvh+QbhN3lMgwbZdE8ZUbke4jUKplQ=', 'TqxfgNbc5ouwi24QfLwQGx8QUe0duyY8n/ZmrsI1nNaTD5puQO6ZZnnzYfojQ8e+mjo0vgEgLQ6hOdvh+QbhN3lMgwbZdE8ZUbke4jUKplQ=', NULL, NULL, NULL, 'K1TC/q+rgKYyBna4TsEqBYXmpfskJCkVakyQ3hGS9dQ=', '+6m6LZH8bA0kh1pTaljQYhjSRiJL/JpLy5xIWSO1Qig=', '+6m6LZH8bA0kh1pTaljQYhjSRiJL/JpLy5xIWSO1Qig=', 'YsCksknXxOPxH9FqfAHmCqvObLin1kGPVIwvjPW1nG4=', 'TqxfgNbc5ouwi24QfLwQGx8QUe0duyY8n/ZmrsI1nNaTD5puQO6ZZnnzYfojQ8e+mjo0vgEgLQ6hOdvh+QbhN3lMgwbZdE8ZUbke4jUKplQ=', 'ZXso+rY7nG1dgL9HjLEDyWZ5B601616ArCnd9SOyHKM=', '+6m6LZH8bA0kh1pTaljQYhjSRiJL/JpLy5xIWSO1Qig=', 'uw0DAMLnbj/OZgDIAC12avpb7rH9yIGjytL+A8EOOtw=', NULL, NULL, NULL, NULL, '2023-07-25 12:47:43', 0),
(45, '65Bu18UDepHViw0/+ayMVQ==', '$2a$10$hoF6zNYdtcu8bCQ53KUPDOQRhmFAcTMac7c5GqUkTf6HZ3I1Neuba', 'QPQfWVZtnSyqVs5TvwYqNr8Ju+ArSvyWzgvIVdXhM16+ZvzlBLIBiMw007d/EJUP', 'LjXKrtaptuJP4fqAoMV1dsamBBOOTvLn1DfTuCbZqLC9qxh/JeOKE/uJ47/kjJnu', 'ZnDAVdg3RI0JFgwhxEJ5AQ==', 'Reh++MWvzZdzYFabajHNfu0vpbDRqPoldkU1PVk6RS8Lsj8hzAFVvmYwcH/vdtzA', '$2a$10$BnWhIfNDUJLib3tOHHqvau1i8MCYxToS6Bl60JSqL3S7wFhQhOrxK', 'FyMXuDrH+nC2KRkEAzMxZqJqIkVU6iiz/VLMdK53hh8=', 'agsC/CO1i9Fe0WziqoPFLIJlaHciFzDEaw8KsioxTTY9qLlNPw9mgvySxSHoTSLH', NULL, NULL, NULL, '8/DEY1QX61h0AfI6UdD2Rd8Uufv1nx+fgqsE+p0diWM=', 'Ian7htpSwlCcddfDQQMf2BM3HMy9lmgnLTxnBSIC8IQ=', 'Ian7htpSwlCcddfDQQMf2BM3HMy9lmgnLTxnBSIC8IQ=', '7oftQcipFyxBSb6XAk9jo5iyBCJ3dF8V1y/m/9kAo0g=', 'OjmN61rOp5NDIIHWDymWWMzaym5hW9CPJzeE63Jf/AY=', 'GF/csk5eZ8jSKE++6+HlupAzDXTeiLs1ZCtQ+mc9GJg=', 'Ian7htpSwlCcddfDQQMf2BM3HMy9lmgnLTxnBSIC8IQ=', 'Ian7htpSwlCcddfDQQMf2BM3HMy9lmgnLTxnBSIC8IQ=', NULL, NULL, NULL, NULL, '2023-07-26 14:15:55', 0),
(46, 'q/58v8sHTSR3pwT36yVmsA==', '$2a$10$5g10DftOfFNQCOWa21U6oO5Yg6vxGobhagq27iJpV/p6pEOiRvBMC', 'FPfA45OmWPXfz1KTlvcZGH093pp9jFUpQCirRSliSRhdutAvF+qgdgBalSDLf5oE', '7mc+ijNhyLqg/rs3bqfB2X5UmSFq3Ym9ejEoQG3La7zQw4RrG02SNF/fbN2PCSWq', 'D6weRHBcU/JP9vJUjObVKg==', '/UvCSa3h8m1ROFr1otNbqSY5me9pwKU7SCh6qGG6l/JOssTk/QfveJ9+Yv/5tT2X', '$2a$10$ljkUAkRWp2o9BwfETuDoa.8GflHQo6rgfdz1/.9m13FF248ArsCfi', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-26 22:11:31', 0),
(47, 'P9rRVDMv3Pwa2ADmuu79NQ==', '$2a$10$ntvVancmiG9WrdEhP.lo0u.FIpj6hP6/jH4.BzJUtsRyYfh2H9m6S', 'U5RCQeLOTH5jnR6aGbhAobE02/2alw2kDy193NlIK5qPGcyl5MdkzwWiZId2pNpx', 'S7PtJE4LW/6wliPxd3twmTXIr4GglTflzvARRqJ+26/i0+747XtJET/VpGJRPB2N', 'o5E6Gq7sZLvelh6pFk5pDA==', 'AAGBa0PiPas+e6mybX+cIo8Vbm69pqR//JaFMhNWc8uMgayUKikkLtA+Vf1vACz0qZeePGzPR1HMYRRCd9WngkoGCRcvfnkGs4zxjFoeVLw=', '$2a$10$Vi/RYLhHJayBhUH5mo/zveCg2WwawPElkney8/k9iVjwZxIMcgPkm', 'Vn9EJSC+pgAme2wFnhhllukQ6PKL8lqMiuENyTt6pLc=', 'pWw6F/MZ7DAG96xAPWXUsaJG6+PQMSedLptBHxlBAk8=', NULL, NULL, NULL, 'ckZiz3nGsWTyn7NDe8dXah380tE72f0aQs/mNvDH65A=', 'NFyLjySQJbt0kIY4TR6U9NioaYTvKVr8Bf598mKH5NU=', 'q7T1pNRufVdeZTy76NkwdMR3czjKC4aEbpU/HFuoeso=', 'iY/WwEu2VP61WSNWxqv3ZgHCPuU0+mV9dCj0Slyo4cs=', 'uKAoKll05jSJI30RzDA4VsOuE2q0Mu1ytTUuBIpo8n8=', 'aFXjJDt8cf3RnhAOANT2rThiSfnGEXqpdgxsj7uqPE4=', 'NFyLjySQJbt0kIY4TR6U9NioaYTvKVr8Bf598mKH5NU=', 'q7T1pNRufVdeZTy76NkwdMR3czjKC4aEbpU/HFuoeso=', NULL, NULL, NULL, NULL, '2023-07-27 16:52:49', 0),
(48, 'QrtvFLdkSC3ggHv5DIwdPQ==', '$2a$10$fISv4vsEj5RXCGCzz2y1EOPSKSPbYDx6pJ1bj2iN87Uunk3p47I0W', 'CyvbzrkR/db6L5HElto+3iCMiAUKVGGb4NZTeePIbrg1lmpzIk+D17uKpLDi8eCs', '0XxrEWFgZnaMkVrZ5xRAx7hhcEvONVxZTZjO2+r717/NOgHCljt8e+yF/JT/evlF', 'BEXBi3ADDXXjWF4SAAPoZQ==', 'lcRZoA+O3jDMPiegs89T+0lnIMg+DYPk1bxHdYZ/1yIN+S7+p+++jmp5GGBCr6LZ', '$2a$10$XbweecrEAugCY4bmo.syyesFLLNN61K9DA9oAvDpU1w3mvTgOhgLC', 'QVhFFU6Qb7tsgeKT/WE0Usmvzr7p/kAsIHoZFrCKsHY=', 'MGUzK0rT51uzngZWYR55lQhYC0yqFNOJJKn+iAu0dLc+NXDOj8F1IvNIpNWCErym', '5tI1MzFa2JusD9Y6XFobmy/afXnpfzhNBzLu0JUHhpY=', 'l4zXWyeWSPUgbMpLAN8Sx1yuqw6M8ENMli4qkSB7RSU=', '4M4rnyAXjZ3Hbf5UJNcaQV+tCiQSjl7gaW+EVM/2WV4=', 'IgEyrjQMZ4C+5kDba42R3EVgZAIxRQcOuiUXnaSkIkU=', 'LFHaeVjVGhQed3ksgHBjVGqUJsgcmwLo8Wr18f7T1E4=', 'LFHaeVjVGhQed3ksgHBjVGqUJsgcmwLo8Wr18f7T1E4=', '2/jEgGCTMgYT9nkfm4D4UfbJJ/MRC510SGgEuDLJOlM=', 'wwuy+NkVeXUx0T54IGqp606LTlNZv/SDO9SLMR6WTUw=', 'UzKT5Q7uxCCwSTxz/7i9YGSFbS0/iczu9uWGH0SR8IA=', 'LFHaeVjVGhQed3ksgHBjVGqUJsgcmwLo8Wr18f7T1E4=', 'LFHaeVjVGhQed3ksgHBjVGqUJsgcmwLo8Wr18f7T1E4=', NULL, NULL, NULL, NULL, '2023-07-27 21:55:30', 0),
(49, 'bfJufWpaDyWXVXxV0dLqUg==', '$2a$10$Vzz77uz95670XFs5Tq7UfueAyNquRZd/jD9EOWmquAGLX4evpWLwK', '2ROgr2BCNrT0MvnQ6T/tHWhUdGkpcGRX88V4pI7BBwvNNXsjnScaZzuC+ci+YgSE', '8g7RIryHzMhUbQFD+bYl8GYwWELvZLlCxLVKVTAJbrGgvmqeexF9C7uPj30Qfhox', 'LLvB4BPg0hFRO/OcfJpqVQ==', 'oKsjrmNPJYrbVZ1yyWRteh6VDXohBvUb6yCDOS/qsy8zLRMcIeBTe2iW/hfZzQK9', '$2a$10$eaQ/dCfO3klmIjEMl8jBZuRC1uRCfLOXEE/ZIiELjaFnbaVtzCG3y', 'lDPqOofATjrzurbSRuv3zrP3qYOyQK2+pHw7hUSr5i4=', 'ZO15jypeQbTf4WbdZoyeQ+PI5xb6WJh6M38wZjHM5zk=', '4D8uK3Ly6tm5+1tYVmrHy9eVdVWharCiZJmU2n0NC4I=', '4gN4tWOkh3sF28rII9jNMtHWoh+FExEWx6AVUGhYtw4=', '5clo5pwD7olLqF6nq2X90WdLoPze8NTRYogulTXBbTc=', 'bVz4V/3DKjAJRW9HXxH8U/wxJbBfSY7kiBAX1VJs+7w=', '/+lM15tgfg9cs/PslAZKxwiIKGrcvk5k2G2D7rPao1Y=', '/+lM15tgfg9cs/PslAZKxwiIKGrcvk5k2G2D7rPao1Y=', 'VIGPiVOYjf/VtZYDlPCZqLpfxXaGU4l8QpxYqH4FeUw=', '5clo5pwD7olLqF6nq2X90WdLoPze8NTRYogulTXBbTc=', 'yk62ZzRGHM6xSReR7JQEVa1H8URjuN+V104yGSRhLuQ=', 'QDAkunXoMKR2kga7jz8zIK67DBHKvelVvcNlCVxQDV0=', 'QDAkunXoMKR2kga7jz8zIK67DBHKvelVvcNlCVxQDV0=', NULL, NULL, NULL, NULL, '2023-07-27 23:10:53', 0),
(50, 'RnNl0lXfVm4OIJXeV7MYeQ==', '$2a$10$Pk4uEfGxoK6/SjwRbygQ8udVbCO7PnIlDqIf13jmPhQ.DAFH7aK/.', 'ZBv/XFZhbd1+R+6sVuISGPICEHQRWC87UxI2aC5ttCsMRCya8brUz93Yn7qnIcah', 'uiKwcdo3/YpuWOr1lRKsfFHZLt9xeJPZUw+ZGL3luGTgS0xwb5MiLp/9NqvHCyqg', 'mhgLVyNINFg/ii/nKl0isQ==', 'WzDAG0Jsv3sgOEmzDFjatDFQn/eWY6u3JTrEo1iWvrinaGjl4XWudNYAYFfQ71To', '$2a$10$g07n/AsvpXzugr9jeCAhpOMIR/ufO.WMvjmw2E6juGYD5iI0rMihi', 'f4ooDBy8L7FmQtbzt+MXJ3BmDmNSES5V6JWogQXi1vo=', 'IOxWMfgO40RCBEpNQ8L1eJ4W0VoqrsHF1EG6lsC1hww=', 'rt/53LMlYi+UVqUFXIdUyvL5qZ3flk2yeiJ5sPrkZqw=', 'H1l2inSUBjqXQe5j3uoiw+aEUP5EpHJxpeMUuh7j2Eg=', 'qMQaZi8nS/gUefrGvbDQU2ZAAw8/4VbujHyCS7DB99A=', 'wVcSUPQRM8hKcT3eG1WF2vF4S7TLRcBoBnH9NLwtOrg=', '1XfumpEG1WAsTHUWXw4vtta3BW6DKJVL2CmAKxMHVhA=', '1XfumpEG1WAsTHUWXw4vtta3BW6DKJVL2CmAKxMHVhA=', 'rrBodBaMzYqBh5zWE/6ba9TeEzogPqnlhGtjaI+Si1A=', 'Zm3vF/cy2P3B5DXlMHMen/cL+S6WiNxdEd9e5XU5IR8=', 'MCW8OwlYTkWEQmgAvXE2tKKifawJXq/kbbtFVix7P9g=', 'c9ZmtdhwQFJ55zP4KE+sMSQ8+1GlUHJrKQCQpMmqkfA=', '1XfumpEG1WAsTHUWXw4vtta3BW6DKJVL2CmAKxMHVhA=', NULL, NULL, NULL, NULL, '2023-07-28 00:26:14', 0),
(51, 'U8VphF5aTaNv9o5zMrRtnQ==', '$2a$10$tcbwyG79D/0jXRTaGduGfuOIoP921H6Yo797Y2Zbtk7fm/.KHR5Xu', 'c3V6P+F8LIpun7pNHcO0t2KK/SFQq7LeJi3napNqOgZAJf5vc+dJmHpzHIvbid8p', NULL, 'TfJO+0l25wUdaYmQJVLQzQ==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-28 00:32:47', 0),
(52, 'BWxpfTAy5ghoVMHX5xWxaA==', '$2a$10$6hsdGOB1h9L.60h1b649NuyPrSQmuhg2cp9KyU.tkYPvtrhPcISzq', 'GDiv44jCdgNlHGF58we0F9mJ5CFl2BAs08Y8K2yPkTD9Hu3BArBTmU7mqhy+pdnO', 'C5FUZkIapQzQS6/W3LPGxAeScXfrT6KX46t+64GkKVFxk+vtvuAp04Pw96YP2U0x', 'M77420H9ebG1CgoyTefSAA==', 'jJXseeR1xAI1I2e+N3MSWEAVFdE+evzu7D29OTFQoLyU29A5Lb3qf8a1k0dykInO', '$2a$10$NHJycTqvZrD1sbYdxUuTX.4KPJBWwghzUazNvfcAXzl7hxy8hzINu', '1IiClSL9ah4ILS8sytssGhCDMHNTKzcUCJDnlkrDU8A=', 'cgsyUYAXIcazjURTSkCIbdG6TS8r9B3DN8elWtiwqBE=', 'bnC2+dlYNHHCbB0vgYmMe/XkdupBAJlSXC1SbhgbxSE=', '+pAJKVUEwBSnO4yQKYr38FjLL6yR3xvWMmL4STgnbYg=', 'kthq2ayCl2mc08M/NOZkb3z1n3RBPPEcjULRbcObuMo=', 'Vp+f0VZz7aIO5edZ2VVNec5BIv1uVVhBwldUTqXtrW8=', 'eLGC/HjHCMn6W7sBEIUeO4ZiozizOnUGOjf851Bq3Mc=', 'I3vPyUqvwESx0AiAQaCsknxiuC9VWWRBrnXxUwr7+VI=', 'Dk95Gn217UZdiHI/j7m26dGQ1J9SH8QiCfJTfFauxLw=', 'UEee1+D/Zee+9OYwUp1ZnGs9wSiKrOdqGcq8fI0yrdM=', '9Ct1Ns+R4hp9ZkZ8oJGaPL2dC62ON7Ug4xeX398QFHE=', 'eLGC/HjHCMn6W7sBEIUeO4ZiozizOnUGOjf851Bq3Mc=', 'eLGC/HjHCMn6W7sBEIUeO4ZiozizOnUGOjf851Bq3Mc=', NULL, NULL, NULL, NULL, '2023-07-28 00:38:11', 0),
(53, 'sx3IXhNV7MSLcpMUhXIgKg==', '$2a$10$HyjGOaSBYBB3gPUgAvEBceaf/XOJ4/NanyuzGmmmOFH84hLf0sWJa', 'fcC8yvLkK099qRkbk/Zq3m0Eh08Y26dtcjH9Xl+zTzchFWnhc+pGPK9DzjypuYWv', '9yE5msluY8huEjY6m4n98kXCOzQzfQTG5FJARtm+8zUwsm+ovn/LHKwhJjSgixoq', 'zy56xVLEkh6CCtvzYAHcqg==', 'v4pi+JdEA26oc2Haj2vc4/dKboiFTVbxtEJgIsYgmow1LjzPnpYXHLUzyWSwhibX', '$2a$10$5YIaDTRlnZauaWL.cHKiJOnNWYhjdVPC3xFs8oK1EzWe4oTAqZOHC', 'lD+eSYjh3opHvdc9h4c7gz2jcn7Pv6kDrCN2TLfe49Y=', 'zHYl7ACvRGYDjHGr9t/G7LngcqM934UCxdcBPX2qonc=', 'sD7fxaOo80K0bC715SsuLUydgTasO0PK/i4DcbhSoGw=', 'z7vkc1C9XAYJmx1HkPkBT46OlGGOcD/7FSDTMTi/AOM=', 'sBgix298scOVoxJ9ZXTBTo3Tf2lmT4LcaEfLWGztlmA=', '41RQYOEXi9XpNKt+FaHhGqevPe622cIXJu5s58+8V0s=', 'y0c+8uH9rRqIzj3TV0l7bRODEigaOcywqPnfg7CW0bY=', 'y0c+8uH9rRqIzj3TV0l7bRODEigaOcywqPnfg7CW0bY=', 'Fov9vGqaDHEqDQ3jkydTk25zDPG9LOUHd3XpoEUbzAo=', 'hWmCob/DYGzs4FTi7vWuln64qrzYfazZh4Se1fD1LR4=', 'ifL0ICiqNWG6HM/CEkhlWDs5LMj8FwTgWXC7YUwPYDk=', 'y0c+8uH9rRqIzj3TV0l7bRODEigaOcywqPnfg7CW0bY=', 'y0c+8uH9rRqIzj3TV0l7bRODEigaOcywqPnfg7CW0bY=', NULL, NULL, NULL, NULL, '2023-07-28 00:43:52', 0),
(54, '6uddDQx4+r4YMNQv+Vy6WA==', '$2a$10$MkCOCugu.kagrLhKJH2NDOInoe2i0xZ5CvptTaBbUeuNmfC.Aw2CC', 'oX6+PpHVeBrKDicd6CRN5SK9iD8ZwSoR8iMX8GauXJNPrcEGiwcj81qsQpnuEXmO', '+eqHOzSHK7D2Rz/htsoWeiZrHOFAPd0hgx+gdFuHZoHDFqJZ+/rBM6tUGTwLoMak', 'SWrip26BCzp4fGSzgUirTg==', 'UI8wMjScT25sYqRFNtC1PbYghov7+JErnbxxuYPJGi7gI3aNirPwyMOzTyqiCXZ7', '$2a$10$RbsW/etIfH2cS6WY0mj8LejqyrUPG2qj26oCTIPuUmMMxCFFqB/FK', '1h2zfgNSKQHb+LfQEOaIkU29jlfIXFxFpHrj4Mkvcqs=', '2yUqp7D18ocUp1G1ndoksqlwi2DkwczYXNOHWqMjkxM=', 'kciGWCFB9cJws5wcVyC8/Y+cFnoDVjI0JYmXwbIVnsc=', 'Xips0upsaXV+wZJJW2XBvsEicoQSB9Zy+vDIb56iFaY=', 'l+r50ScTEkFBuhNA54GCZikPBZNbX5b4uLW3kTx6PqU=', 'Kif31g/3h1r6w1VUDaooG/e5YTLIbueI6r6YUjiYE/Q=', 'VrW6wjcQH/14VaanJL42EhO4VL3tNh9R2gp4R1UWmv4=', 'VrW6wjcQH/14VaanJL42EhO4VL3tNh9R2gp4R1UWmv4=', 'jOXG/0ySIGd91Ou66XQ/6AdcoOHg9vh94PLMqEFzkpQ=', 'TSx9Mqw0v21HkW1qf3SeUp29po4NyXh2y97OCoNJYpU=', 'Hov9RQarO6omzm3fxRyUaG90i/9YGeG3rwsfziU7aLI=', 'gJz1aBhdI+4pKuJfIZryqt3mejjHx2OUhgYfZSJ0Ef4=', 'VrW6wjcQH/14VaanJL42EhO4VL3tNh9R2gp4R1UWmv4=', NULL, NULL, NULL, NULL, '2023-07-28 00:48:58', 0),
(55, '3iKwe7ZtqPoWF4LfaNQNGw==', '$2a$10$a7l0rB5pI/nXvKols7Erqu8EfzdoVttJgru61lkeixc8FMUKIcwVy', 'qtmzx8GgEA8wupc6glXinTjzKQavymlLQthhZi4W3F9rtpgWEMnzMY9Pcf9EME95', 'yCl0Y/8bwccm8s9h9wZzPyBK8ul7OFeqJQXv1d54vwyCbeFhDBiV4eTLSSSiPhVa', '6mqD4IGOlz4BOb8+yRiVqA==', 'yuK4fkQEL6Z0CiyqvwC8XdbtkHDHRq87OonUwD5w2T42wF8glIOLXPvgDChHtUOa', '$2a$10$V9GhGkpCd7kfL7TGY2GOs.LI1PMcAa7vuAXlGW2oqdk8V.1DSWLh.', 'mMDQfj2z2uAl2wkMrxWGUqH0p3SvQbeQfdnez0oI7po=', 'g7+GQ8Uf9Z4p/BVgqjfTVjUZauJVTYzQgpFhPByIFPs=', 'BNhhp2lHZpbosPW07vKDY48/L9k6hE8UatmXTJok3VI=', 'nb4A+RG/Gz/y+pqs8645t2KbMz7RyilEdngWefrAeTE=', 'UIvImESYconwH+RDpTR3kvym7uGaMcoMnpscRPOXY+Y=', 'rMdKUA2wiA8kCignrtZbllyUlA3j1cMP08H7+m+skWE=', '8kb4y0jyKE/a8MGJlWd+P2XhHforp1jPr3t7OEcBmqM=', '8kb4y0jyKE/a8MGJlWd+P2XhHforp1jPr3t7OEcBmqM=', 'NtopW8qwYzLjd4ufPam7bxHjie3Bmwl3EC4+0p+BCJk=', '+DJJM8SDRcFvult1BTnR1ie4YNUsBnK0M1ohICCRkz4=', 'q0KhLNwtRU1F47/fuxscYWZMCW0byhybwMtU9nFHZdQ=', '8kb4y0jyKE/a8MGJlWd+P2XhHforp1jPr3t7OEcBmqM=', '8kb4y0jyKE/a8MGJlWd+P2XhHforp1jPr3t7OEcBmqM=', NULL, NULL, NULL, NULL, '2023-07-28 01:09:47', 0),
(56, 'd9HTL2EP3ZxgVaT+yvWQZw==', '$2a$10$NbNLoJGrnRlvS/tkfCg/7uwSyKgMV41pyP5u6QCoY028o/3/QPSxK', 'J3WGBbqK057k/PFRcoj+2bAMJGMcCTWGODAKkB+NRjGN0SJ196aSgGuNm6YpIWHe', 'loPZDftVd4NGDIYVrvUmtEaeesWbESUPPiyNJ5s5GgVjEyHqdGEnv7PUQhxGVWn/', '6Xut4jlsgLaZiKi4wZhJoQ==', 'pZJziSgTWLrjhsWo8jXm6g4CGUC+69xKJ4iofcJn8AQsRD1Snl1CwV1Uzu/dQKvH', '$2a$10$7cQUTt5Qah8rrAQWqX.nteP.K2QmsNLtWOL3ighCzhTfl0gGTzalS', 'Uow3suLENLX2wtKQkYi+YTGc3hOtNwDosmkrjQYjsSk=', 'K4KL6Sk9gjXR2AZF2qH64YeMmglggvIN0RUW+aBuKKE=', 'CsZqVMIpNWkEXlg1f66r/XCjFi/z2XNoLg34A6VopTE=', 'smTOoSPRhKibKRCHiCTGqA+CQE6a9FduYMCQT3JIbR4=', 'GS8QL37rDxdIJIiPDDhpX3FzNH2MoWvRZfOa/0Okvlg=', 'pJURJiUdrE5AGZRjSH37LLmtOrWGqXEoyAi1kgxlBW4=', '73hTWqNMHCRXZexoWMrm0mzF3rmdiq/d8hX3NETI2sA=', 'wCtQIF40GP3i8JolcWhj8JD/ZgmrdUFJ1p7MKQUGVOk=', 'jHuSvtmAzXdTACuar9wPeI8NU186t0pbxcTSi9lgGro=', '2ltEq+PS+lmFcOdF6EIvaPVcf0XKxsywRdAqew7UGrM=', 'Ja+qZyQuAMDU1sMOHy90INm0Pd0B2SDwkKO9Ot0oP80=', '73hTWqNMHCRXZexoWMrm0mzF3rmdiq/d8hX3NETI2sA=', 'wCtQIF40GP3i8JolcWhj8JD/ZgmrdUFJ1p7MKQUGVOk=', NULL, NULL, NULL, NULL, '2023-07-28 01:28:18', 0),
(57, 'u3JZEtDo6Mhm6hDttudtLg==', '$2a$10$ikOd9FIoEIgjxjBdSGfTGuPmI76VNF8hx9SA4YVFZdXW.I63PfpKa', 'st7GrbYyIAf/dTPSlCBa13YST00N3r0IM6vThmCtMJGUu55TiznTmLQeKIp9YSj/', 'BTmg8cintPy5FjX+4B/wzJuBxE7ieig5DEwT4tfsvJnTdtO7o6HznuLkwQG8CI4W', 'S3Xx/xcZo73QH5WmSPSyAQ==', '6e1onDfZGGX+m3eh2FtB95ga4IGLQ+FZ+zd62+7DBXEEadOJKgxWOtpJjxyTkyS2', '$2a$10$DUPt9nSLrIyvzg2SYi/Jj.ndsGx.dXg56FNb87sQ0fQj7PMnyBDQy', 'ujnei/tejpaiBgT8tXRCX/QRS5wtYjp6dnzZj4X612o=', 'l03SeN2Yv8VAQw8vzm0unFyuadVJHe1AT/u+pYXHGaA=', 'ITaBPQhnpVevJAmazdnEep45R1l/ybdFqEiN/NrA5lI=', '1GFCLi4mTOAq4cx/1Sa1IAq0E8xm6g0x4K2qIR8uUPE=', 'J0P0ku02ihycNnfzvWrw10qW94ckwsySvbpxydNdu4c=', 'M4UNLncQT8mV4cL54/kypepmN7X5YSu1WmS1Ze4W+Bg=', 'ZUGqcgMZS4jgG7+Y4UEVtrBFd6z6YPzrTCtvZp7fpiA=', 'ZUGqcgMZS4jgG7+Y4UEVtrBFd6z6YPzrTCtvZp7fpiA=', 'y2ii5AFc9hqBrJS54ZDCV51maAfgxLq9KVxPOZy0aRc=', 'zgvE8fMHtfv/8l0RMv1Q+RJMbKyKqhLA7Djywd7qeKA=', '+YK939wjSRbV20wYcP8yTZkysf4NHpnPQWKFGCmt3Es=', 'ZUGqcgMZS4jgG7+Y4UEVtrBFd6z6YPzrTCtvZp7fpiA=', 'ZUGqcgMZS4jgG7+Y4UEVtrBFd6z6YPzrTCtvZp7fpiA=', NULL, NULL, NULL, NULL, '2023-07-28 01:31:29', 0),
(58, 'xRUHdkDRETvIJXusfnBfGg==', '$2a$10$x71oHh8Ii4.xx.SrTjTHzeNAQaP/EGO2kpkWOeDe2qlPvLyfZ4iDW', 'DbmHQ+qYOt421M0CxKfJLpgtnHyqCSe8GlnO2KsPmDb/5305E6zhOfuAUYUhNbVV', 'C3EaiZXsHe+RDuuQpym6bMYSYLmm8XJKXUrrHjiWG8C9vCTsp3U/Ww7aXohWLixN', 'NfWzdiF6WPLYlw3EzXbk1A==', 'BjA0HfQjy5dSFCiTi8cCwQqWgGzMSzmnrvQRNs3LoWFem6Im7+QGCtovnl7Ymoww', '$2a$10$pIosWsC.oohQEYXsQ3fuqOuXsBdLax/Ao3j4dglq8xCvUcHaC8maC', 'kYIsr9Y37JlQMsY1QrDY35ucQWEAk8Gz5308gMVubKY=', 'F4LrBKaDBjwBSLSOnnX85drSW67co5yO+lVcD+FIx08=', 'TE1h/K9JgiTXb+99r035c3HkGMpERYnw0ggS2pOzNXc=', 't5lk9obOtnuDZ5lUOm6FVVqVIeLrOZxztT6VTP0uC9Q=', 'LSCgmbAJjjKWXax+uV9QpKgTJfVYePgo3PyudoiukW8=', 'YzC/F9ElxDtYJ0usaVb8xChC5z8zOq/SJcA7Gd1YUH0=', 'PkNn0krT3Na16oRbviZB0z/41Rl7oEGtRdcJTbyOtjc=', 'PkNn0krT3Na16oRbviZB0z/41Rl7oEGtRdcJTbyOtjc=', '3DXAQHPEjE7QvO80iEqANrVA1Y7Mgyfsej5yUcWGAng=', 'mpYM6bDXmmtJZ22Jre96B83Uk1ak9MxfgaK5LBRQ12w=', 'dXatJxwQvoIl02QKf4uCbPuFWnPB6PEgreANJYrMy6c=', 'PkNn0krT3Na16oRbviZB0z/41Rl7oEGtRdcJTbyOtjc=', 'PkNn0krT3Na16oRbviZB0z/41Rl7oEGtRdcJTbyOtjc=', NULL, NULL, NULL, NULL, '2023-07-28 01:35:10', 0),
(59, 'FUo1sLTCXE5cr4JHJvxEXQ==', '$2a$10$Gd6O7W0GKpBZVXsjHKXKVO8CBUvCJUwxrydhUMfDbxl4zKyeHmNpi', 'thYewvF8LTfmswdASqjfqcGsXvNrf5MhGPXyVttIIJ3AsqJYpanqBjYApZMHEQqr', 'hHZ9cJFBIhl9+UR/KYwWrmx8RSVqJDBM8BalEhidIQvbiaLLtpRZmkLjNt/D/qRX', 'UMEoH25LudN7n0NLLYTV0Q==', 'u7obGzG4puLQg5i7RJJAKwaIaxJ0pIDdiClj9s3OTs3c0jJTk+QXxyOWRxguTYuF', '$2a$10$phb/7q8Dyox1HUet4tCdZO0LTyYemgsWNOysjr04eN22o0MhmhlBq', 'iCZhJldZqZ/c3tK/jO3z+zJPsESH+ZMeUUB5rzboWZk=', 'ZhV98auMWpxuDxDosEiN/4TQthtm6jlgIwbuJeqBRw0+Z7frLqgvtlqCGEPANoD/', 'FoVNS4dZ5KP3Xz6cgjq/5t7fd4T66utfYGcwp9wUGhQ=', '95v0J/Y7Tq4zCZXvCmJyNwKdGfNHDejVbLv0warGGJo=', '3/A9a8AfholQJfhw3Eu7aeMLyLrqg40Q5omNtSxPozw=', 'G1mLVY3NLP1VChYk5zcpArV/kFo1eOhMdYeJ6LLRSq0=', '4vC4RHtJl7BK7b4XTifq1PDmZoGgEGU7DOhZLL1jGJg=', '4vC4RHtJl7BK7b4XTifq1PDmZoGgEGU7DOhZLL1jGJg=', 'ZpDd7iB448kcFvmfxw9a5CNegxegXYivXv6/d+YtsRE=', 'vVDw7Pve8w5LsCJYhkb9h1rnem1OFv2dzeN6fEWRPmo=', 'A6HylDhcFmNJWUW8028TKqxwhRiSZB6JHSOxoCQzCAk=', '4vC4RHtJl7BK7b4XTifq1PDmZoGgEGU7DOhZLL1jGJg=', 'QIF0QPuT+g/Qf7JFGqaCW90TCETeoNONqtqwJAlfpVw=', NULL, NULL, NULL, NULL, '2023-07-28 01:43:52', 0),
(60, 's9P5tkG/wNWT/CkSa6DRcg==', '$2a$10$OlfrIquK4yhtk3mYOV4vtuqPntb2ApH6Mfl24u4C1WRz8nj9JEFQK', '2CbbKRyWM11WRXZt63d2Vx+jqMqzoKprU3pkVhqMm87aEq8yOzSsjw1YhvEJgTdD', 'GpEZ+3ZgAT1pdo+WCPbulLVIGPjHUoKvw/h5Kmrq7eoCaowkrMD8nrFnV2Tgt99i', '/peURZlTiJJ6uKHNHMmYhQ==', 'roZRYYMGQGLZA7oyqbwIewcVZIj+WKCer/XEjDhaib731zWy3AFDxFnhaiXRe4Yx', '$2a$10$6SufjxxGS3k4oFkn17rg4ew5.K8pvBZ668eNyOOki6XQEl/Ru9DT.', '0tN5gIVbAVCQmyZywNyo4cvbUx6VHqMoEXioYk8z23o=', 'EFY5hvJpn8jw+dJbPEEO6Mg06k+5CMod18KCbjoqscw=', '6ingFgpwbfQQ2LSrTQREHWcdbl6n0AKs+eXtJ+lHVjM=', 'aSEAnpbyzEfovgcgDVIPXVKSskQuvdwvCIJw2SGhaZE=', 'ptEUt3t2ISEZc+Qfb+TF1t+t4xzgW8GZ07CfoaMJ90w=', 'oOIkchKEKGKNnT4uDld2gBLY0bfCyMWFrgMv8n3Z4EI=', 'Nn5IC7PM/wDKOHkjjyl+e7+wymwrqmGOW77MoQQ3eQE=', 'Nn5IC7PM/wDKOHkjjyl+e7+wymwrqmGOW77MoQQ3eQE=', 'l6Dg7RXBujnct5bsLj0d9upEz0/4Me8A76v/XkyjhUg=', 'RHskKIwHTNsBn5R80a9skOemGDbIPbwiK4EW5761QHQ=', 'HM3v4CEt1b1I2LiWm5vilnUmgijDRllzTM6yYu1dYmw=', 'Ub0leALtkZ1Jn/WJgMv3Vsn1JI0v8wjrtVrobEMmEWo=', 'YPLFoZYSckXGVDLbMH7W1E8WIFcFiud+oeEf+12hoIU=', NULL, NULL, NULL, NULL, '2023-07-28 08:51:21', 0),
(61, 'UKYHNKo9F8q+C1pgF91bcg==', '$2a$10$h5A7mtC3fwYujCoWrQLt2OvIqcKqTRyJr87Lue0.uJ3U.K1QqoGkS', 'Boi6fcd1V83XHLHuJWh7gEs1tZb2iuMFUjUCIStEB26uMIIe7ydtoZNWGmoy55UI', 'KPDObinPBumBkjpfOUT0HaQN1Z8cy2FeYHTBjT6pUKdnBumawSv0rroxF6z/aYcw', 'Wv9SPcE74MA4jV++aXtJng==', 'tFQZzzfRJGTY7+OydjNZghr5ajvvwmXqy5ys1KR3O0LbpVs9xVA8o46yyt8ydOo9', '$2a$10$AlrzRZuk9zJzJkz0iHrGxO0ZZnuZILpn7JlfBwdBO.JyKccNhoRc2', 'D3nN3MjczVBhUM3Yg2zXavmYmQmwvNFsVqhzslN5NqQ=', 'bkAL66ySrFNtUXLZDhO+nhw/H5Nh8x0oq6KMKXV3NSQ=', 'htK4urQEZiwUTBydICggUDVbzohpCrI3pwg4/cU1aIo=', 'sW5JK+zg3wZj5C8YY0VlEtRwhD3Nb7/oCH9iUXQZxUw=', 'SW3Ymvudzbem+7z7A5ft2JdFERaBti2NT+1yjoeaYJfb0e14PVtxjybqMkbDKJPsbcdHcKealcXtfDhZQVdp1o2TiQyocXy896hWCDkoVzQ6BmIQBVegvqWQuNaWsyx3NoJz8Djq/vUIgOjQvuHnqUSTLbEuAUjpUDSERU4D7Gx+Xhoima7BZvLlLaSuaMYy/mgsI0XC4T6OmWgGIwcmcg==', '4XN7tr/0qyTznB3CRNtPbE/KDk22LQZintspOSsdaUY=', 'AjJecldcUi1DAX0p2pPZAQkhI/NZ6zqf2deX3wCkBoI=', 'AjJecldcUi1DAX0p2pPZAQkhI/NZ6zqf2deX3wCkBoI=', 'zVQMOKQTt8YLQM6O81/pzcdzwz5uGJaxXlNBOJ1eDik=', 'xyPOTNTi0HZEPS90p9tXjpkkART/QExr04gIaKYYIrajSKYeQG58yzXn65oP8DB5', 'STlpIDF496NYOtE6pIbVFpObClxR14mdV2+q8Pr371U=', 'AjJecldcUi1DAX0p2pPZAQkhI/NZ6zqf2deX3wCkBoI=', 'AjJecldcUi1DAX0p2pPZAQkhI/NZ6zqf2deX3wCkBoI=', NULL, NULL, NULL, NULL, '2023-07-28 16:13:16', 0),
(62, 'dePJVc+cebJ9Wn/bFqMYeQ==', '$2a$10$7y0xoYJ88IF/WpdqfphnYuNIEWrMnyn5J7O3sakA2/8Z1zCflROuq', 'VpsFeLmnkPbggbfbIEZCwx6hsDPPLQdSJFo5QoqKbR2+Upaf0L5iaofYfRF+wR2O', '4rGXlASMVNQEnbSQgL6rPodM9KnkDYro44hS2zFWDeNPV/kkiJqNqOd1MWIj8LYs', 'EZCmZjl/y4RwaH3Izlo4lQ==', '4+f+ADAhDLKpccmofdH6YjlLAc443KRVeZF2Rvg3DdPZnbqvXzeFCL7YmG+6Ac4O', '$2a$10$L1S.oR1NE3rpFIKgmjIA/Oet44pR5.SlHVP1SFoAN/Yu5QujNpCf6', 'DHMdtak9J5D/n6w/8qkZf3EMj3ytB3nWE20urBS2FIc=', 'j6rW7Gi2GIizAwCj9A4peCmeqjDy+AfeG81wI+ajzhQ=', 'u+/24U0Z7o57lzvq8atYBFdvGqB+rpuKNBi70ZHdmGc=', 'i4rEfk5/Z9aVK3TSpOKFTgnkBW/ImRyuVkHTpIhnlHM=', 'SE33QKNDqmEI23WitbxPAAXYpC/mHZIw2QfUtwIvLx+wqs/X44zW8Ox0bkTUhhMMY36/sDtYekyDPEeraXTLiSbzDbpQA+trDrOdnLaHbzI=', 'YSLTqU2BktdDSCc0/MaNAAoBzereFqXO0Qq6mNvSiBc=', 'cPnHLASVZ9FdVvw0WSpDb7k/W/zmDAyos1sGr76Ea78=', 'cPnHLASVZ9FdVvw0WSpDb7k/W/zmDAyos1sGr76Ea78=', 'RHJeHKt/RVus4v336Tkrr/IR0gTPXElUS3TMxG+7EnY=', 'ugHVqsx5p3PYyrjOybFr7m+8iPTMyTUa8L6RvC9Weo6aHKragyvy9URyLt1jEorB', 'xwSqi/pgxTHM/qKI+1h7z+LfbVvIL+GT6Q5KZs+DqG0=', 'cPnHLASVZ9FdVvw0WSpDb7k/W/zmDAyos1sGr76Ea78=', 'cPnHLASVZ9FdVvw0WSpDb7k/W/zmDAyos1sGr76Ea78=', 'DSAlU5Rwpx+ivEYMSYqZ4N7CXBcWE2wGMViFDq1pSPU=', 'mE09eLEoR68CQ4vdngrINCR64Wge28OesJ4oiSU1kso=', 'aDuG5vPUg7x2002t54oTItSqVRIYwwB3nI2o119jSD4=', 'rWH/v/1F5K2GyN7pyUWpozmtVyb47V+9eBigJYSbbVg=', '2023-07-28 17:52:43', 0),
(63, '0vJj7Djuo6Fk/Ab9dtM7vw==', '$2a$10$am2LVZu4yADnWUjmpHH96eKDcgkzXpRLUJQEzKOlSZTJSKBpDklwu', 'QOJEJ5+61gc4I3Z6YaCG40Wd1LB4ajsn4EpKfzx5tQRphNZdhdDRaJxSA9iz4LDE', 'yLt7vjLzUy8zBPCrz3S8sPfX5pJh5rH5gRk/3bsO7rSzBafaAAUKCJMkFK2Q+bDK', 'L4PJkoTC7rXtOfk6Wy5DaQ==', '4Sk4ldb5wr6+YS4HqutJ4n5HRSQ15VDoroRQ58pwViQZlyeFo1TR+7megG24JwOR', '$2a$10$YUTPDwzvwpXftwNI2iRGOuiGcanJ75BL.5vk0dbfbDzUGIODj/81y', 'IIPbbhlCVJNK95n83h57tj+0YdrgfGiVRHNTZ7tHDuQ=', 'DUwBTDe8WDof3BE7aQsjdCYaS8AcqfrphGD9egGli2c=', 'wS0gDgJyk9tILH2BZpnZ4xPndF73HSVbUvW/xHfaNrA=', 'oJkOb8qzvetc2abg8XiXFAdsu8mtoiwbvPJM55kSVu4=', 'zZgk55telkGnbIgp5GccLs2zWSWmhI6YL0q5tVJ+nUwtwbzDuUSEvOpM9M0opDk4D0eUsE4/0kdkZTgonrXsAT7ptxtVlb5PrN3Ou8qPIxg=', 'ipUgIggII1qho9PmUdx0InOzAPn4gyML+HEon3hweIM=', 'BmyZ9o8yUt0MD1Oy/RCNfxD9/12d7LbijALyIL+X8rU=', 'BmyZ9o8yUt0MD1Oy/RCNfxD9/12d7LbijALyIL+X8rU=', 'maEiazARpeuikX3I7HzdiHYDv4swfAucyb7E7qnNaxg=', '5Is+X8YtyGo4ciR3VNqoYVPfhulagr+2ida7UFun41UuMUi6DYg0ybFZGHQkHh8i', 'GZc1ji3YYjyb1oXqFGKufQe+yRxMBbvDXlWKdY9oiIs=', 'BmyZ9o8yUt0MD1Oy/RCNfxD9/12d7LbijALyIL+X8rU=', 'BmyZ9o8yUt0MD1Oy/RCNfxD9/12d7LbijALyIL+X8rU=', 'GsALegcs6NJe/j1iw1vO8nZFCLw5+bEIPENJHZEAvg8=', '1bTnEVCentkUkV2BB3ARCgdkPkwVEfsbZxsUnLfZRuRMtIa35alMcptWCscSd64S6SUzgFXPBIxvT7inHznSYe09kVI4E3rZSZFYzUBHZUEQkbiV1ZL+qoBauq12wlz9dFzrGnBJf9sZmT7Oigl8Pw==', 'BmyZ9o8yUt0MD1Oy/RCNfxD9/12d7LbijALyIL+X8rU=', 'KIx/bfW3R4V0yzEckL2DuApwdTtDsDVzlD+qLeNLNfI=', '2023-07-28 17:57:30', 0);

--
-- ダンプしたテーブルのインデックス
--

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
-- テーブルの AUTO_INCREMENT `operation_logs`
--
ALTER TABLE `operation_logs`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- テーブルの AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

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
