-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- ホスト: 127.0.0.1
-- 生成日時: 2023-07-20 07:17:21
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
-- データベース: `studentdocumentationsoftware`
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
(1, 10, 'Create SecretQuestion & Answer', '2023-07-20 11:25:17'),
(2, 14, 'Create SecretQuestion & Answer', '2023-07-20 13:42:00'),
(3, 14, 'Create First Setting', '2023-07-20 13:45:10'),
(4, 15, 'Create SecretQuestion & Answer', '2023-07-20 13:50:13'),
(5, 15, 'Create First Setting', '2023-07-20 13:51:12');

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
(1, 'test', '$2a$10$hsEVdcN.RUjEVRshF8OBluFvVkE4gHhtl6WParoMRl6j1xRIzXXHq', 'GfrQllYvNCbrEqQrsF49Q2ePLPtHQuO5KVEpkmR7+DM=', NULL, 'MlW0dVsXxoOMyYwOlUrF4g==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-18 00:00:00'),
(2, 'sample', '$2a$10$/B7rQasV.UvYOQltzYS1X.AxCyEp3n0TBLurPGtnjAcFuSIaK25fO', 'vVwC5aAhQfIaaY28nl2n/vFSiYDiUWoamVL1BvIDIlw=', NULL, 'gH8PZmeKwmdSA/lx4lZQxA==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-18 00:00:00'),
(3, 'test2', '$2a$10$tAe1C/OzGkslm4rBSKl.DOOt.QY3bV7KzO6ePWEk1/IvB/70Wv/u6', 'MWfRSczC7mLVpFlM5ZpjMMKzyUpfulTVpP4sL3WPVVc=', NULL, 'KYqpTAnsY4TERo9WTr825g==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-18 11:27:58'),
(4, 'テスト', '$2a$10$jMf7QcKkYneybobRZxtheuGBe58hvkGnbxlJmjAx60RkTYyfnU1Bm', 'PH11Nd2jW4npd0ASvSjchkjSB+CQu7Uxt2Xz3/GmCyc=', NULL, 'B9mlFo/88TkUqntmczc5Yw==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-18 11:46:41'),
(5, 'aaa', '$2a$10$aG9mmNK58Kzi63hPED7XruQd3Jap8kqRr4mlYLge8BRJSGC69iPTy', 'ZIpoOTkJAGHvc2n1JkHvH12+TjbSxfq8aMpPPRhjJTI=', NULL, '4IqjD8JhEYr5axqES6ht6A==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-18 12:00:21'),
(6, 'watanabe', '$2a$10$SIwMg6tq2kcgBojz7jr4WOWKawVc03ogpLIBYuwhd00S4c6Sj9/XS', 'GXW9WaRArwj/beP96etmRvJGdYPwnpy98f+xm+GSktQ=', NULL, 'yJp3twQeB6SSOZdWA2Xcvw==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-18 15:01:02'),
(7, '$2a$10$VjOpaI.YltIeEpULZd3twe7lbCYI1U078500Sz/X558lQfvHTvOa2', '$2a$10$Ss3NIkqK58kVzAiuqnNkG.qqErEMMK1UgFXkazMn5OpKV/qWHUM1m', '4V83gNdQNawKJtpizpA2GNQNn7teXzMIUrVJuEuhdNU=', NULL, 'FtPdXvS9fZxvJgr/fSs4AQ==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-19 09:19:39'),
(8, '$2a$10$EUPppu4ySm4N1Q3fH0Z5UO2jZDhIv9544THz0reyZKkg.79Q6OT4e', '$2a$10$GRKjAJ.71k.0QaZhlVLpT.4fz.sN/o819.yLUXpmT51aaRl4sOndC', 'ye/0GQaKw4cIrCPSw7iqXBrA0DUzQaW5I+XVSg/a1/I=', NULL, 'W0kWdAuWjuZ4BifNa+uXbw==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-19 09:21:23'),
(9, 'DuRQXUOmPbbS9f/yocH/7A==', '$2a$10$bqujduXMkkYZRpt5ncQ1huLJkNUy9SEEi70XgxHhXCDtP4uHANGay', 'Vj/R8vjRgYqT6rVsATa9APQ0k3Z98WzKL3+tA+WBGv8=', NULL, 'CENu4MdLT9stVBlW0hrHdw==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-19 09:32:40'),
(10, '0ME9RYa7z5n1umV2maso0A==', '$2a$10$CIIZ2.gkpmegySyZuXHoIeGMjkj8/8EWrSdMDhnKsegbstLINya4G', '9rMpIEuMYmNPsouE/PaJVynfHw0cs2khQaGd4lmUP7Q=', NULL, 'YKlRAeFzzYWsAEMWBHnnVQ==', '5r1rf+1nWpCHEEPm2shFVLKtgARlzQ/Op+dCjpPGK/0=', '$2a$10$0mYEtxVwd8WRaP4wu0l7l./Up2K6yK6Simn8.iN8bSyft.u4moKEy', '', '', NULL, '', '', '', '', '', '2023-07-19 09:35:50'),
(11, 'YXjD5wXEQoL6n9vBNbuBqQ==', '$2a$10$SpQ4oJFL/SydsKSaf6vvG.yrhJCmFaNxgAkWpkTJCnbrxMWgNrD5y', 'c0QjXx2s4/kZni/5D2X17V5CtWWlkM5BLEpKdbuEzqU=', NULL, '+jnCLGCTbPexGoFVjEAIFw==', NULL, NULL, '', '', NULL, '', '', '', '', '', '2023-07-19 09:41:20'),
(12, 'EAV8qtZB6DgLkvnwxNgo9A==', '$2a$10$5IqLipx44amc3Mb.lI9VW.t12S6b.1IPwb90e3jNWMUNBKNRL.jwC', '5MEGQvLAggziOLBdKk9+aRzUyGtKKp5sQ65xnV6NG/s=', NULL, 'Dda/6HkTcry9BnfSCAT29g==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', '', '2023-07-20 10:46:48'),
(13, 'suXFzaTg2F0gKWMBum8uHA==', '$2a$10$gLLSNMxPH/rCqPndeaNfcOHHl/phlyM0O6BEj2OjzspFgAnKyoSOa', 'o7nzSw4HvoCB30JvnedpNROdYl2XTh4ctUQhdvD/crk=', NULL, 'X/YbEsHPvHYBbgxRUHvbfA==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', '', '2023-07-20 10:48:30'),
(14, 'EFvoIj70otm5YBgcTQQoIg==', '$2a$10$D6UQVVdkAJXtT4jaM5eV8e9ZOAs/kcmAVCtq4GsUko.zSbuabxZyC', 'z7b4uqgYPrl4rKzDy3nE+UBI5mgV3ecJtg9+Uwve5j4=', 'fq5ds8dUF010n1Inc6vaBzimpPzSLh3LPcwAIR3n4fQ=', 'iAgg9iZO0/TYOgrQjfVmWw==', 'qOKmlnuvzBByRg7YxDd9tyCsnIi+ALilhJ6/G4VXrnk=', '$2a$10$v.4n6CiMTrrEhJeiIikbQe0Ly0jEKo87XLWwsfKVx3qkPZeJTMFK.', '7DhL+E4XlvvUA4wE9UZ2GQ==', 'tXSA3FtVN+NUPj5e8/k6Ig==', 'mWz0HZ3v58PtnhxUlVDR2A==', 'Qzu3N/qpOuNLJQTNCTlciA==', 'uqUV8nCxN+rNYF78QRC/ywPVQgQU+n4qBQANM5XIT54=', 'as2ZNslpmYJbvXjECEkaAQ==', 'Urh78KsDAeNRI5+nClxBVQ==', 'vpv364VTOMQvttCec+VmhA==', '2023-07-20 13:23:36'),
(15, '2KOLkmPquhX7vZcmkU6kIA==', '$2a$10$8nedrbYSRogxnp357KDL7.WF/GYgNVeJyRqzQjyN8vVNC5AAfL0Fu', 's25lJh25zPvaXehe+ONWIhNSisPyuF8s3iVVkk0t8xU=', '1WheyV/gNpnSc+/k6s+0WgD0doieImXUeIf0UdwKjFY=', 'CEQ9wB6gUnVUU7YSeiTUQQ==', 'qOKmlnuvzBByRg7YxDd9tyCsnIi+ALilhJ6/G4VXrnk=', '$2a$10$CLg3fXxnA22LeIArFC58QudX8Jrl/T7DcdFO7sLsN5oNOmdTZcCcy', 'Br4c68SrCClWblFYkWd92w==', 'M2O404tKTxDv/uyVkYae+Q==', 'C6/Z/sxl+kXOdV95RPEqYA==', 'Q2si3SCOWL2ojgXKyMdStQ==', 'GVjLXyUkhwwkN8wmVFmCgg==', 'ENul7/UjoVLK/9MclUhQGg==', 'b7cZ/vfHSQo/+YkEqXM7Ng==', 'ROEgkf58MM8V+5gXW9PQQg==', '2023-07-20 13:49:40'),
(16, '464mf4695bxQIhvBkcAruB+5pL3D2LT8INJbaDb1rBU=', '$2a$10$TNuNGqBF/TptcZujA25Reu0XaWjMs6gIkju9ltXJY1nmIOZlKF2tK', 'IBhxN8M/Mgl6JUDqpbCqRjgiDYN3cBTeVMD8lR0Zk/g=', NULL, 'VOV6u4YpLyxhaJbwLCrGww==', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-07-20 13:53:03');

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
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- テーブルの AUTO_INCREMENT `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

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
